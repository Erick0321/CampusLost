package negocio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import modelo.Objeto;
import modelo.Reporte;

public class GestorObjetosPerdidos {

    private RepositorioReportes repositorio;

    public GestorObjetosPerdidos(RepositorioReportes repositorio) {
        this.repositorio = repositorio;
    }

    // Revisa que un texto no sea null ni esté vacío
    public boolean textoValido(String texto) {
        if (texto == null) {
            return false;
        }
        if (texto.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    // Revisa que TODOS los datos del objeto perdido estén llenos
    public boolean camposCompletos(String nombre, String categoria, String color,
                                   String descripcion, String campus,
                                   String ubicacion, String fecha) {

        String[] campos = {nombre, categoria, color, descripcion, campus, ubicacion, fecha};

        for (int i = 0; i < campos.length; i++) {
            if (!textoValido(campos[i])) {
                return false;
            }
        }
        return true;
    }

    // RF08: la fecha debe tener formato dd/MM/yyyy y no ser posterior a hoy
    public boolean fechaValida(String fecha) {
        if (!textoValido(fecha)) {
            return false;
        }

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate fechaPerdida = LocalDate.parse(fecha.trim(), formato);
            LocalDate hoy = LocalDate.now();

            if (fechaPerdida.isAfter(hoy)) {
                return false;
            }
            return true;

        } catch (DateTimeParseException e) {
            return false;
        }

    }
    // RF08: busca si ya existe un reporte de objeto perdido con las mismas caracteristicas
    public boolean esDuplicado(String nombre, String categoria, String color,
                               String campus, String ubicacion) {

        ArrayList<Reporte> reportes = repositorio.obtenerReportes();

        for (int i = 0; i < reportes.size(); i++) {
            Reporte existente = reportes.get(i);
            Objeto objeto = existente.getObjeto();

            if (existente.getTipo().equalsIgnoreCase("Perdido")
                    && !"Devuelto".equalsIgnoreCase(existente.getEstado())
                    && objeto.getNombre().equalsIgnoreCase(nombre.trim())
                    && objeto.getCategoria().equalsIgnoreCase(categoria.trim())
                    && objeto.getColor().equalsIgnoreCase(color.trim())
                    && existente.getCampus().equalsIgnoreCase(campus.trim())
                    && existente.getUbicacion().equalsIgnoreCase(ubicacion.trim())) {
                return true;
            }
        }
        return false;
    }
    // RF08: aplica todas las validaciones y devuelve "OK" o un mensaje de advertencia
    public String validarObjetoPerdido(String nombre, String categoria, String color,
                                       String descripcion, String campus,
                                       String ubicacion, String fecha) {

        if (!camposCompletos(nombre, categoria, color, descripcion, campus, ubicacion, fecha)) {
            return "ADVERTENCIA: Todos los campos son obligatorios.";
        }

        if (!fechaValida(fecha)) {
            return "ADVERTENCIA: La fecha no es valida. Use el formato dd/MM/yyyy "
                    + "y no ingrese una fecha posterior a hoy.";
        }

        if (esDuplicado(nombre, categoria, color, campus, ubicacion)) {
            return "ADVERTENCIA: Ya existe un reporte de objeto perdido con las mismas caracteristicas.";
        }

        return "OK";
    }
    // RF07: registra el objeto perdido solo si pasa las validaciones de RF08
    public String registrarObjetoPerdido(String nombre, String categoria, String color,
                                         String descripcion, String campus,
                                         String ubicacion, String fecha) {

        String resultado = validarObjetoPerdido(nombre, categoria, color,
                descripcion, campus, ubicacion, fecha);

        if (!resultado.equals("OK")) {
            return resultado;
        }

        int nuevoId = generarId();

        Objeto objeto = new Objeto(nuevoId, nombre.trim(), categoria.trim(),
                color.trim(), descripcion.trim());

        Reporte reporte = new Reporte(nuevoId, objeto, "Perdido",
                campus.trim(), ubicacion.trim(), fecha.trim(), "Pendiente");

        repositorio.agregarReporte(reporte);

        return "Objeto perdido registrado con exito. ID asignado: " + nuevoId;
    }

    // Genera un ID unico: busca el ID mas alto que ya existe y le suma 1
    private int generarId() {
        int mayor = 0;
        ArrayList<Reporte> reportes = repositorio.obtenerReportes();

        for (int i = 0; i < reportes.size(); i++) {
            Reporte reporte = reportes.get(i);

            if (reporte.getIdReporte() > mayor) {
                mayor = reporte.getIdReporte();
            }
            if (reporte.getObjeto().getId() > mayor) {
                mayor = reporte.getObjeto().getId();
            }
        }
        return mayor + 1;
    }
}
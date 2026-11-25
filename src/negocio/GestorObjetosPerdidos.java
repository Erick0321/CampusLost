package negocio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import modelo.Objeto;
import modelo.Reporte;

// RF07 - Registrar objeto perdido / RF08 - Validar informacion del objeto perdido
public class GestorObjetosPerdidos {

    private RepositorioReportes repositorio;

    public GestorObjetosPerdidos(RepositorioReportes repositorio) {
        this.repositorio = repositorio;
    }

    public boolean camposCompletos(String nombre, String categoria, String color,
                                   String campus, String ubicacion, String fecha,
                                   String descripcion) {
        if (nombre == null || categoria == null || color == null
                || campus == null || ubicacion == null || fecha == null
                || descripcion == null) {
            return false;
        }

        if (nombre.trim().isEmpty() || categoria.trim().isEmpty()
                || color.trim().isEmpty() || campus.trim().isEmpty()
                || ubicacion.trim().isEmpty() || fecha.trim().isEmpty()
                || descripcion.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    // La fecha debe existir en el calendario, tener formato dd/MM/yyyy y no ser futura.
    public boolean fechaValida(String fecha) {
        if (fecha == null) {
            return false;
        }
        DateTimeFormatter formato = DateTimeFormatter
                .ofPattern("dd/MM/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        try {
            LocalDate fechaPerdida = LocalDate.parse(fecha.trim(), formato);
            return !fechaPerdida.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // Duplicado: otro reporte PERDIDO aun activo con las mismas caracteristicas.
    public boolean esDuplicado(String nombre, String categoria, String color,
                               String campus, String ubicacion) {

        ArrayList<Reporte> reportes = repositorio.obtenerReportes();

        for (int i = 0; i < reportes.size(); i++) {
            Reporte existente = reportes.get(i);

            if (existente.getObjeto() != null
                    && existente.getTipo().equalsIgnoreCase("Perdido")
                    && existente.getObjeto().getNombre().equalsIgnoreCase(nombre.trim())
                    && existente.getObjeto().getCategoria().equalsIgnoreCase(categoria.trim())
                    && existente.getObjeto().getColor().equalsIgnoreCase(color.trim())
                    && existente.getCampus().equalsIgnoreCase(campus.trim())
                    && existente.getUbicacion().equalsIgnoreCase(ubicacion.trim())) {
                return true;
            }
        }
        return false;
    }

    public String validarObjetoPerdido(String nombre, String categoria, String color,
                                       String campus, String ubicacion, String fecha,
                                       String descripcion) {

        if (!camposCompletos(nombre, categoria, color, campus, ubicacion, fecha, descripcion)) {
            return "ADVERTENCIA: Todos los campos son obligatorios.";
        }

        if (!fechaValida(fecha)) {
            return "ADVERTENCIA: La fecha no es valida. Use el formato dd/MM/yyyy y que no sea posterior a hoy.";
        }

        if (esDuplicado(nombre, categoria, color, campus, ubicacion)) {
            return "ADVERTENCIA: Ya existe un objeto perdido con las mismas caracteristicas.";
        }

        return "OK";
    }

    public String registrarObjetoPerdido(String nombre, String categoria, String color,
                                         String campus, String ubicacion, String fecha,
                                         String descripcion) {

        String resultado = validarObjetoPerdido(nombre, categoria, color,
                campus, ubicacion, fecha, descripcion);

        if (!resultado.equals("OK")) {
            return resultado;
        }

        int nuevoId = generarId();
        Objeto objeto = new Objeto(nuevoId, nombre.trim(), categoria.trim(),
                color.trim(), descripcion.trim());

        Reporte reporte = new Reporte(nuevoId, objeto, "Perdido",
                campus.trim(), ubicacion.trim(), fecha.trim(), "Pendiente");

        repositorio.agregarReporte(reporte);

        return "Objeto perdido registrado con exito. ID asignado: " + reporte.getIdReporte();
    }

    // ID unico: mayor id de reporte u objeto existente + 1 (igual que el modulo de encontrados).
    private int generarId() {
        int mayor = 0;
        ArrayList<Reporte> reportes = repositorio.obtenerReportes();

        for (int i = 0; i < reportes.size(); i++) {
            Reporte reporte = reportes.get(i);

            if (reporte.getIdReporte() > mayor) {
                mayor = reporte.getIdReporte();
            }
            if (reporte.getObjeto() != null && reporte.getObjeto().getId() > mayor) {
                mayor = reporte.getObjeto().getId();
            }
        }
        return mayor + 1;
    }
}
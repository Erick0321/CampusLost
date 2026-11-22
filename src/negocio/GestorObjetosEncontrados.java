package negocio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import modelo.Reporte;
import modelo.Objeto;

public class GestorObjetosEncontrados {

    private RepositorioReportes repositorio;

    public GestorObjetosEncontrados(RepositorioReportes repositorio) {
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
    public boolean fechaValida(String fecha) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate fechaHallazgo = LocalDate.parse(fecha.trim(), formato);
            LocalDate hoy = LocalDate.now();

            if (fechaHallazgo.isAfter(hoy)) {
                return false;
            }
            return true;

        } catch (DateTimeParseException e) {
            return false;
        }
    }
    public boolean esDuplicado(String nombre, String categoria,
                               String color, String ubicacion) {

        ArrayList<Reporte> reportes = repositorio.obtenerReportes();

        for (int i = 0; i < reportes.size(); i++) {
            Reporte existente = reportes.get(i);

            if (existente.getTipo().equalsIgnoreCase("Encontrado")
                    && existente.getObjeto().getNombre().equalsIgnoreCase(nombre.trim())
                    && existente.getObjeto().getCategoria().equalsIgnoreCase(categoria.trim())
                    && existente.getObjeto().getColor().equalsIgnoreCase(color.trim())
                    && existente.getUbicacion().equalsIgnoreCase(ubicacion.trim())) {
                return true;
            }
        }
        return false;
    }
    public String validarObjetoEncontrado(String nombre, String categoria, String color,
                                          String campus, String ubicacion, String fecha,
                                          String descripcion) {

        if (!camposCompletos(nombre, categoria, color, campus, ubicacion, fecha, descripcion)) {
            return "ADVERTENCIA: Todos los campos son obligatorios.";
        }

        if (!fechaValida(fecha)) {
            return "ADVERTENCIA: La fecha no es valida. Use el formato dd/MM/yyyy y que no sea posterior a hoy.";
        }

        if (esDuplicado(nombre, categoria, color, ubicacion)) {
            return "ADVERTENCIA: Ya existe un objeto encontrado con las mismas caracteristicas.";
        }

        return "OK";
    }
    public String registrarObjetoEncontrado(String nombre, String categoria, String color,
                                            String campus, String ubicacion, String fecha,
                                            String descripcion) {

        String resultado = validarObjetoEncontrado(nombre, categoria, color,
                campus, ubicacion, fecha, descripcion);

        if (!resultado.equals("OK")) {
            return resultado;
        }
        int nuevoId = generarId();
        Objeto objeto = new Objeto(nuevoId, nombre.trim(), categoria.trim(),
                color.trim(), descripcion.trim());

        Reporte reporte = new Reporte(nuevoId, objeto, "Encontrado",
                campus.trim(), ubicacion.trim(), fecha.trim(), "Pendiente");

        repositorio.agregarReporte(reporte);

        return "Objeto encontrado registrado con exito. ID asignado: " + reporte.getIdReporte();
    }
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

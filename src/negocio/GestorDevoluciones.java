package negocio;

import modelo.Devolucion;
import modelo.Objeto;
import modelo.Reporte;

import java.util.ArrayList;

public class GestorDevoluciones {
    private RepositorioReportes repositorio;
    private ArrayList<Devolucion> devoluciones;

    public GestorDevoluciones(RepositorioReportes repositorio) {
        this.repositorio = repositorio;
        this.devoluciones = new ArrayList<>();
    }

    public Reporte buscarReportePorId(int idReporte) {
        ArrayList<Reporte> lista = repositorio.obtenerReportes();
        for (int i = 0; i < lista.size(); i++) {
            Reporte actual = lista.get(i);
            if (actual.getIdReporte() == idReporte) {
                return actual;
            }
        }
        return null;
    }

    public String obtenerResumenBasico(Reporte reporte) {
        if (reporte == null) {
            return "No existe un reporte con ese ID.";
        }
        Objeto obj = reporte.getObjeto();
        String nombreObj = (obj != null) ? obj.getNombre() : "No especificado";
        String categoriaObj = (obj != null) ? obj.getCategoria() : "No especificada";

        return "Reporte #" + reporte.getIdReporte()
                + " | Tipo: " + reporte.getTipo()
                + " | Objeto: " + nombreObj
                + " | Categoria: " + categoriaObj
                + " | Campus: " + reporte.getCampus()
                + " | Estado actual: " + reporte.getEstado();
    }

    // RF03: Validacion de propiedad comparando color y parte de la descripcion/ubicacion
    public boolean validarPropiedad(int idReporte, String colorIngresado, String pistaIngresada) {
        Reporte reporte = buscarReportePorId(idReporte);
        if (reporte == null || reporte.getObjeto() == null) {
            return false;
        }
        if ("Devuelto".equalsIgnoreCase(reporte.getEstado())) {
            return false;
        }
        if (colorIngresado == null || colorIngresado.trim().isEmpty()
                || pistaIngresada == null || pistaIngresada.trim().isEmpty()) {
            return false;
        }

        String colorReal = reporte.getObjeto().getColor();
        String descripcionReal = reporte.getObjeto().getDescripcion();
        String ubicacionReal = reporte.getUbicacion();

        boolean coincideColor = colorReal != null
                && colorReal.trim().equalsIgnoreCase(colorIngresado.trim());

        String pistaLimpia = pistaIngresada.trim().toLowerCase();
        boolean coincideDescripcion = descripcionReal != null
                && descripcionReal.toLowerCase().contains(pistaLimpia);
        boolean coincideUbicacion = ubicacionReal != null
                && ubicacionReal.toLowerCase().contains(pistaLimpia);

        return coincideColor && (coincideDescripcion || coincideUbicacion);
    }

    // RF04: Registro de devolucion y actualizacion del estado del reporte
    public boolean registrarDevolucion(int idReporte, String nombreReclamante, String identificacionReclamante, String fechaDevolucion) {
        Reporte reporte = buscarReportePorId(idReporte);
        if (reporte == null) {
            return false;
        }
        if ("Devuelto".equalsIgnoreCase(reporte.getEstado())) {
            return false;
        }
        if (nombreReclamante == null || nombreReclamante.trim().isEmpty()
                || identificacionReclamante == null || identificacionReclamante.trim().isEmpty()
                || fechaDevolucion == null || fechaDevolucion.trim().isEmpty()) {
            return false;
        }

        reporte.setEstado("Devuelto");
        Devolucion nuevaDevolucion = new Devolucion(
                reporte,
                nombreReclamante.trim(),
                identificacionReclamante.trim(),
                fechaDevolucion.trim()
        );
        devoluciones.add(nuevaDevolucion);
        return true;
    }

    public ArrayList<Devolucion> obtenerDevoluciones() {
        return new ArrayList<>(devoluciones);
    }
}
package negocio;
import modelo.Reporte;
import java.util.ArrayList;

public class ConsultorReportes {

    private RepositorioReportes repositorio;

    public ConsultorReportes(RepositorioReportes repositorio) {
        this.repositorio = repositorio;
    }

    public ArrayList<Reporte> consultarTodos() {
        return repositorio.obtenerReportes();
    }

    public ArrayList<Reporte> filtrarPorCategoria(String categoria) {

        ArrayList<Reporte> resultados = new ArrayList<>();

        if (categoria == null || categoria.trim().isEmpty()) {
            return resultados;
        }

        for (Reporte reporte : repositorio.obtenerReportes()) {

            if (reporte.getObjeto().getCategoria()
                    .equalsIgnoreCase(categoria.trim())) {

                resultados.add(reporte);
            }
        }

        return resultados;
    }

    public ArrayList<Reporte> filtrarPorUbicacion(String ubicacion) {

        ArrayList<Reporte> resultados = new ArrayList<>();

        if (ubicacion == null || ubicacion.trim().isEmpty()) {
            return resultados;
        }

        for (Reporte reporte : repositorio.obtenerReportes()) {

            if (reporte.getUbicacion()
                    .equalsIgnoreCase(ubicacion.trim())) {

                resultados.add(reporte);
            }
        }

        return resultados;
    }

    public ArrayList<Reporte> filtrarPorEstado(String estado) {

        ArrayList<Reporte> resultados = new ArrayList<>();

        if (estado == null || estado.trim().isEmpty()) {
            return resultados;
        }

        for (Reporte reporte : repositorio.obtenerReportes()) {

            if (reporte.getEstado()
                    .equalsIgnoreCase(estado.trim())) {

                resultados.add(reporte);
            }
        }

        return resultados;
    }

    public int contarPorTipo(String tipo) {

        int contador = 0;

        if (tipo == null || tipo.trim().isEmpty()) {
            return contador;
        }

        for (Reporte reporte : repositorio.obtenerReportes()) {

            if (reporte.getTipo()
                    .equalsIgnoreCase(tipo.trim())) {

                contador++;
            }
        }

        return contador;
    }

    public int contarDevueltos() {
        return filtrarPorEstado("Devuelto").size();
    }

    public int contarPerdidosPorNombre(String nombre) {

        int contador = 0;

        if (nombre == null || nombre.trim().isEmpty()) {
            return contador;
        }

        for (Reporte reporte : repositorio.obtenerReportes()) {

            if (reporte.getTipo().equalsIgnoreCase("Perdido")
                    && reporte.getObjeto().getNombre()
                    .equalsIgnoreCase(nombre.trim())) {

                contador++;
            }
        }

        return contador;
    }

    public String obtenerObjetoMasPerdido() {

        String nombreMasPerdido = "Sin reportes perdidos";
        int mayorCantidad = 0;

        for (Reporte reporte : repositorio.obtenerReportes()) {

            if (reporte.getTipo().equalsIgnoreCase("Perdido")) {

                String nombre = reporte.getObjeto().getNombre();

                int cantidad = contarPerdidosPorNombre(nombre);

                if (cantidad > mayorCantidad) {
                    mayorCantidad = cantidad;
                    nombreMasPerdido = nombre;
                }
            }
        }

        return nombreMasPerdido;
    }
}
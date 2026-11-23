package negocio;

import java.util.ArrayList;
import modelo.Coincidencia;
import modelo.Objeto;
import modelo.Reporte;

public class ComparadorObjetos {
    private RepositorioReportes repositorio;

    public ComparadorObjetos(RepositorioReportes repositorio) {
        this.repositorio = repositorio;
    }

    public ArrayList<Coincidencia> buscarCoincidenciasParaReporte(int idReporteBase) {
        ArrayList<Coincidencia> resultados = new ArrayList<>();

        if (repositorio == null) {
            return resultados;
        }

        ArrayList<Reporte> todosLosReportes = repositorio.obtenerReportes();
        Reporte reporteBase = buscarReportePorId(todosLosReportes, idReporteBase);

        if (reporteBase == null || reporteBase.getObjeto() == null) {
            return resultados;
        }

        for (int i = 0; i < todosLosReportes.size(); i++) {
            Reporte candidato = todosLosReportes.get(i);

            if (esCandidatoValido(reporteBase, candidato)) {
                int puntaje = calcularPuntajeSimilitud(reporteBase, candidato);
                if (puntaje > 0) {
                    String resumen = construirResumenSimilitud(reporteBase, candidato);
                    Coincidencia coincidencia = new Coincidencia(
                            esPerdido(reporteBase) ? reporteBase : candidato,
                            esEncontrado(reporteBase) ? reporteBase : candidato,
                            puntaje,
                            resumen
                    );
                    resultados.add(coincidencia);
                }
            }
        }

        return resultados;
    }

    public ArrayList<Coincidencia> buscarTodasLasCoincidencias(int puntajeMinimo) {
        ArrayList<Coincidencia> coincidenciasEncontradas = new ArrayList<>();

        if (repositorio == null) {
            return coincidenciasEncontradas;
        }

        ArrayList<Reporte> reportes = repositorio.obtenerReportes();

        for (int i = 0; i < reportes.size(); i++) {
            Reporte base = reportes.get(i);
            if (base != null && esPerdido(base)) {
                for (int j = 0; j < reportes.size(); j++) {
                    Reporte candidato = reportes.get(j);
                    if (esCandidatoValido(base, candidato)) {
                        int puntaje = calcularPuntajeSimilitud(base, candidato);
                        if (puntaje >= puntajeMinimo && puntaje > 0) {
                            String resumen = construirResumenSimilitud(base, candidato);
                            coincidenciasEncontradas.add(
                                    new Coincidencia(base, candidato, puntaje, resumen)
                            );
                        }
                    }
                }
            }
        }

        return coincidenciasEncontradas;
    }

    public String compararDosReportesDetallado(int idReporteA, int idReporteB) {
        if (repositorio == null) {
            return "Error: no hay repositorio disponible para comparar.";
        }

        ArrayList<Reporte> reportes = repositorio.obtenerReportes();
        Reporte r1 = buscarReportePorId(reportes, idReporteA);
        Reporte r2 = buscarReportePorId(reportes, idReporteB);

        if (r1 == null || r2 == null) {
            return "No se encontraron ambos reportes para realizar la comparacion.";
        }
        if (r1.getIdReporte() == r2.getIdReporte()) {
            return "Debes seleccionar dos reportes distintos para compararlos.";
        }
        if (r1.getObjeto() == null || r2.getObjeto() == null) {
            return "Uno o ambos reportes no tienen un objeto asociado.";
        }

        Objeto o1 = r1.getObjeto();
        Objeto o2 = r2.getObjeto();
        int puntaje = calcularPuntajeSimilitud(r1, r2);
        String resumen = construirResumenSimilitud(r1, r2);

        String nivel;
        if (puntaje >= 70) {
            nivel = "Alta (sugerencia fuerte de coincidencia)";
        } else if (puntaje >= 40) {
            nivel = "Media (posible coincidencia, revisar detalles)";
        } else {
            nivel = "Baja (pocas coincidencias)";
        }

        StringBuilder detalle = new StringBuilder();
        detalle.append("=== COMPARACION DETALLADA (RF06) ===\n");
        detalle.append("Reporte A [ID ").append(r1.getIdReporte()).append(" - ").append(r1.getTipo()).append("]: ")
                .append(o1.getNombre()).append(" | Cat: ").append(o1.getCategoria())
                .append(" | Color: ").append(o1.getColor())
                .append(" | Campus: ").append(r1.getCampus())
                .append(" | Ubicacion: ").append(r1.getUbicacion())
                .append(" | Fecha: ").append(r1.getFecha())
                .append(" | Desc: ").append(o1.getDescripcion()).append("\n");

        detalle.append("Reporte B [ID ").append(r2.getIdReporte()).append(" - ").append(r2.getTipo()).append("]: ")
                .append(o2.getNombre()).append(" | Cat: ").append(o2.getCategoria())
                .append(" | Color: ").append(o2.getColor())
                .append(" | Campus: ").append(r2.getCampus())
                .append(" | Ubicacion: ").append(r2.getUbicacion())
                .append(" | Fecha: ").append(r2.getFecha())
                .append(" | Desc: ").append(o2.getDescripcion()).append("\n");

        detalle.append("Puntaje de similitud: ").append(puntaje).append("/100\n");
        detalle.append("Nivel de coincidencia: ").append(nivel).append("\n");
        detalle.append("Atributos coincidentes: ").append(resumen);

        return detalle.toString();
    }

    private Reporte buscarReportePorId(ArrayList<Reporte> lista, int idBuscado) {
        for (int i = 0; i < lista.size(); i++) {
            Reporte actual = lista.get(i);
            if (actual != null && actual.getIdReporte() == idBuscado) {
                return actual;
            }
        }
        return null;
    }

    private boolean esPerdido(Reporte reporte) {
        return reporte.getTipo() != null && reporte.getTipo().equalsIgnoreCase("Perdido");
    }

    private boolean esEncontrado(Reporte reporte) {
        return reporte.getTipo() != null && reporte.getTipo().equalsIgnoreCase("Encontrado");
    }

    private boolean esCandidatoValido(Reporte base, Reporte candidato) {
        if (candidato == null || candidato.getObjeto() == null) {
            return false;
        }
        if (candidato.getIdReporte() == base.getIdReporte()) {
            return false;
        }
        boolean tiposOpuestos = (esPerdido(base) && esEncontrado(candidato))
                || (esEncontrado(base) && esPerdido(candidato));
        if (!tiposOpuestos) {
            return false;
        }
        boolean basePendiente = base.getEstado() != null && base.getEstado().equalsIgnoreCase("Pendiente");
        boolean candidatoPendiente = candidato.getEstado() != null && candidato.getEstado().equalsIgnoreCase("Pendiente");
        return basePendiente && candidatoPendiente;
    }

    private int calcularPuntajeSimilitud(Reporte r1, Reporte r2) {
        Objeto o1 = r1.getObjeto();
        Objeto o2 = r2.getObjeto();
        int puntaje = 0;

        if (textoIgualIgnorandoCaso(o1.getCategoria(), o2.getCategoria())) {
            puntaje += 30;
        }
        if (textoIgualIgnorandoCaso(o1.getNombre(), o2.getNombre())) {
            puntaje += 25;
        } else if (contieneTextoIgnorandoCaso(o1.getNombre(), o2.getNombre())
                || contieneTextoIgnorandoCaso(o2.getNombre(), o1.getNombre())) {
            puntaje += 15;
        }
        if (textoIgualIgnorandoCaso(o1.getColor(), o2.getColor())) {
            puntaje += 20;
        }
        if (textoIgualIgnorandoCaso(r1.getCampus(), r2.getCampus())) {
            puntaje += 15;
        }
        if (textoContienePalabrasClave(o1.getDescripcion(), o2.getDescripcion())) {
            puntaje += 10;
        }

        return puntaje;
    }

    private String construirResumenSimilitud(Reporte r1, Reporte r2) {
        Objeto o1 = r1.getObjeto();
        Objeto o2 = r2.getObjeto();
        StringBuilder sb = new StringBuilder();

        if (textoIgualIgnorandoCaso(o1.getCategoria(), o2.getCategoria())) {
            sb.append("Categoria");
        }
        if (textoIgualIgnorandoCaso(o1.getNombre(), o2.getNombre())
                || contieneTextoIgnorandoCaso(o1.getNombre(), o2.getNombre())
                || contieneTextoIgnorandoCaso(o2.getNombre(), o1.getNombre())) {
            agregarSeparador(sb);
            sb.append("Nombre");
        }
        if (textoIgualIgnorandoCaso(o1.getColor(), o2.getColor())) {
            agregarSeparador(sb);
            sb.append("Color");
        }
        if (textoIgualIgnorandoCaso(r1.getCampus(), r2.getCampus())) {
            agregarSeparador(sb);
            sb.append("Campus");
        }
        if (textoContienePalabrasClave(o1.getDescripcion(), o2.getDescripcion())) {
            agregarSeparador(sb);
            sb.append("Descripcion");
        }

        return sb.length() == 0 ? "Sin atributos coincidentes" : sb.toString();
    }

    private void agregarSeparador(StringBuilder sb) {
        if (sb.length() > 0) {
            sb.append(", ");
        }
    }

    private boolean textoIgualIgnorandoCaso(String a, String b) {
        if (a == null || b == null) {
            return false;
        }
        return a.trim().equalsIgnoreCase(b.trim());
    }

    private boolean contieneTextoIgnorandoCaso(String mayor, String menor) {
        if (mayor == null || menor == null) {
            return false;
        }
        String m1 = mayor.trim().toLowerCase();
        String m2 = menor.trim().toLowerCase();
        return !m1.isEmpty() && !m2.isEmpty() && m1.contains(m2);
    }

    private boolean textoContienePalabrasClave(String d1, String d2) {
        if (d1 == null || d2 == null) {
            return false;
        }
        String[] palabras1 = d1.toLowerCase().split("\\s+");
        String d2Minuscula = d2.toLowerCase();
        for (int i = 0; i < palabras1.length; i++) {
            String palabra = palabras1[i].replaceAll("[^a-záéíóúñ0-9]", "");
            if (palabra.length() >= 4 && d2Minuscula.contains(palabra)) {
                return true;
            }
        }
        return false;
    }
}
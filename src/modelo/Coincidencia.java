package modelo;

public class Coincidencia {
    private Reporte reportePerdido;
    private Reporte reporteEncontrado;
    private int puntaje;
    private String resumenCoincidencias;

    public Coincidencia(Reporte reportePerdido, Reporte reporteEncontrado, int puntaje, String resumenCoincidencias) {
        this.reportePerdido = reportePerdido;
        this.reporteEncontrado = reporteEncontrado;
        this.puntaje = puntaje;
        this.resumenCoincidencias = resumenCoincidencias;
    }

    public Reporte getReportePerdido() {
        return reportePerdido;
    }

    public Reporte getReporteEncontrado() {
        return reporteEncontrado;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public String getResumenCoincidencias() {
        return resumenCoincidencias;
    }

    public String obtenerNivelSimilitud() {
        if (puntaje >= 70) {
            return "Alta";
        } else if (puntaje >= 40) {
            return "Media";
        } else {
            return "Baja";
        }
    }

    @Override
    public String toString() {
        return "Coincidencia [" + obtenerNivelSimilitud() + " - " + puntaje + " pts] | "
                + "Perdido #" + reportePerdido.getIdReporte() + " (" + reportePerdido.getObjeto().getNombre() + ") "
                + "<-> Encontrado #" + reporteEncontrado.getIdReporte() + " (" + reporteEncontrado.getObjeto().getNombre() + ") "
                + "| Detalles: " + resumenCoincidencias;
    }
}
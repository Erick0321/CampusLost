package modelo;

public class Devolucion {
    private Reporte reporte;
    private String nombreReclamante;
    private String identificacionReclamante;
    private String fechaDevolucion;

    public Devolucion(Reporte reporte, String nombreReclamante, String identificacionReclamante, String fechaDevolucion) {
        this.reporte = reporte;
        this.nombreReclamante = nombreReclamante;
        this.identificacionReclamante = identificacionReclamante;
        this.fechaDevolucion = fechaDevolucion;
    }

    public Reporte getReporte() {
        return reporte;
    }

    public void setReporte(Reporte reporte) {
        this.reporte = reporte;
    }

    public String getNombreReclamante() {
        return nombreReclamante;
    }

    public void setNombreReclamante(String nombreReclamante) {
        this.nombreReclamante = nombreReclamante;
    }

    public String getIdentificacionReclamante() {
        return identificacionReclamante;
    }

    public void setIdentificacionReclamante(String identificacionReclamante) {
        this.identificacionReclamante = identificacionReclamante;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    @Override
    public String toString() {
        return "Devolucion{" +
                "reporte=" + reporte +
                ", nombreReclamante='" + nombreReclamante + '\'' +
                ", identificacionReclamante='" + identificacionReclamante + '\'' +
                ", fechaDevolucion='" + fechaDevolucion + '\'' +
                '}';
    }
}
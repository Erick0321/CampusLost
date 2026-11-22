package modelo;

public class Reporte {

    private int idReporte;
    private Objeto objeto;
    private String tipo;
    private String campus;
    private String ubicacion;
    private String fecha;
    private String estado;

    public Reporte(int idReporte, Objeto objeto, String tipo,
                   String campus, String ubicacion, String fecha,
                   String estado) {

        this.idReporte = idReporte;
        this.objeto = objeto;
        this.tipo = tipo;
        this.campus = campus;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.estado = estado;
    }

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public Objeto getObjeto() {
        return objeto;
    }

    public void setObjeto(Objeto objeto) {
        this.objeto = objeto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Reporte{" +
                "idReporte=" + idReporte +
                ", objeto=" + objeto +
                ", tipo='" + tipo + '\'' +
                ", campus='" + campus + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", fecha='" + fecha + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
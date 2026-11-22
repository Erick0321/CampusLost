package modelo;

public class Objeto {

    private int id;
    private String nombre;
    private String categoria;
    private String color;
    private String descripcion;

    public Objeto(int id, String nombre, String categoria,
                  String color, String descripcion) {

        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.color = color;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Objeto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", color='" + color + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
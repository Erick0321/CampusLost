package negocio;

import modelo.Reporte;
import java.util.ArrayList;

public class RepositorioReportes {

    private ArrayList<Reporte> reportes;

    public RepositorioReportes() {
        reportes = new ArrayList<>();
    }

    public void agregarReporte(Reporte reporte) {
        reportes.add(reporte);
    }

    public ArrayList<Reporte> obtenerReportes() {
        return new ArrayList<>(reportes);
    }
}
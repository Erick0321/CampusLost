package interfaz;

import modelo.Objeto;
import modelo.Reporte;
import negocio.RepositorioReportes;

public class Main {

    public static void main(String[] args) {

        // Crear un objeto
        Objeto objeto1 = new Objeto(
                1, "Audifonos", "Electronicos",
                "Negro", "Marca Sony"
        );

        // Crear un reporte
        Reporte reporte1 = new Reporte(
                101, objeto1, "Perdido",
                "UDLA Park", "Biblioteca",
                "21/11/2026", "Pendiente"
        );

        // Crear el repositorio
        RepositorioReportes repositorio =
                new RepositorioReportes();

        // Guardar el reporte
        repositorio.agregarReporte(reporte1);

        // Mostrar el reporte guardado
        System.out.println("=== CAMPUSLOST ===");
        System.out.println(repositorio.obtenerReportes().get(0));

        // Mostrar el total de reportes
        System.out.println("Total de reportes: "
                + repositorio.obtenerReportes().size());
    }
}

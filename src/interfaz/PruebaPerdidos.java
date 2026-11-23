package interfaz;

import java.util.ArrayList;
import modelo.Reporte;
import negocio.GestorObjetosPerdidos;
import negocio.RepositorioReportes;

public class PruebaPerdidos {

    public static void main(String[] args) {

        RepositorioReportes repositorio = new RepositorioReportes();
        GestorObjetosPerdidos gestor = new GestorObjetosPerdidos(repositorio);

        System.out.println("=== PRUEBAS MODULO 4: OBJETOS PERDIDOS ===");

        System.out.println("\nPrueba 1 - Registro correcto (RF07):");
        System.out.println(gestor.registrarObjetoPerdido("Audifonos", "Electronicos", "Negro",
                "Marca Sony", "UDLA Park", "Biblioteca", "20/11/2026"));

        System.out.println("\nPrueba 2 - Campo vacio (RF08):");
        System.out.println(gestor.registrarObjetoPerdido("Billetera", "", "Cafe",
                "Cuero", "UDLA Park", "Cafeteria", "20/11/2026"));

        System.out.println("\nPrueba 3 - Fecha futura (RF08):");
        System.out.println(gestor.registrarObjetoPerdido("Billetera", "Accesorios", "Cafe",
                "Cuero", "UDLA Park", "Cafeteria", "31/12/2099"));

        System.out.println("\nPrueba 4 - Fecha con formato incorrecto (RF08):");
        System.out.println(gestor.registrarObjetoPerdido("Billetera", "Accesorios", "Cafe",
                "Cuero", "UDLA Park", "Cafeteria", "2026-11-20"));

        System.out.println("\nPrueba 5 - Duplicado con mayusculas y espacios distintos (RF08):");
        System.out.println(gestor.registrarObjetoPerdido("  audifonos ", "ELECTRONICOS", "negro",
                "Otra descripcion", "udla park", "biblioteca", "21/11/2026"));

        System.out.println("\nPrueba 6 - Segundo registro correcto (ID siguiente):");
        System.out.println(gestor.registrarObjetoPerdido("Billetera", "Accesorios", "Cafe",
                "Cuero", "UDLA Park", "Cafeteria", "20/11/2026"));

        System.out.println("\nPrueba 7 - El reporte anterior ya fue devuelto, se puede registrar otra vez:");
        repositorio.obtenerReportes().get(0).setEstado("Devuelto");
        System.out.println(gestor.registrarObjetoPerdido("Audifonos", "Electronicos", "Negro",
                "Marca Sony", "UDLA Park", "Biblioteca", "21/11/2026"));

        System.out.println("\n=== REPORTES GUARDADOS ===");
        ArrayList<Reporte> reportes = repositorio.obtenerReportes();
        for (int i = 0; i < reportes.size(); i++) {
            System.out.println(reportes.get(i));
        }
        System.out.println("Total de reportes: " + reportes.size());
    }
}
package interfaz;

import negocio.GestorObjetosEncontrados;
import negocio.RepositorioReportes;

public class PruebaEncontrados {

    public static void main(String[] args) {

        RepositorioReportes repositorio = new RepositorioReportes();
        GestorObjetosEncontrados gestor = new GestorObjetosEncontrados(repositorio);

        System.out.println("=== PRUEBA 1: registro valido ===");
        System.out.println(gestor.registrarObjetoEncontrado(
                "Audifonos", "Electronicos", "Negro",
                "UDLA Park", "Biblioteca", "21/11/2026", "Marca Sony"));

        System.out.println("\n=== PRUEBA 2: duplicado ===");
        System.out.println(gestor.registrarObjetoEncontrado(
                "audifonos", "electronicos", "negro",
                "UDLA Park", "biblioteca", "21/11/2026", "Con estuche"));

        System.out.println("\n=== PRUEBA 3: campo vacio ===");
        System.out.println(gestor.registrarObjetoEncontrado(
                "Llaves", "Accesorios", "   ",
                "UDLA Park", "Cafeteria", "20/11/2026", "Con llavero"));

        System.out.println("\n=== PRUEBA 4: fecha futura ===");
        System.out.println(gestor.registrarObjetoEncontrado(
                "Mochila", "Accesorios", "Azul",
                "UDLA Park", "Laboratorio", "01/01/2030", "Mochila grande"));

        System.out.println("\n=== PRUEBA 5: formato de fecha incorrecto ===");
        System.out.println(gestor.registrarObjetoEncontrado(
                "Billetera", "Accesorios", "Cafe",
                "UDLA Park", "Aula 101", "ayer", "De cuero"));

        System.out.println("\nTotal de reportes guardados: "
                + repositorio.obtenerReportes().size());
    }
}
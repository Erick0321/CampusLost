package interfaz;

import modelo.Objeto;
import modelo.Reporte;
import negocio.GestorObjetosEncontrados;
import negocio.RepositorioReportes;

public class PruebaEncontrados {

    public static void main(String[] args) {

        RepositorioReportes repositorio = new RepositorioReportes();
        GestorObjetosEncontrados gestor = new GestorObjetosEncontrados(repositorio);

        // Simula un reporte de objeto perdido registrado por otro modulo
        Objeto perdido = new Objeto(10, "Celular", "Electronicos", "Blanco", "iPhone con funda");
        Reporte reportePerdido = new Reporte(10, perdido, "Perdido",
                "UDLA Park", "Aula 205", "19/11/2026", "Pendiente");
        repositorio.agregarReporte(reportePerdido);

        System.out.println("=== PRUEBA 1: registro valido (el id no debe repetir el 10) ===");
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

        System.out.println("\n=== PRUEBA 6: dato null ===");
        System.out.println(gestor.registrarObjetoEncontrado(
                null, "Accesorios", "Rojo",
                "UDLA Park", "Cafeteria", "20/11/2026", "Paraguas"));

        System.out.println("\n=== PRUEBA 7: segundo registro valido ===");
        System.out.println(gestor.registrarObjetoEncontrado(
                "Llaves", "Accesorios", "Plateado",
                "UDLA Park", "Cafeteria", "20/11/2026", "Con llavero"));

        System.out.println("\n=== PRUEBA 8: mismo objeto en otro campus (debe registrarse) ===");
        System.out.println(gestor.registrarObjetoEncontrado(
                "Audifonos", "Electronicos", "Negro",
                "UDLA Granados", "Biblioteca", "21/11/2026", "Marca Sony"));

        System.out.println("\n=== PRUEBA 9: repetir el de la Prueba 8 (debe ser duplicado) ===");
        System.out.println(gestor.registrarObjetoEncontrado(
                "audifonos", "electronicos", "negro",
                "UDLA Granados", "biblioteca", "21/11/2026", "Con estuche"));

        System.out.println("\nTotal de reportes guardados: "
                + repositorio.obtenerReportes().size());
    }
}
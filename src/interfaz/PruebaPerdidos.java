package interfaz;

import modelo.Objeto;
import modelo.Reporte;
import negocio.GestorObjetosPerdidos;
import negocio.RepositorioReportes;

public class PruebaPerdidos {

    public static void main(String[] args) {

        RepositorioReportes repositorio = new RepositorioReportes();
        GestorObjetosPerdidos gestor = new GestorObjetosPerdidos(repositorio);

        // Simula un reporte de objeto encontrado registrado por otro modulo
        Objeto encontrado = new Objeto(10, "Celular", "Electronicos", "Blanco", "iPhone con funda");
        repositorio.agregarReporte(new Reporte(10, encontrado, "Encontrado",
                "UDLA Park", "Aula 205", "19/11/2026", "Pendiente"));

        System.out.println("=== PRUEBA 1: registro valido (el id no debe repetir el 10) ===");
        System.out.println(gestor.registrarObjetoPerdido(
                "Audifonos", "Electronicos", "Negro",
                "UDLA Park", "Biblioteca", "21/11/2026", "Marca Sony"));

        System.out.println("\n=== PRUEBA 2: duplicado ===");
        System.out.println(gestor.registrarObjetoPerdido(
                "audifonos", "electronicos", "negro",
                "UDLA Park", "biblioteca", "21/11/2026", "Con estuche"));

        System.out.println("\n=== PRUEBA 3: campo vacio ===");
        System.out.println(gestor.registrarObjetoPerdido(
                "Llaves", "Accesorios", "   ",
                "UDLA Park", "Cafeteria", "20/11/2026", "Con llavero"));

        System.out.println("\n=== PRUEBA 4: fecha futura ===");
        System.out.println(gestor.registrarObjetoPerdido(
                "Mochila", "Accesorios", "Azul",
                "UDLA Park", "Laboratorio", "01/01/2030", "Mochila grande"));

        System.out.println("\n=== PRUEBA 5: formato de fecha incorrecto ===");
        System.out.println(gestor.registrarObjetoPerdido(
                "Billetera", "Accesorios", "Cafe",
                "UDLA Park", "Aula 101", "ayer", "De cuero"));

        System.out.println("\n=== PRUEBA 6: fecha inexistente (31/02) ===");
        System.out.println(gestor.registrarObjetoPerdido(
                "Billetera", "Accesorios", "Cafe",
                "UDLA Park", "Aula 101", "31/02/2026", "De cuero"));

        System.out.println("\n=== PRUEBA 7: dato null ===");
        System.out.println(gestor.registrarObjetoPerdido(
                null, "Accesorios", "Rojo",
                "UDLA Park", "Cafeteria", "20/11/2026", "Paraguas"));

        System.out.println("\n=== PRUEBA 8: mismo objeto en otro campus (debe registrarse) ===");
        System.out.println(gestor.registrarObjetoPerdido(
                "Audifonos", "Electronicos", "Negro",
                "UDLA Granados", "Biblioteca", "21/11/2026", "Marca Sony"));

        System.out.println("\n=== PRUEBA 9: un perdido NO es duplicado de un encontrado igual ===");
        System.out.println(gestor.registrarObjetoPerdido(
                "Celular", "Electronicos", "Blanco",
                "UDLA Park", "Aula 205", "19/11/2026", "iPhone con funda"));

        System.out.println("\nTotal de reportes guardados: "
                + repositorio.obtenerReportes().size());
    }
}

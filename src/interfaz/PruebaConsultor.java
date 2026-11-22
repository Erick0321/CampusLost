package interfaz;

import modelo.Objeto;
import modelo.Reporte;
import negocio.ConsultorReportes;
import negocio.RepositorioReportes;

public class PruebaConsultor {

    public static void main(String[] args) {

        RepositorioReportes repositorio = new RepositorioReportes();

        Objeto objeto1 = new Objeto(
                1, "Audifonos", "Electronicos",
                "Negro", "Marca Sony"
        );

        Reporte reporte1 = new Reporte(
                101, objeto1, "Perdido",
                "UDLA Park", "Biblioteca",
                "22/11/2026", "Pendiente"
        );

        repositorio.agregarReporte(reporte1);

        Objeto objeto2 = new Objeto(
                2, "Billetera", "Accesorios",
                "Cafe", "Billetera de cuero"
        );

        Reporte reporte2 = new Reporte(
                102, objeto2, "Encontrado",
                "UDLA Park", "Cafeteria",
                "22/11/2026", "Devuelto"
        );

        repositorio.agregarReporte(reporte2);

        Objeto objeto3 = new Objeto(
                3, "Celular", "Electronicos",
                "Negro", "Samsung"
        );

        Reporte reporte3 = new Reporte(
                103, objeto3, "Perdido",
                "UDLA Park", "Biblioteca",
                "22/11/2026", "Pendiente"
        );

        repositorio.agregarReporte(reporte3);

        Objeto objeto4 = new Objeto(
                4, "Celular", "Electronicos",
                "Azul", "Xiaomi"
        );

        Reporte reporte4 = new Reporte(
                104, objeto4, "Perdido",
                "UDLA Park", "Cafeteria",
                "22/11/2026", "Pendiente"
        );

        repositorio.agregarReporte(reporte4);

        ConsultorReportes consultor =
                new ConsultorReportes(repositorio);

        System.out.println("CONSULTA CAMPUSLOST");

        for (Reporte reporte : consultor.consultarTodos()) {
            System.out.println(reporte);
        }

        System.out.println("Total consultados: "
                + consultor.consultarTodos().size());

        System.out.println("FILTRO POR CATEGORIA");

        for (Reporte reporte : consultor.filtrarPorCategoria("Electronicos")) {
            System.out.println(reporte);
        }

        System.out.println("Coincidencias: "
                + consultor.filtrarPorCategoria("Electronicos").size());

        System.out.println("Sin coincidencias: "
                + consultor.filtrarPorCategoria("Ropa").size());

        System.out.println("FILTRO POR UBICACION");

        for (Reporte reporte : consultor.filtrarPorUbicacion("Biblioteca")) {
            System.out.println(reporte);
        }

        System.out.println("Coincidencias en Biblioteca: "
                + consultor.filtrarPorUbicacion("Biblioteca").size());

        System.out.println("Coincidencias en Cafeteria: "
                + consultor.filtrarPorUbicacion("Cafeteria").size());

        System.out.println("FILTRO POR ESTADO");

        for (Reporte reporte : consultor.filtrarPorEstado("Pendiente")) {
            System.out.println(reporte);
        }

        System.out.println("Reportes pendientes: "
                + consultor.filtrarPorEstado("Pendiente").size());

        System.out.println("Reportes devueltos: "
                + consultor.filtrarPorEstado("Devuelto").size());

        System.out.println("ESTADISTICAS CAMPUSLOST");

        System.out.println("Objetos perdidos: "
                + consultor.contarPorTipo("Perdido"));

        System.out.println("Objetos encontrados: "
                + consultor.contarPorTipo("Encontrado"));

        System.out.println("Objetos devueltos: "
                + consultor.contarDevueltos());

        System.out.println("OBJETOS MAS PERDIDOS");

        System.out.println("Audifonos perdidos: "
                + consultor.contarPerdidosPorNombre("Audifonos"));

        System.out.println("Billeteras perdidas: "
                + consultor.contarPerdidosPorNombre("Billetera"));

        System.out.println("Celulares perdidos: "
                + consultor.contarPerdidosPorNombre("Celular"));

        System.out.println("OBJETO MAS PERDIDO");

        System.out.println("Objeto mas perdido: "
                + consultor.obtenerObjetoMasPerdido());
    }
}

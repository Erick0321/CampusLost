package interfaz;

import java.util.ArrayList;
import modelo.Coincidencia;
import modelo.Objeto;
import modelo.Reporte;
import negocio.ComparadorObjetos;
import negocio.RepositorioReportes;

public class PruebaComparador {
    public static void main(String[] args) {
        RepositorioReportes repositorio = new RepositorioReportes();

        Objeto obj1 = new Objeto(1, "Audifonos", "Electronicos", "Negro", "Marca Sony con estuche");
        Objeto obj2 = new Objeto(2, "Audifonos Sony", "Electronicos", "Negro", "Estuche negro con cable");
        Objeto obj3 = new Objeto(3, "Termo", "Hogar", "Azul", "Botella metalica de agua");

        Reporte rep1 = new Reporte(101, obj1, "Perdido", "Campus Norte", "Biblioteca", "21/11/2026", "Pendiente");
        Reporte rep2 = new Reporte(102, obj2, "Encontrado", "Campus Norte", "Cafeteria", "21/11/2026", "Pendiente");
        Reporte rep3 = new Reporte(103, obj3, "Encontrado", "Campus Sur", "Coliseo", "22/11/2026", "Pendiente");

        repositorio.agregarReporte(rep1);
        repositorio.agregarReporte(rep2);
        repositorio.agregarReporte(rep3);

        ComparadorObjetos comparador = new ComparadorObjetos(repositorio);

        System.out.println("=== PRUEBA RF05: COINCIDENCIAS PARA REPORTE 101 ===");
        ArrayList<Coincidencia> coincidencias101 = comparador.buscarCoincidenciasParaReporte(101);
        for (int i = 0; i < coincidencias101.size(); i++) {
            System.out.println(coincidencias101.get(i));
        }

        System.out.println("\n=== PRUEBA RF05: TODAS LAS COINCIDENCIAS (>= 40 PTS) ===");
        ArrayList<Coincidencia> todas = comparador.buscarTodasLasCoincidencias(40);
        for (int i = 0; i < todas.size(); i++) {
            System.out.println(todas.get(i));
        }

        System.out.println("\n=== PRUEBA RF06: COMPARACION DETALLADA (101 vs 102) ===");
        System.out.println(comparador.compararDosReportesDetallado(101, 102));
    }
}
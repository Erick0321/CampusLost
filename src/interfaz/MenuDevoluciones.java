package interfaz;

import modelo.Devolucion;
import modelo.Objeto;
import modelo.Reporte;
import negocio.GestorDevoluciones;
import negocio.RepositorioReportes;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuDevoluciones {

    public static void main(String[] args) {
        RepositorioReportes repositorio = new RepositorioReportes();
        cargarDatosDePrueba(repositorio);

        GestorDevoluciones gestor = new GestorDevoluciones(repositorio);
        Scanner scanner = new Scanner(System.in);

        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n=== MODULO 2: VALIDACION Y DEVOLUCION (RF03 - RF04) ===");
            System.out.println("1. Validar propiedad de un objeto (RF03)");
            System.out.println("2. Registrar devolucion de objeto (RF04)");
            System.out.println("3. Ver historial de devoluciones");
            System.out.println("0. Salir");
            System.out.print("Elige una opcion: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar salto de linea
            } else {
                System.out.println("Por favor, ingresa un numero valido.");
                scanner.nextLine();
                continue;
            }

            if (opcion == 1) {
                System.out.print("Ingresa el ID del reporte: ");
                if (!scanner.hasNextInt()) {
                    System.out.println("ID invalido.");
                    scanner.nextLine();
                    continue;
                }
                int id = scanner.nextInt();
                scanner.nextLine();

                Reporte reporte = gestor.buscarReportePorId(id);
                System.out.println(gestor.obtenerResumenBasico(reporte));

                if (reporte != null && !"Devuelto".equalsIgnoreCase(reporte.getEstado())) {
                    System.out.print("Confirma el color del objeto: ");
                    String color = scanner.nextLine();
                    System.out.print("Ingresa una pista (palabra de la descripcion o ubicacion): ");
                    String pista = scanner.nextLine();

                    boolean valida = gestor.validarPropiedad(id, color, pista);
                    if (valida) {
                        System.out.println("Validacion EXITOSA: los datos coinciden con el reporte.");
                    } else {
                        System.out.println("Validacion FALLIDA: los datos no coinciden o el reporte no esta disponible.");
                    }
                }
            } else if (opcion == 2) {
                System.out.print("Ingresa el ID del reporte a devolver: ");
                if (!scanner.hasNextInt()) {
                    System.out.println("ID invalido.");
                    scanner.nextLine();
                    continue;
                }
                int id = scanner.nextInt();
                scanner.nextLine();

                Reporte reporte = gestor.buscarReportePorId(id);
                System.out.println(gestor.obtenerResumenBasico(reporte));

                if (reporte != null && !"Devuelto".equalsIgnoreCase(reporte.getEstado())) {
                    System.out.print("Confirma el color del objeto: ");
                    String color = scanner.nextLine();
                    System.out.print("Ingresa una pista (descripcion o ubicacion): ");
                    String pista = scanner.nextLine();

                    if (!gestor.validarPropiedad(id, color, pista)) {
                        System.out.println("No se puede devolver: la validacion de propiedad fallo.");
                        continue;
                    }

                    System.out.print("Nombre de quien retira: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Cedula o ID universitario: ");
                    String identificacion = scanner.nextLine();
                    System.out.print("Fecha de devolucion (ej. 22/11/2026): ");
                    String fecha = scanner.nextLine();

                    boolean registrada = gestor.registrarDevolucion(id, nombre, identificacion, fecha);
                    if (registrada) {
                        System.out.println("Devolucion registrada con exito. Nuevo estado: " + reporte.getEstado());
                    } else {
                        System.out.println("No se pudo registrar la devolucion. Revisa que ningun campo este vacio.");
                    }
                }
            } else if (opcion == 3) {
                ArrayList<Devolucion> historial = gestor.obtenerDevoluciones();
                if (historial.isEmpty()) {
                    System.out.println("Aun no hay devoluciones registradas.");
                } else {
                    System.out.println("\n--- Historial de devoluciones ---");
                    for (int i = 0; i < historial.size(); i++) {
                        System.out.println((i + 1) + ". " + historial.get(i));
                    }
                }
            } else if (opcion == 0) {
                System.out.println("Saliendo del Modulo 2...");
            } else {
                System.out.println("Opcion no valida.");
            }
        }

        scanner.close();
    }

    private static void cargarDatosDePrueba(RepositorioReportes repositorio) {
        Objeto obj1 = new Objeto(1, "Mochila", "Accesorios", "Negro", "Mochila con llavero azul");
        Reporte rep1 = new Reporte(101, obj1, "Encontrado", "Campus Principal", "Biblioteca", "20/11/2026", "Encontrado");

        Objeto obj2 = new Objeto(2, "Botella", "Hogar", "Azul", "Botella termica de acero");
        Reporte rep2 = new Reporte(102, obj2, "Perdido", "Campus Norte", "Cafeteria", "21/11/2026", "Pendiente");

        repositorio.agregarReporte(rep1);
        repositorio.agregarReporte(rep2);
    }
}
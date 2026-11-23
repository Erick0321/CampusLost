package interfaz;

import java.util.Scanner;
import java.util.ArrayList;

import modelo.Reporte;
import negocio.RepositorioReportes;
import negocio.ConsultorReportes;
import negocio.GestorObjetosEncontrados;
import negocio.GestorDevoluciones;


public class MenuCampusLost {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        // Repositorio compartido por todos los modulos
        RepositorioReportes repositorio = new RepositorioReportes();

        // Modulo 1: consultas y estadisticas
        ConsultorReportes consultor =
                new ConsultorReportes(repositorio);

        // Modulo 5: registro de objetos encontrados
        GestorObjetosEncontrados gestorEncontrados =
                new GestorObjetosEncontrados(repositorio);

        // Modulo 2: validacion y devolucion
        GestorDevoluciones gestorDevoluciones =
                new GestorDevoluciones(repositorio);

        String opcion;

        do {
            System.out.println("\n===== CAMPUSLOST =====");
            System.out.println("1. Registrar objeto perdido");
            System.out.println("2. Registrar objeto encontrado");
            System.out.println("3. Consultar reportes");
            System.out.println("4. Validar y devolver objeto");
            System.out.println("5. Comparar objetos");
            System.out.println("6. Ver estadisticas");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = entrada.nextLine();

            switch (opcion) {

                // MODULO 4 - MAJO
                case "1":
                    System.out.println(
                            "Modulo de objetos perdidos pendiente de integrar.");
                    break;

                // MODULO 5 - FELIPE
                case "2": {

                    System.out.println(
                            "\n=== REGISTRAR OBJETO ENCONTRADO ===");

                    System.out.print("Nombre del objeto: ");
                    String nombre = entrada.nextLine();

                    System.out.print("Categoria: ");
                    String categoria = entrada.nextLine();

                    System.out.print("Color: ");
                    String color = entrada.nextLine();

                    System.out.print("Campus: ");
                    String campus = entrada.nextLine();

                    System.out.print("Ubicacion: ");
                    String ubicacion = entrada.nextLine();

                    System.out.print("Fecha (dd/MM/yyyy): ");
                    String fecha = entrada.nextLine();

                    System.out.print("Descripcion: ");
                    String descripcion = entrada.nextLine();

                    String resultado =
                            gestorEncontrados.registrarObjetoEncontrado(
                                    nombre,
                                    categoria,
                                    color,
                                    campus,
                                    ubicacion,
                                    fecha,
                                    descripcion
                            );

                    System.out.println(resultado);
                    break;
                }

                // MODULO 1 - ERICK: CONSULTAR REPORTES
                case "3": {

                    System.out.println(
                            "\n=== CONSULTA DE REPORTES ===");

                    System.out.println("1. Consultar todos");
                    System.out.println("2. Filtrar por categoria");
                    System.out.println("3. Filtrar por ubicacion");
                    System.out.println("4. Filtrar por estado");
                    System.out.print("Seleccione un filtro: ");

                    String filtro = entrada.nextLine();

                    ArrayList<Reporte> resultados = new ArrayList<>();
                    boolean filtroValido = true;

                    switch (filtro) {

                        case "1":
                            resultados = consultor.consultarTodos();
                            break;

                        case "2":
                            System.out.print(
                                    "Ingrese la categoria: ");
                            String categoria = entrada.nextLine();

                            resultados =
                                    consultor.filtrarPorCategoria(categoria);
                            break;

                        case "3":
                            System.out.print(
                                    "Ingrese la ubicacion: ");
                            String ubicacion = entrada.nextLine();

                            resultados =
                                    consultor.filtrarPorUbicacion(ubicacion);
                            break;

                        case "4":
                            System.out.print(
                                    "Ingrese el estado: ");
                            String estado = entrada.nextLine();

                            resultados =
                                    consultor.filtrarPorEstado(estado);
                            break;

                        default:
                            System.out.println("Filtro no valido.");
                            filtroValido = false;
                            break;
                    }

                    if (filtroValido) {
                        mostrarReportes(resultados);
                    }

                    break;
                }


                // MODULO 2 - RAFA
                case "4": {

                    System.out.println("\n=== VALIDACION DE PROPIEDAD ===");

                    System.out.print("Ingrese el ID del reporte: ");
                    String idTexto = entrada.nextLine();

                    try {
                        int id = Integer.parseInt(idTexto);

                        Reporte reporte = gestorDevoluciones.buscarReportePorId(id);

                        System.out.println(
                                gestorDevoluciones.obtenerResumenBasico(reporte));

                        if (reporte != null
                                && !"Devuelto".equalsIgnoreCase(reporte.getEstado())) {

                            System.out.print("Confirme el color del objeto: ");
                            String color = entrada.nextLine();

                            System.out.print("Ingrese una pista del objeto: ");
                            String pista = entrada.nextLine();

                            boolean valido = gestorDevoluciones.validarPropiedad(
                                    id, color, pista);


                            if (valido) {
                                System.out.println(
                                        "Validacion exitosa. Los datos coinciden.");

                                System.out.print("Desea registrar la devolucion? (S/N): ");
                                String respuesta = entrada.nextLine();

                                if (respuesta.equalsIgnoreCase("S")) {

                                    System.out.print("Nombre de quien retira: ");
                                    String nombre = entrada.nextLine();

                                    System.out.print("Cedula o ID universitario: ");
                                    String identificacion = entrada.nextLine();

                                    System.out.print("Fecha de devolucion (dd/MM/yyyy): ");
                                    String fecha = entrada.nextLine();

                                    boolean registrada = gestorDevoluciones.registrarDevolucion(
                                            id, nombre, identificacion, fecha);

                                    if (registrada) {
                                        System.out.println(
                                                "Devolucion registrada correctamente.");
                                        System.out.println(
                                                "Nuevo estado: " + reporte.getEstado());
                                    } else {
                                        System.out.println(
                                                "No se pudo registrar la devolucion.");
                                    }
                                }

                            } else {
                                System.out.println(
                                        "Validacion fallida. Los datos no coinciden.");
                            }

                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Debe ingresar un ID numerico.");
                    }

                    break;
                }

                // MODULO 3 - ALEJANDRO
                case "5":
                    System.out.println(
                            "Modulo de comparacion pendiente de integrar.");
                    break;

                // MODULO 1 - ERICK: ESTADISTICAS
                case "6": {

                    System.out.println(
                            "\n=== ESTADISTICAS CAMPUSLOST ===");

                    System.out.println("Total de reportes: "
                            + consultor.consultarTodos().size());

                    System.out.println("Objetos perdidos: "
                            + consultor.contarPorTipo("Perdido"));

                    System.out.println("Objetos encontrados: "
                            + consultor.contarPorTipo("Encontrado"));

                    System.out.println("Objetos devueltos: "
                            + consultor.contarDevueltos());

                    System.out.println("Objeto mas perdido: "
                            + consultor.obtenerObjetoMasPerdido());

                    System.out.println(
                            "\n=== CONSULTA POR NOMBRE ===");

                    System.out.print(
                            "Ingrese un objeto para contar sus perdidas: ");

                    String nombre = entrada.nextLine();

                    System.out.println("Cantidad de reportes perdidos: "
                            + consultor.contarPerdidosPorNombre(nombre));

                    break;
                }

                case "0":
                    System.out.println(
                            "Gracias por usar CampusLost");
                    break;

                default:
                    System.out.println(
                            "Opcion no valida. Intente nuevamente.");
                    break;
            }

        } while (!opcion.equals("0"));

        entrada.close();
    }

    // Metodo auxiliar para mostrar reportes
    public static void mostrarReportes(ArrayList<Reporte> reportes) {

        if (reportes.isEmpty()) {
            System.out.println("No se encontraron reportes.");
        } else {

            System.out.println("\n=== RESULTADOS ===");

            for (Reporte reporte : reportes) {
                System.out.println(reporte);
            }

            System.out.println("Total de coincidencias: "
                    + reportes.size());
        }
    }
}

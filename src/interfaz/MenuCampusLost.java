package interfaz;

import java.util.ArrayList;
import java.util.Scanner;

import modelo.Coincidencia;
import modelo.Reporte;
import negocio.ComparadorObjetos;
import negocio.ConsultorReportes;
import negocio.GestorDevoluciones;
import negocio.GestorObjetosEncontrados;
import negocio.GestorObjetosPerdidos;
import negocio.RepositorioReportes;

public class MenuCampusLost {

    // Lista de categorias que se muestra al usuario.
    private static final String CATEGORIAS =
            "Electronicos, Accesorios, Documentos, Material academico, Ropa, Llaves, Otros";

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        // Todos los modulos trabajan con este mismo repositorio.
        RepositorioReportes repositorio = new RepositorioReportes();
        ConsultorReportes consultor = new ConsultorReportes(repositorio);
        GestorObjetosPerdidos gestorPerdidos =
                new GestorObjetosPerdidos(repositorio);
        GestorObjetosEncontrados gestorEncontrados =
                new GestorObjetosEncontrados(repositorio);
        GestorDevoluciones gestorDevoluciones =
                new GestorDevoluciones(repositorio);
        ComparadorObjetos comparador = new ComparadorObjetos(repositorio);

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

                // Registro de objetos perdidos (RF07 - RF08): Majo.
                case "1": {
                    System.out.println("\n=== REGISTRAR OBJETO PERDIDO ===");

                    System.out.print("Nombre del objeto: ");
                    String nombre = entrada.nextLine();
                    System.out.println("Categorias disponibles: " + CATEGORIAS);
                    System.out.print("Categoria (escriba una de la lista, sin tildes): ");
                    String categoria = entrada.nextLine();
                    System.out.print("Color: ");
                    String color = entrada.nextLine();
                    System.out.print("Campus: ");
                    String campus = entrada.nextLine();
                    System.out.print("Ubicacion: ");
                    String ubicacion = entrada.nextLine();
                    System.out.print("Fecha de perdida (dd/MM/yyyy): ");
                    String fecha = entrada.nextLine();
                    System.out.print("Descripcion: ");
                    String descripcion = entrada.nextLine();

                    String resultado = gestorPerdidos.registrarObjetoPerdido(
                            nombre, categoria, color, campus,
                            ubicacion, fecha, descripcion);
                    System.out.println(resultado);

                    // Si se registro, se buscan posibles coincidencias entre los hallazgos.
                    if (resultado.startsWith("Objeto perdido registrado")) {
                        ArrayList<Reporte> todos = consultor.consultarTodos();
                        Reporte nuevo = todos.get(todos.size() - 1);
                        ArrayList<Coincidencia> posibles =
                                comparador.buscarCoincidenciasParaReporte(nuevo.getIdReporte());
                        if (!posibles.isEmpty()) {
                            System.out.println("\nSe encontraron posibles coincidencias "
                                    + "con objetos ya encontrados:");
                            for (Coincidencia coincidencia : posibles) {
                                System.out.println(coincidencia);
                            }
                        }
                    }
                    break;
                }

                // Registro de objetos encontrados: Felipe.
                case "2": {
                    System.out.println("\n=== REGISTRAR OBJETO ENCONTRADO ===");

                    System.out.print("Nombre del objeto: ");
                    String nombre = entrada.nextLine();
                    System.out.println("Categorias disponibles: " + CATEGORIAS);
                    System.out.print("Categoria (escriba una de la lista, sin tildes): ");
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

                    String resultado = gestorEncontrados.registrarObjetoEncontrado(
                            nombre, categoria, color, campus,
                            ubicacion, fecha, descripcion);
                    System.out.println(resultado);
                    break;
                }

                // Consultas y filtros: Erick.
                case "3": {
                    System.out.println("\n=== CONSULTA DE REPORTES ===");
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
                            System.out.println("Categorias disponibles: " + CATEGORIAS);
                            System.out.print("Ingrese la categoria (sin tildes): ");
                            resultados = consultor.filtrarPorCategoria(entrada.nextLine());
                            break;
                        case "3":
                            System.out.print("Ingrese la ubicacion: ");
                            resultados = consultor.filtrarPorUbicacion(entrada.nextLine());
                            break;
                        case "4":
                            System.out.print("Ingrese el estado: ");
                            resultados = consultor.filtrarPorEstado(entrada.nextLine());
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

                // Validacion y devolucion: Rafa.
                case "4": {
                    System.out.println("\n=== VALIDACION DE PROPIEDAD ===");
                    System.out.print("Ingrese el ID del reporte: ");
                    int id = leerEntero(entrada);
                    if (id <= 0) {
                        System.out.println("Debe ingresar un ID numerico valido.");
                        break;
                    }

                    Reporte reporte = gestorDevoluciones.buscarReportePorId(id);
                    System.out.println(gestorDevoluciones.obtenerResumenBasico(reporte));
                    if (reporte == null || "Devuelto".equalsIgnoreCase(reporte.getEstado())) {
                        break;
                    }
                    if (!"Encontrado".equalsIgnoreCase(reporte.getTipo())) {
                        System.out.println("Solo se pueden devolver objetos registrados como "
                                + "encontrados.");
                        break;
                    }

                    System.out.print("Confirme el color del objeto: ");
                    String color = entrada.nextLine();
                    System.out.print("Ingrese una pista del objeto: ");
                    String pista = entrada.nextLine();

                    if (!gestorDevoluciones.validarPropiedad(id, color, pista)) {
                        System.out.println("Validacion fallida. Los datos no coinciden.");
                        break;
                    }

                    System.out.println("Validacion exitosa. Los datos coinciden.");
                    System.out.print("Desea registrar la devolucion? (S/N): ");
                    if (!entrada.nextLine().equalsIgnoreCase("S")) {
                        break;
                    }

                    System.out.print("Nombre de quien retira: ");
                    String nombre = entrada.nextLine();
                    System.out.print("Cedula o ID universitario: ");
                    String identificacion = entrada.nextLine();
                    System.out.print("Fecha de devolucion (dd/MM/yyyy): ");
                    String fecha = entrada.nextLine();

                    boolean registrada = gestorDevoluciones.registrarDevolucion(
                            id, nombre, identificacion, fecha);
                    if (registrada) {
                        System.out.println("Devolucion registrada correctamente.");
                        System.out.println("Nuevo estado: " + reporte.getEstado());
                    } else {
                        System.out.println("No se pudo registrar la devolucion.");
                    }
                    break;
                }

                // Comparacion de objetos: Alejandro.
                case "5": {
                    System.out.println("\n=== COMPARACION DE OBJETOS ===");
                    System.out.println("1. Buscar coincidencias para un reporte");
                    System.out.println("2. Buscar todas las coincidencias");
                    System.out.println("3. Comparar dos reportes detalladamente");
                    System.out.println("0. Volver al menu principal");
                    System.out.print("Seleccione una opcion: ");
                    String tipoConsulta = entrada.nextLine();

                    switch (tipoConsulta) {
                        case "1": {
                            System.out.print("ID del reporte base: ");
                            int id = leerEntero(entrada);
                            if (id <= 0) {
                                System.out.println("ID invalido.");
                            } else {
                                mostrarCoincidencias(
                                        comparador.buscarCoincidenciasParaReporte(id));
                            }
                            break;
                        }
                        case "2": {
                            System.out.print("Puntaje minimo (0-100): ");
                            int minimo = leerEntero(entrada);
                            if (minimo < 0 || minimo > 100) {
                                System.out.println("El puntaje debe estar entre 0 y 100.");
                            } else {
                                mostrarCoincidencias(
                                        comparador.buscarTodasLasCoincidencias(minimo));
                            }
                            break;
                        }
                        case "3": {
                            System.out.print("ID del primer reporte: ");
                            int idA = leerEntero(entrada);
                            System.out.print("ID del segundo reporte: ");
                            int idB = leerEntero(entrada);
                            if (idA <= 0 || idB <= 0) {
                                System.out.println("Los IDs deben ser numericos y positivos.");
                            } else {
                                System.out.println(
                                        comparador.compararDosReportesDetallado(idA, idB));
                            }
                            break;
                        }
                        case "0":
                            break;
                        default:
                            System.out.println("Opcion no valida.");
                            break;
                    }
                    break;
                }

                // Estadisticas: Erick.
                case "6": {
                    System.out.println("\n=== ESTADISTICAS CAMPUSLOST ===");
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
                    System.out.println("\n=== CONSULTA POR NOMBRE ===");
                    System.out.print("Ingrese un objeto para contar sus perdidas: ");
                    String nombre = entrada.nextLine();
                    System.out.println("Cantidad de reportes perdidos: "
                            + consultor.contarPerdidosPorNombre(nombre));
                    break;
                }

                case "0":
                    System.out.println("Gracias por usar CampusLost");
                    break;

                default:
                    System.out.println("Opcion no valida. Intente nuevamente.");
                    break;
            }
        } while (!opcion.equals("0"));

        entrada.close();
    }

    public static void mostrarReportes(ArrayList<Reporte> reportes) {
        if (reportes.isEmpty()) {
            System.out.println("No se encontraron reportes.");
        } else {
            System.out.println("\n=== RESULTADOS ===");
            for (Reporte reporte : reportes) {
                System.out.println(reporte);
            }
            System.out.println("Total de coincidencias: " + reportes.size());
        }
    }

    private static void mostrarCoincidencias(ArrayList<Coincidencia> coincidencias) {
        if (coincidencias.isEmpty()) {
            System.out.println("No se encontraron coincidencias.");
            System.out.println("Para comparar, se necesitan reportes Perdido y Encontrado pendientes.");
        } else {
            System.out.println("\n=== COINCIDENCIAS ===");
            for (Coincidencia coincidencia : coincidencias) {
                System.out.println(coincidencia);
            }
            System.out.println("Coincidencias encontradas: " + coincidencias.size());
        }
    }

    private static int leerEntero(Scanner entrada) {
        try {
            return Integer.parseInt(entrada.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}

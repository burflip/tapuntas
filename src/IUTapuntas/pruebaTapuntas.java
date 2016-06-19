/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IUTapuntas;

import ModeloTapuntas.Tapuntas;
import ModeloTapuntas.TipoTransaccion;
import java.util.*;

/**
 *
 * @author aanaya
 */
public class pruebaTapuntas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Obtener la única instancia de la clase BuenProvecho (patrón sigleton)
        Tapuntas aViajar = Tapuntas.getInstance();

        // Definir la variable que nos permite leer String desde teclado
        final Scanner in = new Scanner(System.in);
        String input;
        int opcion = 0;
        /**
         * ----------------------------------------------------------------------
         * INSERCIÓN DE DATOS DE PRUEBA Comentar las siguientes líneas para no
         * insertar los datos de prueba
         * ----------------------------------------------------------------------
         */
        try {
            insertarDatosDePrueba();
        } catch (Exception ex) {
            System.err.println("Se ha producido la siguiente excepcion: " + ex);
        }
        /**
         * ----------------------------------------------------------------------
         * FIN INSERCIÓN DE DATOS DE PRUEBA
         * ----------------------------------------------------------------------
         */
        do {
            try { // tratamiento de las excepciones. Bloque try en el que se puede producir una excepción y la capturamos

                //Terminar de diseñar el menú (usando System.out.println(...)) con las opciones que faltan
                // Podéis hacer vuestros propios diseños de interfaz, esta es la interfaz mínima que tenéis que entregar
                System.out.println("\n\n*********************************** MENU ***********************************\n"
                        + "GESTIÓN DE USUARIOS   \n"
                        + "\t10. Nuevo Usuario \n"
                        + "\t11. Consultar usuarios del sistema \n"
                        + "\t12. Incluir Perfil de Usuario \n"
                        + "\t13. Consultar Perfil de un Usuario \n");

                System.out.println("GESTIÓN DE VEHICULOS  \n"
                        + "\t20. Nuevo vehículo \n"
                        + "\t21. Consultar vehículos de un usuario \n"
                        + "\t22. Eliminar vehículo\n");

                System.out.println("GESTIÓN DE PLANES DE ALQUILER  \n"
                        + "\t30. Definir nuevo plan de alquiler \n"
                        + "\t31. Consultar mis planes de alquiler\n"
                        + "\t32. Ofertar un plan de alquiler \n"
                        + "\t33. Buscar ofertas de planes de alquiler \n");

                System.out.println("\n**********************************************************************");

                System.out.println("\t0. TERMINAR");
                System.out.println("\n**********************************************************************");

                // Lectura de un int, para darle valor a opcion.
                opcion = Integer.parseInt(in.nextLine());

                // Estructura switch con todas las opciones de menú. Algunos de ellos ya lo tenéis hecho
                // Tenéis que terminar las opciones que están incompletas y las que no están hechas
                switch (opcion) {
                    case 10: //incluir un nuevo usuario en el sistema 

                        System.out.print("Nombre de Usuario:");
                        String nombreUsuario = in.nextLine();

                        System.out.print("Clave:");
                        String claveUsuario = in.nextLine();

                        System.out.print("Dirección de correo:");
                        String correoUsuario = in.nextLine();

                        aViajar.altaRegistro(nombreUsuario, claveUsuario, correoUsuario);
                        System.out.print("++++++  Operación realizada con éxito ++++++");
                        break;

                    case 11:/*Ver usuarios del sistema */
                        List<ArrayList<Object>> usuarios = aViajar.obtenerUsuarios();
                        System.out.println("Actualmente tenemos " + usuarios.size() + " usuario" + ((usuarios.size() > 1) ? "s" : "") + ".");
                        usuarios.stream().forEach((usuario) -> {
                            imprimirLista(usuario);
                        });
                        break;

                    case 12:/*Incluir Perfil */
                        System.out.print("Nombre de Usuario:");
                        nombreUsuario = in.nextLine();

                        System.out.print("Nombre del Usuario:");
                        String nombre = in.nextLine();

                        System.out.print("Telefono:");
                        String telefono = in.nextLine();

                        System.out.print("Breve descripcion personal:");
                        String breveDescripcionPersonal = in.nextLine();

                        boolean preferenciaElegida = false;
                        String pCobro = "0";
                        while (!preferenciaElegida && TipoTransaccion.values().length > 0) {
                            System.out.println("Preferencia de cobro (elige uno de los siguientes):");
                            int i = 1;
                            for (TipoTransaccion t : TipoTransaccion.values()) {
                                System.out.println(i + ":" + t);
                                i++;
                            }
                            System.out.print("Elección:");
                            pCobro = in.nextLine();
                            if (Integer.parseInt(pCobro) >= 1 && Integer.parseInt(pCobro) < i) {
                                preferenciaElegida = true;
                            } else {
                                System.out.println("Debes elegir un número entre 1 y " + i);
                            }
                        }

                        if (Integer.parseInt(pCobro) <= 0) {
                            throw new Exception("No hay Tipos de Transacción definidos");
                        }

                        TipoTransaccion preferenciaCobro = TipoTransaccion.values()[Integer.parseInt(pCobro) - 1];

                        aViajar.introducirPerfil(nombreUsuario, nombre, telefono, breveDescripcionPersonal, (TipoTransaccion) preferenciaCobro);
                        System.out.println("++++++  Operación realizada con éxito ++++++");
                        break;
                    case 13:/*Consultar perfil */
                        System.out.print("Nombre de Usuario:");
                        nombreUsuario = in.nextLine();

                        List<Object> infoPerfil = aViajar.consultarPerfil(nombreUsuario);
                        imprimirLista(infoPerfil, "Perfil de '" + nombreUsuario + "'");
                        break;

                    case 20:
                        /*Nuevo vehículo */
                        System.out.print("Nombre de Usuario al que añadir el vehículo:");
                        nombreUsuario = in.nextLine();

                        System.out.print("Matrícula:");
                        String matricula = in.nextLine();

                        System.out.print("Marca:");
                        String marca = in.nextLine();

                        System.out.print("Modelo:");
                        String modelo = in.nextLine();

                        System.out.print("Color:");
                        String color = in.nextLine();

                        System.out.print("Número de plazas:");
                        int numeroPlazas = Integer.parseInt(in.nextLine());

                        System.out.print("Categoría:");
                        String categoria = in.nextLine();

                        System.out.print("Confort:");
                        String confor = in.nextLine();

                        aViajar.añadirVehículo(nombreUsuario, matricula, marca, modelo, color, numeroPlazas, categoria, confor);
                        System.out.println("++++++  Operación realizada con éxito ++++++");

                        break;

                    case 21:
                        /* Consultar vehículos de un usuario  */
                        System.out.print("Nombre de Usuario:");
                        nombreUsuario = in.nextLine();
                        List<ArrayList<Object>> vehiculosUsuario = aViajar.obtenerVehiculosUsuario(nombreUsuario);
                        System.out.println("El usuario " + nombreUsuario + " tiene " + vehiculosUsuario.size() + " vehiculo" + ((vehiculosUsuario.size() > 1) ? "s" : "") + " .");
                        vehiculosUsuario.stream().forEach((vehiculo) -> {
                            imprimirLista(vehiculo, "");
                        });
                        break;

                    case 22:
                        /* Eliminar vehículo  */
                        System.out.print("Nombre de Usuario:");
                        nombreUsuario = in.nextLine();

                        if (aViajar.usuarioTieneVehiculos(nombreUsuario)) {
                            System.out.print("El usuario posee los siguientes vehículos:");
                            imprimirMatriculasUsuario(nombreUsuario);

                            System.out.print("Matrícula:");
                            matricula = in.nextLine();

                            aViajar.eliminarVehículo(nombreUsuario, matricula);
                            System.out.println("++++++  Operación realizada con éxito ++++++");
                        } else {
                            System.out.println("Ese usuario no tiene vehículos");
                        }

                        break;

                    case 30:
                        /* Nuevo plan de alquiler */
                        System.out.print("Nombre de Usuario al que añadir el plan de Alquiler:");
                        nombreUsuario = in.nextLine();

                        if (aViajar.usuarioTieneVehiculos(nombreUsuario)) {
                            System.out.print("El usuario posee los siguientes vehículos:");
                            imprimirMatriculasUsuario(nombreUsuario);

                            System.out.print("Matrícula:");
                            matricula = in.nextLine();

                            System.out.print("Fecha inicio----------------");
                            System.out.print("Dia : ");
                            int diaInicio = Integer.parseInt(in.nextLine());
                            System.out.print("Mes : ");
                            int mesInicio = Integer.parseInt(in.nextLine());
                            System.out.print("Año : ");
                            int añoInicio = Integer.parseInt(in.nextLine());

                            GregorianCalendar fechaInicio = new GregorianCalendar(añoInicio, mesInicio - 1, diaInicio);

                            System.out.print("Fecha fin----------------");
                            System.out.print("Dia : ");
                            int diaFinal = Integer.parseInt(in.nextLine());
                            System.out.print("Mes (" + mesInicio + ") : ");
                            input = in.nextLine();
                            int mesFinal = (input.equals("")) ? mesInicio : Integer.parseInt(input);
                            System.out.print("Año (" + añoInicio + "): ");
                            input = in.nextLine();
                            int añoFinal = (input.equals("")) ? añoInicio : Integer.parseInt(input);

                            GregorianCalendar fechaFin = new GregorianCalendar(añoFinal, mesFinal - 1, diaFinal);

                            System.out.print("Ciudad recogida:");
                            String ciudadRecogida = in.nextLine();

                            aViajar.definirPlanAlquiler(nombreUsuario, matricula, fechaInicio, fechaFin, ciudadRecogida);
                            System.out.println("++++++  Operación realizada con éxito ++++++");
                        } else {
                            System.out.println("Ese usuario no tiene vehículos");
                        }
                        break;

                    case 31:
                        /* Consultar planes de alquiler de un usuario */
                        System.out.print("Nombre de Usuario:");
                        nombreUsuario = in.nextLine();

                        List<ArrayList<Object>> planesAlquiler = aViajar.obtenerPlanesAlquiler(nombreUsuario);
                        System.out.println("El usuario '" + nombreUsuario + "' tiene " + planesAlquiler.size() + "plane" + ((planesAlquiler.size() > 1) ? "s" : "") + " de alquiler.");
                        planesAlquiler.stream().forEach((datosPlanAlquiler) -> {
                            imprimirLista(datosPlanAlquiler);
                        });
                        break;
                    case 32:
                        /* Ofertar un plan de alquiler */

                        System.out.print("Nombre de Usuario:");
                        nombreUsuario = in.nextLine();

                        if (aViajar.usuarioTieneVehiculos(nombreUsuario)) {
                            System.out.print("El usuario posee los siguientes vehículos:");
                            imprimirMatriculasUsuario(nombreUsuario);

                            System.out.print("Matrícula:");
                            matricula = in.nextLine();

                            System.out.println("Fecha inicio----------------");
                            System.out.print("Dia : ");
                            int diaInicio = Integer.parseInt(in.nextLine());
                            System.out.print("Mes : ");
                            int mesInicio = Integer.parseInt(in.nextLine());
                            System.out.print("Año : ");
                            int añoInicio = Integer.parseInt(in.nextLine());

                            GregorianCalendar fechaInicio = new GregorianCalendar(añoInicio, mesInicio - 1, diaInicio);

                            aViajar.ofertarPlanAlquiler(nombreUsuario, fechaInicio, matricula);
                            System.out.println("++++++  Operación realizada con éxito ++++++");
                        } else {
                            System.out.println("Ese usuario no tiene vehículos");
                        }
                        break;

                    case 33:
                        /* Buscar ofertas de planes de alquiler  */
                        System.out.print("Ciudad recogida:");
                        String ciudadRecogida = in.nextLine();

                        System.out.println("Fecha inicio----------------");
                        System.out.print("Dia : ");
                        int diaInicio = Integer.parseInt(in.nextLine());
                        System.out.print("Mes : ");
                        int mesInicio = Integer.parseInt(in.nextLine());
                        System.out.print("Año : ");
                        int añoInicio = Integer.parseInt(in.nextLine());

                        GregorianCalendar fechaInicio = new GregorianCalendar(añoInicio, mesInicio - 1, diaInicio);

                        System.out.println("Fecha fin----------------");
                        System.out.print("Dia : ");
                        int diaFinal = Integer.parseInt(in.nextLine());
                        System.out.print("Mes (" + mesInicio + ") : ");
                        input = in.nextLine();
                        int mesFinal = (input.equals("")) ? mesInicio : Integer.parseInt(input);
                        System.out.print("Año (" + añoInicio + "): ");
                        input = in.nextLine();
                        int añoFinal = (input.equals("")) ? añoInicio : Integer.parseInt(input);

                        GregorianCalendar fechaFin = new GregorianCalendar(añoFinal, mesFinal - 1, diaFinal);

                        List<List<Object>> ofertas = aViajar.buscarOfertasAlquiler(ciudadRecogida, fechaInicio, fechaFin);
                        if (ofertas.isEmpty()) {
                            System.out.println("No hay ofertas disponibles para las fechas seleccionadas.");
                        } else {
                            System.out.println("Ofertas disponibles:");
                            System.out.println("----------------------------");
                            for (List<Object> oferta : ofertas) {
                                for (Object datos : oferta) {
                                    if (datos.getClass().equals(ArrayList.class)) {
                                        System.out.println("Coste alquiler:" + ((ArrayList) datos).get(0));
                                        System.out.print("Datos del vehículo:");
                                        imprimirLista((ArrayList<Object>) ((ArrayList) datos).get(1));
                                    } else {
                                        System.out.println(datos);
                                    }
                                }
                                System.out.println("----------------------------");
                            }
                        }

                        break;

                    case 0:
                        /* terminar */
                        break;

                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
//               
            } catch (Exception ex) { // captura de la excepción
                System.err.println("Se ha producido la siguiente excepcion: " + ex);
            }
        } while (opcion != 0);
        System.exit(0);
    }

    private static void imprimirLista(List lista) {
        imprimirLista(lista, "");
    }

    private static void imprimirLista(List lista, String titulo) {
        Iterator it_titulos = ((ArrayList<Object>) lista.get(0)).iterator();
        Iterator it_elems = ((ArrayList<Object>) lista.get(1)).iterator();

        System.out.println("\n-------------" + titulo + "---------------");
        while (it_titulos.hasNext() && it_elems.hasNext()) {
            System.out.println(it_titulos.next() + ": " + it_elems.next());
        }
        String slices = "";
        for (int i = 0; i < titulo.length(); i++) {
            slices += "-";
        }
        System.out.println("-------------" + slices + "---------------");
    }

    private static void imprimirMatriculasUsuario(String nombreUsuario) {
        Tapuntas aViajar = Tapuntas.getInstance();
        ArrayList<String> matriculas = new ArrayList<>(aViajar.obtenerMatriculasUsuario(nombreUsuario));
        System.out.println("\n----------------------------");
        matriculas.stream().forEach((matricula) -> {
            System.out.println(matricula);
        });
        System.out.println("----------------------------");
    }

    private static void insertarDatosDePrueba() throws Exception {
        Tapuntas aViajar = Tapuntas.getInstance();
        String nombreUsuario = "antony", matricula = "6458HVN";
        aViajar.altaRegistro(nombreUsuario, "clavesecreta", "correo@gmail.com");
        aViajar.introducirPerfil(nombreUsuario, "Antonio Martín Garzón", "658628314", "Soy buena gente.", TipoTransaccion.TARJETA);
        aViajar.añadirVehículo(nombreUsuario, matricula, "Renault", "Megane", "Azul", 5, "Sedán", "Alto");
        aViajar.definirPlanAlquiler(nombreUsuario, matricula, new GregorianCalendar(2016, 5, 20), new GregorianCalendar(2016, 5, 30), "Granada");
        aViajar.ofertarPlanAlquiler(nombreUsuario, new GregorianCalendar(20, 6, 2016), matricula);
    }

}

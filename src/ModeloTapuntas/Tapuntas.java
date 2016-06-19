package ModeloTapuntas;

import java.util.*;

public class Tapuntas {

    private Map<String, Usuario> usuarios = new HashMap();

    private static Tapuntas instance = null;

    /**
     * Hacemos privado el constructor siguiendo el patrón singleton
     */
    private Tapuntas() {
    }

    /**
     * Devuelve la instancia de Tapuntas si existe y si no, la crea
     * @return Instancia de Tapuntas
     */
    public static Tapuntas getInstance() {
        if (instance == null) {
            createInstance();
        }
        return instance;
    }

    /**
     * Crea la instancia de Tapuntas de forma sincronizada en caso de que la
     * instancia no esté creada
     */
    private static void createInstance() {
        if (instance == null) {
            synchronized (Tapuntas.class) {
                if (instance == null) {
                    instance = new Tapuntas();
                }
            }
        }
    }

    /**
     * Hacemos que no se pueda clonar Tapuntas usando: Tapuntas Tapuntasclonado
     * = (Tapuntas) aViajar.clone();
     *
     * @return Siempre se lanza la excepción antes de que devuelva el objeto.
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * Crea un nuevo usuario
     *
     * @param nombreUsuario Nombre del usuario
     * @param contraseña Contraseña del usuario, que se encriptará usando un
     * algoritmo PBKDF2: https://en.wikipedia.org/wiki/PBKDF2
     * @param direccionCorreo Dirección de correo electrónico
     * @throws Exception
     */
    public void altaRegistro(String nombreUsuario, String contraseña, String direccionCorreo) throws Exception {
        if (usuarios.containsKey(nombreUsuario)) {
            throw new Exception("ya existe un usuario con ese nombre de usuario");
        }
        Usuario unUsuario = new Usuario(nombreUsuario, contraseña, direccionCorreo);
        unUsuario.modificarVisibilidad(false);
        usuarios.put(nombreUsuario, unUsuario);
    }

    /**
     * Crea un nuevo vehículo y lo asocia a un usuario concreto.
     *
     * @param nombreUsuario Nombre del usuario al que asociar vehículo
     * @param matricula Matrícula del vehículo
     * @param marca Marca del vehículo
     * @param modelo Modelo del vehículo
     * @param color Color del vehículo
     * @param numeroPlazas Número de plazas del vehículo
     * @param categoria Categoría del vehículo
     * @param confor Confort del vehículo
     * @throws Exception Si existe un vehículo con esa matrícula en el sistema
     */
    public void añadirVehículo(String nombreUsuario, String matricula, String marca, String modelo, String color, int numeroPlazas, String categoria, String confor) throws Exception {
        boolean existe = false;
        if (existeVehiculo(matricula)) {
            throw new Exception("ya existe otro vehículo en el sistema con esa matrícula");
        }
        Usuario usuario = this.buscarUsuario(nombreUsuario);
        usuario.nuevoVehículo(matricula, marca, modelo, color, numeroPlazas, categoria, confor);
    }

    /**
     * Busca las ofertas de alquiler en un intervalo de fechas y para una ciudad
     * concreta. En el diseño NO se ha tenido en cuenta la visibilidad de la
     * oferta, así que en la obtenerPlanesQueCumplanRequisitos he incluido ambas
     * implementaciones, la que yo creo correcta comentada.
     *
     * @param ciudadRecogida Ciudad para la que se buscan planes de alquiler
     * @param fechaInicio Fecha de inicio de la búsqueda, del tipo
     * GregorianCalendar
     * @param fechaFin Fecha de fin de la búsqueda, del tipo GregorianCalendar
     * @return Lista de Ofertas ordenadas por nombre de usuario
     */
    public List buscarOfertasAlquiler(String ciudadRecogida, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
        List<List<Object>> listaOfertas = new ArrayList<>();
        for (String u_key : usuarios.keySet()) {
            Usuario usuario = usuarios.get(u_key);
            List<Object> datosPAUsuario = usuario.obtenerPlanesQueCumplanRequisitos(ciudadRecogida, fechaInicio, fechaFin);
            listaOfertas.add(datosPAUsuario);
        }
        return ordenarOfertas(listaOfertas);
    }

    /**
     * Ordena las ofertas por el nombre del usuario al que pertenecen. De la
     * forma en la que está planteado el diagrama de comunicación, no se pueden
     * ordenar correctamente por fecha o valoración, lo lógico habría sido que
     * se ordenaran por fecha, pero como cada usuario tiene varios planes, eso
     * hace que no se puedan ordenar por fecha.
     *
     * @param listaOfertas Lista de las ofertas sin ordenar
     * @return listaOfertas Lista de ofertas ordenadas
     */
    private List ordenarOfertas(List<List<Object>> listaOfertas) {
        Collections.sort(listaOfertas, (o1, o2) -> ((String) o1.get(0)).compareTo(((String) o2.get(0))));
        return listaOfertas;
    }

    public void definirPlanAlquiler(String nombreUsuario, String matricula, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, String ciudadRecogida) throws Exception {
        Usuario usuario = buscarUsuario(nombreUsuario);
        usuario.definirPlanAlquiler(matricula, fechaInicio, fechaFin, ciudadRecogida);
    }

    /**
     * Elimina un vehículo de un usuario
     *
     * @param nombreUsuario Nombre del usuario a buscar
     * @param matricula Matrícula del vehículo a eliminar
     * @throws Exception
     */
    public void eliminarVehículo(String nombreUsuario, String matricula) throws Exception {
        Usuario usuario = buscarUsuario(nombreUsuario);
        usuario.eliminarVehículo(matricula);
    }

    /**
     * Sirve para introducir el perfil de un usuario en el sistema
     *
     * @param nombreUsuario Nombre del usuario al que se introduce el perfil.
     * @param nombre Nombre real del usuario
     * @param telefono Teléfono del usuario
     * @param breveDescripcion Breve descripción del usuario
     * @param preferenciaCobro Preferencia de cobro de tipo TipoTransaccion
     * @throws Exception
     */
    public void introducirPerfil(String nombreUsuario, String nombre, String telefono, String breveDescripcion, TipoTransaccion preferenciaCobro) throws Exception {
        Usuario usuario = buscarUsuario(nombreUsuario);
        if (usuario.isPerfilDefinido()) {
            throw new Exception("el usuario ya tiene un perfil definido");
        }
        usuario.introducirPerfil(nombre, telefono, breveDescripcion, preferenciaCobro);
    }

    /**
     * Devuelve los planes de alquiler de un usuario dado
     *
     * @param nombreUsuario Nombre del usuario a buscar
     * @return misPlanesAlquiler Lista de PlanAlquiler con los planes de un
     * usuario
     */
    public List obtenerPlanesAlquiler(String nombreUsuario) {
        List<PlanAlquiler> misPlanesAlquiler;
        Usuario usuario = buscarUsuario(nombreUsuario);
        misPlanesAlquiler = new ArrayList<>(usuario.obtenerPlanesAlquiler());
        return misPlanesAlquiler;
    }

    /**
     * Devuelve la información del perfil del usuario en cuestión
     *
     * @param nombreUsuario Nombre del usuario a buscar
     * @return infoPerfil Lista con la información de usuario imprimible por
     * imprimirLista
     * @throws Exception
     */
    public List consultarPerfil(String nombreUsuario) throws Exception {
        Usuario usuario = buscarUsuario(nombreUsuario);
        List<Object> infoPerfil;
        // He hecho que usuario.consultarPerfil() lance una excepción en caso de que el usuario no tenga perfil definido
        infoPerfil = new ArrayList<>(usuario.consultarPerfil());
        return infoPerfil;
    }

    /**
     * Sirve para ofertar un plan de alquiler. Dado el diseño actual, NO se
     * tiene en cuenta para la búsqueda. En la búsqueda se ha especificado cómo
     * sí se tendría en cuenta.
     *
     * @param nombreUsuario Nombre del usuario que va a ofertar el plan
     * @param fechaInicio Identificador formado por fechaInicio+matricula
     * @param matricula Identificador formado por fechaInicio+matricula
     */
    public void ofertarPlanAlquiler(String nombreUsuario, GregorianCalendar fechaInicio, String matricula) {
        Usuario usuario = buscarUsuario(nombreUsuario);
        usuario.ofertarPlanAlquiler(fechaInicio, matricula);
    }

    /**
     * Devuelve si existe un usuario.
     *
     * @param nombreUsuario Nombre del usuario a buscar
     * @return true si existe, false si no
     */
    private boolean existeUsuario(String nombreUsuario) {
        return (usuarios.get(nombreUsuario) != null);
    }

    /**
     * Devuelve la instancia del usuario buscado
     *
     * @param nombreUsuario Nombre del usuario a buscar
     * @return Instancia de Usuario o null, si no se encuentra.
     */
    private Usuario buscarUsuario(String nombreUsuario) {
        return usuarios.get(nombreUsuario);
    }

    /**
     * Comprueba si un usuario tiene algún vehículo
     *
     * @param nombreUsuario usuario sobre el que se comprueba
     * @return true si existe, false si no.
     */
    public boolean usuarioTieneVehiculos(String nombreUsuario) {
        return buscarUsuario(nombreUsuario).tengoVehiculos();
    }

    /**
     * Comprueba si existe un vehículo en todo el sistema
     *
     * @param matricula Matrícula a buscar
     * @return existe True si existe, false si no
     */
    private boolean existeVehiculo(String matricula) {
        boolean existe = false;
        for (Usuario usuario : usuarios.values()) {
            if (usuario.buscarVehiculo(matricula) != null) {
                existe = true;
            }
        }
        return existe;
    }

    /**
     * Método que devuelve una lista con los datos de los usuarios. No estaba
     * planteado en el diseño, pero se ha hecho necesario para uno de los
     * elementos del menú.
     *
     * @return datosUsuarios Lista de datos de usuarios imprimible por el método
     * imprimirLista
     */
    public List obtenerUsuarios() {
        List<Object> datosUsuarios = new ArrayList<>();
        usuarios.entrySet().stream().forEach((usuario) -> {
            List<Object> datosUsuario = new ArrayList<>();
            ArrayList<Object> titulos = new ArrayList<>();
            ArrayList<Object> elems = new ArrayList<>();

            titulos.add("Nombre de usuario");
            titulos.add("Teléfono");
            titulos.add("Contraseña");
            elems.add(usuario.getKey());
            elems.add(usuario.getValue().getDireccionCorreo());
            elems.add(usuario.getValue().getContraseña());

            datosUsuario.add(titulos);
            datosUsuario.add(elems);
            datosUsuarios.add(datosUsuario);
        });

        return datosUsuarios;
    }

    /**
     * Devuelve los vehículos de un usuario.
     *
     * @param nombreUsuario Nombre del usuario a devolver los vehículos
     * @return vehiculosUsuario Lista de vehículos del usuario imprimible por el
     * método imprimirLista
     */
    public List obtenerVehiculosUsuario(String nombreUsuario) {
        Usuario usuario = buscarUsuario(nombreUsuario);
        List<Object> vehiculosUsuario = new ArrayList<>();

        Map<String, Vehiculo> vUsuarios = usuario.getVehiculos();
        vUsuarios.entrySet().stream().forEach((vehiculo) -> {
            List<Object> datosVehiculo = new ArrayList<>(vehiculo.getValue().obtenerDatosVehiculo());
            vehiculosUsuario.add(datosVehiculo);
        });

        return vehiculosUsuario;
    }

    /**
     * Devuelve la lista de matrículas de los vehículos de un usuario para
     * mejorar la experiencia de usuario.
     *
     * @param nombreUsuario
     * @return matriculas Lista de las matrículas
     */
    public List obtenerMatriculasUsuario(String nombreUsuario) {
        Usuario usuario = buscarUsuario(nombreUsuario);
        Map<String, Vehiculo> vehiculosUsuario = usuario.getVehiculos();
        ArrayList<String> matriculas = new ArrayList<>();
        vehiculosUsuario.entrySet().stream().forEach((vehiculo) -> {
            matriculas.add(vehiculo.getKey());
        });
        return matriculas;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloTapuntas;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author aanaya
 */
class Usuario {

    private String nombreUsuario;
    private String contraseña;
    private String direccionCorreo;
    private boolean visibilidad = false;
    private boolean pendienteBaja = false;
    private String nombre;
    private String telefono;
    private String breveDescripcionPersonal;
    private TipoTransaccion preferenciaCobro;
    private boolean perfilDefinido = false;
    private ArrayList<PlanAlquiler> planesAlquiler = new ArrayList();
    private Map<String, Vehiculo> vehiculos = new HashMap();

    /**
     * Variables para encriptación de contraseñas con PBKDF2
     */
    public static final String ID = "$31$";
    public static final int DEFAULT_COST = 16;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int SIZE = 128;
    private static final Pattern layout = Pattern.compile("\\$31\\$(\\d\\d?)\\$(.{43})");
    private final SecureRandom random;
    private final int cost;

    /**
     * Constructor de la clase usuario.
     *
     * @param nombreUsuario Nombre del nuevo usuario
     * @param contraseña Contraseña del nuevo usuario
     * @param direccionCorreo Email del nuevo usuario
     */
    Usuario(String nombreUsuario, String contraseña, String direccionCorreo) {
        this.cost = DEFAULT_COST;
        this.random = new SecureRandom();
        crear(nombreUsuario, contraseña, direccionCorreo);
    }

    /**
     * Constructor de la clase usuario. Permite encriptación opcional de
     * contraseña
     *
     * @param nombreUsuario Nombre del nuevo usuario
     * @param contraseña Contraseña del nuevo usuario
     * @param direccionCorreo Email del nuevo usuario
     * @param noHash True si no queremos encriptar la contraseña y false si sí
     * la queremos encriptada
     */
    Usuario(String nombreUsuario, String contraseña, String direccionCorreo, boolean noHash) {
        this.cost = DEFAULT_COST;
        this.random = new SecureRandom();
        crear(nombreUsuario, contraseña, direccionCorreo, noHash);
    }

    /**
     * Crea un nuevo usuario
     *
     * @param nombreUsuario Nombre del nuevo usuario
     * @param contraseña Contraseña del nuevo usuario
     * @param direccionCorreo Email del nuevo usuario
     */
    protected final void crear(String nombreUsuario, String contraseña, String direccionCorreo) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = hash(contraseña);
        this.direccionCorreo = direccionCorreo;
    }

    /**
     * Crea un nuevo usuario. Permite encriptación opcional de contraseña
     *
     * @param nombreUsuario Nombre del nuevo usuario
     * @param contraseña Contraseña del nuevo usuario
     * @param direccionCorreo Email del nuevo usuario
     * @param noHash True si no queremos encriptar la contraseña y false si sí
     * la queremos encriptada
     */
    protected final void crear(String nombreUsuario, String contraseña, String direccionCorreo, boolean noHash) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = (noHash) ? contraseña : hash(contraseña);
        this.direccionCorreo = direccionCorreo;
    }

    /**
     * Modifica el valor de visibilidad. Actualmente en el diseño NO se tiene en
     * cuenta que el usuario esté invisible a la hora de mostrar todos los
     * usuarios.
     *
     * @param visibilidad True si queremos que el usuario sea visible y false si
     * no.
     */
    protected void modificarVisibilidad(boolean visibilidad) {
        this.visibilidad = visibilidad;
    }

    /**
     * Creamos un nuevo vehículo para este usuario.
     *
     * @param nombreUsuario Nombre del usuario al que asociar vehículo
     * @param matricula Matrícula del vehículo
     * @param marca Marca del vehículo
     * @param modelo Modelo del vehículo
     * @param color Color del vehículo
     * @param numeroPlazas Número de plazas del vehículo
     * @param categoria Categoría del vehículo
     * @param confor Confort del vehículo
     */
    protected void nuevoVehículo(String matricula, String marca, String modelo, String color, int numeroPlazas, String categoría, String confor) {
        Vehiculo miVehiculo = new Vehiculo();
        miVehiculo.crear(matricula, marca, modelo, color, numeroPlazas, categoría, confor);
        vehiculos.put(matricula, miVehiculo);
    }

    /**
     * Obtiene los Planes de Alquiler del usuario que cumplen los requisitos.
     * Aunque en el diseño no estaba contemplado, he dejado comentada la
     * condición (cumpleRequisitos) que hace que si no se oferta primero un
     * alquiler no aparezca en la lista de buscados. Sólo habría que
     * descomentarla y comentar la otra para que funcionara.
     *
     * @param ciudadRecogida Ciudad para la que se buscan planes de alquiler
     * @param fechaInicio Fecha de inicio de la búsqueda, del tipo
     * GregorianCalendar
     * @param fechaFin Fecha de fin de la búsqueda, del tipo GregorianCalendar
     * @return Lista de Planes de alquiler del usuario junto con sus datos
     */
    protected List obtenerPlanesQueCumplanRequisitos(String ciudadRecogida, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
        List<Object> datosPAUsuario = new ArrayList<>();
        datosPAUsuario.add(nombre);
        datosPAUsuario.add(preferenciaCobro);
        boolean hay_ofertas = false;
        for (PlanAlquiler PA : planesAlquiler) {
            //System.out.println("Primer dia: " + PA.getPrimerDiaAlquiler().toString() + " FechaInicio: " + fechaInicio.toString() + " Ultimo dia:"+PA.getUltimoDiaAlquiler().toString()+ " Fecha fin:"+fechaFin.toString());
            //System.out.println(PA.getCiudadRecogida().equals(ciudadRecogida) + " && " + PA.getPrimerDiaAlquiler().after(fechaInicio) +" && "+ PA.getUltimoDiaAlquiler().before(fechaFin));
            //boolean cumpleRequisitos = PA.getCiudadRecogida().equals(ciudadRecogida) && PA.getPrimerDiaAlquiler().after(fechaInicio) && PA.getUltimoDiaAlquiler().before(fechaFin) && PA.isVisible();
            boolean cumpleRequisitos = PA.getCiudadRecogida().equals(ciudadRecogida) && PA.getPrimerDiaAlquiler().after(fechaInicio) && PA.getUltimoDiaAlquiler().before(fechaFin);
            if (cumpleRequisitos) {
                //System.out.println("added");
                datosPAUsuario.add(PA.obtenerDatosPA());
                hay_ofertas = true;
            }
        }
        if (!hay_ofertas) {
            datosPAUsuario = new ArrayList<>();
        }
        return datosPAUsuario;
    }

    /**
     * Define un plan de alquiler para un usuario.
     *
     * @param matricula Matrícula del coche
     * @param fechaInicio Fecha de inicio del plan
     * @param fechaFin Fecha de fin del plan
     * @param ciudadRecogida Ciudad de recogida del plan
     * @throws Exception
     */
    protected void definirPlanAlquiler(String matricula, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, String ciudadRecogida) throws Exception {
        Vehiculo vehiculo = buscarVehiculo(matricula);
        boolean disponible = vehiculo.estasDisponible(fechaInicio, fechaFin);
        if (!disponible) {
            throw new Exception("el vehículo ya pertenece a un plan alquiler en esas fechas");
        }
        PlanAlquiler miPlanAlquiler = new PlanAlquiler();
        miPlanAlquiler.crear(vehiculo, fechaInicio, fechaFin, ciudadRecogida);
        planesAlquiler.add(miPlanAlquiler);
        vehiculo.incluirPlanAlquiler(miPlanAlquiler);
    }

    /**
     * Elimina el vehículo de un usuario.
     *
     * @param matricula Matrícula del vehículo
     * @throws Exception El vehículo tiene planes de alquiler asociados
     */
    protected void eliminarVehículo(String matricula) throws Exception {
        Vehiculo vehiculo = buscarVehiculo(matricula);
        boolean alquilado = vehiculo.comprobarEstadoAlquileres();
        if (!alquilado) {
            vehiculo.eliminarVehiculoAlquileres();
            vehiculos.remove(vehiculo.obtenerMatrícula());
        } else {
            throw new Exception("el vehículo no se puede eliminar, tiene vigentes alquileres o viajes");
        }
    }

    /**
     * Introduce el perfil de un usuario.
     *
     * @param nombre Nombre real del usuario
     * @param telefono Teléfono del usuario
     * @param breveDescripcion Breve descripción del usuario
     * @param preferenciaCobro Preferencia de cobro de tipo TipoTransaccion
     */
    protected void introducirPerfil(String nombre, String telefono, String breveDescripcion, TipoTransaccion preferenciaCobro) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.breveDescripcionPersonal = breveDescripcion;
        this.preferenciaCobro = preferenciaCobro;
        this.modificarVisibilidad(true);
        this.perfilDefinido = true;
    }

    /**
     * Devuelve los planes de alquiler del usuario.
     *
     * @return misPlanesAlquiler Lista de PlanAlquiler con los planes del
     * usuario
     */
    protected List obtenerPlanesAlquiler() {
        List<Object> misPlanesAlquiler = new ArrayList<>();
        //System.out.println("PLANES: "+planesAlquiler);
        for (PlanAlquiler pa : planesAlquiler) {
            //System.out.println("isVisible:" +pa.isVisible()+ " vigencia: "+ pa.vigente());
            if (!pa.isVisible() && pa.vigente()) {
                List<Object> datosPlanAlquiler = new ArrayList<>(pa.obtenerDatosPlanAlquiler());
                misPlanesAlquiler.add(datosPlanAlquiler);
            }
        }
        return misPlanesAlquiler;
    }

    /**
     * Devuelve los datos del perfil de un usuario. He hecho que
     * consultarPerfil() lance una excepción en caso de que el usuario no tenga
     * perfil definido
     *
     * @return Lista con información del perfil del usuario imprimible por
     * imprimirLista
     * @throws Exception
     */
    protected List consultarPerfil() throws Exception {
        if (!this.perfilDefinido) {
            throw new Exception("Este usuario no tiene un perfil definido");
        }
        List<ArrayList<Object>> infoPerfil = new ArrayList<>();
        ArrayList<Object> titulos = new ArrayList<>();
        ArrayList<Object> elems = new ArrayList<>();
        titulos.add("nombre");
        elems.add(nombre);
        titulos.add("telefono");
        elems.add(telefono);
        titulos.add("breve descripcion personal");
        elems.add(breveDescripcionPersonal);
        titulos.add("visibilidad");
        elems.add(visibilidad);
        infoPerfil.add(titulos);
        infoPerfil.add(elems);
        return infoPerfil;
    }

    /**
     * Sirve para ofertar un plan de alquiler. Dado el diseño actual, NO se
     * tiene en cuenta para la búsqueda. En la búsqueda se ha especificado cómo
     * sí se tendría en cuenta.
     *
     * @param fechaInicio Identificador formado por fechaInicio+matricula
     * @param matricula Identificador formado por fechaInicio+matricula
     */
    protected void ofertarPlanAlquiler(GregorianCalendar fechaInicio, String matricula) {
        for (PlanAlquiler pa : planesAlquiler) {
            if (pa.getVehiculo().obtenerMatrícula().equals(matricula) && fechaInicio == pa.getPrimerDiaAlquiler()) {
                pa.modificarVisibilidad(true);
            }
            break;
        }
    }

    /**
     * Devuelve un vehículo del usuario
     *
     * @param matricula Matrícula del vehículo
     * @return Instancia de Vehiculo o null, si no existe.
     */
    protected Vehiculo buscarVehiculo(String matricula) {
        return vehiculos.get(matricula);
    }

    /**
     * Busca un plan de alquiler del usuario
     *
     * @param fechaInicio Identificador formado por fechaInicio+matricula
     * @param matricula Identificador formado por fechaInicio+matricula
     * @return Instancia de PlanAlquiler o null, si no existe.
     */
    private PlanAlquiler buscarPlanAlquiler(Date fechaInicio, String matricula) {
        PlanAlquiler p = new PlanAlquiler();
        return p;
    }

    public boolean isPerfilDefinido() {
        return perfilDefinido;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getDireccionCorreo() {
        return direccionCorreo;
    }

    /**
     * Devuelve el hash de una contraseña siguiendo el algoritmo PBKDF2:
     * https://en.wikipedia.org/wiki/PBKDF2
     *
     * @param password Contraseña
     * @return Hash de la contraseña
     */
    protected String hash(String password) {
        return hash(password.toCharArray());
    }

    public Map<String, Vehiculo> getVehiculos() {
        return vehiculos;
    }

    /**
     * Crea un hash de una contraseña
     *
     * @param password Contraseña
     * @return Hash de la contraseña
     */
    protected String hash(char[] password) {
        byte[] salt = new byte[SIZE / 8];
        random.nextBytes(salt);
        byte[] dk = pbkdf2(password, salt, 1 << cost);
        byte[] hash = new byte[salt.length + dk.length];
        System.arraycopy(salt, 0, hash, 0, salt.length);
        System.arraycopy(dk, 0, hash, salt.length, dk.length);
        Base64.Encoder enc = Base64.getUrlEncoder().withoutPadding();
        return ID + cost + '$' + enc.encodeToString(hash);
    }

    /**
     * Algoritmo PBKDF2
     *
     * @param password Contraseña
     * @param salt Sal de la contraseña
     * @param iterations Número de iteraciones (> iteraciones = > seguridad = >
     * tiempo ejecución)
     * @return
     */
    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations) {
        KeySpec spec = new PBEKeySpec(password, salt, iterations, SIZE);
        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance(ALGORITHM);
            return f.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Missing algorithm: " + ALGORITHM, ex);
        } catch (InvalidKeySpecException ex) {
            throw new IllegalStateException("Invalid SecretKeyFactory", ex);
        }
    }

    /**
     * Devuelve si el usuario tiene algún vehículo
     * @return true o false
     */
    protected boolean tengoVehiculos() {
        return vehiculos.size() > 0;
    }

}

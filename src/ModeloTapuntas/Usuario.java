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

    Usuario(String nombreUsuario, String contraseña, String direccionCorreo) {
        this.cost = DEFAULT_COST;
        this.random = new SecureRandom();
        crear(nombreUsuario, contraseña, direccionCorreo);
    }

    protected void crear(String nombreUsuario, String contraseña, String direccionCorreo) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = hash(contraseña);
        this.direccionCorreo = direccionCorreo;
    }

    protected void modificarVisibilidad(boolean visibilidad) {
        this.visibilidad = visibilidad;
    }

    protected void nuevoVehículo(String matricula, String marca, String modelo, String color, int numeroPlazas, String categoría, String confor) {
        Vehiculo miVehiculo = new Vehiculo();
        miVehiculo.crear(matricula, marca, modelo, color, numeroPlazas, categoría, confor);
        vehiculos.put(matricula, miVehiculo);
    }

    protected List obtenerPlanesQueCumplanRequisitos(String ciudadRecogida, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
        List<Object> datosPAUsuario = new ArrayList<>();
        datosPAUsuario.add(nombre);
        datosPAUsuario.add(preferenciaCobro);
        boolean hay_ofertas = false;
        for(PlanAlquiler PA : planesAlquiler) {
            //System.out.println("Primer dia: " + PA.getPrimerDiaAlquiler().toString() + " FechaInicio: " + fechaInicio.toString() + " Ultimo dia:"+PA.getUltimoDiaAlquiler().toString()+ " Fecha fin:"+fechaFin.toString());
            //System.out.println(PA.getCiudadRecogida().equals(ciudadRecogida) + " && " + PA.getPrimerDiaAlquiler().after(fechaInicio) +" && "+ PA.getUltimoDiaAlquiler().before(fechaFin));
            if(PA.getCiudadRecogida().equals(ciudadRecogida) && PA.getPrimerDiaAlquiler().after(fechaInicio) && PA.getUltimoDiaAlquiler().before(fechaFin))
            {
                //System.out.println("added");
                datosPAUsuario.add(PA.obtenerDatosPA());
                hay_ofertas = true;
            }
        }
        if(!hay_ofertas) datosPAUsuario = new ArrayList<>();
        return datosPAUsuario;
    }

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

    protected void introducirPerfil(String nombre, String telefono, String breveDescripcion, TipoTransaccion preferenciaCobro) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.breveDescripcionPersonal = breveDescripcion;
        this.preferenciaCobro = preferenciaCobro;
        this.modificarVisibilidad(true);
        this.perfilDefinido = true;
    }

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

    // He hecho que consultarPerfil() lance una excepción en caso de que el usuario no tenga perfil definido
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

    protected void ofertarPlanAlquiler(GregorianCalendar fechaInicio, String matricula) {
        for (PlanAlquiler pa : planesAlquiler) {
            if (pa.getVehiculo().obtenerMatrícula().equals(matricula) && fechaInicio == pa.getPrimerDiaAlquiler()) {
                pa.modificarVisibilidad(true);
            }
            break;
        }
    }

    protected Vehiculo buscarVehiculo(String matricula) {
        return vehiculos.get(matricula);
    }

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

    protected String hash(String password) {
        return hash(password.toCharArray());
    }
    
    public Map<String, Vehiculo> getVehiculos() {
        return vehiculos;
    }

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
    
    protected boolean tengoVehiculos(){
        return vehiculos.size() > 0;
    }

}

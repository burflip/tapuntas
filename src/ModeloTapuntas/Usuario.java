/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloTapuntas;

import java.util.*;

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
    private String breveDescripciónPersonal;
    private TipoTransaccion preferenciaCobro;
    private ArrayList<PlanAlquiler> planesAlquiler = new ArrayList();
    private Map<String, Vehiculo> vehiculos = new HashMap();

    Usuario(String nombreUsuario, String contraseña, String direccionCorreo) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.direccionCorreo = direccionCorreo;
    }

    protected void crear(String nombreUsuario, String contraseña, String direccionCorreo) {

    }

    protected void modificarVisibilidad(boolean visibilidad) {
        this.visibilidad = visibilidad;
    }

    protected void nuevoVehículo(String matricula, String marca, String modelo, String color, int numeroPlazas, String categoría, String confor) {

    }

    protected List obtenerPlatesQueCumplanRequisitos(String ciudadRecogida, Date fechaInicio, Date fechaFin) {
        LinkedList a = new LinkedList();
        return a;
    }

    protected void definirPlanAlquiler(String matricula, Date fechaInicio, Date fechaFin, String ciudadRecogida) {

    }

    protected void eliminarVehículo(String matricula) {

    }

    protected void introducirPerfil(String nombre, String telefono, String breveDescripcion, TipoTransaccion preferenciaCobro) {

    }

    protected List obtenerPlanesAlquiler() {
        LinkedList a = new LinkedList();
        return a;
    }

    protected List consultarPerfil() {
        LinkedList a = new LinkedList();
        return a;
    }
    
    protected void ofertarPlanALquiler(Date fechaInicio, String matricula) {
        
    }
    
    private Vehiculo buscarVehículo(String matricula) {
        Vehiculo v = new Vehiculo();
        return v;
    }
    
    private PlanAlquiler buscarPlanAlquiler(Date fechaInicio, String matricula){
        PlanAlquiler p = new PlanAlquiler();
        return p;
    }
    
}

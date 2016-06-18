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
    private String breveDescripcionPersonal;
    private TipoTransaccion preferenciaCobro;
    private boolean perfilDefinido = false;
    private ArrayList<PlanAlquiler> planesAlquiler = new ArrayList();
    private Map<String, Vehiculo> vehiculos = new HashMap();

    Usuario(String nombreUsuario, String contraseña, String direccionCorreo) {
        crear(nombreUsuario, contraseña, direccionCorreo);
    }

    protected void crear(String nombreUsuario, String contraseña, String direccionCorreo) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
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

    protected List obtenerPlatesQueCumplanRequisitos(String ciudadRecogida, Date fechaInicio, Date fechaFin) {
        List<Object> datosPAUsuario = new ArrayList<>();
        datosPAUsuario.add(nombre);
        datosPAUsuario.add(preferenciaCobro);
        planesAlquiler.stream().filter((PA) -> (PA.getCiudadRecogida().equals(ciudadRecogida) && PA.getPrimerDiaAlquiler().after(fechaInicio) && PA.getUltimoDiaAlquiler().before(fechaFin))).forEach((PA) -> {
            datosPAUsuario.add(PA.obtenerDatosPA());
        });
        return datosPAUsuario;
    }

    protected void definirPlanAlquiler(String matricula, Date fechaInicio, Date fechaFin, String ciudadRecogida) throws Exception {
        Vehiculo vehiculo = buscarVehiculo(matricula);
        boolean disponible = vehiculo.estasDisponible(fechaInicio, fechaFin);
        if (!disponible) {
            throw new Exception("el vehículo ya pertenece a un plan alquiler en esas fechas");
        }
        PlanAlquiler miPlanAlquiler = new PlanAlquiler();
        miPlanAlquiler.crear(vehiculo, fechaInicio, fechaFin, ciudadRecogida);
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
        for(PlanAlquiler pa : planesAlquiler) {
            if(!pa.isVisible() && pa.vigente()){
                List<Object> datosPlanAlquiler = new ArrayList<>(pa.obtenerDatosPlanAlquiler());
                misPlanesAlquiler.add(datosPlanAlquiler);
            }
        }
        return misPlanesAlquiler;
    }

    protected List consultarPerfil() {
        List<Object> infoPerfil = new ArrayList<>();
        infoPerfil.add(nombre);
        infoPerfil.add(telefono);
        infoPerfil.add(breveDescripcionPersonal);
        infoPerfil.add(visibilidad);
        return infoPerfil;
    }

    protected void ofertarPlanAlquiler(Date fechaInicio, String matricula) {
        for(PlanAlquiler pa : planesAlquiler) {
            if(pa.getVehiculo().obtenerMatrícula().equals(matricula) && fechaInicio == pa.getPrimerDiaAlquiler())
                pa.modificarVisibilidad(true); break;
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
    

}

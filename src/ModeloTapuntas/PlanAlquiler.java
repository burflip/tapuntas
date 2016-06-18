/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloTapuntas;

import java.util.*;

/**
 *
 * @author valentin
 */
public class PlanAlquiler {

    private boolean visible;
    private Date primerDiaAlquiler;
    private Date ultimoDiaAlquiler;
    private double costeAlquilerAlDia;
    private String ciudadRecogida;
    private Vehiculo vehiculo;

    public boolean isVisible() {
        return visible;
    }

    public Date getPrimerDiaAlquiler() {
        return primerDiaAlquiler;
    }

    public Date getUltimoDiaAlquiler() {
        return ultimoDiaAlquiler;
    }

    public double getCosteAlquilerAlDia() {
        return costeAlquilerAlDia;
    }

    public String getCiudadRecogida() {
        return ciudadRecogida;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    protected List obtenerDatosPA() {
        List<Object> datosPA = new ArrayList<>();
        datosPA.add(costeAlquilerAlDia);
        List<Object> datosVehiculo = new ArrayList<>(vehiculo.obtenerDatosVehículo());
        datosPA.add(datosVehiculo);
        return datosPA;
    }

    protected void crear(Vehiculo unVehiculo, Date fechaInicio, Date fechaFin, String ciudadRecogida) {
        this.vehiculo = unVehiculo;
        this.primerDiaAlquiler = fechaInicio;
        this.ultimoDiaAlquiler = fechaFin;
        this.ciudadRecogida = ciudadRecogida;
    }

    protected void eliminarVehiculo() {
        this.vehiculo = null;
    }

    protected List obtenerDatosPlanAlquiler() {
        List<Object> datosPA = new ArrayList<>();
        String matricula = vehiculo.obtenerMatrícula();
        datosPA.add(matricula);
        datosPA.add(primerDiaAlquiler);
        datosPA.add(ultimoDiaAlquiler);
        datosPA.add(costeAlquilerAlDia);
        datosPA.add(ciudadRecogida);
        return datosPA;
    }

    protected void modificarVisibilidad(boolean visible) {
        this.visible = visible;
    }
    
    protected boolean poseeAlquileresAceptados()
    {
        return false;
    }
    
    protected boolean vigente()
    {
        Date fechaActual = new Date();
        return fechaActual.before(this.primerDiaAlquiler);
    }
}

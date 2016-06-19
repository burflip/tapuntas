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
    private GregorianCalendar primerDiaAlquiler;
    private GregorianCalendar ultimoDiaAlquiler;
    private double costeAlquilerAlDia;
    private String ciudadRecogida;
    private Vehiculo vehiculo;

    public boolean isVisible() {
        return visible;
    }

    public GregorianCalendar getPrimerDiaAlquiler() {
        return primerDiaAlquiler;
    }

    public GregorianCalendar getUltimoDiaAlquiler() {
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
        List<Object> datosVehiculo = new ArrayList<>(vehiculo.obtenerDatosVehiculo());
        datosPA.add(datosVehiculo);
        return datosPA;
    }

    protected void crear(Vehiculo unVehiculo, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, String ciudadRecogida) {
        this.vehiculo = unVehiculo;
        this.primerDiaAlquiler = fechaInicio;
        this.ultimoDiaAlquiler = fechaFin;
        this.ciudadRecogida = ciudadRecogida;
    }

    protected void eliminarVehiculo() {
        this.vehiculo = null;
    }

    protected List obtenerDatosPlanAlquiler() {
        List<List<Object>> datosPA = new ArrayList<>();
        String matricula = vehiculo.obtenerMatrícula();
        ArrayList<Object> titulos = new ArrayList<>();
        ArrayList<Object> elems = new ArrayList<>();
        titulos.add("Matrícula");
        titulos.add("Primer dia alquiler");
        titulos.add("Ultimo dia alquiler");
        titulos.add("Coste de alquiler al día");
        titulos.add("Ciudad de recogida");
        elems.add(matricula);
        elems.add(primerDiaAlquiler);
        elems.add(ultimoDiaAlquiler);
        elems.add(costeAlquilerAlDia);
        elems.add(ciudadRecogida);
        datosPA.add(titulos);
        datosPA.add(elems);
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
        GregorianCalendar fechaActual = new GregorianCalendar();
        return fechaActual.before(this.primerDiaAlquiler);
    }
}

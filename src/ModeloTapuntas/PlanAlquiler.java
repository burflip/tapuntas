/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloTapuntas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    /**
     * Devuelve los datos del PlanAlquiler. He añadido primer y último día de
     * alquiler para saber de cuándo es cada uno.
     *
     * @return Los datos del Plan de Alquiler en formato imprimible por
     * imprimirLista
     */
    protected List obtenerDatosPA() {
        List<Object> datosPA = new ArrayList<>();
        datosPA.add(costeAlquilerAlDia);
        datosPA.add(primerDiaAlquiler);
        datosPA.add(ultimoDiaAlquiler);
        List<Object> datosVehiculo = new ArrayList<>(vehiculo.obtenerDatosVehiculo());
        datosPA.add(datosVehiculo);
        return datosPA;
    }

    /**
     * Crea un nuevo plan de alquiler
     *
     * @param unVehiculo Vehiculo asociado
     * @param fechaInicio Fecha de inicio del plan
     * @param fechaFin Fecha de fin del plan
     * @param ciudadRecogida Ciudad de recogida del plan
     */
    protected void crear(Vehiculo unVehiculo, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, String ciudadRecogida) {
        this.vehiculo = unVehiculo;
        this.primerDiaAlquiler = fechaInicio;
        this.ultimoDiaAlquiler = fechaFin;
        this.ciudadRecogida = ciudadRecogida;
    }

    /**
     * Elimina el vehículo del plan
     */
    protected void eliminarVehiculo() {
        this.vehiculo = null;
    }

    /**
     * Devuelve los datos completos del PlanAlquiler.
     *
     * @return Los datos del Plan de Alquiler en formato imprimible por
     * imprimirLista
     */
    protected List obtenerDatosPlanAlquiler() {
        List<List<Object>> datosPA = new ArrayList<>();
        String matricula = vehiculo.obtenerMatrícula();
        ArrayList<Object> titulos = new ArrayList<>();
        ArrayList<Object> elems = new ArrayList<>();
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        String fechaInicio = formatoFecha.format(primerDiaAlquiler.getTime());
        String fechaFin = formatoFecha.format(ultimoDiaAlquiler.getTime());
        titulos.add("Matrícula");
        titulos.add("Primer dia alquiler");
        titulos.add("Ultimo dia alquiler");
        titulos.add("Coste de alquiler al día");
        titulos.add("Ciudad de recogida");
        elems.add(matricula);
        elems.add(fechaInicio);
        elems.add(fechaFin);
        elems.add(costeAlquilerAlDia);
        elems.add(ciudadRecogida);
        datosPA.add(titulos);
        datosPA.add(elems);
        return datosPA;
    }

    /**
     * Hace un Plan de Alquiler visible o invisible
     *
     * @param visible Si queremos que sea visible true e invisible false
     */
    protected void modificarVisibilidad(boolean visible) {
        this.visible = visible;
    }

    /**
     * Esta función no está implementada pues depende de la clase Alquiler que
     * no está definida en esta iteración del diseño.
     *
     * @return false
     */
    protected boolean poseeAlquileresAceptados() {
        return false;
    }

    /**
     * Devuelve true si el plan está vigente. La condición de vigencia es que el
     * la fechaInicio del plan sea > a la fecha de hoy.
     *
     * @return true si aún está vigente y false si no
     */
    protected boolean vigente() {
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy"); //Da formato al día String
        String fechaString = formatoFecha.format(this.ultimoDiaAlquiler.getTime());
        GregorianCalendar fechaActual = new GregorianCalendar();
        //Debug: System.out.println("Fecha actual: "+fechaActual+" Fecha ult dia:" + fechaString);
        return fechaActual.before(this.ultimoDiaAlquiler);
    }
}

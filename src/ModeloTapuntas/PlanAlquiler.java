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

    }

    protected void eliminarVehiculo() {

    }

    protected List obtenerDatosPlanAlquiler() {
        LinkedList a = new LinkedList();
        return a;
    }

    protected void modificarVisibilidad(boolean visible) {
        this.visible = visible;
    }
}

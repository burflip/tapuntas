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
    private double costeAlquilerAlDia;
    private String ciudadRecogida;
    private Vehiculo vehiculo;

    protected List obtenerDatosPA() {
        LinkedList a = new LinkedList();
        return a;
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

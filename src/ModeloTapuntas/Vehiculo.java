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
public class Vehiculo {

    private String marca;
    private String modelo;
    private String confor;
    private int numeroPlazas;
    private String color;
    private String categoria;
    private String matricula;
    private ArrayList<PlanAlquiler> planesAlquiler = new ArrayList();

    protected void crear(String matricula, String marca, String modelo, String color, int numerPlazas, String categoría, String confor) {

    }

    protected List obtenerDatosVehículo() {
        LinkedList a = new LinkedList();
        return a;
    }

    protected boolean estasDisponible(Date fechaInicio, Date fechaFin) {
        return true;
    }

    protected void incluirPlanAlquiler() {

    }

    protected boolean comprobarEstadoALquileres() {
        return true;
    }

    protected void eliminarVehículoAlquileres() {

    }

    protected void obtenerMatrícula() {

    }

}

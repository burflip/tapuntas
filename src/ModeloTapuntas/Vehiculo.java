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

    protected void crear(String matricula, String marca, String modelo, String color, int numeroPlazas, String categoría, String confor) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.numeroPlazas = numeroPlazas;
        this.categoria = categoria;
        this.confor = confor;
    }

    protected List obtenerDatosVehículo() {
        List<Object> datosVehiculo = new ArrayList<>();
        datosVehiculo.add(marca);
        datosVehiculo.add(modelo);
        datosVehiculo.add(confor);
        datosVehiculo.add(numeroPlazas);
        datosVehiculo.add(color);
        datosVehiculo.add(categoria);
        datosVehiculo.add(matricula);
        return datosVehiculo;
    }

    protected boolean estasDisponible(Date fechaInicio, Date fechaFin) {
        boolean disponible = false;
        for(PlanAlquiler pa : planesAlquiler) {
            if((fechaInicio.after(pa.getPrimerDiaAlquiler()) && fechaInicio.before(pa.getUltimoDiaAlquiler())) ||
                    (fechaInicio.before(pa.getPrimerDiaAlquiler()) && fechaFin.after(pa.getPrimerDiaAlquiler())))
                disponible = false;
        }
        return disponible;
    }

    protected void incluirPlanAlquiler(PlanAlquiler pa) {
        planesAlquiler.add(pa);
    }

    protected boolean comprobarEstadoAlquileres() {
        Date fechaActual = new Date();
        boolean estaPillado = true;
        for(PlanAlquiler pa : planesAlquiler) {
            if((fechaActual.after(pa.getPrimerDiaAlquiler()) && fechaActual.before(pa.getUltimoDiaAlquiler())) ||
                    (fechaActual.before(pa.getPrimerDiaAlquiler()) && pa.poseeAlquileresAceptados()))
                estaPillado = false;
        }
        return estaPillado;
    }

    protected void eliminarVehiculoAlquileres() {
        for(PlanAlquiler pa : planesAlquiler) {
            pa.eliminarVehiculo();
        }
    }

    protected String obtenerMatrícula() {
        return matricula;
    }

}

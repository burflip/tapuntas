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

    protected void crear(String matricula, String marca, String modelo, String color, int numeroPlazas, String categoria, String confor) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.numeroPlazas = numeroPlazas;
        this.categoria = categoria;
        this.confor = confor;
    }

    protected List obtenerDatosVehiculo() {
        List<ArrayList<Object>> datosVehiculo = new ArrayList<>();
        ArrayList<Object> titulos = new ArrayList<>();
        ArrayList<Object> elems = new ArrayList<>();
        titulos.add("Marca");
        titulos.add("Modelo");
        titulos.add("Confor");
        titulos.add("Numero de plazas");
        titulos.add("Color");
        titulos.add("Categoria");
        titulos.add("Matricula");
        elems.add(marca);
        elems.add(modelo);
        elems.add(confor);
        elems.add(numeroPlazas);
        elems.add(color);
        elems.add(categoria);
        elems.add(matricula);
        datosVehiculo.add(titulos);
        datosVehiculo.add(elems);
        return datosVehiculo;
    }

    protected boolean estasDisponible(GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
        boolean disponible = true;
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
        GregorianCalendar fechaActual = new GregorianCalendar();
        boolean estaPillado = true;
        if(planesAlquiler.isEmpty()) {
            estaPillado = false;
        } else {
            for(PlanAlquiler pa : planesAlquiler) {
                if((fechaActual.after(pa.getPrimerDiaAlquiler()) && fechaActual.before(pa.getUltimoDiaAlquiler())) ||
                        (fechaActual.before(pa.getPrimerDiaAlquiler()) && pa.poseeAlquileresAceptados()))
                    estaPillado = false;
            }
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

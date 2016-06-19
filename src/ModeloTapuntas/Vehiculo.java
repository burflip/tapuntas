package ModeloTapuntas;

import java.util.*;

public class Vehiculo {

    private String marca;
    private String modelo;
    private String confor;
    private int numeroPlazas;
    private String color;
    private String categoria;
    private String matricula;
    private ArrayList<PlanAlquiler> planesAlquiler = new ArrayList();

    /**
     * Crea un nuevo Vehículo
     *
     * @param matricula Matrícula del vehículo
     * @param marca Marca del vehículo
     * @param modelo Modelo del vehículo
     * @param color Color del vehículo
     * @param numeroPlazas Número de plazas del vehículo
     * @param categoria Categoría del vehículo
     * @param confor Confort del vehículo
     */
    void crear(String matricula, String marca, String modelo, String color, int numeroPlazas, String categoria, String confor) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.numeroPlazas = numeroPlazas;
        this.categoria = categoria;
        this.confor = confor;
    }

    /**
     * * Devuelve los datos completos del Vehiculo.
     *
     * @return Los datos del Vehiculo en formato imprimible por imprimirLista
     */
    List obtenerDatosVehiculo() {
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

    /**
     * Devuelve true si un vehículo no tiene asociado ningún planAlquiler para
     * unas fechas determinadas
     *
     * @param fechaInicio Fecha de inicio para evaluación
     * @param fechaFin Fecha de fin para evaluación
     * @return true si está disponible y false si no.
     */
    boolean estasDisponible(GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
        boolean disponible = true;
        for (PlanAlquiler pa : planesAlquiler) {
            if ((fechaInicio.after(pa.getPrimerDiaAlquiler()) && fechaInicio.before(pa.getUltimoDiaAlquiler()))
                    || (fechaInicio.before(pa.getPrimerDiaAlquiler()) && fechaFin.after(pa.getPrimerDiaAlquiler()))) {
                disponible = false;
            }
        }
        return disponible;
    }

    /**
     * Incluye un plan de alquiler con el que está asociado el vehículo
     *
     * @param pa PlanAlquiler que asociar.
     */
    void incluirPlanAlquiler(PlanAlquiler pa) {
        planesAlquiler.add(pa);
    }

    /**
     * Comprobar si un Vehiculo tiene PlanesAlquileres asociados en un intervalo
     * de fechas.
     *
     * @return true si está disponible, false si no
     */
    boolean comprobarEstadoAlquileres() {
        GregorianCalendar fechaActual = new GregorianCalendar();
        boolean estaPillado = true;
        if (planesAlquiler.isEmpty()) {
            estaPillado = false;
        } else {
            for (PlanAlquiler pa : planesAlquiler) {
                if ((fechaActual.after(pa.getPrimerDiaAlquiler()) && fechaActual.before(pa.getUltimoDiaAlquiler()))
                        || (fechaActual.before(pa.getPrimerDiaAlquiler()) && pa.poseeAlquileresAceptados())) {
                    estaPillado = false;
                }
            }
        }
        return estaPillado;
    }

    /**
     * Elimina el vehículo de los alquileres que tenga asociados
     */
    void eliminarVehiculoAlquileres() {
        for (PlanAlquiler pa : planesAlquiler) {
            pa.eliminarVehiculo();
        }
    }

    String obtenerMatrícula() {
        return matricula;
    }

}

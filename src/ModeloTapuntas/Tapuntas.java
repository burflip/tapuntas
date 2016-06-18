/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloTapuntas;

import java.util.*;


public class Tapuntas {
    private Map<String,Usuario> usuarios = new HashMap();
    
    private static Tapuntas instance = null;
    
    private Tapuntas() {}
    
    // Este método mo está bien, está hecho para probar, tenéis que implementar el singleton
    public static Tapuntas getInstance() {
        if(instance == null)
            instance = new Tapuntas();
        
        return instance;
    }

    public void altaRegistro(String nombreUsuario, String contraseña, String direccionCorreo) throws Exception {
       if(usuarios.containsKey(nombreUsuario)) throw new Exception("ya existe un usuario con ese nombre de usuario");
       Usuario unUsuario = new Usuario(nombreUsuario,contraseña,direccionCorreo);
       unUsuario.modificarVisibilidad(false);
       usuarios.put(nombreUsuario, unUsuario);
    }
    
    public void añadirVehículo(String nombreUsuario, String matricula, String marca, String modelo, String color, int numeroPlazas, String categoria, String confor) throws Exception {
        boolean existe = false;
        for(Map.Entry<String,Usuario> entry : usuarios.entrySet()) {
            if(entry.getValue().buscarVehiculo(matricula) != null)
                existe = true;
        }
        if (existe) throw new Exception("ya existe otro vehículo en el sistema con esa matrícula");
        Usuario usuario = this.buscarUsuario(nombreUsuario);
        usuario.nuevoVehículo(matricula, marca, modelo, color, numeroPlazas, categoria, confor);
    }
    
    public List buscarOfertasAlquiler(String ciudadRecogida, Date fechaInicio, Date fechaFin) {
        List<Object> listaOfertas = new ArrayList<>();
        Iterator it = usuarios.keySet().iterator();
        while(it.hasNext()) {
            String u_key = (String) it.next();
            Usuario usuario = usuarios.get(u_key);
            List<Object> datosPAUsuario =  usuario.obtenerPlatesQueCumplanRequisitos(ciudadRecogida, fechaInicio, fechaFin);
            listaOfertas.add(datosPAUsuario);
        }
        return listaOfertas;
    }
    
    public void definirPlanAlquiler(String nombreUsuario, String matricula, Date fechaInicio, Date fechaFin, String ciudadRecogida) throws Exception {
        Usuario usuario = buscarUsuario(nombreUsuario);
        usuario.definirPlanAlquiler(matricula, fechaInicio, fechaFin, ciudadRecogida);
    }
    
    public void eliminarVehículo(String nombreUsuario, String matricula) throws Exception {
        Usuario usuario = buscarUsuario(nombreUsuario);
        usuario.eliminarVehículo(matricula);
    }
    
    public void introducirPerfil(String nombreUsuario, String nombre, String telefono, String breveDescripcion, TipoTransaccion preferenciaCobro) throws Exception {
        Usuario usuario = buscarUsuario(nombreUsuario);
        if(usuario.isPerfilDefinido()) throw new Exception("el usuario ya tiene un perfil definido");
        usuario.introducirPerfil(nombre, telefono, breveDescripcion, preferenciaCobro);
    }
    
    public List obtenerPlanesAlquiler(String nombreUsuario) {
        List<PlanAlquiler> misPlanesAlquiler= new ArrayList<>();
        Usuario usuario = buscarUsuario(nombreUsuario);
        misPlanesAlquiler = usuario.obtenerPlanesAlquiler();
        return misPlanesAlquiler;
    }
    
    public List consultarPerfil(String nombreUsuario){
        Usuario usuario = buscarUsuario(nombreUsuario);
        List<Object> infoPerfil;
        infoPerfil = new ArrayList<>(usuario.consultarPerfil());
        return infoPerfil;
    }
    
    public void ofertarPlanAlquiler(String nombreUsuario, Date fechaInicio, String matricula) {
        Usuario usuario = buscarUsuario(nombreUsuario);
        usuario.ofertarPlanAlquiler(fechaInicio, matricula);
    }
    
    private boolean existeUsuario(String nombreUsuario) {
        return (usuarios.get(nombreUsuario) != null);
    }
    
    private Usuario buscarUsuario(String nombreUsuario) {
        return usuarios.get(nombreUsuario);
    }
    
    private void ordenarOfertas(ArrayList<String> listaOfertas) {
        
    }
    
    private boolean existeVehículo(String matricula) {
        return true;
    }
    
    
}

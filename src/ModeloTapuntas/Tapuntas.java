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
    
    public void añadirVehículo(String nombreUsuario, String matricula, String modelo, String color, int numeroPlazas, String categoria, String confor) {
        
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
    
    public void definirPlanAlquiler(String nombreUsuario, Date fechaInicio, Date fechaFin, String ciudadRecogida) {
        
    }
    
    public void eliminarVehículoPropietario(String nombreUsuario, String matricula) {
        
    }
    
    public void introducirPerfil(String nombreUsuario, String nombre, String telefono, String breveDescripcion) {
        
    }
    
    public List obtenerPlanesAlquiler(String nombreUsuario) {
        LinkedList a = new LinkedList();
        return a;
    }
    
    public List consultarPerfil(String nombreUsuario){
        LinkedList a = new LinkedList();
        return a;
    }
    
    public void ofertarPlanAlquiler(String nombreUsuario, Date fechaInicio, String matricula) {
        
    }
    
    private boolean existeUsuario(String nombreUsuario) {
        return true;
    }
    
    private Usuario buscarUsuario(String nombreUsuario) {
        Usuario a = new Usuario("Pepe", "pepesecret", "pepe@gmail.com");
        return a;
    }
    
    private void ordenarOfertas(ArrayList<String> listaOfertas) {
        
    }
    
    private boolean existeVehículo(String matricula) {
        return true;
    }
    
    
}

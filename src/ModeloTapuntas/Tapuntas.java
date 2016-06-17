/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloTapuntas;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aanaya
 */
public class Tapuntas {
    private Map<String,Usuario> usuarios = new HashMap();

    // Este método mo está bien, está hecho para probar, tenéis que implementar el singleton
    public static Tapuntas getInstance() {
        return new Tapuntas();        
    }

    public void altaRegistro(String nombreUsuario, String contraseña, String direccionCorreo) throws Exception {
       if(usuarios.containsKey(nombreUsuario)) throw new Exception("ya existe un usuario con ese nombre de usuario");
       usuarios.put(nombreUsuario, new Usuario(nombreUsuario,contraseña,direccionCorreo));
    }
    
}

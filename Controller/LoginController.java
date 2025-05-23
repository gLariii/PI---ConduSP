package Controller;

import DAO.UsuarioDAO;
import Model.Usuario;
// controlador de login ( autenticar o usuario )
public class LoginController {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    public boolean login(String rg, String senha) {
        Usuario usuario = new Usuario(rg, senha);
        return usuarioDAO.autenticar(usuario);
    }
}

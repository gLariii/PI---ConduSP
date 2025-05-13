package Controller;

import DAO.UsuarioDAO;
import Model.Usuario;
// controlador de login ( autenticar o usuario )
public class LoginController {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    public boolean login(String login, String senha) {
        Usuario usuario = new Usuario(login, senha);
        return usuarioDAO.autenticar(usuario);
    }
}

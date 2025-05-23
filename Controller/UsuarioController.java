package Controller;

import DAO.UsuarioDAO;
import Model.Usuario;
// controlador de usuario (cadastrar e verificação se ja existe ou não )

public class UsuarioController {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean cadastrarUsuario(String rg, String senha, String nome, String tipoUsuario) {
        if (usuarioDAO.existeRg(rg)) {
            System.out.println("RG já cadastrado.");
            return false;
        }

        Usuario novoUsuario = new Usuario(0, rg, senha, nome, tipoUsuario);
        return usuarioDAO.cadastrar(novoUsuario);
    }
}

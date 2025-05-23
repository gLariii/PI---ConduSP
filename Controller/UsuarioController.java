package Controller;

import DAO.UsuarioDAO;
import Model.Usuario;

public class UsuarioController {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean cadastrarUsuario(String rg, String senha, String nome, String tipoUsuario) {
        Usuario novoUsuario = new Usuario(0, rg, senha, nome, tipoUsuario); // ID será gerado automaticamente
        return usuarioDAO.cadastrar(novoUsuario);
    }
}

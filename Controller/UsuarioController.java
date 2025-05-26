package Controller;

import DAO.UsuarioDAO;
import Model.Usuario;
// controlador de usuario (cadastrar e verificação se ja existe ou não )

public class UsuarioController {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    private boolean RGValido(String rg) {
        return rg != null && rg.matches("^\\d{2}\\.\\d{3}\\.\\d{3}-\\d{1}$");
    }
    
    public boolean cadastrarUsuario(String rg, String senha, String nome, String tipoUsuario) {
        if (!RGValido(rg)) {
            System.out.println("RG inválido. Deve estar no formato (XX.XXX.XXX-X)");
            return false;
        }
    
        if (usuarioDAO.existeRg(rg)) {
            System.out.println("RG já cadastrado.");
            return false;
        }
    
        Usuario novoUsuario = new Usuario(0, rg, senha, nome, tipoUsuario);
        return usuarioDAO.cadastrar(novoUsuario);
    }
    
    
}

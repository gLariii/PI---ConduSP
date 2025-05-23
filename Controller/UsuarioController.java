package Controller;

import DAO.UsuarioDAO;
import Model.Usuario;
// controlador de usuario (cadastrar e verificação se ja existe ou não )

public class UsuarioController {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    private boolean RGValido(String rg) {
        return rg != null && rg.matches("\\d{10}");
    }
    
    public boolean cadastrarUsuario(String rg, String senha, String nome, String tipoUsuario) {
        if (!RGValido(rg)) {
            System.out.println("RG inválido. Deve conter exatamente 10 dígitos numéricos.");
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

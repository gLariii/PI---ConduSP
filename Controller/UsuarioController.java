package Controller;

import DAO.UsuarioDAO;
import Model.Usuario;

// controlador de usuario (cadastrar e verificação se ja existe ou não)
public class UsuarioController {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    private boolean RGValido(String rg) {
        return rg != null && rg.matches("^\\d{2}\\.\\d{3}\\.\\d{3}-\\d{1}$");
    }

    public Usuario buscarUsuario(String rg) {
        if (!RGValido(rg)) {
            System.out.println("RG inválido para busca. Deve estar no formato (XX.XXX.XXX-X)");
            return null;
        }
        return usuarioDAO.buscarUsuarioPorRg(rg);
    }

    public boolean atualizarTipoUsuario(String rg, String novoTipoUsuario) {
        if (!RGValido(rg)) {
            System.out.println("RG inválido para atualização. Deve estar no formato (XX.XXX.XXX-X)");
            return false;
        }
        if (novoTipoUsuario == null || novoTipoUsuario.trim().isEmpty()) {
            System.out.println("Tipo de usuário não pode ser vazio.");
            return false;
        }
        
        return usuarioDAO.atualizarTipoUsuario(rg, novoTipoUsuario);
    }
}

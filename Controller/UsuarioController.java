package Controller;

import DAO.UsuarioDAO;
import Model.Usuario;

// controlador de usuario (cadastrar e verificação se ja existe ou não)
public class UsuarioController {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    private boolean RGValido(String rg) {
        return rg != null && rg.matches("^\\d{2}\\.\\d{3}\\.\\d{3}-\\d{1}$");
    }

    public boolean cadastrarUsuario(String rg, String senha, String nome, String tipoUsuario, int volume) {
        if (!RGValido(rg)) {
            System.out.println("RG inválido. Deve estar no formato (XX.XXX.XXX-X)");
            return false;
        }

        if (usuarioDAO.existeRg(rg)) {
            System.out.println("RG já cadastrado.");
            return false;
        }

        // Garante que o volume está entre 0 e 100
        if (volume < 0) volume = 0;
        if (volume > 100) volume = 100;

        Usuario novoUsuario = new Usuario(0, rg, senha, nome, tipoUsuario, volume);
        return usuarioDAO.cadastrar(novoUsuario);
    }
}

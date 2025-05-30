package Controller;

import DAO.UsuarioDAO;
import Model.Usuario;
import Util.Criptografia;

public class LoginController {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    public boolean login(String rg, String senha) {
        if (rg == null || rg.trim().isEmpty()) {
            System.out.println("RG não pode ser vazio.");
            return false;
        }
        if (senha == null || senha.trim().isEmpty()) {
            System.out.println("Senha não pode ser vazia.");
            return false;
        }

        String senhaHasheada = Criptografia.gerarHashSHA256(senha);
        if (senhaHasheada == null) {
            System.out.println("Erro ao gerar hash da senha para login.");
            return false;
        }

        Usuario usuario = new Usuario(rg, senhaHasheada);
        return usuarioDAO.autenticar(usuario);
    }
}
package Controller;

import DAO.UsuarioDAO;
import Model.Usuario;
import Util.Criptografia;

public class UsuarioController {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    private boolean RGValido(String rg) {
        return rg != null && rg.matches("^[0-9]{1,2}\\.?[0-9]{3}\\.?[0-9]{3}-?[0-9A-Za-z]{1}$|^[0-9]{1,12}$");
    }

    public Usuario buscarUsuarioCompleto(String rg) {
        if (!RGValido(rg)) {
            System.out.println("RG inválido para busca. Verifique o formato.");
            return null;
        }
        return usuarioDAO.getUsuarioByRg(rg);
    }
    
    public Usuario buscarUsuario(String rg) {
        if (!RGValido(rg)) {
            System.out.println("RG inválido para busca. Verifique o formato.");
            return null;
        }
        return usuarioDAO.buscarUsuarioPorRg(rg);
    }

    public boolean atualizartipo_usuario(String rg, String novotipo_usuario) {
        if (!RGValido(rg)) {
            System.out.println("RG inválido para atualização. Verifique o formato.");
            return false;
        }
        if (novotipo_usuario == null || novotipo_usuario.trim().isEmpty()) {
            System.out.println("Tipo de usuário não pode ser vazio.");
            return false;
        }
        if (!novotipo_usuario.equalsIgnoreCase("operario") && !novotipo_usuario.equalsIgnoreCase("supervisor")) {
            System.out.println("Tipo de usuário inválido. Deve ser 'operario' ou 'supervisor'.");
            return false;
        }
        return usuarioDAO.atualizartipo_usuario(rg, novotipo_usuario);
    }
    
    public boolean registrarUsuario(String rg, String senha, String nome, String tipo_usuario, int volume) {
        if (!RGValido(rg)) {
            System.out.println("RG inválido para registro. Verifique o formato.");
            return false;
        }
        if (usuarioDAO.existeRg(rg)) {
            System.out.println("RG já cadastrado.");
            return false;
        }
        if (senha == null || senha.trim().isEmpty()) {
            System.out.println("A senha não pode ser vazia.");
            return false;
        }
        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("O nome não pode ser vazio.");
            return false;
        }
        if (!tipo_usuario.equalsIgnoreCase("operario") && !tipo_usuario.equalsIgnoreCase("supervisor")) {
            System.out.println("Tipo de usuário inválido. Deve ser 'operario' ou 'supervisor'.");
            return false;
        }
        if (volume < 0 || volume > 100) {
            System.out.println("O volume deve ser entre 0 e 100.");
            return false;
        }
        
        String senhaHasheada = Criptografia.gerarHashSHA256(senha);
        if (senhaHasheada == null) {
            System.out.println("Erro ao gerar hash da senha.");
            return false;
        }

        Usuario novoUsuario = new Usuario(0, rg, senhaHasheada, nome, tipo_usuario, volume);
        return usuarioDAO.registrarUsuario(novoUsuario);
    }
    
    public boolean atualizarVolumeUsuario(String rg, int novoVolume) {
        if (!RGValido(rg)) {
            System.out.println("RG inválido para atualização de volume. Verifique o formato.");
            return false;
        }
        if (novoVolume < 0 || novoVolume > 100) {
            System.out.println("O volume deve ser entre 0 e 100.");
            return false;
        }
        return usuarioDAO.atualizarVolume(rg, novoVolume);
    }
}
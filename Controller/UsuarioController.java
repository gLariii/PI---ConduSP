package Controller;

import DAO.UsuarioDAO;
import Model.Usuario;
import Util.Criptografia;

public class UsuarioController {

// Instância do DAO para acessar os dados do usuário

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

// Método para ver se o RG é válido

    private boolean RGValido(String rg) {
        return rg != null && rg.matches("^[0-9]{1,2}\\.?[0-9]{3}\\.?[0-9]{3}-?[0-9A-Za-z]{1}$|^[0-9]{1,12}$");
    }

// Método para buscar todas as informações do usuário pelo RG
    public Usuario buscarUsuarioCompleto(String rg) {
        if (!RGValido(rg)) {
            System.out.println("RG inválido para busca. Verifique o formato.");
            return null;
        }
        return usuarioDAO.getUsuarioByRg(rg);
    }

// Método para buscar o usuário apenas pelo RG
    public Usuario buscarUsuario(String rg) {
        if (!RGValido(rg)) {
            System.out.println("RG inválido para busca. Verifique o formato.");
            return null;
        }
        return usuarioDAO.buscarUsuarioPorRg(rg);
    }

//metodo para atualizar o tipo do usuário( de supervisor para operario e vice-versa)
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
    
// Método para atualizar o volume do usuário de acordo com oque ele escolher na tela de configurações
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
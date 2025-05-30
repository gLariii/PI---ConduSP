package DAO;

import Model.Usuario;
import Util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public boolean autenticar(Usuario usuario) {
        String sql = "SELECT * FROM tb_usuario WHERE rg = ? AND senha = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getRg());
            stmt.setString(2, usuario.getSenha()); 

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Erro ao autenticar usuário: " + e.getMessage());
            return false;
        }
    }

    public boolean existeRg(String rg) {
        String sql = "SELECT COUNT(*) FROM tb_usuario WHERE rg = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rg);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar existência de RG: " + e.getMessage());
        }
        return false;
    }

    public Usuario getUsuarioByRg(String rg) {
        String sql = "SELECT id, rg, senha, nome, tipo_usuario, volume FROM tb_usuario WHERE rg = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, rg);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("rg"),
                        rs.getString("senha"),
                        rs.getString("nome"),
                        rs.getString("tipo_usuario"),
                        rs.getInt("volume")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por RG: " + e.getMessage());
        }
        return null;
    }
    
    public Usuario buscarUsuarioPorRg(String rg) {
        String sql = "SELECT rg, nome, tipo_usuario FROM tb_usuario WHERE rg = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, rg);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Usuario(
                    rs.getString("rg"),
                    rs.getString("nome"),
                    rs.getString("tipo_usuario")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário (RG, nome, tipo) por RG: " + e.getMessage());
        }
        return null;
    }

    public boolean atualizartipo_usuario(String rg, String novotipo_usuario) {
        String sql = "UPDATE tb_usuario SET tipo_usuario = ? WHERE rg = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novotipo_usuario);
            stmt.setString(2, rg);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar tipo de usuário: " + e.getMessage());
        }
        return false;
    }
    
    public boolean registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO tb_usuario (rg, senha, nome, tipo_usuario, volume) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getRg());
            stmt.setString(2, usuario.getSenha()); 
            stmt.setString(3, usuario.getNome());
            stmt.setString(4, usuario.gettipo_usuario());
            stmt.setInt(5, usuario.getVolume());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao registrar usuário: " + e.getMessage());
            return false;
        }
    }
    
    public boolean atualizarVolume(String rg, int novoVolume) {
        String sql = "UPDATE tb_usuario SET volume = ? WHERE rg = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, novoVolume);
            stmt.setString(2, rg);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar volume do usuário: " + e.getMessage());
            return false;
        }
    }
}
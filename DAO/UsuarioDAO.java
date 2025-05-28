
package DAO;

import Model.Usuario;
import Util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    
    // Autenticação do usuário pelo RG e senha
    public boolean autenticar(Usuario usuario) {
        String sql = "SELECT * FROM tb_usuario WHERE rg = ? AND senha = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getRg());
            stmt.setString(2, usuario.getSenha());

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true se encontrou

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existeRg(String rg) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE rg = ?";
         try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rg);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    public Usuario buscarUsuarioPorRg(String rg) {
        String sql = "SELECT id, rg, senha, nome, tipoUsuario, volume FROM usuarios WHERE rg = ?";
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
                    rs.getString("tipoUsuario"),
                    rs.getInt("volume")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean atualizarTipoUsuario(String rg, String novoTipoUsuario) {
        String sql = "UPDATE usuarios SET tipoUsuario = ? WHERE rg = ?";
         try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoTipoUsuario);
            stmt.setString(2, rg);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

        // Recupera um usuário pelo RG
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
            e.printStackTrace();
        }

        return null;
    }
}
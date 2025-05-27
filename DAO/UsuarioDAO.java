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

    // Cadastro de novo usuário
    public boolean cadastrar(Usuario usuario) {
        String sql = "INSERT INTO tb_usuario (rg, senha, nome, tipo_usuario, volume) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getRg());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getNome());
            stmt.setString(4, usuario.getTipoUsuario());
            stmt.setInt(5, usuario.getVolume()); // Novo campo volume

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Verifica se um RG já existe
    public boolean existeRg(String rg) {
        String sql = "SELECT 1 FROM tb_usuario WHERE rg = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, rg);
            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
                    rs.getInt("volume") // Campo volume incluído
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}

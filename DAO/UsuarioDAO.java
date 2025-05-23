package DAO;

import Model.Usuario;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// conectar o banco de dados com o java ( pegar informações do banco de dados )
// importar as bibliotecas necessárias para o banco de dados

public class UsuarioDAO 
{
 public boolean autenticar(Usuario usuario) 
 {
    try(Connection conn = Conexao.getConexao()) 
    {
        String sql = "SELECT * FROM tb_usuario WHERE rg = ? AND senha = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, usuario.getRg());
        stmt.setString(2, usuario.getSenha());//tem q ser a senha do banco de dados?

        ResultSet rs = stmt.executeQuery();
        return rs.next(); // Retorna true se o usuário foi encontrado
    } 
    catch (SQLException e) 
    {
        e.printStackTrace();
        return false;
    } 
 } 
 public boolean cadastrar(Usuario usuario) {
    String sql = "INSERT INTO tb_usuario (rg, senha, nome, tipo_usuario) VALUES (?, ?, ?, ?)";

    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, usuario.getRg());
        stmt.setString(2, usuario.getSenha());
        stmt.setString(3, usuario.getNome());
        stmt.setString(4, usuario.getTipoUsuario());

        int linhasAfetadas = stmt.executeUpdate();
        return linhasAfetadas > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
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

  
}

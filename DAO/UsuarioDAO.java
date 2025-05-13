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
        String sql = "SELECT * FROM tb_usuario WHERE usuario = ? AND senha = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, usuario.getLogin());
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
}

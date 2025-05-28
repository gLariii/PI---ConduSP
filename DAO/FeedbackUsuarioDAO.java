package DAO;

import Model.FeedbackUsuario;
import Util.Conexao; // Importa sua classe de conexão

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackUsuarioDAO {

    public void inserirFeedback(FeedbackUsuario feedback) {
        
        String sql = "INSERT INTO Feedback (id_usuario, pontuacao, observacoes, data) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, feedback.getIdUsuario());
            stmt.setInt(2, feedback.getPontuacao());
            stmt.setString(3, feedback.getObservacoes());
            stmt.setTimestamp(4, feedback.getData());

            stmt.executeUpdate();
            System.out.println("Feedback inserido com sucesso para o usuário ID: " + feedback.getIdUsuario()); // Adicionado para depuração
        } catch (SQLException e) { 
            System.err.println("Erro ao inserir feedback: " + e.getMessage()); 
            e.printStackTrace();
        }
    }

    public FeedbackUsuario buscarFeedbackDoUsuario(int idUsuario) {
        
        String sql = "SELECT id_feedback, id_usuario, pontuacao, observacoes, data FROM Feedback WHERE id_usuario = ? ORDER BY data DESC LIMIT 1";

        try (Connection conn = Conexao.getConexao(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                FeedbackUsuario feedback = new FeedbackUsuario();
                feedback.setIdFeedback(rs.getInt("id_feedback"));
                feedback.setIdUsuario(rs.getInt("id_usuario"));
                feedback.setPontuacao(rs.getInt("pontuacao"));
                feedback.setObservacoes(rs.getString("observacoes")); 
                feedback.setData(rs.getTimestamp("data")); 
                return feedback;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar último feedback: " + e.getMessage()); 
        }
        return null;
    }
    
    public List<FeedbackUsuario> listarFeedbacksDoUsuario(int idUsuario) {
        List<FeedbackUsuario> lista = new ArrayList<>();

        String sql = "SELECT id_feedback, id_usuario, pontuacao, observacoes, data FROM Feedback WHERE id_usuario = ? ORDER BY data DESC";

        System.out.println("SQL a ser executada para listar: " + sql); 
        System.out.println("Buscando feedbacks para idUsuario: " + idUsuario); 

        try (Connection conn = Conexao.getConexao(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            int count = 0; 
            while (rs.next()) {
                FeedbackUsuario f = new FeedbackUsuario();
                f.setIdFeedback(rs.getInt("id_feedback")); 
                f.setIdUsuario(rs.getInt("id_usuario"));
                f.setPontuacao(rs.getInt("pontuacao"));
                f.setObservacoes(rs.getString("observacoes")); 
                f.setData(rs.getTimestamp("data")); 
                lista.add(f);
                count++; 
            }
            System.out.println("Total de feedbacks encontrados no DAO para " + idUsuario + ": " + count); 
        } catch (SQLException e) {
            System.err.println("Erro ao listar feedbacks no DAO: " + e.getMessage()); 
            e.printStackTrace();
        }
        return lista;
    }
}
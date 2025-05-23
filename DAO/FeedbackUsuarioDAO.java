package DAO;

import Model.FeedbackUsuario;
import Util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class FeedbackUsuarioDAO {
    public void inserirFeedback(FeedbackUsuario feedback) {
        String sql = "INSERT INTO Feedback (id_usuario, pontuacao, observacoes) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.getConexao(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, feedback.getIdUsuario());
            stmt.setInt(2, feedback.getPontuacao());
            stmt.setString(3, feedback.getObservacoes());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

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
    
    public void gerarFeedback(int idUsuario, int pontuacaoFinal, String resumoRespostas) {
        // Implemente a lógica de persistência conforme necessário
        // Exemplo: salvar feedback no banco de dados ou apenas imprimir/logar
        System.out.println("Feedback gerado para usuário " + idUsuario + ": " + resumoRespostas + " (Pontuação: " + pontuacaoFinal + ")");
    }
    
}

    

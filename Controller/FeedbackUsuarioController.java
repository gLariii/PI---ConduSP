package Controller;

import DAO.FeedbackUsuarioDAO;
import Model.FeedbackUsuario;

import java.sql.Timestamp;
import java.util.List;

public class FeedbackUsuarioController {

    public boolean gerarFeedback(int idUsuario, int pontuacaoTotal, String resumoRespostas) {
        try {
            FeedbackUsuario feedback = new FeedbackUsuario();
            feedback.setIdUsuario(idUsuario);
            feedback.setPontuacao(pontuacaoTotal);
            feedback.setObservacoes(resumoRespostas);
            feedback.setData(new Timestamp(System.currentTimeMillis())); // Data/hora atual

            FeedbackUsuarioDAO dao = new FeedbackUsuarioDAO();
            dao.inserirFeedback(feedback);

            return true;
        } catch (Exception e) { // Catching Exception aqui é aceitável para o controller
            System.err.println("Erro no controller ao gerar feedback: " + e.getMessage()); // Adicionado para depuração
            e.printStackTrace();
            return false;
        }
    }

    public List<FeedbackUsuario> obterFeedbacksDoUsuario(int idUsuario) {
        FeedbackUsuarioDAO dao = new FeedbackUsuarioDAO();
        List<FeedbackUsuario> feedbacks = dao.listarFeedbacksDoUsuario(idUsuario);
        System.out.println("Feedbacks obtidos pelo Controller para ID " + idUsuario + ": " + feedbacks.size()); // Para depuração
        return feedbacks;
    }
}
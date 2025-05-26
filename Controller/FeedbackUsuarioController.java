package Controller;

import DAO.FeedbackUsuarioDAO;
import Model.FeedbackUsuario;

import java.sql.Timestamp;
// controlador do feedback (manda o feedback armazenado)

public class FeedbackUsuarioController {

    public boolean gerarFeedback(int idUsuario, int pontuacaoTotal, String resumoRespostas) {
        try {
            FeedbackUsuario feedback = new FeedbackUsuario();
            feedback.setIdUsuario(idUsuario);
            feedback.setPontuacao(pontuacaoTotal);
            feedback.setObservacoes(resumoRespostas);
            feedback.setData(new Timestamp(System.currentTimeMillis()));

            FeedbackUsuarioDAO dao = new FeedbackUsuarioDAO();
            dao.inserirFeedback(feedback);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

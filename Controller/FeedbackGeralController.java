package Controller;

import DAO.FeedbackGeralDAO;
import Model.FeedbackGeral;

import java.util.List;

// metodo que controla o feedback geral ( de todos os usuarios)
public class FeedbackGeralController {
    public List<FeedbackGeral> listarFeedbacks() {
        FeedbackGeralDAO dao = new FeedbackGeralDAO();
        return dao.listarTodos();
    }

// metodo que controla o feedback geral ( apenas do usuario logado)

    public List<FeedbackGeral> listarFeedbacksPorUsuario(int idUsuario) {
        FeedbackGeralDAO dao = new FeedbackGeralDAO();
        return dao.listarPorIdUsuario(idUsuario);
    }
}
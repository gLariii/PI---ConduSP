package Controller;

import DAO.FeedbackGeralDAO;
import Model.FeedbackGeral;

import java.util.List;

public class FeedbackGeralController {
    public List<FeedbackGeral> listarFeedbacks() {
        FeedbackGeralDAO dao = new FeedbackGeralDAO();
        return dao.listarTodos();
    }


    public List<FeedbackGeral> listarFeedbacksPorUsuario(int idUsuario) {
        FeedbackGeralDAO dao = new FeedbackGeralDAO();
        return dao.listarPorIdUsuario(idUsuario);
    }
}
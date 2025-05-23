package Controller;

import DAO.FeedbackGeralDAO;
import Model.FeedbackGeral;
import java.util.List;

public class FeedbackGeralController {
    private FeedbackGeralDAO feedbackDAO = new FeedbackGeralDAO();

    public List<FeedbackGeral> listarFeedbacks() {
        return feedbackDAO.listarTodos();
    }
}

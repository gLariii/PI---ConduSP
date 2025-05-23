package Controller;

import DAO.FeedbackGeralDAO;
import Model.FeedbackGeral;
import java.util.List;
// controlador de feedback para o supervisor ( ele lista todos os feedback )

public class FeedbackGeralController {
    private FeedbackGeralDAO feedbackDAO = new FeedbackGeralDAO();

    public List<FeedbackGeral> listarFeedbacks() {
        return feedbackDAO.listarTodos();
    }
}

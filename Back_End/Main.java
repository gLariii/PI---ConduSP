package Back_End;

import Controller.LoginController;
import Controller.FeedbackUsuarioController;
// import Model.Usuario; // You might need this if LoginController returns Usuario object

public class Main {

    public static void main(String[] args) {
        LoginController loginController = new LoginController();
        boolean resultadoLogin = loginController.login("1231231235", "1234");
        System.out.println(resultadoLogin ? "Login bem-sucedido!" : "Falha no login.");

        FeedbackUsuarioController feedbackController = new FeedbackUsuarioController();
        boolean resultadoFeedback = feedbackController.gerarFeedback(3, 5, "Ótimo atendimento!");
        System.out.println(resultadoFeedback ? "Feedback enviado com sucesso!" : "Falha ao enviar feedback.");
        
    }
}


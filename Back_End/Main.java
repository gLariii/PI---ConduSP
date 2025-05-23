package Back_End;

import Controller.LoginController;
import Controller.UsuarioController;
import Model.FeedbackGeral;

import java.util.List;

import Controller.FeedbackGeralController;
import Controller.FeedbackUsuarioController;
import Model.Usuario;// alguma hr vai precisar rlx

// por hr estou usando a classe para testar os codigos e a conexão com bd
public class Main {

    public static void main(String[] args) {
        LoginController loginController = new LoginController();
        boolean resultadoLogin = loginController.login("1231231235", "1234");
        System.out.println(resultadoLogin ? "Login bem-sucedido!" : "Falha no login.");

        FeedbackUsuarioController feedbackController = new FeedbackUsuarioController();
        boolean resultadoFeedback = feedbackController.gerarFeedback(3, 5, "Ótimo atendimento!");
        System.out.println(resultadoFeedback ? "Feedback enviado com sucesso!" : "Falha ao enviar feedback.");
        
        FeedbackGeralController feedbackgeralController = new FeedbackGeralController();
        List<FeedbackGeral> lista = feedbackgeralController.listarFeedbacks();

        if (lista.isEmpty()) {
            System.out.println("Nenhum feedback encontrado.");
        } else {
            System.out.println("Feedbacks encontrados:");
            for (FeedbackGeral f : lista) {
                System.out.println("ID: " + f.getId());
                System.out.println("Registro: " + f.getRegistro());
                System.out.println("Nome: " + f.getNome());
                System.out.println("Pontuação: " + f.getPontuacao());
                System.out.println("Observações: " + f.getObservacoes());
                System.out.println("Data: " + f.getData());
                System.out.println("--------------------------");
            }
        }
        UsuarioController controller = new UsuarioController();
        boolean sucesso = controller.cadastrarUsuario("1234567890", "senha123", "Maria Souza", "operario");

        if (sucesso) {
            System.out.println("Usuário cadastrado com sucesso!");
        } else {
            //já tem verificação em outro lugar n precisa especificar aqui
        }

    }
}


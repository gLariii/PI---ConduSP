package Controller;

import DAO.RespostaUsuarioDAO;
import Model.RespostaUsuario;
import java.util.List;

public class RespostaUsuarioController {

    private RespostaUsuarioDAO respostaUsuarioDAO;
 
// Método que gerencia as respostas dos usuários, como feedbacks e respostas a perguntas

    public RespostaUsuarioController() {
        this.respostaUsuarioDAO = new RespostaUsuarioDAO();
    }

// Método que obtém os feedbacks do usuário com base do usuario logado
    public List<RespostaUsuario> obterFeedbacksDoUsuario(int idUsuario) {
        return respostaUsuarioDAO.obterRespostasDoUsuario(idUsuario);
    }

// Método que salva a resposta do usuário

    public boolean salvarResposta(RespostaUsuario resposta) {
    return respostaUsuarioDAO.salvarRespostaUsuario(resposta);
}

}

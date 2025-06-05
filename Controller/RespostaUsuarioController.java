package Controller;

import DAO.RespostaUsuarioDAO;
import Model.RespostaUsuario;
import java.util.List;

public class RespostaUsuarioController {

    private RespostaUsuarioDAO respostaUsuarioDAO;

    public RespostaUsuarioController() {
        this.respostaUsuarioDAO = new RespostaUsuarioDAO();
    }

    public List<RespostaUsuario> obterFeedbacksDoUsuario(int idUsuario) {
        return respostaUsuarioDAO.obterRespostasDoUsuario(idUsuario);
    }
    
    public boolean salvarResposta(RespostaUsuario resposta) {
    return respostaUsuarioDAO.salvarRespostaUsuario(resposta);
}

}

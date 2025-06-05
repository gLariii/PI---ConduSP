package Model;
import DAO.RespostaUsuarioDAO;
import Model.RespostaUsuario;

public class SalvarResposta {
    public static int pontuacao = 0;

    public static void salvarResposta(int idUsuario, int idFeedback) {
        RespostaUsuario resposta = new RespostaUsuario();
        resposta.setIdUsuario(idUsuario);
        resposta.setIdFeedback(idFeedback);
        resposta.setPontuacaoAtual(pontuacao); 
        resposta.setData(new java.sql.Timestamp(System.currentTimeMillis()));

        RespostaUsuarioDAO dao = new RespostaUsuarioDAO();
        boolean salvo = dao.salvarRespostaUsuario(resposta);

        if (salvo) {
            System.out.println("Pontuação salva com sucesso para o usuário ID: " + idUsuario);
        } else {
            System.out.println("Erro ao salvar pontuação para o usuário ID: " + idUsuario);
        }
    }
}

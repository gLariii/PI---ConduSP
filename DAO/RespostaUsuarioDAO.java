package DAO;

import Model.RespostaUsuario;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RespostaUsuarioDAO {

    public boolean inserirRespostaUsuario(RespostaUsuario resposta) {
        String sql = "INSERT INTO RespostaUsuario (id_questao, id_usuario, acao_executada, ordem_usuario, tipo_resposta, pontuacao_recebida, data_hora) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, resposta.getIdQuestao());
            stmt.setInt(2, resposta.getIdUsuario());
            stmt.setString(3, resposta.getAcaoExecutada());
            stmt.setInt(4, resposta.getOrdemUsuario());
            stmt.setString(5, resposta.getTipoResposta());
            stmt.setInt(6, resposta.getPontuacaoRecebida());
            stmt.setTimestamp(7, resposta.getDataHora());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
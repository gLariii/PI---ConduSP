package DAO;

import Model.RespostaUsuario;
import Util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RespostaUsuarioDAO {

    // Inserir nova resposta
    public boolean inserirResposta(RespostaUsuario resposta) {
        String sql = "INSERT INTO tb_resposta_usuario " +
                "(id_questao, id_usuario, acao_executada, ordem_usuario, tipo_resposta, pontuacao_recebida, data_hora, ordem_Cliques) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, resposta.getIdQuestao());
            stmt.setInt(2, resposta.getIdUsuario());
            stmt.setString(3, resposta.getAcaoExecutada());
            stmt.setInt(4, resposta.getOrdemUsuario());
            stmt.setString(5, resposta.getTipoResposta());
            stmt.setInt(6, resposta.getPontuacaoRecebida());
            stmt.setTimestamp(7, resposta.getDataHora());
            stmt.setInt(8, resposta.getOrdemCliques());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Buscar respostas de um usuário específico
    public List<RespostaUsuario> listarRespostasPorUsuario(int idUsuario) {
        List<RespostaUsuario> respostas = new ArrayList<>();
        String sql = "SELECT * FROM tb_resposta_usuario WHERE id_usuario = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RespostaUsuario resposta = new RespostaUsuario(
                        rs.getInt("id_resposta"),
                        rs.getInt("id_questao"),
                        rs.getInt("id_usuario"),
                        rs.getString("acao_executada"),
                        rs.getInt("ordem_usuario"),
                        rs.getString("tipo_resposta"),
                        rs.getInt("pontuacao_recebida"),
                        rs.getTimestamp("data_hora"),
                        rs.getInt("ordem_Cliques")
                );
                respostas.add(resposta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return respostas;
    }
}

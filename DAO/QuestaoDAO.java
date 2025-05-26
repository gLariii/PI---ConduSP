package DAO;

import Model.Questao;
import Model.RespostaPossivelQuestao;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestaoDAO {

    public Questao getQuestaoById(int idQuestao) {
        String sql = "SELECT * FROM Questao WHERE id_questao = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idQuestao);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Questao(
                    rs.getInt("id_questao"),
                    rs.getInt("id_fase"),
                    rs.getString("descricao"),
                    rs.getString("feedback_erro")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<RespostaPossivelQuestao> getRespostasPossiveisByQuestao(int idQuestao) {
        List<RespostaPossivelQuestao> respostas = new ArrayList<>();
        String sql = "SELECT * FROM RespostaPossivelQuestao WHERE id_questao = ? ORDER BY ordem";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idQuestao);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                respostas.add(new RespostaPossivelQuestao(
                    rs.getInt("id_resposta_possivel"),
                    rs.getInt("id_questao"),
                    rs.getInt("ordem"),
                    rs.getString("acao"),
                    rs.getString("tipo_resposta"),
                    rs.getInt("pontuacao")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return respostas;
    }
}
package DAO;

import Model.FeedbackGeral;
import Util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FeedbackGeralDAO {
// Método para listar todos os feedbacks gerais (pega da view vw_feedback_supervisor todos os feedbacks)
    public List<FeedbackGeral> listarTodos() {
        List<FeedbackGeral> lista = new ArrayList<>();
        try {
            Connection con = Conexao.getConexao();
            String sql = "SELECT * FROM vw_feedback_supervisor";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FeedbackGeral f = new FeedbackGeral(
                    rs.getInt("id_usuario"),
                    rs.getString("nome_usuario"),
                    rs.getString("rg"),
                    rs.getString("tipo_usuario"),
                    rs.getInt("id_resposta"),
                    rs.getInt("pontuacaoAtual"),
                    rs.getDate("data_resposta"),
                    rs.getInt("id_feedback"),
                    rs.getInt("Id_passo"),
                    rs.getString("observacoes"),
                    rs.getString("tipo_feedback_esperado"),
                    rs.getDate("data_feedback_definido"),
                    rs.getInt("id_fase"),
                    rs.getString("nome_fase"),
                    rs.getString("descricao_fase")
                );
                lista.add(f);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Método para listar feedbacks gerais por ID de usuário (pega da view vw_feedback_supervisor todos os feedbacks do usuario logado)
    public List<FeedbackGeral> listarPorIdUsuario(int idUsuario) {
        List<FeedbackGeral> lista = new ArrayList<>();
        try {
            Connection con = Conexao.getConexao();
            String sql = "SELECT * FROM vw_feedback_supervisor WHERE id_usuario = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FeedbackGeral f = new FeedbackGeral(
                    rs.getInt("id_usuario"),
                    rs.getString("nome_usuario"),
                    rs.getString("rg"),
                    rs.getString("tipo_usuario"),
                    rs.getInt("id_resposta"),
                    rs.getInt("pontuacaoAtual"),
                    rs.getDate("data_resposta"),
                    rs.getInt("id_feedback"),
                    rs.getInt("Id_passo"),
                    rs.getString("observacoes"),
                    rs.getString("tipo_feedback_esperado"),
                    rs.getDate("data_feedback_definido"),
                    rs.getInt("id_fase"),
                    rs.getString("nome_fase"),
                    rs.getString("descricao_fase")
                );
                lista.add(f);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
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

// Método para obter as respostas do usuário por ID

    public List<RespostaUsuario> obterRespostasDoUsuario(int idUsuario) {
        List<RespostaUsuario> respostas = new ArrayList<>();
        String sql = "SELECT id_resposta, id_usuario, id_feedback, pontuacaoAtual, data " +
                     "FROM RespostaUsuario " +
                     "WHERE id_usuario = ? " +
                     "ORDER BY data DESC";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    RespostaUsuario resposta = new RespostaUsuario();
                    resposta.setIdResposta(rs.getInt("id_resposta"));
                    resposta.setIdUsuario(rs.getInt("id_usuario"));
                    resposta.setIdFeedback(rs.getInt("id_feedback"));
                    resposta.setPontuacaoAtual(rs.getInt("pontuacaoAtual"));
                    resposta.setData(rs.getTimestamp("data"));
                    respostas.add(resposta);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter respostas do usuário: " + e.getMessage());
            e.printStackTrace();
        }
        return respostas;
    }

// Método para salvar a resposta do usuário

public boolean salvarRespostaUsuario(RespostaUsuario resposta) {
    String sql = "INSERT INTO RespostaUsuario (id_usuario, id_feedback, pontuacaoAtual, data) VALUES (?, ?, ?, ?)";

    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, resposta.getIdUsuario());
        stmt.setInt(2, resposta.getIdFeedback());
        stmt.setInt(3, resposta.getPontuacaoAtual());
        stmt.setTimestamp(4, resposta.getData());

        return stmt.executeUpdate() > 0;

    } catch (SQLException e) {
        System.err.println("Erro ao salvar resposta do usuário: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}


}
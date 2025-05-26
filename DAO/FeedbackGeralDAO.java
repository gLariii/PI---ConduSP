package DAO;

import Model.FeedbackGeral;
import Util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackGeralDAO {
    public List<FeedbackGeral> listarTodos() {
        List<FeedbackGeral> lista = new ArrayList<>();

        String sql = "SELECT * FROM vw_feedback_usuario";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                FeedbackGeral feedback = new FeedbackGeral(
                    rs.getInt("id"),
                    rs.getString("registro"),
                    rs.getString("nome"),
                    rs.getInt("pontuacao"),
                    rs.getString("observacoes"),
                    rs.getDate("data")
                );
                lista.add(feedback);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}

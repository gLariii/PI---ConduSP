package DAO;

import Model.FeedbackGeral;
import Util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FeedbackGeralDAO {
    public List<FeedbackGeral> listarTodos() {
        List<FeedbackGeral> lista = new ArrayList<>();
        try {
            Connection con = Conexao.getConexao();
            String sql = "SELECT * FROM vw_feedback_usuario";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FeedbackGeral f = new FeedbackGeral(
                    rs.getInt("id"),
                    rs.getString("registro"),
                    rs.getString("nome"),
                    rs.getInt("pontuacao"),
                    rs.getString("observacoes"),
                    rs.getDate("data")
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

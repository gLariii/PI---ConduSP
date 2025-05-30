package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao 
{
    public static Connection getConexao() throws SQLException 
    {
        String url = "jdbc:mysql://condusp-studylarih-2dcb.l.aivencloud.com:27034/condusp";
        String user = "avnadmin";
        String password = "AVNS_zbo21IxO4CkVBRBP-s5";

        return DriverManager.getConnection(url, user, password);
    }   
}
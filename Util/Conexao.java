package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//conexão com o banco de dados MySQL
// precisa da lib para funcionar, que é o mysql-connector-java-8.0.32.jar
//conexao por um banco de dados remoto, hospedado na Aiven

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
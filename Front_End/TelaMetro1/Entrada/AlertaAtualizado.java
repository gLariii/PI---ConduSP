package TelaMetro1.Entrada;

// Alerta atualização 

import javax.swing.*;
import java.awt.*;

public class AlertaAtualizado extends JDialog {

    public AlertaAtualizado(JFrame parent, String title, String message) {
        super(parent, title, true); 
        initUI(message);
    }

    private void initUI(String message) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Redimensionando a borda 
        panel.setLayout(new BorderLayout(10, 10));


        JLabel messageLabel = new JLabel("<html><h2 style='color:#333;'>Atualização do usuario</h2>"
                + "<p>" + message + "</p></html>");
        panel.add(messageLabel, BorderLayout.CENTER); // Setando a menssagem de alerta

        JButton okButton = new JButton("OK"); // Botão de confirmação
        okButton.setFocusPainted(false);
        okButton.setBackground(new Color(59, 89, 182));
        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Arial", Font.BOLD, 14)); // Fonte 
        okButton.setPreferredSize(new Dimension(80, 35)); // Definindo o tamanho do botão
        okButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE); // Criando o painel do botão
        buttonPanel.add(okButton);

        getContentPane().add(panel, BorderLayout.CENTER); // Centralizando 
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        pack();

        setLocationRelativeTo(null); 
        setResizable(false);
    }
}

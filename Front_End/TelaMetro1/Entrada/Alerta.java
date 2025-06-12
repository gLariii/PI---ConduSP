package TelaMetro1.Entrada;

// Setando Alerta de Erro de Login 
import javax.swing.*;
import java.awt.*;

public class Alerta extends JDialog {

    public Alerta(JFrame parent, String title, String message) {
        super(parent, title, true);
        initUI(message);
    }

    private void initUI(String message) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BorderLayout(10, 10));

        JLabel messageLabel = new JLabel("<html><h2 style='color:#333;'>Erro de Login</h2>"
                + "<p>" + message + "</p></html>");
        panel.add(messageLabel, BorderLayout.CENTER); // Menssagem de erro 

    
        // Botão de confirmação
        JButton okButton = new JButton("OK");
        okButton.setFocusPainted(false);
        okButton.setBackground(new Color(59, 89, 182));
        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Arial", Font.BOLD, 14));
        okButton.setPreferredSize(new Dimension(80, 35));
        okButton.addActionListener(e -> dispose());

        // Criando o painel do botão e posição 
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(okButton);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        pack();

        setLocationRelativeTo(null); 
        setResizable(false);
    }
}

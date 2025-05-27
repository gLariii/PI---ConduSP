package TelaMetro1.Entrada;

import javax.swing.*;
import java.awt.*;

public class Alerta extends JDialog {

    public Alerta(JFrame parent, String title, String message) {
        super(parent, title, true); // título e modal
        initUI(message);
    }

    private void initUI(String message) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BorderLayout(10, 10));

        // Ícone (você pode personalizar esse caminho ou remover a linha)
        JLabel iconLabel = new JLabel(new ImageIcon("src/Assets/Imagens/warning.png"));
        panel.add(iconLabel, BorderLayout.WEST);

        // Mensagem com HTML estilizado
        JLabel messageLabel = new JLabel("<html><h2 style='color:#333;'>Erro de Login</h2>"
                + "<p>" + message + "</p></html>");
        panel.add(messageLabel, BorderLayout.CENTER);

        // Botão OK
        JButton okButton = new JButton("OK");
        okButton.setFocusPainted(false);
        okButton.setBackground(new Color(59, 89, 182));
        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Arial", Font.BOLD, 14));
        okButton.setPreferredSize(new Dimension(80, 35));
        okButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(okButton);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        pack();

        setLocationRelativeTo(null); // Centraliza a janela
        setResizable(false);
    }
}

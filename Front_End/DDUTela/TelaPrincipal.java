package DDUTela;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Menu Principal");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // tela cheia
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Painel com layout absoluto
        JPanel painel = new JPanel(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("assets/images/DDUcomFachadaAberta.jpg"));

        // Redimensiona a imagem para o tamanho da tela (ex: 1920x1080)
        Image imagem = icon.getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);

        JLabel fundo = new JLabel(new ImageIcon(imagem));
        fundo.setBounds(0, 0, 1920, 1080);


        // Botão 1
        JButton botao1 = new JButton("");
        botao1.setBounds(547, 808, 55, 55); // ajuste posição
        botao1.addActionListener(e -> abrirPagina("Página 1"));
        botao1.setOpaque(false);
        botao1.setContentAreaFilled(false);
        botao1.setBorderPainted(false);
        botao1.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Botão 2
        JButton botao2 = new JButton("");
        botao2.setBounds(797, 818, 55, 53);
        botao2.addActionListener(e -> abrirPagina("Página 2"));
        botao2.setOpaque(false);
        botao2.setContentAreaFilled(false);
        botao2.setBorderPainted(false);
        botao2.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Botão 3
        JButton botao3 = new JButton("");
        botao3.setBounds(882, 818, 55, 53);
        botao3.addActionListener(e -> abrirPagina("Página 3"));
        botao3.setOpaque(false);
        botao3.setContentAreaFilled(false);
        botao3.setBorderPainted(false);
        botao3.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Botão 4
        JButton botao4 = new JButton("");
        botao4.setBounds(968, 818, 60, 55);
        botao4.addActionListener(e -> abrirPagina("Página 4"));
        botao4.setOpaque(false);
        botao4.setContentAreaFilled(false);
        botao4.setBorderPainted(false);
        botao4.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Botão 5
        JButton botao5 = new JButton("");
        botao5.setBounds(1140, 818, 60, 55);
        botao5.addActionListener(e -> abrirPagina("Página 5"));
        botao5.setOpaque(false);
        botao5.setContentAreaFilled(false);
        botao5.setBorderPainted(false);
        botao5.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Adicionar na ordem correta
        painel.add(botao1);
        painel.add(botao2);
        painel.add(botao3);
        painel.add(botao4);
        painel.add(botao5);
        painel.add(fundo);

        setContentPane(painel);
        setVisible(true);
    }

    // Método para simular mudança de página
    public void abrirPagina(String nome) {
        JOptionPane.showMessageDialog(this, "Você abriu " + nome);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal());
    }
}
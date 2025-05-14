package DDUTela;

import javax.swing.*;

import CabineDeControleTela.CabineDeControleTela;

import java.awt.*;


public class DDUPrincipal extends JPanel {

    private JFrame parentFrame;

    private Image imagemDeFundo;

    public DDUPrincipal(JFrame frame) {
        this.parentFrame = frame;


        setLayout(null);

        // Carrega imagem (caminho absoluto ou relativo ajustado)
        ImageIcon icon = new ImageIcon(getClass().getResource("/DDUTela/assets/images/DDUprepara.jpg"));
        imagemDeFundo = icon.getImage();

        // Painel interno para botões
        JPanel painel = new JPanel(null);
        painel.setBounds(0, 0, 1920, 1080);
        painel.setOpaque(false); // transparência para exibir imagem de fundo

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(10, 10, 100, 30);
        btnVoltar.addActionListener(e -> trocarTela(new CabineDeControleTela(parentFrame)));

        // Botão 2
        JButton botao2 = new JButton("FE");
        botao2.setBounds(787, 787, 55, 53);
        botao2.addActionListener(e -> trocarTela(new FE(parentFrame)));
        estiloBotao(botao2);

        // Botão 3
        JButton botao3 = new JButton("INFOPAS");
        botao3.setBounds(872, 787, 55, 53);
        botao3.addActionListener(e -> trocarTela(new INFOPASS(parentFrame)));
        estiloBotao(botao3);

        // Botão 4
        JButton botao4 = new JButton("");
        botao4.setBounds(953, 787, 60, 55);
        estiloBotao(botao4);

        // Botão 5
        JButton botao5 = new JButton("MANUT");
        botao5.setBounds(1130, 787, 60, 55);
        botao5.addActionListener(e -> trocarTela(new MANUT(parentFrame)));
        estiloBotao(botao5);

        // Adiciona componentes
        painel.add(btnVoltar);
        painel.add(botao2);
        painel.add(botao3);
        painel.add(botao4);
        painel.add(botao5);

        add(painel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
    }

    private void estiloBotao(JButton botao) {
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setForeground(new Color(0, 0, 0, 0));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void trocarTela(JPanel novaTela) {
        parentFrame.setContentPane(novaTela);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}

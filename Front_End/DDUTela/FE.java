package DDUTela;

import javax.swing.*;

import CabineDeControleTela.CabineDeControleTela;

import java.awt.*;

public class FE extends JPanel {

    private JFrame parentFrame;

    public FE(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("/DDUTela/assets/images/DDUfe.jpg"));
        Image imagem = icon.getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
        JLabel fundo = new JLabel(new ImageIcon(imagem));
        fundo.setBounds(0, 0, 1920, 1080);

        // Botões
        JButton botao1 = criarBotao(390, 870, e -> trocarTela(new DDUPrincipal(parentFrame)));
        JButton botao2 = criarBotao(780, 870, e -> trocarTela(new FE(parentFrame)));
        JButton botao3 = criarBotao(900, 870, e -> trocarTela(new INFOPASS(parentFrame)));
        JButton botao5 = criarBotao(1300, 880, e -> trocarTela(new MANUT(parentFrame)));
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(10, 10, 100, 30);
        btnVoltar.addActionListener(e -> trocarTela(new CabineDeControleTela(parentFrame)));


        add(botao1);
        add(botao2);
        add(botao3);
        add(botao5);
        add(btnVoltar);
        add(fundo);
    }

    private JButton criarBotao(int x, int y, java.awt.event.ActionListener acao) {
        JButton btn = new JButton("");
        btn.setBounds(x, y, 100, 80);
        btn.addActionListener(acao);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void trocarTela(JPanel novaTela) {
        parentFrame.setContentPane(novaTela);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}

package VDUTelas;

import javax.swing.*;

import CabineDeControleTela.CabineDeControleTela;

import java.awt.*;

public class VDUMenu extends JPanel {

    private JFrame parentFrame;

    public VDUMenu(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("imagens/VDU.jpg"));
        Image imagem = icon.getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
        JLabel fundo = new JLabel(new ImageIcon(imagem));
        fundo.setBounds(0, 0, 1920, 1080);

        // Botões
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(10, 10, 100, 30);
        btnVoltar.addActionListener(e -> trocarTela(new CabineDeControleTela(parentFrame)));
        add(btnVoltar);
        add(fundo);
    }

    private void trocarTela(JPanel novaTela) {
        parentFrame.setContentPane(novaTela);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}

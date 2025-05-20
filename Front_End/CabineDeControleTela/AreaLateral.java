package CabineDeControleTela;

import javax.swing.*;

import BoteiraLateralTelas.BoteiraLateralTela;

import java.awt.*;

public class AreaLateral extends JPanel {

    private JFrame parentFrame;

    public AreaLateral(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/AreaLateral.png"));
        Image imagem = icon.getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
        JLabel fundo = new JLabel(new ImageIcon(imagem));
        fundo.setBounds(0, 0, 1920, 1080);

        // Botões
        JButton botao1 = new JButton("");
        botao1.setBounds(275, 528, 143, 294);
        botao1.addActionListener(e -> trocarTela(new BoteiraLateralTela(parentFrame)));
        botao1.setOpaque(false);
        botao1.setContentAreaFilled(false); 
        botao1.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(10, 10, 100, 30);
        btnVoltar.addActionListener(e -> trocarTela(new CabineDeControleTela(parentFrame)));


        add(botao1);
        add(btnVoltar);
        add(fundo);
    }

    private void trocarTela(JPanel novaTela) {
        parentFrame.setContentPane(novaTela);
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    
}

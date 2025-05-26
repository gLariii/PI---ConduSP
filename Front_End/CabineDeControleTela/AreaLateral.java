package CabineDeControleTela;

import javax.swing.*;

import BoteiraLateralTelas.BoteiraLateralTela;
import Carro.Carro5VisaoGeral;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class AreaLateral extends JPanel {

    private JFrame parentFrame;
    private Image imagemDeFundoLateral;

    private JButton botao1;
    private JButton botaoTraseira;
    private JButton btnPorta;
    private JButton btnVoltar;

    public AreaLateral(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);
        adicionarBotoes();

        // Listener para responsividade
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                reposicionarBotoes();
            }
        });
    }

    @Override 
    protected void paintComponent(Graphics g) { 
        super.paintComponent(g); 
        if (imagemDeFundoLateral == null) { 
            ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/AreaLateral.png")); 
            imagemDeFundoLateral = icon.getImage(); 
        } 
        g.drawImage(imagemDeFundoLateral, 0, 0, getWidth(), getHeight(), this); 
    } 

    private void adicionarBotoes() { 
        botao1 = new JButton("");
        botao1.addActionListener(e -> trocarTela(new BoteiraLateralTela(parentFrame)));            
        botao1.setOpaque(false);
        botao1.setContentAreaFilled(false);
        botao1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(botao1);

        botaoTraseira = new JButton("");
        botaoTraseira.addActionListener(e -> trocarTela(new CabineTraseira(parentFrame)));            
        botaoTraseira.setOpaque(false);
        botaoTraseira.setContentAreaFilled(false);
        botaoTraseira.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(botaoTraseira);

        btnPorta = new JButton("");
        btnPorta.addActionListener(e -> trocarTela(new Carro5VisaoGeral(parentFrame)));            
        btnPorta.setOpaque(false);
        btnPorta.setContentAreaFilled(false);
        btnPorta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnPorta);


        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> trocarTela(new CabineDeControleTela(parentFrame)));
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnVoltar);

        reposicionarBotoes();
    }

    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();

        // Ajuste proporcional aos valores que você tinha:
        // Original: botao1.setBounds(275, 528, 143, 294);
        // Proporção aproximada:
        botao1.setBounds((int)(w * 0.14), (int)(h * 0.49), (int)(w * 0.07), (int)(h * 0.27));

        botaoTraseira.setBounds((int)(w * 0.02), (int)(h * 0.49), (int)(w * 0.02), (int)(h * 0.15));

        btnPorta.setBounds((int)(w * 0.252), (int)(h * 0.083), (int)(w * 0.34), (int)(h * 0.9));

        // Original: btnVoltar.setBounds(10, 10, 100, 30);
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));

        repaint();
    }

    private void trocarTela(JPanel novaTela) {
        parentFrame.setContentPane(novaTela);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}

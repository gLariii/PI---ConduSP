package CabineDeControleTela;

import javax.swing.*;

import BoteiraLateralTelas.BoteiraLateralTela;
import Carro.Carro5VisaoGeral;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import CabineDeControleTela.*;
import Assets.*;

public class AreaLateral extends JPanel {

    private JFrame parentFrame;
    private Image imagemDeFundoLateral;

    private JButton botao1;
    private JButton botaoTraseira;
    private JButton btnPorta;
    private JButton btnVoltar;

    private int ordemCliques;
    
    public AreaLateral(JFrame frame, int ordemCliques) {
        this. ordemCliques = ordemCliques;


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
            ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/AreaLateral.jpg")); 
            imagemDeFundoLateral = icon.getImage(); 
        } 
        g.drawImage(imagemDeFundoLateral, 0, 0, getWidth(), getHeight(), this); 
        int w = getWidth();
        int h = getHeight();
        if (PainelCBTCeChave.indexChave == 1) {
            Image imagemExtra = new ImageIcon(getClass().getResource("/Assets/Imagens/ChaveIcone.png")).getImage();
            g.drawImage(imagemExtra, (int)(w * 0.9), (int)(h * 0.05), (int)(w * 0.1), (int)(h * 0.1), this);
        }
        if (Cinturao.index == 1) {
            Image imagemExtra = new ImageIcon(getClass().getResource("/Assets/Imagens/CinturaoIcone.png")).getImage();
            g.drawImage(imagemExtra, (int)(w * 0.8), (int)(h * 0.05), (int)(w * 0.1), (int)(h * 0.1), this);
            Image imagemExtra2 = new ImageIcon(getClass().getResource("/Assets/Imagens/AdesivoIcone.png")).getImage();
            g.drawImage(imagemExtra2, (int)(w * 0.7), (int)(h * 0.05), (int)(w * 0.1), (int)(h * 0.1), this);
        }
    } 

    private void adicionarBotoes() { 
        botao1 = new JButton("");
        botao1.addActionListener(e -> trocarTela(new BoteiraLateralTela(parentFrame, ordemCliques)));            
        botao1.setOpaque(false);
        botao1.setContentAreaFilled(false);
        botao1.setBorderPainted(false);
        botao1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(botao1);

        botaoTraseira = new JButton("");
        botaoTraseira.addActionListener(e -> trocarTela(new Cinturao(parentFrame, ordemCliques)));            
        botaoTraseira.setOpaque(false);
        botaoTraseira.setContentAreaFilled(false);
        botaoTraseira.setBorderPainted(false);
        botaoTraseira.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(botaoTraseira);

        btnPorta = new JButton("");
        btnPorta.addActionListener(e -> trocarTela(new Carro5VisaoGeral(parentFrame, ordemCliques)));            
        btnPorta.setOpaque(false);
        btnPorta.setContentAreaFilled(false);
        btnPorta.setBorderPainted(false);
        btnPorta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnPorta);


        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> trocarTela(new CabineDeControleTela(parentFrame, ordemCliques)));
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
        botao1.setBounds((int)(w * 0.35), (int)(h * 0.55), (int)(w * 0.07), (int)(h * 0.30));

        botaoTraseira.setBounds((int)(w * 0), (int)(h * 0.035), (int)(w * 0.08), (int)(h * 0.48));

        btnPorta.setBounds((int)(w * 0.45), (int)(h * 0), (int)(w * 0.20) ,(int)(h * 1));

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

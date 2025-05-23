package Carro;
import CabineDeControleTela.AreaLateral;
import CabineDeControleTela.CabineDeControleTela;
import Carro.PortasAbertas;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

public class Carro5VisaoGeral extends JPanel {

    private Image imagemDeFundo;
    private JFrame parentFrame;

    // Botões como atributos
    private JButton btnPortas, btnVoltar;

    public Carro5VisaoGeral(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);
        adicionarBotoes();

        // Listener para redimensionar os botões conforme a tela
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                reposicionarBotoes();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemDeFundo == null) {
            ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/13 - Visão geral do carro 5.jpg"));
            imagemDeFundo = icon.getImage();
        }
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
    }

    private void adicionarBotoes() {
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> substituirPainel(new CabineDeControleTela(parentFrame)));
        btnPortas = criarBotao(() -> substituirPainel(new PortasAbertas(parentFrame)));
        add(btnVoltar);
        add(btnPortas);
        reposicionarBotoes();
    }

    private JButton criarBotao(Runnable action) {
        JButton botao = new JButton("");
        botao.addActionListener(e -> action.run());
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setFocusPainted(false);
        botao.setForeground(Color.BLACK); // cor padrão da borda
        return botao;
    }

    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();

        //Tamanho e Posicionamento
        btnPortas.setBounds((int)(w * 0.475), (int)(h * 0.22), (int)(w * 0.17), (int)(h * 0.7));
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
        
        repaint();
    }

    private void substituirPainel(JPanel novoPainel) {
        parentFrame.setContentPane(novoPainel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}

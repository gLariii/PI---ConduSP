package DDUTela;

import javax.swing.*;

import CabineDeControleTela.CabineDeControleTela;

import java.awt.*;

public class DDUMenu extends JPanel {

    private JFrame parentFrame;

    private Image imagemDeFundo;

    public DDUMenu(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);
    }
    @Override 
    protected void paintComponent(Graphics g) { 
        super.paintComponent(g); 
        // Aqui, garantimos que a imagem de fundo seja ajustada conforme o tamanho da tela 
        if (imagemDeFundo == null) { 
            ImageIcon icon = new ImageIcon(getClass().getResource("/DDUTela/assets/images/DDUMenu.jpg")); 
            imagemDeFundo = icon.getImage(); 
        } 
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this); 
        // Agora, ajustamos os botões depois que o painel foi renderizado 
        adicionarBotoes(); 
    } 

    private void adicionarBotoes() {
        // Botões
        JButton botao2 = criarBotao(794, 812, e -> trocarTela(new FE(parentFrame)));
        JButton botao3 = criarBotao(880, 814, e -> trocarTela(new INFOPASS(parentFrame)));
        JButton botao5 = criarBotao(1140, 820, e -> trocarTela(new MANUT(parentFrame)));
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(10, 10, 100, 30);
        btnVoltar.addActionListener(e -> trocarTela(new CabineDeControleTela(parentFrame)));

        add(botao2);
        add(botao3);
        add(botao5);
        add(btnVoltar);
    }

    private JButton criarBotao(int x, int y, java.awt.event.ActionListener acao) {
        JButton btn = new JButton("");
        btn.setBounds(x, y, 60, 55);
        btn.addActionListener(acao);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);  
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void trocarTela(JPanel novaTela) {
        parentFrame.setContentPane(novaTela);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}

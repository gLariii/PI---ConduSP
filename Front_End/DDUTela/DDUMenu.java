package DDUTela;

import javax.swing.*;
import CabineDeControleTela.CabineDeControleTela;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DDUMenu extends JPanel {

    private JFrame parentFrame;
    private Image imagemDeFundo;

    private JButton botaoFE;
    private JButton botaoINFOPASS;
    private JButton botaoMANUT;
    private JButton btnVoltar;

    public DDUMenu(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);
        setSize(frame.getSize());

        adicionarBotoes();
        adicionarListenerDeRedimensionamento();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemDeFundo == null) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/DDUTela/assets/images/DDUMenu.jpg"));
            imagemDeFundo = icon.getImage();
        }
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
    }

    private void adicionarBotoes() {
        botaoFE = criarBotao(e -> trocarTela(new FE(parentFrame)));
        botaoINFOPASS = criarBotao(e -> trocarTela(new INFOPASS(parentFrame)));
        botaoMANUT = criarBotao(e -> trocarTela(new MANUT(parentFrame)));
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> trocarTela(new CabineDeControleTela(parentFrame)));

        add(botaoFE);
        add(botaoINFOPASS);
        add(botaoMANUT);
        add(btnVoltar);

        reposicionarBotoes();
    }

    private JButton criarBotao(java.awt.event.ActionListener acao) {
        JButton btn = new JButton("");
        btn.addActionListener(acao);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();
        //Tamanho e Posicionamento
        botaoFE.setBounds((int) (w * 0.414), (int) (h * 0.755), (int) (w * 0.03), (int) (h * 0.05));
        botaoINFOPASS.setBounds((int) (w * 0.459), (int) (h * 0.755), (int) (w * 0.03), (int) (h * 0.05));
        botaoMANUT.setBounds((int) (w * 0.594), (int) (h * 0.76), (int) (w * 0.03), (int) (h * 0.05));
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
    }

    private void adicionarListenerDeRedimensionamento() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                reposicionarBotoes();
            }
        });
    }

    private void trocarTela(JPanel novaTela) {
        parentFrame.setContentPane(novaTela);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}

package ModuloDeComunicaçãoTela;

import javax.swing.*;
import CabineDeControleTela.CabineDeControleTela;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ModuloDeComunicacaoTelaInicial extends JPanel {

    private Image imagemDeFundo;
    private JFrame parentFrame;

    private JButton botao1;
    private JButton botao2;
    private JButton btnVoltar;

    public ModuloDeComunicacaoTelaInicial(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("Imagens/Modulo de comunicação.jpg"));
        imagemDeFundo = icon.getImage();

        criarBotoes();
        adicionarListenerRedimensionamento();
        reposicionarBotoes();
    }

    private void criarBotoes() {
        botao1 = criarBotao("PA", e -> substituirPainel(new PATela(parentFrame)));
        botao2 = criarBotao("Ajustes", e -> substituirPainel(new PALista(parentFrame)));
        btnVoltar = new JButton("Voltar");
        
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> substituirPainel(new CabineDeControleTela(parentFrame)));

        add(botao1);
        add(botao2);
        add(btnVoltar);
    }

    private JButton criarBotao(String texto, java.awt.event.ActionListener acao) {
        JButton btn = new JButton(texto);
        btn.addActionListener(acao);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setForeground(new Color(0, 0, 0, 0)); // invisível
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
    }

    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();

        //Tamanho e Posicionamento
        botao1.setBounds((int)(w * 0.263), (int)(h * 0.25), (int)(w * 0.13), (int)(h * 0.093));
        botao2.setBounds((int)(w * 0.6), (int)(h * 0.669), (int)(w * 0.13), (int)(h * 0.093));    
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
    }

    private void adicionarListenerRedimensionamento() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                reposicionarBotoes();
            }
        });
    }

    private void substituirPainel(JPanel novoPainel) {
        parentFrame.setContentPane(novoPainel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }


}

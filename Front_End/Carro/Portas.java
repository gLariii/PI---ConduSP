package Carro;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class Portas extends JPanel {
    public final String[] backgrounds = {
        "Imagens/PortaAberta.jpg",
        "Imagens/PortaFechada.jpg",
        "Imagens/PortaLacrada.jpg"
    };
    public static int index = 0;
    private Image imagemDeFundo;
    private JFrame parentFrame;
    // Botões como atributos
    private static JButton botao1, botao2, btnVoltar,btnFechar, btnLacrar;

    public Portas(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);
        adicionarBotoes();
        carregarImagemFundo();

        // Listener para redimensionar os botões conforme a tela
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                reposicionarBotoes();
            }
        });
    }

    private void carregarImagemFundo() {
        ImageIcon icon = new ImageIcon(getClass().getResource(backgrounds[index]));
        imagemDeFundo = icon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemDeFundo != null) {
            g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void adicionarBotoes() {
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> substituirPainel(new Carro5VisaoGeral(parentFrame)));
        botao1 = criarBotao(() -> substituirPainel(new DispositivosDeEmergência(parentFrame)));
        botao2 = criarBotao(() -> substituirPainel(new Soleira(parentFrame)));
        btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> {
            if (index == 0){
                botao1.setVisible(false);
                botao2.setVisible(false);
                btnFechar.setText("Abrir");
                add(btnLacrar);
                btnLacrar.setVisible(true);
            }else if (index == 1){
                botao1.setVisible(true);
                botao2.setVisible(true);
                btnFechar.setText("Fechar");
                btnLacrar.setVisible(false);
            }
            index = (index + 1) % (backgrounds.length - 1);
            carregarImagemFundo();
            repaint();
        });

        btnLacrar = new JButton("Passar Adesivo");
        btnLacrar.addActionListener(e -> {
            index = 2;
            btnFechar.setVisible(false);
            btnLacrar.setVisible(false);
            carregarImagemFundo();
            revalidate();
            repaint();
        });
        add(btnVoltar);

        if (Portas.index == 0){
            add (botao1);
            add (botao2);
        }

        if (PainelExternoAberto.index == 1 && index != 2){
            add(btnFechar);
        }

        reposicionarBotoes();
    }

    private JButton criarBotao(Runnable action) {
        JButton botao = new JButton("");
        botao.addActionListener(e -> action.run());
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setForeground(Color.BLACK); // cor padrão da borda
        return botao;
    }

    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();
        int largura = (int)(w * 0.052);
        int altura = (int)(h * 0.028);

        //Tamanho e Posicionamento
        botao1.setBounds((int)(w * 0.66), (int)(h * 0.42), (int)(w * 0.08), (int)(h * 0.14));
        botao2.setBounds((int)(w * 0.37), (int)(h * 0.92), (int)(w * 0.28), (int)(h * 0.06));
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
        btnFechar.setBounds((int)(w -largura) / 2, (int) (h - altura) / 2, (int)(w * 0.052), (int)(h * 0.028));
        btnLacrar.setBounds((int)(w -largura) / 2, (int) ((h - altura) / 2) + (int)(h * 0.2), (int)(w * 0.052), (int)(h * 0.028));
        repaint();
    }

    private void substituirPainel(JPanel novoPainel) {
        parentFrame.setContentPane(novoPainel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}

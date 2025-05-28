package Carro;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import CabineDeControleTela.*;

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
    
    private int ordemCliques;
    

    public Portas(JFrame frame, int ordemCliques) {
        this.ordemCliques = ordemCliques;
        ordemCliques++;

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
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> substituirPainel(new Carro5VisaoGeral(parentFrame, ordemCliques)));
        add(btnVoltar);
        botao1 = criarBotao(() -> substituirPainel(new DispositivosDeEmergência(parentFrame, ordemCliques)));
        add(botao1);
        botao2 = criarBotao(() -> substituirPainel(new Soleira(parentFrame, ordemCliques)));
        add(botao2);
        btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> {
            if (index == 0){
                botao1.setVisible(false);
                botao2.setVisible(false);
                btnLacrar.setVisible(true);
                btnFechar.setText("Abrir");
            }else if (index == 1){
                botao1.setVisible(true);
                botao2.setVisible(true);
                btnLacrar.setVisible(false);
                btnFechar.setText("Fechar");
            }
            index = (index + 1) % (backgrounds.length - 1);
            carregarImagemFundo();
            repaint();
        });
        add(btnFechar);

        btnLacrar = new JButton("Passar Adesivo");
        btnLacrar.addActionListener(e -> {
            index = 2;
            btnFechar.setVisible(false);
            btnLacrar.setVisible(false);
            carregarImagemFundo();
            revalidate();
            repaint();
        });
        add (btnLacrar);
        reposicionarBotoes();

        if (PainelExternoAberto.index == 1 && index != 2){
            btnFechar.setVisible(true);
        }
        if (index == 0){
            botao1.setVisible(true);
            botao2.setVisible(true);
            btnLacrar.setVisible(false);
            btnFechar.setText("Fechar");
            if (PainelExternoAberto.index != 1)
                btnFechar.setVisible(false);
        }
        if (index == 1){
            btnFechar.setText("Abrir");
            btnLacrar.setVisible(true);
        }
        if (index == 2){
            btnLacrar.setVisible(false);
            btnFechar.setVisible(false);
        }

    }

    private JButton criarBotao(Runnable action) {
        JButton botao = new JButton("");
        botao.addActionListener(e -> action.run());
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setFocusPainted(false);
        botao.setVisible(false);
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

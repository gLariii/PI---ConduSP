package Carro;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import CabineDeControleTela.*;
import Model.*;

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
    private static JButton botao1, botao2, btnVoltar,btnFechar, btnLacrar, btnVerificar;
    
    private int ordemCliques;
    private int idUsuarioLogado;
    private static boolean primeiroCliqueEmergencia = true;
    private static boolean primeiroCliqueSoleira = true;
    private static boolean primeiroCliqueFechar = true;
    private static boolean primeiroCliqueVerificar = true;
    private static boolean primeiroCliqueLacrar = true;
    private int feedback;
    

    public Portas(JFrame frame, int idUsuario) {
        this.parentFrame = frame;
        this.idUsuarioLogado = idUsuario;
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
        btnVoltar.addActionListener(e -> {AudioPlayer.playSound("SomCaminhar.wav");substituirPainel(new Carro5VisaoGeral(parentFrame, ordemCliques));});
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBackground(new Color(30, 60, 90));
        btnVoltar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.setOpaque(true);
        add(btnVoltar);

        botao1 = new JButton("");
        botao1.addActionListener(e -> {
            if (primeiroCliqueEmergencia == true){
                SalvarResposta.pontuacao += 1;
                this.feedback = 21;
                SalvarResposta.salvarResposta(idUsuarioLogado, this.feedback);
                primeiroCliqueEmergencia = false;
            }
            substituirPainel(new DispositivosDeEmergência(parentFrame, ordemCliques));
        });
        botao1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao1.setOpaque(false);
        botao1.setContentAreaFilled(false);
        botao1.setFocusPainted(false);
        botao1.setVisible(false);
        botao1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao1.setForeground(Color.BLACK); // cor padrão da borda
        botao1.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3, true));
        add(botao1);

        botao2 = new JButton("");
        botao2.addActionListener(e -> {
            if (primeiroCliqueSoleira == true){
                SalvarResposta.pontuacao += 1;
                this.feedback = 20;
                SalvarResposta.salvarResposta(idUsuarioLogado, this.feedback);
                primeiroCliqueSoleira = false;
            }
            substituirPainel(new Soleira(parentFrame, ordemCliques));
        });
        botao2.setContentAreaFilled(false);
        botao2.setVisible(false);
        botao2.setText("Conferir soleira");
        botao2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao2.setBackground(new Color(30, 60, 90));
        botao2.setForeground(Color.WHITE);
        botao2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        botao2.setFocusPainted(false);
        botao2.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 5, true));
        botao2.setOpaque(true);
        add(botao2);

        btnFechar = new JButton("Fechar");
        btnFechar.setFont(new Font("Arial", Font.BOLD, 14));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setBackground(new Color(30, 60, 90));
        btnFechar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btnFechar.setFocusPainted(false);
        btnFechar.setContentAreaFilled(false);
        btnFechar.setOpaque(true);
        btnFechar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFechar.addActionListener(e -> {
            AudioPlayer.playSound("SomPorta.wav");
            if (index == 0){
                botao1.setVisible(false);
                botao2.setVisible(false);
                btnLacrar.setVisible(true);
                btnFechar.setText("Abrir");
                btnVerificar.setVisible(true);
            }else if (index == 1){
                botao1.setVisible(true);
                botao2.setVisible(true);
                btnLacrar.setVisible(false);
                btnVerificar.setVisible(false);
                btnFechar.setText("Fechar");
            }
            if (primeiroCliqueFechar == true){
                SalvarResposta.pontuacao += 2;
                this.feedback = 26;
                SalvarResposta.salvarResposta(idUsuarioLogado, this.feedback);
                primeiroCliqueFechar = false;
            }
            index = (index + 1) % (backgrounds.length - 1);
            carregarImagemFundo();
            revalidate();
            repaint();
        });
        add(btnFechar);

        btnLacrar = new JButton("Passar Adesivo");
        btnLacrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnLacrar.setForeground(Color.WHITE);
        btnLacrar.setBackground(new Color(30, 60, 90));
        btnLacrar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btnLacrar.setFocusPainted(false);
        btnLacrar.setContentAreaFilled(false);
        btnLacrar.setOpaque(true);
        btnLacrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLacrar.addActionListener(e -> {
            if (primeiroCliqueLacrar == true){
                SalvarResposta.pontuacao += 2;
                this.feedback = 28;
                SalvarResposta.salvarResposta(idUsuarioLogado, this.feedback);
                primeiroCliqueLacrar = false;
            }
            AudioPlayer.playSound("SomFita.wav");
            index = 2;
            btnFechar.setVisible(false);
            btnLacrar.setVisible(false);
            btnVerificar.setVisible(false);
            carregarImagemFundo();
            revalidate();
            repaint();
        });
        add (btnLacrar);

        btnVerificar = new JButton("Verificar Porta");
        btnVerificar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVerificar.setForeground(Color.WHITE);
        btnVerificar.setBackground(new Color(30, 60, 90));
        btnVerificar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btnVerificar.setFocusPainted(false);
        btnVerificar.setContentAreaFilled(false);
        btnVerificar.setOpaque(true);
        btnVerificar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVerificar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "A porta foi fechada corretamente", "Correto", JOptionPane.INFORMATION_MESSAGE);
            if (primeiroCliqueVerificar == true){
                SalvarResposta.pontuacao += 2;
                this.feedback = 27;
                SalvarResposta.salvarResposta(idUsuarioLogado, this.feedback);
                primeiroCliqueVerificar = false;
            }
            btnVerificar.setVisible(false);
            revalidate();
            repaint();
        });
        add (btnVerificar);

        if (PainelExternoAberto.index == 1 && index != 2){
            btnFechar.setVisible(true);
        }
        if (index == 0){
            botao1.setVisible(true);
            botao2.setVisible(true);
            btnLacrar.setVisible(false);
            btnFechar.setText("Fechar");
            btnVerificar.setVisible(false);
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
            btnVerificar.setVisible(false);
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
        botao.setVisible(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setForeground(Color.BLACK); // cor padrão da borda
        return botao;
    }
    

    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();
        int largura = (int)(w * 0.2); 
        int altura = (int)(h * 0.07);

        //Tamanho e Posicionamento
        botao1.setBounds((int)(w * 0.66), (int)(h * 0.42), (int)(w * 0.08), (int)(h * 0.14));
        botao2.setBounds((int)(w * 0.37), (int)(h * 0.92), (int)(w * 0.28), (int)(h * 0.06));
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
        btnFechar.setBounds((int)(w -largura) / 2, (int) (h - altura) / 2, (largura), (int)(altura));
        btnLacrar.setBounds((int)(w -largura) / 2, (int) ((h - altura) / 2) + (int)(h * 0.2), (largura), (int)(altura));
        btnVerificar.setBounds((int)(w -largura) / 2, (int) ((h - altura) / 2) + (int)(h * 0.4), (largura), (int)(altura));
        repaint();
    }

    private void substituirPainel(JPanel novoPainel) {
        parentFrame.setContentPane(novoPainel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}

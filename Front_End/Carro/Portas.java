package Carro;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import CabineDeControleTela.*;
import Model.*;

public class Portas extends JPanel {
    // Caminhos das imagens de fundo
    public final String[] backgrounds = {
        "Imagens/PortaAberta.jpg",
        "Imagens/PortaFechada.jpg",
        "Imagens/PortaLacrada.jpg"
    };
    public static int index = 0; // Índice da imagem de fundo atual
    private Image imagemDeFundo;
    private JFrame parentFrame; // Referência ao frame principal

    // Botões como atributos
    private static JButton botao1, botao2, btnVoltar, btnFechar, btnLacrar, btnVerificar;

    private int ordemCliques;
    private int idUsuarioLogado; 
    private String tipo_usuarioLogado; 

    // Variáveis de controle de primeiros cliques
    private static boolean primeiroCliqueEmergencia = true;
    private static boolean primeiroCliqueSoleira = true;
    private static boolean primeiroCliqueFechar = true;
    private static boolean primeiroCliqueVerificar = true;
    private static boolean primeiroCliqueLacrar = true;

    private int feedback; // Armazena o código de feedback

    public Portas(JFrame frame, String tipo_usuario, int idUsuario) {
        this.tipo_usuarioLogado = tipo_usuario;
        this.parentFrame = frame;
        this.idUsuarioLogado = idUsuario;
        this.ordemCliques = ordemCliques;
        ordemCliques++; // Incrementa a ordem de cliques
        setLayout(null);
        adicionarBotoes(); 
        carregarImagemFundo(); 

        // Listener para redimensionar botões ao redimensionar janela
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                reposicionarBotoes();
            }
        });
    }

    // Carrega a imagem de fundo conforme o index
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

        // Desenha ícone da chave se necessário
        if (PainelCBTCeChave.indexChave == 1) {
            Image imagemExtra = new ImageIcon(getClass().getResource("/Assets/Imagens/ChaveIcone.png")).getImage();
            g.drawImage(imagemExtra, (int)(w * 0.9), (int)(h * 0.05), (int)(w * 0.1), (int)(h * 0.1), this);
        }

        // Desenha ícones do cinto e adesivo se necessário
        if (Cinturao.index == 1) {
            Image imagemExtra = new ImageIcon(getClass().getResource("/Assets/Imagens/CinturaoIcone.png")).getImage();
            g.drawImage(imagemExtra, (int)(w * 0.8), (int)(h * 0.05), (int)(w * 0.1), (int)(h * 0.1), this);
            Image imagemExtra2 = new ImageIcon(getClass().getResource("/Assets/Imagens/AdesivoIcone.png")).getImage();
            g.drawImage(imagemExtra2, (int)(w * 0.7), (int)(h * 0.05), (int)(w * 0.1), (int)(h * 0.1), this);
        }
    }

    // Criação e configuração dos botões
    private void adicionarBotoes() {
        // Botão Voltar
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            AudioPlayer.playSound("SomCaminhar.wav");
            substituirPainel(new Carro5VisaoGeral(parentFrame, tipo_usuarioLogado, idUsuarioLogado));
        });
        configurarBotao(btnVoltar);

        // Botão Emergência
        botao1 = new JButton("");
        botao1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao1.setOpaque(false);
        botao1.setContentAreaFilled(false);
        botao1.setFocusPainted(false);
        botao1.setVisible(false);
        botao1.addActionListener(e -> {
            if (primeiroCliqueEmergencia) {
                SalvarResposta.pontuacao += 1;
                this.feedback = 21;
                SalvarResposta.salvarResposta(idUsuarioLogado, this.feedback);
                primeiroCliqueEmergencia = false;
            }
            substituirPainel(new DispositivosDeEmergência(parentFrame, tipo_usuarioLogado, idUsuarioLogado));
        });
        configurarBotaoOculto(botao1);

        // Botão Soleira
        botao2 = new JButton("");
        botao2.setFont(new Font("Arial", Font.BOLD, 14));
        botao2.setForeground(Color.WHITE);
        botao2.setBackground(new Color(30, 60, 90));
        botao2.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        botao2.setFocusPainted(false);
        botao2.setContentAreaFilled(false);
        botao2.setOpaque(true);
        botao2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao2.addActionListener(e -> {
            if (primeiroCliqueSoleira) {
                SalvarResposta.pontuacao += 1;
                this.feedback = 20;
                SalvarResposta.salvarResposta(idUsuarioLogado, this.feedback);
                primeiroCliqueSoleira = false;
            }
            substituirPainel(new Soleira(parentFrame, tipo_usuarioLogado, idUsuarioLogado));
        });
        configurarBotaoOculto(botao2);
        botao2.setText("Conferir soleira");

        // Botão Fechar/Abrir Porta
        btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> acaoFecharPorta());
        configurarBotao(btnFechar);

        // Botão Lacrar (Passar Adesivo)
        btnLacrar = new JButton("Passar Adesivo");
        btnLacrar.addActionListener(e -> acaoLacrarPorta());
        configurarBotao(btnLacrar);

        // Botão Verificar Porta
        btnVerificar = new JButton("Verificar Porta");
        btnVerificar.addActionListener(e -> acaoVerificarPorta());
        configurarBotao(btnVerificar);

        // Configura visibilidade inicial dos botões
        configurarVisibilidadeInicial();
        reposicionarBotoes();
    }

    // Define estilo para botões normais
    private void configurarBotao(JButton botao) {
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setForeground(Color.WHITE);
        botao.setBackground(new Color(30, 60, 90));
        botao.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        botao.setFocusPainted(false);
        botao.setContentAreaFilled(false);
        botao.setOpaque(true);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(botao);
    }

    // Define estilo para botões invisíveis
    private void configurarBotaoOculto(JButton botao) {
        add(botao);
    }

    // Ação do botão Fechar/Abrir
    private void acaoFecharPorta() {
        AudioPlayer.playSound("SomPorta.wav");
        if (index == 0) { // Porta aberta
            botao1.setVisible(false);
            botao2.setVisible(false);
            if (!Cinturao.primeiroClique) btnLacrar.setVisible(true);
            btnFechar.setText("Abrir");
            btnVerificar.setVisible(true);
        } else if (index == 1) { // Porta fechada
            botao1.setVisible(true);
            botao2.setVisible(true);
            btnLacrar.setVisible(false);
            btnVerificar.setVisible(false);
            btnFechar.setText("Fechar");
        }
        if (primeiroCliqueFechar) {
            SalvarResposta.pontuacao += 2;
            this.feedback = 26;
            SalvarResposta.salvarResposta(idUsuarioLogado, this.feedback);
            primeiroCliqueFechar = false;
        }
        index = (index + 1) % (backgrounds.length - 1); // Alterna imagem
        carregarImagemFundo();
        revalidate();
        repaint();
    }

    // Ação do botão Lacrar
    private void acaoLacrarPorta() {
        if (primeiroCliqueLacrar) {
            SalvarResposta.pontuacao += 2;
            this.feedback = 28;
            SalvarResposta.salvarResposta(idUsuarioLogado, this.feedback);
            primeiroCliqueLacrar = false;
        }
        AudioPlayer.playSound("SomFita.wav");
        index = 2; // Porta lacrada
        btnFechar.setVisible(false);
        btnLacrar.setVisible(false);
        btnVerificar.setVisible(false);
        carregarImagemFundo();
        revalidate();
        repaint();
    }

    // Ação do botão Verificar
    private void acaoVerificarPorta() {
        JOptionPane.showMessageDialog(this, "A porta foi fechada corretamente", "Correto", JOptionPane.INFORMATION_MESSAGE);
        if (primeiroCliqueVerificar) {
            SalvarResposta.pontuacao += 2;
            this.feedback = 27;
            SalvarResposta.salvarResposta(idUsuarioLogado, this.feedback);
            primeiroCliqueVerificar = false;
        }
        btnVerificar.setVisible(false);
        revalidate();
        repaint();
    }

    // Define visibilidade inicial dos botões
    private void configurarVisibilidadeInicial() {
        if (PainelExternoAberto.index == 1 && index != 2) btnFechar.setVisible(true);
        if (index == 0) {
            botao1.setVisible(true);
            botao2.setVisible(true);
            btnLacrar.setVisible(false);
            btnFechar.setText("Fechar");
            btnVerificar.setVisible(false);
            if (PainelExternoAberto.index != 1) btnFechar.setVisible(false);
        }
        if (index == 1) {
            btnFechar.setText("Abrir");
            if (!Cinturao.primeiroClique) btnLacrar.setVisible(true);
        }
        if (index == 2) {
            btnLacrar.setVisible(false);
            btnFechar.setVisible(false);
            btnVerificar.setVisible(false);
        }
    }

    // Posiciona os botões na tela
    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();
        int largura = (int)(w * 0.2);
        int altura = (int)(h * 0.07);

        botao1.setBounds((int)(w * 0.66), (int)(h * 0.42), (int)(w * 0.08), (int)(h * 0.14));
        botao2.setBounds((int)(w * 0.37), (int)(h * 0.92), (int)(w * 0.28), (int)(h * 0.06));
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
        btnFechar.setBounds((w - largura) / 2, (h - altura) / 2, largura, altura);
        btnLacrar.setBounds((w - largura) / 2, ((h - altura) / 2) + (int)(h * 0.2), largura, altura);
        btnVerificar.setBounds((w - largura) / 2, ((h - altura) / 2) + (int)(h * 0.4), largura, altura);
        repaint();
    }

    // Troca o painel atual
    private void substituirPainel(JPanel novoPainel) {
        parentFrame.setContentPane(novoPainel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}

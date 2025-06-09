package ModuloDeComunicaçãoTela;

import javax.swing.*;
import CabineDeControleTela.*;
import java.awt.*;
import java.awt.event.*;
import Carro.*;
import Model.SalvarResposta;
import ChaveReversoraTela.*;
import Model.*;
import Controller.RespostaUsuarioController;
import DAO.RespostaUsuarioDAO;

public class ModuloDeComunicacaoTelaInicial extends JPanel {

    private final String[] backgrounds = {
        "Imagens/Modulo de comunicação.jpg",
        "Imagens/Modulo de comunicação Microfone PA.jpg",
        "Imagens/Modulo de comunicação radio.jpg"
    };

    private int idUsuarioLogado;
    
    private static int index = 0;
    private Image imagemDeFundo;
    private JFrame parentFrame;

    private CircleButton botaoPA;
    private CircleButton botaoCCO;
    private JButton btnVoltar;

    private int ordemCliques;

    private JLabel labelMensagem;
    private Timer timerEscrita;
    private StringBuilder mensagemParcial = new StringBuilder();
    private int CCOEmitido = 0;
    public static boolean primeiroClique = true;
    public static boolean segundoClique = true;
    public static boolean terceiroClique = true;
    private int feedback;

    // Mensagens para PA
    private final String[] mensagensPA = {
        "<html>Não segurem as portas do trem, isso causa atrasos em todo o sistema</html>",
        "<html>Paramos devido a uma falha neste trem, estamos trabalhando para a normalização,<br>agradecemos a compreensão</html>"
    };

    // Mensagem única para CCO
    private final String mensagemProblemaPorta = "<html>"
        + "Operador: L38 em Rosa 2 ao CCO<br>"
        + "CCO: CCO em QAP, L38 Rosa 2<br>"
        + "Operador: CCO, a porta 53 do L38 não fecha, já foi feito Reciclo, PA, reciclo, porém a mesma permanece aberta.<br>"
        + "CCO: QSL, L38, porta 53 não fecha, saia para verificar seu problema<br>"
        + "Operador: QSL, saindo para verificar o problema."
        + "</html>";

    private final String mensagemPortaIsolada = "<html>"
            + "Operador: L38 em Rosa 2 ao CCO<br>"
            + "CCO: CCO em QAP, L38 Rosa 2<br>"
            + "Operador: Porta 53 devidamente isolada<br>"
            + "CCO: Permissão para partir<br>"
            + "Operador: QSL, partindo."
            + "</html>";


    public ModuloDeComunicacaoTelaInicial(JFrame frame, int idUsuario) {
        this.idUsuarioLogado = idUsuario;
        this.parentFrame = frame;
        this.ordemCliques = ordemCliques;
        setLayout(null);

        carregarImagemFundo();
        criarBotoes();
        criarLabelMensagem();
        adicionarListenerRedimensionamento();
        reposicionarBotoes();
    }

    private void criarBotoes() {
        botaoCCO = new CircleButton("", null);
        botaoCCO.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                AudioPlayer.playSound("SomDigitacao.wav");
                index = 2;
                carregarImagemFundo();
                repaint();
                labelMensagem.setVisible(true);
                if (Portas.index == 0){
                    iniciarEscrita(mensagemProblemaPorta);
                    CCOEmitido = 1;
                }
                else{
                    iniciarEscrita(mensagemPortaIsolada);
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                AudioPlayer.stopSound();
                index = 0;
                if (segundoClique == true){
                    SalvarResposta.pontuacao += 1;
                    feedback = 12;
                    segundoClique = false;
                    SalvarResposta.salvarResposta(idUsuarioLogado, feedback);
                }
                carregarImagemFundo();
                repaint();
                pararEscrita();
                labelMensagem.setVisible(false);
            }
        });

        botaoPA = new CircleButton("", null);
        botaoPA.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                AudioPlayer.playSound("SomDigitacao.wav");
                index = 1;
                carregarImagemFundo();
                repaint();
                labelMensagem.setVisible(true);
                iniciarEscrita(mensagensPA[CCOEmitido]);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                AudioPlayer.stopSound();
                index = 0;
                carregarImagemFundo();
                repaint();
                pararEscrita();
                labelMensagem.setVisible(false);
                if (primeiroClique == true){
                    SalvarResposta.pontuacao += 1;
                    if (ChaveReversoraTela.indexChaveReversora == 0)
                        feedback = 8;
                    else{
                        feedback = 7;
                    }
                    primeiroClique = false;
                    SalvarResposta.salvarResposta(idUsuarioLogado, feedback);
                }
                if (terceiroClique == true){
                    SalvarResposta.pontuacao += 1;
                    feedback = 13;
                    terceiroClique = false;
                    SalvarResposta.salvarResposta(idUsuarioLogado, feedback);
                }
            }
        });

        btnVoltar = new JButton("Voltar");
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBackground(new Color(30, 60, 90));
        btnVoltar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setOpaque(true);
        btnVoltar.addActionListener(e -> substituirPainel(new CabineDeControleTela(parentFrame, ordemCliques)));

        add(botaoPA);
        add(botaoCCO);
        add(btnVoltar);
    }

    private void criarLabelMensagem() {
        labelMensagem = new JLabel("");
        labelMensagem.setForeground(Color.WHITE);
        labelMensagem.setBackground(Color.BLACK);
        labelMensagem.setOpaque(true);
        labelMensagem.setFont(new Font("Arial", Font.BOLD, 20));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
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

    private void carregarImagemFundo() {
        ImageIcon icon = new ImageIcon(getClass().getResource(backgrounds[index]));
        imagemDeFundo = icon.getImage();
    }

    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();

        botaoPA.setBounds((int) (w * 0.164), (int) (h * 0.257), (int) (w * 0.04), (int) (h * 0.06));
        botaoCCO.setBounds((int) (w * 0.159), (int) (h * 0.559), (int) (w * 0.04), (int) (h * 0.06));
        btnVoltar.setBounds((int) (w * 0.005), (int) (h * 0.009), (int) (w * 0.052), (int) (h * 0.028));
        labelMensagem.setBounds((int)(w * 0.1), (int)(h * 0.8), (int)(w * 0.8), (int)(h *0.2));
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

    private void iniciarEscrita(String mensagemCompleta) {
        mensagemParcial.setLength(0);
        labelMensagem.setText("");
        add(labelMensagem);

        int totalDuration = 5000;
        int numCaracteres = mensagemCompleta.length();
        int delay = totalDuration / numCaracteres;

        timerEscrita = new Timer(delay, new ActionListener() {
            int pos = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (pos < mensagemCompleta.length()) {
                    mensagemParcial.append(mensagemCompleta.charAt(pos));
                    labelMensagem.setText(mensagemParcial.toString());
                    pos++;
                } else {
                    timerEscrita.stop();
                }
            }
        });

        timerEscrita.start();
    }

    private void pararEscrita() {
        if (timerEscrita != null && timerEscrita.isRunning()) {
            timerEscrita.stop();
        }
        labelMensagem.setText("");
    }

    class CircleButton extends JButton {
        private boolean isHovering = false;

        public CircleButton(String texto, java.awt.event.ActionListener acao) {
            super(texto);
            if (acao != null) addActionListener(acao);
            setOpaque(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setFocusPainted(false);
            setBorderPainted(false);
            setForeground(Color.BLACK);

            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    isHovering = true;
                    repaint();
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    isHovering = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            if (isHovering) {
                g2.setColor(Color.YELLOW);
                g2.setStroke(new BasicStroke(2));
            } else {
                g2.setColor(getForeground());
                g2.setStroke(new BasicStroke(1));
            }
            g2.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
        }

        @Override
        public boolean contains(int x, int y) {
            int radius = getWidth() / 2;
            return Math.pow(x - getWidth() / 2, 2) + Math.pow(y - getHeight() / 2, 2) <= Math.pow(radius, 2);
        }
    }
}

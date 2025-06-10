package CabineDeControleTela;

import javax.swing.*;
import BoteiraLateralTelas.BoteiraLateralTela;
import Carro.EscolhaDeCarro;
import TelaFimDeJogo.TelaGameOver;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AreaLateral extends JPanel {

    private JFrame parentFrame;
    private Image imagemDeFundoLateral;

    private JButton botao1;
    private JButton botaoTraseira;
    private JButton btnPorta;
    private JButton btnVoltar;

    private int ordemCliques;

    private String tipo_usuarioLogado;
    private int idUsuarioLogado;

    // Variáveis para o efeito de fade
    private Timer fadeOutTimer;
    private float alpha = 0.0f;

    public AreaLateral(JFrame frame, String tipo_usuario, int idUsuario) { // Construtor agora recebe o ID do usuário
        this.idUsuarioLogado = idUsuario;
        this.tipo_usuarioLogado = tipo_usuario;
        this.ordemCliques = 0; // Inicializando a variável

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

        // Desenha a sobreposição para o fade out
        if (fadeOutTimer != null && fadeOutTimer.isRunning()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(0, 0, 0, alpha)); // Cor preta com alpha variável
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void adicionarBotoes() {
        botao1 = new JButton("");
        botao1.addActionListener(e -> trocarTela(new BoteiraLateralTela(parentFrame, tipo_usuarioLogado, idUsuarioLogado)));
        configurarBotaoTransparente(botao1);
        add(botao1);

        botaoTraseira = new JButton("");
        botaoTraseira.addActionListener(e -> trocarTela(new Cinturao(parentFrame, tipo_usuarioLogado, idUsuarioLogado)));
        configurarBotaoTransparente(botaoTraseira);
        add(botaoTraseira);

        btnPorta = new JButton("");
        btnPorta.addActionListener(e -> {
            if(PainelCBTCeChave.chaveInserida == true) {
                // Inicia a transição com fade para a tela de fim de jogo
                trocarTelaComFade(new TelaGameOver(parentFrame, tipo_usuarioLogado, idUsuarioLogado));
            } else {
                trocarTela(new EscolhaDeCarro(parentFrame, tipo_usuarioLogado, idUsuarioLogado));
            }
        });
        configurarBotaoTransparente(btnPorta);
        add(btnPorta);

        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> trocarTela(new CabineDeControleTela(parentFrame, tipo_usuarioLogado, idUsuarioLogado)));
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBackground(new Color(30, 60, 90));
        btnVoltar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setOpaque(true);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnVoltar);

        reposicionarBotoes();
    }

    private void configurarBotaoTransparente(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void reposicionarBotoes() {
        int w = getWidth();
        int h = getHeight();

        botao1.setBounds((int)(w * 0.35), (int)(h * 0.55), (int)(w * 0.07), (int)(h * 0.30));
        botaoTraseira.setBounds(0, (int)(h * 0.035), (int)(w * 0.08), (int)(h * 0.48));
        btnPorta.setBounds((int)(w * 0.45), 0, (int)(w * 0.20), h);
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));

        repaint();
    }

    private void trocarTela(JPanel novaTela) {
        parentFrame.setContentPane(novaTela);
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void trocarTelaComFade(final JPanel novaTela) {
        if (fadeOutTimer != null && fadeOutTimer.isRunning()) {
            return; // Já está em transição
        }

        // Configuração do Timer para o fade out
        fadeOutTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += 0.05f; // Aumenta a opacidade da sobreposição
                if (alpha >= 1.0f) {
                    alpha = 1.0f;
                    fadeOutTimer.stop();
                    
                    // Lógica para o fade in na nova tela (simplificado)
                    if (novaTela instanceof TelaGameOver) {
                        ((TelaGameOver) novaTela).iniciarFadeIn();
                    }
                    
                    parentFrame.setContentPane(novaTela);
                    parentFrame.revalidate();
                    parentFrame.repaint();
                }
                repaint();
            }
        });

        alpha = 0.0f; // Reseta o alpha
        fadeOutTimer.start();
    }
}
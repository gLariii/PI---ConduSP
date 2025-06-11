package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import CabineDeControleTela.CabineDeControleTela;
import Model.SalvarResposta;

public class Video extends JPanel {

    private final String textoCompleto = "Iniciando caso de falha: Problema de Portas...";
    private JLabel textoLabel;
    private Timer timer;
    private int charIndex = 0;

    private JFrame parentFrame;
    private String tipo_usuarioLogado;
    private int idUsuarioLogado;

    public Video(JFrame frame, String tipo_usuario, int idUsuario) {
        this.parentFrame = frame;
        this.tipo_usuarioLogado = tipo_usuario;
        this.idUsuarioLogado = idUsuario;

        // Configurações do painel
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());

        // Configurações do texto
        textoLabel = new JLabel();
        textoLabel.setFont(new Font("Monospaced", Font.BOLD, 42));
        textoLabel.setForeground(Color.WHITE);
        add(textoLabel);

        // Inicia a animação
        iniciarAnimacaoDeTexto();
    }

    private void iniciarAnimacaoDeTexto() {
        // O Timer será chamado a cada 100 milissegundos
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (charIndex < textoCompleto.length()) {
                    // Adiciona a próxima letra ao texto do label
                    textoLabel.setText(textoCompleto.substring(0, charIndex + 1));
                    charIndex++;
                } else {
                    // A animação terminou, para o timer
                    timer.stop();
                    // Adiciona uma pequena pausa antes de transicionar
                    pausarETransicionar();
                }
            }
        });
        timer.start();
    }

    private void pausarETransicionar() {
        // Cria um segundo Timer para uma pausa de 2 segundos após o texto aparecer
        Timer pausaTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transicionarParaCabine();
            }
        });
        pausaTimer.setRepeats(false); // Garante que execute apenas uma vez
        pausaTimer.start();
    }

    private void transicionarParaCabine() {
        parentFrame.setContentPane(new CabineDeControleTela(parentFrame, tipo_usuarioLogado, idUsuarioLogado));
        parentFrame.revalidate();
        parentFrame.repaint();
        SalvarResposta.pontuacao = 0;
    }
}
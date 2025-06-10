package ChaveReversoraTela;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import CabineDeControleTela.*;
import Carro.*;
import Controller.RespostaUsuarioController;
import DAO.RespostaUsuarioDAO;
import Model.*;
import ChaveReversoraTela.AudioPlayer;

public class ChaveReversoraTela extends JPanel {

    private final String[] backgrounds = {
        "ChaveReversoraNeutro.jpg",
        "ChaveReversoraRe.jpg",
        "ChaveReversoraFrente.jpg"
    };

    public static int indexChaveReversora = 0;
    private Image imagemDeFundo;
    private JFrame parentFrame;
    private JButton btnTrocar;
    private JButton btnVoltar;
    private int idUsuarioLogado;
    private boolean primeiroClique = true;
    private int feedback;
    private int ordemCliques;
    

    // Modifique o construtor para receber o idUsuario
    public ChaveReversoraTela(JFrame frame, int idUsuario) {
        this.parentFrame = frame;
        this.idUsuarioLogado = idUsuario; // Atribua o valor do parâmetro à variável de instância
        this.ordemCliques = 0; // Você pode redefinir ou gerenciar 'ordemCliques' de outra forma se precisar

        setLayout(null);

        carregarImagemFundo();

        // Botão para trocar o fundo
        btnTrocar = new JButton("");
        btnTrocar.setOpaque(false);
        btnTrocar.setContentAreaFilled(false);
        btnTrocar.setBorderPainted(false);
        btnTrocar.setFocusPainted(false);
        btnTrocar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTrocar.addActionListener(e -> {
            AudioPlayer.playSound("SomAlavanca.wav");
            indexChaveReversora = (indexChaveReversora + 1) % backgrounds.length;
            carregarImagemFundo();
            repaint();
        });
        add(btnTrocar);

       // Botão Voltar
        btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBackground(new Color(30, 60, 90));
        btnVoltar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setOpaque(true);
        
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> {
            //Verifica se chave reversora foi colocada em ré e retira pontos
            if (primeiroClique == true){
                if (indexChaveReversora == 1){
                    SalvarResposta.pontuacao -= 3;
                    this.feedback = 2;
                    SalvarResposta.salvarResposta(idUsuario, this.feedback);
                }
            }
            // if (Portas.index != 0 && indexChaveReversora == 2 && PainelCBTCeChave.indexCBCT == 00 && PainelCBTCeChave.indexChave == 0){ -- Código para terminar o jogo corretamente

            voltarParaCabine();
        });
        
        add(btnVoltar);

        adicionarListenerRedimensionamento();
        reposicionarComponentes();
    }

    private void carregarImagemFundo() {
        ImageIcon icon = new ImageIcon(getClass().getResource(backgrounds[indexChaveReversora]));
        imagemDeFundo = icon.getImage();
    }

    private void voltarParaCabine() {
        // Ao voltar para a CabineDeControleTela, passe o idUsuarioLogado novamente
        parentFrame.setContentPane(new CabineDeControleTela(parentFrame, idUsuarioLogado));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemDeFundo != null) {
            // carregarImagemFundo(); // Esta linha pode causar recarregamento excessivo se não houver mudança
        }
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

    private void reposicionarComponentes() {
        int w = getWidth();
        int h = getHeight();

        //Tamanho e Posicionamento
        btnVoltar.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.052), (int)(h * 0.028));
        btnTrocar.setBounds((int)(w * 0.396), (int)(h * 0.296), (int)(w * 0.208), (int)(h * 0.407));
    }

    private void adicionarListenerRedimensionamento() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                reposicionarComponentes();
            }
        });
    }
}
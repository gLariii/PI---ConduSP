package TelaMetro1.telaMenu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;

import Assets.Cores; 
import Controller.FeedbackUsuarioController; 
import Model.FeedbackUsuario;

public class FeedbackPanel extends JPanel {

    private Image imagemDeFundo;
    private Image logoOriginal, logoRedimensionada;
    private int logoWidth = 60;
    private int logoHeight = 60;
    private Runnable voltarAcao;

    private JTable tabelaFeedbacks;
    private DefaultTableModel modeloTabela;
    private int idUsuarioLogado; // ID do usuário logado

    public FeedbackPanel(String imagemPath, Runnable voltarAcao, int idUsuario) {
        this.voltarAcao = voltarAcao;
        this.idUsuarioLogado = idUsuario; 

        carregarImagens(imagemPath);

        setLayout(new BorderLayout());
        setBackground(Cores.AZUL_METRO); 
        setOpaque(true);

        add(criarNavBar(), BorderLayout.NORTH);
        add(criarPainelCentral(), BorderLayout.CENTER);

        redimensionarLogo(logoWidth, logoHeight);

        carregarFeedbacksDoUsuario(idUsuarioLogado);
    }

    private void carregarImagens(String imagemPath) {
        try {
            InputStream isFundo = getClass().getResourceAsStream(imagemPath);
            if (isFundo != null) {
                imagemDeFundo = ImageIO.read(isFundo);
            }

            InputStream isLogo = getClass().getResourceAsStream("/Assets/Imagens/LogoCorrigida4.png");
            if (isLogo != null) {
                logoOriginal = ImageIO.read(isLogo);
            }

        } catch (IOException e) {
            System.err.println("Erro ao carregar imagens: " + e.getMessage()); 
            e.printStackTrace();
        }
    }

    private JPanel criarNavBar() {
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(Cores.AZUL_METRO);
        navBar.setPreferredSize(new Dimension(getWidth(), 60));

        JButton btnVoltar = botoes.criarBotaoVoltar(); 
        btnVoltar.addActionListener(e -> {
            if (voltarAcao != null) voltarAcao.run();
        });

        JPanel painelEsquerdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        painelEsquerdo.setOpaque(false);
        painelEsquerdo.add(btnVoltar);
        navBar.add(painelEsquerdo, BorderLayout.WEST);

        JLabel titulo = new JLabel("Histórico de Partidas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);
        navBar.add(titulo, BorderLayout.CENTER);

        return navBar;
    }

    private JPanel criarPainelCentral() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setOpaque(false);
        painel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
        painel.setBackground(Cores.AZUL_METRO);


        modeloTabela = new DefaultTableModel(new Object[]{"Data", "Pontuação", "Observações"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        tabelaFeedbacks = new JTable(modeloTabela);
        tabelaFeedbacks.setFont(new Font("Arial", Font.PLAIN, 16));
        tabelaFeedbacks.setRowHeight(25);
        tabelaFeedbacks.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        tabelaFeedbacks.getTableHeader().setBackground(Cores.AZUL_METRO);
        tabelaFeedbacks.getTableHeader().setForeground(Color.WHITE);

        tabelaFeedbacks.setBackground(Cores.AZUL_METRO_TRANSPARENTE);
        tabelaFeedbacks.setForeground(Color.white);
        tabelaFeedbacks.setGridColor(Color.WHITE);
        tabelaFeedbacks.setShowGrid(true);

        JScrollPane scroll = new JScrollPane(tabelaFeedbacks);
        scroll.getViewport().setBackground(Cores.AZUL_METRO);

        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }

    void carregarFeedbacksDoUsuario(int idUsuario) {
        System.out.println("Carregando feedbacks para o ID de usuário: " + idUsuarioLogado); // Para depuração

        FeedbackUsuarioController controller = new FeedbackUsuarioController();
        List<FeedbackUsuario> feedbacks = controller.obterFeedbacksDoUsuario(idUsuario);

        modeloTabela.setRowCount(0);

        if (feedbacks.isEmpty()) {
            System.out.println("Nenhum feedback encontrado para o usuário ID: " + idUsuarioLogado); // Para depuração

            modeloTabela.addRow(new Object[]{"", "Nenhum feedback encontrado", ""});
        } else {
            for (FeedbackUsuario f : feedbacks) {
                modeloTabela.addRow(new Object[]{
                    f.getData(),
                    f.getPontuacao(),
                    f.getObservacoes()
                });
                System.out.println("Adicionado feedback à tabela: " + f.toString());
           }
        }
    }

    public void redimensionarLogo(int width, int height) {
        if (logoOriginal != null) {
            logoRedimensionada = logoOriginal.getScaledInstance(
                    Math.max(20, Math.min(width, 150)),
                    Math.max(20, Math.min(height, 150)),
                    Image.SCALE_SMOOTH
            );
            this.logoWidth = width;
            this.logoHeight = height;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imagemDeFundo != null) {
            g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        }

        if (logoRedimensionada != null) {
            int x = getWidth() - logoWidth - 15;
            int y = getHeight() - logoHeight - 15;
            g.drawImage(logoRedimensionada, x, y, this);

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            g2d.drawString("Condução SP", 15, getHeight() - 15);
            g2d.dispose();
        }
    }
}
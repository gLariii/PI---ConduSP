package TelaMetro1.telaMenu;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Assets.Cores;
import Controller.FeedbackGeralController;
import Model.FeedbackGeral;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;
import java.util.List;

public class FeedbackGeralPanel extends JPanel {

    private Image imagemDeFundo;
    private Image logoOriginal, logoRedimensionada;
    private int logoWidth = 60;
    private int logoHeight = 60;

    private JTable tabelaFeedbacks;
    private DefaultTableModel modeloTabela;

    private Runnable voltarAcao;

    public FeedbackGeralPanel(String imagemPath, Runnable voltarAcao) {
        this.voltarAcao = voltarAcao;

        carregarImagens(imagemPath);

        setLayout(new BorderLayout());
        setOpaque(true);

        add(criarNavBar(), BorderLayout.NORTH);
        add(criarPainelCentral(), BorderLayout.CENTER);

        redimensionarLogo(logoWidth, logoHeight);

        carregarFeedbacks();
    }

    private void carregarImagens(String imagemPath) {
        try {
            InputStream isFundo = getClass().getResourceAsStream(imagemPath);
            if (isFundo != null) imagemDeFundo = ImageIO.read(isFundo);

            InputStream isLogo = getClass().getResourceAsStream("/Assets/Imagens/LogoCorrigida4.png");
            if (isLogo != null) logoOriginal = ImageIO.read(isLogo);

        } catch (Exception e) {
            System.err.println("Erro ao carregar imagens: " + e.getMessage());
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

        JLabel titulo = new JLabel("Feedback Geral dos Usuários", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        navBar.add(titulo, BorderLayout.CENTER);

        JPanel painelDireito = new JPanel();
        painelDireito.setOpaque(false);
        painelDireito.setPreferredSize(new Dimension(80, 1));
        navBar.add(painelDireito, BorderLayout.EAST);

        return navBar;
    }

    private JPanel criarPainelCentral() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setOpaque(false);
        painel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));


        modeloTabela = new DefaultTableModel(new Object[]{
            "Nome Usuário", "RG", "Pontuação Atual",
            "Data Resposta", "Observações", "Nome Fase"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaFeedbacks = new JTable(modeloTabela);
        tabelaFeedbacks.setFont(new Font("Arial", Font.PLAIN, 16));
        tabelaFeedbacks.setRowHeight(25);
        tabelaFeedbacks.setFillsViewportHeight(true);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setBackground(Cores.AZUL_METRO);
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setFont(new Font("Arial", Font.BOLD, 16));
        headerRenderer.setOpaque(true);

        for (int i = 0; i < tabelaFeedbacks.getColumnCount(); i++) {
            tabelaFeedbacks.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }


        tabelaFeedbacks.getColumnModel().getColumn(4).setPreferredWidth(300); 

        tabelaFeedbacks.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setHorizontalAlignment(SwingConstants.CENTER);
                c.setForeground(Color.WHITE);
                c.setBackground(isSelected ? new Color(0, 80, 200) : Cores.AZUL_METRO);
                c.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(tabelaFeedbacks);
        scroll.setBorder(new RoundBorder(20, Color.WHITE));
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }

    private void carregarFeedbacks() {
        FeedbackGeralController controller = new FeedbackGeralController();
        List<FeedbackGeral> feedbacks = controller.listarFeedbacks();

        modeloTabela.setRowCount(0);

        for (FeedbackGeral f : feedbacks) {

            modeloTabela.addRow(new Object[]{
                f.getNomeUsuario(),
                f.getRg(),
                f.getPontuacaoAtual(),
                f.getDataResposta(),
                f.getObservacoes(),
                f.getNomeFase()
            });
        }

        while (modeloTabela.getRowCount() < 22) {
            modeloTabela.addRow(new Object[]{"", "", "", "", "", ""});
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

    private static class RoundBorder extends AbstractBorder {
        private int radius;
        private Color color;

        RoundBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            int inset = radius / 2 + 2;
            return new Insets(inset, inset, inset, inset);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            int inset = radius / 2 + 2;
            insets.left = insets.top = insets.right = insets.bottom = inset;
            return insets;
        }
    }
}
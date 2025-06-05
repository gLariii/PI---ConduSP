package TelaMetro1.telaMenu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;

import Assets.Cores;
import Controller.RespostaUsuarioController; 
import Model.RespostaUsuario;


import Controller.FeedbackGeralController; 
import Model.FeedbackGeral; 
import Assets.*;


public class FeedbackPanel extends JPanel {

    private Image imagemDeFundo;
    private Image logoOriginal, logoRedimensionada;
    private int logoWidth = 60;
    private int logoHeight = 60;
    private Runnable voltarAcao;

    private JTable tabelaFeedbacks;
    private DefaultTableModel modeloTabela;
    private int idUsuarioLogado;

    private static final int NUM_LINHAS_VISIVEIS_MINIMO = 22;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public FeedbackPanel(String imagemPath, Runnable voltarAcao, int idUsuario) {
        this.voltarAcao = voltarAcao;
        this.idUsuarioLogado = idUsuario;

        carregarImagens(imagemPath);

        setLayout(new BorderLayout());
        setOpaque(true);

        add(criarNavBar(), BorderLayout.NORTH);
        add(criarPainelCentral(), BorderLayout.CENTER);

        redimensionarLogo(logoWidth, logoHeight);

        carregarDadosFeedbackGeral(idUsuarioLogado); 
    }

    private void carregarImagens(String imagemPath) {
        try {
            InputStream isFundo = getClass().getResourceAsStream(imagemPath);
            if (isFundo != null) {
                imagemDeFundo = ImageIO.read(isFundo);
            } else {
                System.err.println("Imagem de fundo não encontrada: " + imagemPath);
            }

            InputStream isLogo = getClass().getResourceAsStream("/Assets/Imagens/LogoCorrigida4.png");
            if (isLogo != null) {
                logoOriginal = ImageIO.read(isLogo);
            } else {
                System.err.println("Logo não encontrada: /Assets/Imagens/LogoCorrigida4.png");
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

        JPanel painelDireitoVazio = new JPanel();
        painelDireitoVazio.setOpaque(false);
        painelDireitoVazio.setPreferredSize(new Dimension(btnVoltar.getPreferredSize().width + 30, 1));
        navBar.add(painelDireitoVazio, BorderLayout.EAST);

        return navBar;
    }

    private JPanel criarPainelCentral() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setOpaque(false);
        painel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        modeloTabela = new DefaultTableModel(new Object[]{
            "Nome da Fase", "Observações", "Pontuação Atual", "Data da Resposta"}, 0) 
            {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        tabelaFeedbacks = new JTable(modeloTabela);
        tabelaFeedbacks.setFont(new Font("Arial", Font.PLAIN, 16));
        tabelaFeedbacks.setRowHeight(25);
        tabelaFeedbacks.setFillsViewportHeight(true);
        tabelaFeedbacks.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(Cores.AZUL_METRO);
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Arial", Font.BOLD, 16));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.WHITE));
                label.setOpaque(true);
                return label;
            }
        };

        for (int i = 0; i < tabelaFeedbacks.getColumnModel().getColumnCount(); i++) {
            tabelaFeedbacks.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        tabelaFeedbacks.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    c.setBackground(Cores.AZUL_METRO);
                } else {
                    c.setBackground(new Color(0, 80, 200));
                }
                c.setForeground(Color.WHITE);
                ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                ((JComponent) c).setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                if (c instanceof JComponent) {
                    ((JComponent) c).setOpaque(true);
                }
                return c;
            }
        });

        tabelaFeedbacks.setGridColor(Color.WHITE);
        tabelaFeedbacks.setShowGrid(true);
        tabelaFeedbacks.setOpaque(false);

        JScrollPane scroll = new JScrollPane(tabelaFeedbacks);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);

        int radius = 20;
        Color borderColor = Color.WHITE;
        scroll.setBorder(new RoundBorder(radius, borderColor));

        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }

    void carregarDadosFeedbackGeral(int idUsuario) {
        System.out.println("Carregando registros de Feedback Geral para o ID: " + idUsuario);

        FeedbackGeralController controller = new FeedbackGeralController();
        List<FeedbackGeral> listaFeedbacks = controller.listarFeedbacksPorUsuario(idUsuario);

        modeloTabela.setRowCount(0);

        if (!listaFeedbacks.isEmpty()) {
            for (FeedbackGeral feedback : listaFeedbacks) {
                modeloTabela.addRow(new Object[]{
                    feedback.getNomeFase(),
                    feedback.getObservacoes(),
                    feedback.getPontuacaoAtual(),
                    dateFormat.format(feedback.getDataResposta())
            });

                System.out.println("Adicionado registro à tabela: Data: " + dateFormat.format(feedback.getDataResposta()) +
                                   ", Pontuação: " + feedback.getPontuacaoAtual() +
                                   ", Nome Fase: " + feedback.getNomeFase() +
                                   ", Observações: " + feedback.getObservacoes());
            }
        } else {
            System.out.println("Nenhum registro de Feedback Geral encontrado para o usuário ID: " + idUsuario);
        }

        int linhasAtuais = modeloTabela.getRowCount();
        int linhasParaAdicionar = NUM_LINHAS_VISIVEIS_MINIMO - linhasAtuais;

        if (linhasParaAdicionar > 0) {
            for (int i = 0; i < linhasParaAdicionar; i++) {
                modeloTabela.addRow(new Object[]{"", "", "", ""});
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
package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import Assets.Cores;

public class SobrePanel extends JPanel {

    private Image ImagemDeFundo;
    private Image logoOriginal, logoRedimensionada;
    private int logoWidth = 40;
    private int logoHeight = 40;
    private Runnable voltarAcao;

    // Cria o painel "Sobre"
    public SobrePanel(String imagemPath, Runnable voltarAcao) {
        this.voltarAcao = voltarAcao;
        carregarImagens(imagemPath);
        setLayout(new BorderLayout());
        setOpaque(true);

        // Adiciona a barra de navegação e o painel central com conteúdo
        add(criarNavBar(), BorderLayout.NORTH);
        add(criarPainelCentralComConteudo(), BorderLayout.CENTER);
        redimensionadaLogo(logoWidth, logoHeight);
    }

    // Carrega imagens de fundo e logo
    private void carregarImagens(String imagemPath) {
        try {
            InputStream isFundo = getClass().getResourceAsStream(imagemPath);
            if (isFundo != null) {
                ImagemDeFundo = ImageIO.read(isFundo);
            } else {
                System.err.println("Imagem de fundo não encontrada: " + imagemPath);
            }

            InputStream isLogo = getClass().getResourceAsStream("/Assets/Imagens/logoORG.png");
            if (isLogo != null) {
                logoOriginal = ImageIO.read(isLogo);
            } else {
                System.err.println("Logo não encontrada: /Assets/Imagens/logoORG.png");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar imagens para SobrePanel: " + e.getMessage());
        }
    }

    // Redimensiona a imagem da logo
    private void redimensionadaLogo(int width, int height) {
        if (logoOriginal != null) {
            this.logoWidth = width;
            this.logoHeight = height;
            logoRedimensionada = logoOriginal.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            repaint();
        }
    }

    // Cria a barra de navegação
    private JPanel criarNavBar() {
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(Cores.AZUL_METRO);
        navBar.setPreferredSize(new Dimension(getWidth(), 60));

        // Cria o botão "Voltar"
        JButton btnVoltar = botoes.criarBotaoVoltar();
        btnVoltar.addActionListener(e -> {
            if (voltarAcao != null) {
                voltarAcao.run();
            }
        });

        // Cria painel esquerdo para o botão "Voltar"
        JPanel painelEsquerdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        painelEsquerdo.setOpaque(false);
        painelEsquerdo.add(btnVoltar);
        navBar.add(painelEsquerdo, BorderLayout.WEST);

        // Cria o título da barra de navegação
        JLabel titulo = new JLabel("Sobre o Condução SP", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);
        navBar.add(titulo, BorderLayout.CENTER);

        // Cria painel direito vazio para alinhamento
        JPanel painelDireito = new JPanel();
        painelDireito.setOpaque(false);
        painelDireito.setPreferredSize(new Dimension(btnVoltar.getPreferredSize().width + 30, 60));
        navBar.add(painelDireito, BorderLayout.EAST);

        return navBar;
    }

    // Cria o painel central com o conteúdo informativo
    private JPanel criarPainelCentralComConteudo() {
        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setOpaque(false);
        painelCentral.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        // Cria painel "O que é o Condução SP?"
        RoundedPanel oQueEhPanel = new RoundedPanel(20, Cores.AZUL_METRO, Cores.AZUL_METRO.darker());
        oQueEhPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        oQueEhPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        oQueEhPanel.setMaximumSize(new Dimension(800, Integer.MAX_VALUE));

        JLabel tituloOQueEh = new JLabel("<html><b style='font-size:24px;'>O que é o Condução SP?</b></html>");
        tituloOQueEh.setForeground(Color.WHITE);
        tituloOQueEh.setAlignmentX(Component.RIGHT_ALIGNMENT);
        tituloOQueEh.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        oQueEhPanel.add(tituloOQueEh);

        // Cria e estiliza a área de texto com a descrição do Condução SP
        JTextArea textoConducaoSP = new JTextArea();
        textoConducaoSP.setText(
                "O Condução SP é um projeto inovador, desenvolvido por alunos do Instituto Mauá de Tecnologia em " +
                        "colaboração estratégica com o Metrô de São Paulo. Nosso foco principal é a Linha 1-Azul, visando "
                        +
                        "a capacitação e o aprimoramento contínuo dos operadores de metrô por meio de uma metodologia "
                        +
                        "interativa e envolvente.\n\n" +
                        "Para isso, criamos um simulador no formato de jogo click & point. Nele, os operadores podem vivenciar "
                        +
                        "cenários realistas, treinando e aprimorando suas habilidades no reconhecimento e na correção de falhas, "
                        +
                        "além de reforçar as práticas operacionais corretas.");
        estilizarTextArea(textoConducaoSP, 0);
        oQueEhPanel.add(textoConducaoSP);

        painelCentral.add(oQueEhPanel);
        painelCentral.add(Box.createVerticalStrut(40));

        // Cria painel "Quem Somos Nós"
        RoundedPanel quemSomosNosPanel = new RoundedPanel(20, Cores.AZUL_METRO, Cores.AZUL_METRO.darker());
        quemSomosNosPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        quemSomosNosPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        quemSomosNosPanel.setMaximumSize(new Dimension(800, Integer.MAX_VALUE));

        JLabel tituloQuemSomos = new JLabel(
                "<html><b style='font-size:24px;'>Quem Somos Nós:</b><br>Alunos do primeiro semestre de Ciência da Computação do Instituto Mauá de Tecnologia.</html>");
        tituloQuemSomos.setForeground(Color.WHITE);
        tituloQuemSomos.setAlignmentX(Component.LEFT_ALIGNMENT);
        tituloQuemSomos.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        quemSomosNosPanel.add(tituloQuemSomos);

        JLabel equipeDevLabel = new JLabel("<html><b style='font-size:18px;'>Equipe de desenvolvimento:</b></html>");
        equipeDevLabel.setForeground(Color.WHITE);
        equipeDevLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        equipeDevLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        quemSomosNosPanel.add(equipeDevLabel);

        // Nomes dos membros da equipe
        String[] nomes = {
                "Lucas Castilho",
                "Larissa Gomes",
                "Vinicius Xavier",
                "Sergio Henrique",
                "Maria Gatti"
        };

        // Adiciona cada nome à lista da equipe
        for (String nome : nomes) {
            JLabel nomeLabel = new JLabel(nome);
            nomeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            nomeLabel.setForeground(Color.WHITE);
            nomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            quemSomosNosPanel.add(nomeLabel);
            quemSomosNosPanel.add(Box.createVerticalStrut(5));
        }

        painelCentral.add(quemSomosNosPanel);

        return painelCentral;
    }

    // Estiliza a JTextArea
    private void estilizarTextArea(JTextArea textArea, int horizontalPadding) {
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(new Color(0, 0, 0, 0));
        textArea.setOpaque(false);
        textArea.setBorder(BorderFactory.createEmptyBorder(0, horizontalPadding, 0, horizontalPadding));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setFocusable(false);
    }

    // Desenha componentes personalizados no painel (imagem de fundo e logo)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (ImagemDeFundo != null) {
            g.drawImage(ImagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.BLACK); // Preenche com preto se a imagem de fundo não carregar
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        if (logoRedimensionada != null) {
            int x = getWidth() - logoWidth - 15;
            int y = getHeight() - logoHeight - 15;
            g.drawImage(logoRedimensionada, x, y, this);

            // Desenha o texto "Condução SP"
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));

            String texto = "Condução SP";
            int textoX = 15;
            int textoY = getHeight() - 15;

            g2d.drawString(texto, textoX, textoY);
            g2d.dispose();
        }
    }
}
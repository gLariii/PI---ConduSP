package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException; // Mantido, mesmo que não usado diretamente
import java.io.File; // Mantido, mesmo que não usado diretamente

import Assets.Cores; // Classe de cores personalizada
import CabineDeControleTela.CabineDeControleTela; // Exemplo de importação, ajuste se necessário
import Model.*; // Exemplo de importação, ajuste se necessário
import TelaMetro1.Musica.InicialMusica; // Classe para controle de música

// Importação da nova tela de vídeo (exemplo)
import TelaMetro1.telaMenu.Video;

public class Menu extends JLayeredPane {
    private Image ImagemDeFundo, logoOriginal, logoRedimensionada;
    private int logoWidth = 40;
    private int logoHeight = 40;

    private JButton btnMaquinario, btnFeedbacks, btnSupervisor;

    private JPanel mainContentPanel;
    private ConfiguracoesPanel configuracoesPanel;
    private JPanel sidebarContainerPanel;
    private SupervisorPanel supervisorPanel;
    private FeedbackPanel feedbackPanel;
    private SobrePanel sobrePanel;

    private JFrame parentFrame; // O JFrame principal que contém este menu
    private String tipo_usuarioLogado;
    private int idUsuarioLogado;

    private final int SIDEBAR_WIDTH = 300;

    // Construtor do painel de menu
    public Menu(JFrame frame, String imagemPath, String tipo_usuario, int idUsuarioLogado) {
        this.parentFrame = frame;
        this.tipo_usuarioLogado = tipo_usuario;
        this.idUsuarioLogado = idUsuarioLogado;

        // Inicia a música de fundo do menu
        InicialMusica.startBackgroundMusic("/TelaMetro1/Musica/musicamenu.wav", true);

        // Carrega as imagens de fundo e logo
        carregarImagens(imagemPath);

        // Define o layout como nulo para posicionamento manual dos componentes
        setLayout(null); // Permite setBounds() para posicionamento absoluto

        // Painel principal de conteúdo, onde a imagem de fundo é desenhada
        mainContentPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Desenha a imagem de fundo preenchendo todo o painel
                if (ImagemDeFundo != null) {
                    g.drawImage(ImagemDeFundo, 0, 0, getWidth(), getHeight(), this);
                }
                // Desenha a logo redimensionada no canto inferior direito
                if (logoRedimensionada != null) {
                    int x = getWidth() - Menu.this.logoWidth - 15;
                    int y = getHeight() - Menu.this.logoHeight - 15;
                    g.drawImage(logoRedimensionada, x, y, this);
                    // Desenha o texto "Condução SP" no canto inferior esquerdo
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
        };
        mainContentPanel.setOpaque(false); // Torna o painel transparente para exibir a imagem de fundo
        mainContentPanel.add(criarNavBar(), BorderLayout.NORTH); // Adiciona a barra de navegação no topo
        mainContentPanel.add(criarPainelCentral(), BorderLayout.CENTER); // Adiciona o painel central

        // Adiciona o painel de conteúdo principal à camada padrão do JLayeredPane
        add(mainContentPanel, JLayeredPane.DEFAULT_LAYER);

        // Redimensiona a logo inicialmente
        redimensionarLogo(40, 40);

        // Painel contêiner para a barra lateral de configurações
        sidebarContainerPanel = new JPanel(new BorderLayout());
        sidebarContainerPanel.setOpaque(true);
        sidebarContainerPanel.setBackground(Cores.AZUL_METRO);

        // Inicializa o painel de configurações
        configuracoesPanel = new ConfiguracoesPanel(() -> {toggleConfigPanel(false);},idUsuarioLogado);
        // Define a ação para o botão "Sobre" dentro das configurações
        configuracoesPanel.setOnSobreAction(() -> {
            showPanel("sobre");
        });

        // Define a ação para o botão "Desconectar" dentro das configurações
        configuracoesPanel.setOnDesconectarAction(() -> {
            // Chama o método que gerencia o efeito de escurecimento e o diálogo ConfirmarSaida
            showConfirmarSaidaDialog();
        });

        // Adiciona um listener de propriedade para garantir que o player de música seja passado para o painel de configurações
        configuracoesPanel.addPropertyChangeListener("ancestor", evt -> {
            if (evt.getNewValue() != null && InicialMusica.getBackgroundMusicPlayer() != null) {
                configuracoesPanel.setMusicaPlayer(InicialMusica.getBackgroundMusicPlayer());
            }
        });

        sidebarContainerPanel.add(configuracoesPanel, BorderLayout.CENTER);

        // Adiciona o painel da barra lateral à camada PALETTE_LAYER para que fique acima do conteúdo principal
        add(sidebarContainerPanel, JLayeredPane.PALETTE_LAYER);

        // Inicializa e adiciona os outros painéis (Supervisor, Feedback, Sobre)
        supervisorPanel = new SupervisorPanel("/Assets/Imagens/TelaInicial4Corrigida.png", () -> {
            showPanel("main");
        });
        add(supervisorPanel, JLayeredPane.MODAL_LAYER); // Adiciona à camada MODAL para ficar acima dos outros

        feedbackPanel = new FeedbackPanel("/Assets/Imagens/TelaInicial4Corrigida.png", () -> {
            showPanel("main");
        }, idUsuarioLogado);
        add(feedbackPanel, JLayeredPane.MODAL_LAYER);

        sobrePanel = new SobrePanel("/Assets/Imagens/TelaInicial4Corrigida.png", () -> {
            showPanel("config");
        });
        add(sobrePanel, JLayeredPane.MODAL_LAYER);

        // Adiciona um listener para o redimensionamento do componente Menu
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                // Atualiza os limites dos painéis para que se ajustem ao novo tamanho da janela
                mainContentPanel.setBounds(0, 0, getWidth(), getHeight());

                // Lógica de posicionamento da sidebar (fora ou dentro da tela)
                if (sidebarContainerPanel.isVisible()) {
                    sidebarContainerPanel.setBounds(0, 0, SIDEBAR_WIDTH, getHeight());
                } else {
                    sidebarContainerPanel.setBounds(-SIDEBAR_WIDTH, 0, SIDEBAR_WIDTH, getHeight());
                }

                // Garante que os painéis modais cubram toda a área
                supervisorPanel.setBounds(0, 0, getWidth(), getHeight());
                feedbackPanel.setBounds(0, 0, getWidth(), getHeight());
                sobrePanel.setBounds(0, 0, getWidth(), getHeight());
                
                // Revalida e redesenha os componentes
                revalidate();
                repaint();
            }
        });

        // Exibe o painel principal inicialmente
        showPanel("main");
    }

    // Carrega as imagens de fundo e logo a partir dos recursos
    private void carregarImagens(String imagemPath) {
        try {
            InputStream isFundo = getClass().getResourceAsStream(imagemPath);
            if (isFundo != null)
                ImagemDeFundo = ImageIO.read(isFundo);

            InputStream isLogo = getClass().getResourceAsStream("/Assets/Imagens/logoORG.png");
            if (isLogo != null) {
                logoOriginal = ImageIO.read(isLogo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Redimensiona a imagem da logo e força o redesenho do painel principal
    public void redimensionarLogo(int width, int height) {
        if (logoOriginal != null) {
            // Garante que as dimensões estejam dentro de limites razoáveis
            width = Math.max(20, Math.min(width, 100));
            height = Math.max(20, Math.min(height, 100));

            logoRedimensionada = logoOriginal.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            this.logoWidth = width;
            this.logoHeight = height;
            if (mainContentPanel != null) {
                mainContentPanel.repaint(); // Força a repintura para exibir a logo redimensionada
            }
        }
    }

    // Cria a barra de navegação no topo da tela
    private JPanel criarNavBar() {
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(Cores.AZUL_METRO);
        navBar.setPreferredSize(new Dimension(this.getWidth(), 60)); // Altura fixa para a barra de navegação

        JLabel titulo = new JLabel("Sistema de Gerenciamento");
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 350, 0, 0)); // Margem à esquerda

        navBar.add(titulo, BorderLayout.CENTER);

        // Cria o botão de configurações e adiciona sua ação
        JButton btnConfiguracoes = botoes.criarBotaoConfiguracoes(); // Assume que 'botoes' é uma classe auxiliar
        btnConfiguracoes.addActionListener(e -> {
            toggleConfigPanel(true); // Abre o painel de configurações
        });

        JPanel painelConfig = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        painelConfig.setOpaque(false);
        painelConfig.add(btnConfiguracoes);
        navBar.add(painelConfig, BorderLayout.WEST);

        return navBar;
    }

    // Cria o painel central que contém os botões de ação
    private JPanel criarPainelCentral() {
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);

        JPanel painelBotoes = new JPanel(new GridLayout(3, 1, 0, 40)); // Layout de grade para os botões
        painelBotoes.setOpaque(false);
        painelBotoes.setPreferredSize(new Dimension(500, 570));

        // Cria os botões
        btnMaquinario = botoes.criarBotaoMaquinario();
        btnFeedbacks = botoes.criarBotaoFeedBackPessoal();
        btnSupervisor = botoes.criarBotaoSupervisor();

        // ActionListener do botão "Maquinário" para transicionar para a tela de vídeo
        btnMaquinario.addActionListener(e -> {
            InicialMusica.stopMusic(); // Para a música do menu
            // Transiciona para a nova tela de vídeo
            parentFrame.setContentPane(new Video(parentFrame, tipo_usuarioLogado, idUsuarioLogado));
            parentFrame.revalidate(); // Revalida o layout do parentFrame
            parentFrame.repaint(); // Repinta o parentFrame
        });

        // ActionListener do botão "Supervisor"
        btnSupervisor.addActionListener(e -> {
            showPanel("supervisor");
        });

        // ActionListener do botão "Feedbacks"
        btnFeedbacks.addActionListener(e -> {
            // Carrega os dados de feedback antes de mostrar o painel
            if (feedbackPanel instanceof FeedbackPanel) {
                ((FeedbackPanel)feedbackPanel).carregarDadosFeedbackGeral(idUsuarioLogado);
            }
            showPanel("feedback");
        });

        // Adiciona os botões ao painel de botões
        painelBotoes.add(btnMaquinario);
        painelBotoes.add(btnFeedbacks);

        // Adiciona o botão "Supervisor" apenas se o tipo de usuário for "supervisor"
        if ("supervisor".equalsIgnoreCase(tipo_usuarioLogado)) {
            painelBotoes.add(btnSupervisor);
        } else {
            btnSupervisor.setVisible(false); // Esconde o botão se não for supervisor
        }

        // Configurações de layout para centralizar o painel de botões
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Centraliza o painel de botões
        gbc.insets = new Insets(60, 0, 0, 0); // Margem superior para o painel de botões

        centro.add(painelBotoes, gbc);
        return centro;
    }

    // Método para substituir o painel de conteúdo do JFrame pai (útil para transições de tela completa)
    private void substituirPainel(JPanel newPanel) {
        if (parentFrame != null) {
            parentFrame.setContentPane(newPanel);
            parentFrame.revalidate();
            parentFrame.repaint();
        }
    }

    // Gerencia a visibilidade dos diferentes painéis do menu (principal, configurações, supervisor, feedback, sobre)
    private void showPanel(String panelName) {
        // Oculta todos os painéis primeiro para garantir que apenas o desejado seja visível
        mainContentPanel.setVisible(false);
        sidebarContainerPanel.setVisible(false);
        supervisorPanel.setVisible(false);
        feedbackPanel.setVisible(false);
        sobrePanel.setVisible(false);

        // Exibe o painel solicitado
        switch (panelName) {
            case "main":
                mainContentPanel.setVisible(true);
                break;
            case "config":
                mainContentPanel.setVisible(true); // Mantém o painel principal visível por trás da sidebar
                sidebarContainerPanel.setVisible(true); // Exibe a barra lateral de configurações
                break;
            case "supervisor":
                supervisorPanel.setVisible(true);
                break;
            case "feedback":
                feedbackPanel.setVisible(true);
                break;
            case "sobre":
                sobrePanel.setVisible(true);
                break;
        }
        revalidate(); // Revalida o layout do Menu
        repaint(); // Repinta o Menu
    }

    // Alterna a visibilidade do painel de configurações (com animação de deslizamento)
    private void toggleConfigPanel(boolean show) {
        if (show) {
            showPanel("config"); // Exibe o painel de configurações (ativa sidebarContainerPanel)
            configuracoesPanel.revalidate(); // Garante que o conteúdo interno do painel de configs esteja correto
            configuracoesPanel.repaint();
            // Inicia a animação para deslizar o painel para dentro da tela
            animatePanel(sidebarContainerPanel, -SIDEBAR_WIDTH, 0, SIDEBAR_WIDTH, getHeight(), null);
        } else {
            // Inicia a animação para deslizar o painel para fora da tela
            animatePanel(sidebarContainerPanel, 0, -SIDEBAR_WIDTH, SIDEBAR_WIDTH, getHeight(), () -> {
                sidebarContainerPanel.setVisible(false); // Oculta o painel após a animação
                showPanel("main"); // Retorna ao painel principal
            });
        }
    }

    // Realiza uma animação de deslizamento horizontal para um painel
    private void animatePanel(JPanel panel, int startX, int endX, int width, int height, Runnable onComplete) {
        final int frames = 20; // Número de quadros da animação
        final int int_delay = 10; // Atraso entre os quadros (em milissegundos)
        Timer timer = new Timer(int_delay, new AbstractAction() {
            int currentX = startX;
            int step = (endX - startX) / frames; // Incremento de X por quadro

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentX += step;
                // Ajusta currentX para o valor final se ele ultrapassar ou for menor que o endX
                if ((step > 0 && currentX >= endX) || (step < 0 && currentX <= endX)) {
                    currentX = endX;
                    ((Timer) e.getSource()).stop(); // Para o timer quando a animação termina
                    if (onComplete != null) {
                        onComplete.run(); // Executa a ação de conclusão, se houver
                    }
                }
                panel.setBounds(currentX, 0, width, height); // Atualiza a posição do painel
            }
        });
        timer.start(); // Inicia o timer da animação
    }

    // --- NOVO MÉTODO PARA GERENCIAR O DIÁLOGO CONFIRMARSAIDA COM OVERLAY ---
    private void showConfirmarSaidaDialog() {
        // 1. Prepara o GlassPane da janela principal para escurecer a tela
        // O GlassPane é uma camada transparente que fica por cima de todos os componentes do JFrame.
        // Ele serve para interceptar eventos ou desenhar sobre tudo.
        JPanel glassPane = (JPanel) parentFrame.getGlassPane();
        glassPane.setLayout(new BorderLayout()); // Define um layout para o GlassPane para que ele ocupe toda a área
        
        // CORREÇÃO AQUI: Fundo 100% preto
        glassPane.setBackground(new Color(0, 0, 0, 255)); // Cor preta com 255 de transparência (totalmente opaca)
        // OU: glassPane.setBackground(Color.BLACK); // Uma forma mais simples de definir preto total

        glassPane.setOpaque(true); // É CRUCIAL definir como opaco para que a cor de fundo seja de fato pintada
        glassPane.setVisible(true); // Torna o GlassPane visível, escurecendo a tela principal imediatamente

        // 2. Cria e exibe o diálogo de confirmação
        // Como 'ConfirmarSaida' é um JDialog modal (definido como 'true' no construtor 'super(parent, ..., true)'),
        // a execução do código neste ponto é PAUSADA até que o diálogo 'ConfirmarSaida' seja fechado
        // (ou seja, até que o método 'dispose()' seja chamado dentro dele).
        ConfirmarSaida dialog = new ConfirmarSaida(parentFrame);
        dialog.setVisible(true); // Exibe o diálogo. A linha de código seguinte só será executada após o diálogo fechar.

        // 3. O código continua a partir daqui SOMENTE DEPOIS que o diálogo 'ConfirmarSaida' é fechado.
        // Esconde o GlassPane para remover o efeito de escurecimento
        glassPane.setVisible(false);
        glassPane.setOpaque(false); // Retorna a ser transparente (boa prática para liberar recursos e eventos)
        glassPane.removeAll(); // Limpa quaisquer componentes que possam ter sido adicionados ao GlassPane (importante!)
        glassPane.revalidate(); // Força o re-cálculo do layout do GlassPane (se houver algo, mesmo que vazio)
        glassPane.repaint(); // Redesenha o GlassPane para garantir que a transparência seja aplicada corretamente

        // 4. Verifica a resposta do diálogo e age de acordo
        if (dialog.isConfirmed()) {
            System.out.println("Usuário confirmou a saída. Fechando a aplicação...");
            InicialMusica.stopMusic(); // Para a música antes de sair
            System.exit(0); // Encerra a aplicação
        } else {
            System.out.println("Usuário cancelou a saída.");
        }
    }
}
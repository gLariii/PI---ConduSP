package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import Assets.Cores; 

public class SupervisorPanel extends JPanel {
    private Image ImagemDeFundo;
    private Image logoOriginal, logoRedimensionada;
    private int logoWidth = 40;
    private int logoHeight = 40;
    private Runnable voltarAcao; 

    private JPanel mainContentPanel; 
    private BuscarSupervisorPanel buscarSupervisorPanel; 
    private CardLayout cardLayout; 
    private JPanel cardPanel; 

    public SupervisorPanel(String imagemPath, Runnable voltarAcao) {
        this.voltarAcao = voltarAcao; 
        carregarImagem(imagemPath);
        carregarLogo("/Assets/Imagens/logoORG.png"); 
        
        setLayout(new BorderLayout()); 

        setOpaque(true);


        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false); 
       
        mainContentPanel = new JPanel(new BorderLayout()); 
        mainContentPanel.setOpaque(false); 

     
        mainContentPanel.add(criarNavBarSupervisor(), BorderLayout.NORTH);

       
        JPanel botoesPanel = new JPanel(new GridBagLayout());
        botoesPanel.setOpaque(false); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton btnRelatorios = botoes.criarBotaoPadrao(
                "Acessar relatórios operários",
                "/Assets/Imagens/relatorio.png", 
                80, 80, SwingConstants.RIGHT, new Dimension(600, 150), 30);
        btnRelatorios.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Funcionalidade de Relatórios será implementada!");
        });
        botoesPanel.add(btnRelatorios, gbc);

        gbc.gridy = 1; 
        JButton btnCadastrarSupervisor = botoes.criarBotaoPadrao(
                "Cadastrar novos supervisores", 
                "/Assets/Imagens/cadastro.png", 
                80, 80, SwingConstants.RIGHT, new Dimension(600, 150), 30);
        btnCadastrarSupervisor.addActionListener(e -> {
            cardLayout.show(cardPanel, "buscarSupervisor"); 
        });
        botoesPanel.add(btnCadastrarSupervisor, gbc);
        
        mainContentPanel.add(botoesPanel, BorderLayout.CENTER);

      
        buscarSupervisorPanel = new BuscarSupervisorPanel(imagemPath, () -> cardLayout.show(cardPanel, "main")); // Ação de voltar para o mainContentPanel

        cardPanel.add(mainContentPanel, "main"); 
        cardPanel.add(buscarSupervisorPanel, "buscarSupervisor"); 

        add(cardPanel, BorderLayout.CENTER);

        cardLayout.show(cardPanel, "main");
    }

    private void carregarImagem(String imagemPath) {
        try {
            InputStream isFundo = getClass().getResourceAsStream(imagemPath);
            if (isFundo != null) {
                ImagemDeFundo = ImageIO.read(isFundo);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar imagem de fundo para SupervisorPanel: " + imagemPath);
        }
    }

    private void carregarLogo(String logoPath) {
        try (InputStream isLogo = getClass().getResourceAsStream(logoPath)) {
            if (isLogo != null) {
                logoOriginal = ImageIO.read(isLogo);
                if (logoOriginal != null) {
                    redimensionarLogo(logoWidth, logoHeight); 
                }
            } else {
                System.err.println("Logo não encontrada: " + logoPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar logo para SupervisorPanel: " + logoPath);
        }
    }
    
    private void redimensionarLogo(int width, int height) {
        if (logoOriginal != null) {
            this.logoWidth = width;
            this.logoHeight = height;
            logoRedimensionada = logoOriginal.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        }
    }

    private JPanel criarNavBarSupervisor() {
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(Cores.AZUL_METRO); 
        navBar.setPreferredSize(new Dimension(getWidth(), 60)); 

        JButton btnVoltar = botoes.criarBotaoVoltar();
        btnVoltar.addActionListener(e -> {
            
            if (voltarAcao != null) {
                voltarAcao.run();
            }
        });

        JPanel painelEsquerdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0)); 
        painelEsquerdo.setOpaque(false);
        painelEsquerdo.add(btnVoltar);
        navBar.add(painelEsquerdo, BorderLayout.WEST);

        JLabel titulo = new JLabel("Área Supervisor", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32)); 
        titulo.setForeground(Color.WHITE); 
        navBar.add(titulo, BorderLayout.CENTER);

        JPanel painelDireito = new JPanel();
        painelDireito.setOpaque(false);
        painelDireito.setPreferredSize(new Dimension(btnVoltar.getPreferredSize().width + 30, 60)); 
        navBar.add(painelDireito, BorderLayout.EAST);

        return navBar;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
     
        if (ImagemDeFundo != null) {
            g.drawImage(ImagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        }

        if (logoRedimensionada != null) {
            int x = getWidth() - logoWidth - 15; 
            int y = getHeight() - logoHeight - 15; 
            g.drawImage(logoRedimensionada, x, y, this);
    
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
package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import Assets.Cores; 

public class BuscarSupervisorPanel extends JPanel {
    private Image ImagemDeFundo;
    private Image logoOriginal, logoRedimensionada;
    private int logoWidth = 40;
    private int logoHeight = 40;
    private Runnable voltarAcao; 

    private JTextField txtRG;

    public BuscarSupervisorPanel(String imagemPath, Runnable voltarAcao) {
        this.voltarAcao = voltarAcao;
        carregarImagem(imagemPath);
        carregarLogo("/Assets/Imagens/logoORG.png"); 
        setLayout(new BorderLayout()); 

        setOpaque(true);

        add(criarNavBarBuscarSupervisor(), BorderLayout.NORTH);

        JPanel painelCentralBuscar = new JPanel(new GridBagLayout());
        painelCentralBuscar.setOpaque(false); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL; 

       
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(0, 0, 0, 150));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); 
        
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(10, 5, 10, 5);
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;

        JLabel lblTituloForm = new JLabel("Buscar Supervisor por RG", SwingConstants.CENTER);
        lblTituloForm.setFont(new Font("Arial", Font.BOLD, 28));
        lblTituloForm.setForeground(Color.WHITE);
        formGbc.gridx = 0;
        formGbc.gridy = 0;
        formGbc.gridwidth = 2; 
        formGbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(lblTituloForm, formGbc);

        formGbc.gridwidth = 1; 
        formGbc.gridx = 0;
        formGbc.gridy = 1;
        formPanel.add(criarLabel("RG:"), formGbc);
        formGbc.gridx = 1;
        txtRG = criarCampoTextoComIcone("/Assets/Imagens/lupa.png", 20, 20); 
        formPanel.add(txtRG, formGbc);

        JPanel botoesAcaoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        botoesAcaoPanel.setOpaque(false); 

        JButton btnBuscar = botoes.criarBotaoPadrao("Buscar", null, 0, 0, SwingConstants.CENTER, new Dimension(180, 50), 20);
        btnBuscar.addActionListener(e -> realizarBuscaRG());
        botoesAcaoPanel.add(btnBuscar);
        
        formGbc.gridx = 0;
        formGbc.gridy = 2;
        formGbc.gridwidth = 2; 
        formGbc.insets = new Insets(30, 0, 0, 0); 
        formPanel.add(botoesAcaoPanel, formGbc);

        
        painelCentralBuscar.add(formPanel); 
        add(painelCentralBuscar, BorderLayout.CENTER);
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setForeground(Color.WHITE);
        return label;
    }


    private JTextField criarCampoTextoComIcone(String iconPath, int iconWidth, int iconHeight) {
        JTextField campo = new JTextField(20);
        campo.setFont(new Font("Arial", Font.PLAIN, 18));
        campo.setBackground(new Color(255, 255, 255, 200));
        campo.setForeground(Color.BLACK);
        campo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JPanel panelComIcone = new JPanel(new BorderLayout());
        panelComIcone.setOpaque(false); 
        panelComIcone.add(campo, BorderLayout.CENTER);

        try (InputStream is = getClass().getResourceAsStream(iconPath)) {
            if (is != null) {
                Image img = ImageIO.read(is).getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
                JLabel iconLabel = new JLabel(new ImageIcon(img));
                iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5)); 
                panelComIcone.add(iconLabel, BorderLayout.EAST); 
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar ícone para campo de texto: " + iconPath);
        }
        return campo; 
    }

    private void realizarBuscaRG() {
        String rg = txtRG.getText().trim();

        if (rg.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, digite o RG para buscar.", "Campo Vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }


        if (!rg.matches("\\d{2}\\.\\d{3}\\.\\d{3}-\\d{1}")) { 
            JOptionPane.showMessageDialog(this, "Formato de RG inválido. Use XX.XXX.XXX-X", "Formato Inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }


        JOptionPane.showMessageDialog(this, 
            "Buscando supervisor com RG: " + rg + "\n(Lógica de integração com banco de dados será implementada aqui)", 
            "Busca de Supervisor", JOptionPane.INFORMATION_MESSAGE);
        

        if (rg.equals("12.345.678-9")) {
            JOptionPane.showMessageDialog(this, "Supervisor encontrado: João da Silva\n" +
                                                 "Você pode agora cadastrá-lo como supervisor.", 
                                                 "Supervisor Encontrado", JOptionPane.INFORMATION_MESSAGE);
           
        } else {
            JOptionPane.showMessageDialog(this, "Supervisor com RG " + rg + " não encontrado.", 
                                                 "Supervisor Não Encontrado", JOptionPane.INFORMATION_MESSAGE);
           
        }
    }

    private void carregarImagem(String imagemPath) {
        try {
            InputStream isFundo = getClass().getResourceAsStream(imagemPath);
            if (isFundo != null) {
                ImagemDeFundo = ImageIO.read(isFundo);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar imagem de fundo para BuscarSupervisorPanel: " + imagemPath);
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
            System.err.println("Erro ao carregar logo para BuscarSupervisorPanel: " + logoPath);
        }
    }
    
    private void redimensionarLogo(int width, int height) {
        if (logoOriginal != null) {
            this.logoWidth = width;
            this.logoHeight = height;
            logoRedimensionada = logoOriginal.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        }
    }

    private JPanel criarNavBarBuscarSupervisor() {
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

        
        JLabel titulo = new JLabel("Buscar Supervisor", SwingConstants.CENTER);
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
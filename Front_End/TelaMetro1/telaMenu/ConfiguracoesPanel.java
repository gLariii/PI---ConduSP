package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import Assets.Cores; 
import java.io.IOException;
import java.net.URISyntaxException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.plaf.basic.BasicSliderUI; 

public class ConfiguracoesPanel extends JPanel {
    private JSlider volumeSlider;    
    private Runnable onVoltarAction;

    public ConfiguracoesPanel(Runnable onVoltarAction) {
        this.onVoltarAction = onVoltarAction;
        setLayout(new BorderLayout());
        setBackground(Cores.AZUL_METRO); 
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Cores.AZUL_METRO);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton backButton = new JButton(" Voltar");
        try (InputStream is = getClass().getResourceAsStream("/Assets/Imagens/seta_voltar.png")) {
            if (is != null) {
                ImageIcon backIcon = new ImageIcon(ImageIO.read(is).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
                backButton.setIcon(backIcon);
            } else {
                System.err.println("Ícone de voltar não encontrado em /Assets/Imagens/seta_voltar.png");
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar ícone de voltar: " + e.getMessage());
        }
        
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(Cores.AZUL_METRO);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        backButton.addActionListener(e -> {
            if (this.onVoltarAction != null) {
                this.onVoltarAction.run();
            }
        });
        headerPanel.add(backButton, BorderLayout.WEST);

        JLabel titulo = new JLabel("Configurações", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        headerPanel.add(titulo, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        painelCentral.setBackground(Cores.AZUL_METRO);

        adicionarSecao(painelCentral, "Gráficos");
        String[] temas = {"Padrão", "Escuro", "Claro"};
        CustomComboBoxPanel temaPanel = new CustomComboBoxPanel("Tema:", temas);
        painelCentral.add(temaPanel);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 15))); 

        adicionarSecao(painelCentral, "Tamanho da letra");
        String[] tamanhos = {"Pequeno", "Médio", "Grande", "Padrão"};
        CustomComboBoxPanel tamanhoFontePanel = new CustomComboBoxPanel("Tamanho:", tamanhos);
        painelCentral.add(tamanhoFontePanel);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 15))); 

        adicionarSecao(painelCentral, "Volume");
        volumeSlider = new JSlider(0, 100, 100);
        volumeSlider.setMajorTickSpacing(20);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setBackground(Cores.AZUL_METRO); 
        volumeSlider.setForeground(Color.WHITE); 
        volumeSlider.setUI(new CustomSliderUI(volumeSlider)); 
        JPanel sliderPanel = new JPanel(new BorderLayout());
        sliderPanel.setBackground(Cores.AZUL_METRO);
        JLabel volumeLabel = new JLabel("Volume:");
        volumeLabel.setForeground(Color.WHITE);
        volumeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        sliderPanel.add(volumeLabel, BorderLayout.WEST);
        sliderPanel.add(volumeSlider, BorderLayout.CENTER);
        painelCentral.add(sliderPanel);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 15))); 
        adicionarSecao(painelCentral, "Informações do Usuário");
        JLabel nomeUsuario = new JLabel("Nome: " + System.getProperty("user.name"));
        JLabel raUsuario = new JLabel("RA: 1234567");
        nomeUsuario.setForeground(Color.WHITE);
        raUsuario.setForeground(Color.WHITE);
        nomeUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
        raUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
        nomeUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        raUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelCentral.add(nomeUsuario);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 5)));
        painelCentral.add(raUsuario);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 15))); 
        JLabel versao = new JLabel("Versão: 1.0", SwingConstants.CENTER);
        versao.setForeground(Color.WHITE);
        versao.setFont(new Font("Arial", Font.PLAIN, 14));
        versao.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        versao.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelCentral.add(versao);

        add(painelCentral, BorderLayout.CENTER);
    }

    private void adicionarSecao(JPanel painel, String titulo) {
        JLabel label = new JLabel(titulo);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        painel.add(label);
    }


    private void adicionarConfiguracao(JPanel painel, String label, JComponent componente) {
        JPanel painelConfig = new JPanel(new BorderLayout());
        painelConfig.setBackground(Cores.AZUL_METRO);
        painelConfig.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60)); 
        
        JLabel lbl = new JLabel(label);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.PLAIN, 16));
        
        painelConfig.add(lbl, BorderLayout.WEST);
        painelConfig.add(componente, BorderLayout.CENTER); 
        
        painel.add(painelConfig);
        painel.add(Box.createRigidArea(new Dimension(0, 10)));
    }



    private static class CustomComboBoxPanel extends JPanel { 
        private JLabel currentOptionLabel;
        private String[] options;
        private int currentIndex = 0;

        public CustomComboBoxPanel(String labelText, String[] ops) {
            this.options = ops;
            setLayout(new BorderLayout(10, 0)); 
            setBackground(Cores.AZUL_METRO);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); 

            JLabel label = new JLabel(labelText);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            add(label, BorderLayout.WEST);

            JPanel optionsPanel = new JPanel(new BorderLayout(5, 0));
            optionsPanel.setOpaque(false);

            JButton leftButton = createArrowButton("<");
            leftButton.addActionListener(e -> {
                currentIndex = (currentIndex - 1 + options.length) % options.length;
                updateOptionLabel();
            });
            optionsPanel.add(leftButton, BorderLayout.WEST);

            currentOptionLabel = new JLabel(options[currentIndex], SwingConstants.CENTER);
            currentOptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            currentOptionLabel.setForeground(Color.WHITE);
            currentOptionLabel.setBackground(Cores.AZUL_METRO);
            currentOptionLabel.setOpaque(true);
            currentOptionLabel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1)); 
            optionsPanel.add(currentOptionLabel, BorderLayout.CENTER);

            JButton rightButton = createArrowButton(">");
            rightButton.addActionListener(e -> {
                currentIndex = (currentIndex + 1) % options.length;
                updateOptionLabel();
            });
            optionsPanel.add(rightButton, BorderLayout.EAST);

            add(optionsPanel, BorderLayout.CENTER);
        }

        private JButton createArrowButton(String text) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setForeground(Color.WHITE);
            button.setBackground(Cores.AZUL_METRO);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setPreferredSize(new Dimension(30, 30)); 
            return button;
        }

        private void updateOptionLabel() {
            currentOptionLabel.setText(options[currentIndex]);
        }
    }

    private static class CustomSliderUI extends BasicSliderUI { 

        public CustomSliderUI(JSlider b) {
            super(b);
        }

        @Override
        public void paintTrack(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            Rectangle trackBounds = trackRect;

            g2d.setColor(new Color(255, 255, 255, 100)); 
            g2d.fillRect(trackBounds.x, trackBounds.y + (trackBounds.height / 2) - 2, trackBounds.width, 4);

            int thumbX = thumbRect.x + (thumbRect.width / 2);
            g2d.setColor(Color.WHITE); 
            g2d.fillRect(trackBounds.x, trackBounds.y + (trackBounds.height / 2) - 2, thumbX - trackBounds.x, 4);
        }

        @Override
        public void paintThumb(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(Color.WHITE); 
            g2d.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);

            g2d.setColor(Cores.AZUL_METRO); 
            g2d.setStroke(new BasicStroke(1)); 
            g2d.drawOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
        }

        @Override
        public void paintTicks(Graphics g) {
            Graphics2D g2d = (Graphics2D) g; 
            g2d.setColor(Color.WHITE); 
            super.paintTicks(g); }
        
        @Override
        public void paintLabels(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.WHITE); 
            g2d.setFont(new Font("Arial", Font.PLAIN, 12)); 
            super.paintLabels(g); 
        }
    }
}
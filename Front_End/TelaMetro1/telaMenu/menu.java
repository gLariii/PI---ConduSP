package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import Assets.Cores;

public class menu extends JPanel {
    private Image ImagemDeFundo;
    private JButton btnMaquinario, btnFeedbacks, btnSupervisor, btnConfiguracoes;

    public menu(String imagemPath) {

        try (InputStream is = getClass().getResourceAsStream(imagemPath)) {
            if (is != null) {
                ImagemDeFundo = ImageIO.read(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(Cores.AZUL_METRO_TRANSPARENTE);
        navBar.setPreferredSize(new Dimension(getWidth(), 50));
        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        painelTitulo.setOpaque(false); 

        JLabel titulo = new JLabel("Sistema de Gerenciamento");
                                                                
        titulo.setFont(new Font("Arial", Font.BOLD, 32)); 
        titulo.setForeground(Color.WHITE);
        final int MARGEM_ESQUERDA_TITULO = 135; 
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(0, MARGEM_ESQUERDA_TITULO, 0, 0));

        painelTitulo.add(titulo); 
        navBar.add(painelTitulo, BorderLayout.CENTER); 

        JPanel painelBotaoConfig = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotaoConfig.setOpaque(false);
        painelBotaoConfig.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15)); 

        btnConfiguracoes = botoes.criarBotaoConfiguracoes();
        btnConfiguracoes.setText("Config...");
        btnConfiguracoes.setPreferredSize(new Dimension(150, 45));

        painelBotaoConfig.add(btnConfiguracoes);

        navBar.add(painelBotaoConfig, BorderLayout.EAST); 

        add(navBar, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);

        JPanel painelBotoes = new JPanel(new GridLayout(3, 1, 0, 40));
        painelBotoes.setOpaque(false);

        painelBotoes.setPreferredSize(new Dimension(500, 570));
        painelBotoes.setMinimumSize(new Dimension(500, 570));
        painelBotoes.setMaximumSize(new Dimension(500, 570));

        btnMaquinario = botoes.criarBotaoMaquinario();
        btnFeedbacks = botoes.criarBotaoFeedBackPessoal();
        btnSupervisor = botoes.criarBotaoSupervisor();

        painelBotoes.add(btnMaquinario);
        painelBotoes.add(btnFeedbacks);
        painelBotoes.add(btnSupervisor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.insets = new Insets(60, 0, 0, 0);

        centro.add(painelBotoes, gbc);

        add(centro, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (ImagemDeFundo != null) {
            g.drawImage(ImagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
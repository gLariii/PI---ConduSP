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
        
        JLabel titulo = new JLabel("Sistema de Gerenciamento", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24)); 
        titulo.setForeground(Color.WHITE);
        navBar.add(titulo, BorderLayout.CENTER);

        add(navBar, BorderLayout.NORTH); 

        JPanel centro = new JPanel(new GridBagLayout()); 
        centro.setOpaque(false);
        

        // Ajustar Tamanho Botões (500x470)
        JPanel painelBotoes = new JPanel(new GridLayout(3, 1, 0, 40)); 
        painelBotoes.setOpaque(false);
        painelBotoes.setPreferredSize(new Dimension(500, 470)); 
        painelBotoes.setMinimumSize(new Dimension(500, 470)); 
        painelBotoes.setMaximumSize(new Dimension(500, 470)); 

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
        
        
        //Ajustar Posição Botões 
        gbc.insets = new Insets(60, 0, 0, 0); 

        centro.add(painelBotoes, gbc); 

        add(centro, BorderLayout.CENTER); 

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rodape.setOpaque(false);
        rodape.setBorder(BorderFactory.createEmptyBorder(0, 0, 60, 40)); 
        
        btnConfiguracoes = botoes.criarBotaoConfiguracoes();
        rodape.add(btnConfiguracoes);
        
        add(rodape, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (ImagemDeFundo != null) {
            g.drawImage(ImagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
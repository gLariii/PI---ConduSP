package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import Assets.Cores; 

public class SupervisorPanel extends JPanel {
    private Image ImagemDeFundo;
    private Runnable onVoltarAction; 

    public SupervisorPanel(String imagemPath, Runnable onVoltarAction) {
        this.onVoltarAction = onVoltarAction;
        carregarImagemDeFundo(imagemPath);
        setLayout(new BorderLayout()); 
        setOpaque(false); 
        JPanel painelBotoes = new JPanel(new GridBagLayout());
        painelBotoes.setOpaque(false); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); 
        gbc.anchor = GridBagConstraints.CENTER; 

        JLabel titulo = new JLabel("Área Supervisor");
        titulo.setFont(new Font("Arial", Font.BOLD, 48)); 
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; 
        gbc.weighty = 0.5; 
        painelBotoes.add(titulo, gbc);

  
        JButton btnRelatorios = botoes.criarBotaoPadrao(
            "Acessar relatórios dos operários", 
            null, 
            0, 0, SwingConstants.CENTER, new Dimension(350, 100), 22 
        );
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; 
        gbc.weighty = 0; 
        painelBotoes.add(btnRelatorios, gbc);

        JButton btnCadastrar = botoes.criarBotaoPadrao(
            "Cadastrar novos operários", 
            null, 
            0, 0, SwingConstants.CENTER, new Dimension(350, 100), 22 
        );
        gbc.gridx = 1;
        gbc.gridy = 1;
        painelBotoes.add(btnCadastrar, gbc);

       
        add(painelBotoes, BorderLayout.CENTER);

        JButton backButton = botoes.criarBotaoDesconectar(); 

        try (InputStream is = getClass().getResourceAsStream("/Assets/Imagens/seta.png")) {
            if (is != null) {
                ImageIcon backIcon = new ImageIcon(ImageIO.read(is).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
                backButton.setHorizontalTextPosition(SwingConstants.RIGHT); 
                backButton.setIcon(backIcon);
            } else {
                System.err.println("Ícone de voltar não encontrado para o botão 'Voltar' em SupervisorPanel.");
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar ícone de voltar em SupervisorPanel: " + e.getMessage());
        }


        backButton.addActionListener(e -> {
            if (onVoltarAction != null) {
                onVoltarAction.run();
            }
        });

       
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 30)); 
        southPanel.setOpaque(false); 
        southPanel.add(backButton);
        add(southPanel, BorderLayout.SOUTH);
    }

    private void carregarImagemDeFundo(String imagemPath) {
        try (InputStream is = getClass().getResourceAsStream(imagemPath)) {
            if (is != null) {
                ImagemDeFundo = ImageIO.read(is);
            } else {
                System.err.println("Imagem de fundo não encontrada para SupervisorPanel: " + imagemPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (ImagemDeFundo != null) {
            g.drawImage(ImagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
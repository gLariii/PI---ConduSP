package TelaMetro1.telaMenu;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import Assets.Cores; 

public class ConfirmarSaidaDialog extends JDialog {

    private boolean confirmed = false; 

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
            g2.fillRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius / 2, radius / 2, radius / 2, radius / 2);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = this.radius / 2;
            return insets;
        }
    }


    public ConfirmarSaidaDialog(JFrame parent) {
        super(parent, "Confirmação de Saída", true);

        setUndecorated(true); 
        setBackground(new Color(0, 0, 0, 0)); 

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); 
        contentPanel.setBackground(Cores.AZUL_METRO); 
        contentPanel.setBorder(new RoundBorder(20, Cores.AZUL_METRO)); 
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT); 

        JPanel messageArea = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15)); 
        messageArea.setOpaque(false); 

        ImageIcon avisoIcon = carregarIcone("/Assets/Imagens/aviso.png", 60, 60); 

        JLabel avisoLabel = new JLabel();
        if (avisoIcon != null) {
            avisoLabel.setIcon(avisoIcon);
        }
        avisoLabel.setVerticalAlignment(SwingConstants.CENTER); 
        avisoLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        
        JPanel iconAndTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0)); 
        iconAndTextPanel.setOpaque(false);
        
        iconAndTextPanel.add(avisoLabel);

        JLabel messageLabel = new JLabel("<html><center>Você tem certeza <br>que deseja sair?</center></html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 22)); 
        messageLabel.setForeground(Color.WHITE); 
        messageLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        messageLabel.setBorder(new EmptyBorder(5, 0, 0, 0)); 

        iconAndTextPanel.add(messageLabel);
        
        messageArea.add(iconAndTextPanel);

        contentPanel.add(Box.createVerticalStrut(15)); 
        contentPanel.add(messageArea);
        contentPanel.add(Box.createVerticalStrut(15)); 

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15)); 
        buttonPanel.setOpaque(false); 

        JButton simButton = createStyledButton("", new Color(40, 160, 40), "/Assets/Imagens/sim.png", 40, 40); 
        simButton.addActionListener(e -> {
            confirmed = true;
            dispose(); 
        });

        JButton naoButton = createStyledButton("", new Color(160, 40, 40), "/Assets/Imagens/nao.png", 40, 40); 
        naoButton.addActionListener(e -> {
            confirmed = false;
            dispose(); 
        });

        buttonPanel.add(simButton);
        buttonPanel.add(naoButton);

        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalStrut(15)); 

        add(contentPanel);

        pack(); 
        setLocationRelativeTo(parent); 
    }

    private ImageIcon carregarIcone(String path, int width, int height) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is != null) {
                Image img = ImageIO.read(is).getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            }
        } catch (IOException e) {
        }
        return null;
    }

    private JButton createStyledButton(String text, Color bgColor, String iconPath, int iconWidth, int iconHeight) {
        JButton button = new JButton(); 
        
        ImageIcon buttonIcon = carregarIcone(iconPath, iconWidth, iconHeight);
        
        if (buttonIcon != null) {
            JLabel iconLabel = new JLabel(buttonIcon);
            iconLabel.setPreferredSize(new Dimension(iconWidth, iconHeight));
            iconLabel.setMaximumSize(new Dimension(iconWidth, iconHeight));
            iconLabel.setMinimumSize(new Dimension(iconWidth, iconHeight));
            
            button.setLayout(new BorderLayout()); 
            button.add(iconLabel, BorderLayout.CENTER);
        } else {
            button.setText(text); 
        }

        button.setFont(new Font("Arial", Font.BOLD, 18)); 
        button.setForeground(Color.WHITE); 
        button.setBackground(bgColor); 
        button.setFocusPainted(false); 
        button.setBorder(new RoundBorder(15, bgColor)); 
        
        button.setPreferredSize(new Dimension(140, 80)); 

        Color hoverColor = new Color(Math.max(0, bgColor.getRed() - 20), 
                                     Math.max(0, bgColor.getGreen() - 20), 
                                     Math.max(0, bgColor.getBlue() - 20));
        Color pressedColor = new Color(Math.max(0, bgColor.getRed() - 40), 
                                       Math.max(0, bgColor.getGreen() - 40), 
                                       Math.max(0, bgColor.getBlue() - 40));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(pressedColor);
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (button.contains(evt.getPoint())) { 
                    button.setBackground(hoverColor);
                } else {
                    button.setBackground(bgColor);
                }
            }
        });

        return button;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
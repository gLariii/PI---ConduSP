package TelaMetro1.Entrada;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaInicial extends JPanel {
    public TelaInicial() {
        setOpaque(false);
        setLayout(null);
        initComponents();
    }

    private JTextField rgTextField;
    private JTextField senhaTextField;
    private JButton entrarButton;
    private JLabel perfilLabel;

    private void initComponents() {
        ImageIcon perfilIconOriginal = new ImageIcon(getClass().getResource("/Assets/Imagens/perfil.png"));
        int larguraPerfil = 80;
        int alturaPerfil = 80;
        Image imagemPerfilRedimensionada = perfilIconOriginal.getImage().getScaledInstance(larguraPerfil, alturaPerfil,
                Image.SCALE_SMOOTH);
        ImageIcon perfilIconRedimensionado = new ImageIcon(imagemPerfilRedimensionada);
        perfilLabel = new JLabel(perfilIconRedimensionado);
        perfilLabel.setBounds(100, 10, larguraPerfil, alturaPerfil);
        add(perfilLabel);

        // Campo de Texto RG - Castilho
        rgTextField = new RoundedTextField("RG:",15);
        rgTextField.setBounds(30, 100, 210, 28);
        rgTextField.setBorder(new PlaceholderBorder(getAzulMetro(), "RG:", 15));
        rgTextField.setForeground(Color.BLACK);
        rgTextField.setCaretColor(getAzulMetro());
        rgTextField.setBackground(Color.WHITE);
        rgTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (rgTextField.getText().equals("RG:")) {
                    rgTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (rgTextField.getText().isEmpty()) {
                    rgTextField.setText("RG:");
                }
            }
        });
        add(rgTextField);

        // Campo de Texto Senha - Castilho
        senhaTextField = new RoundedTextField("Senha:",15);
        senhaTextField.setBounds(30, 140, 210, 28);
        senhaTextField.setBorder(new PlaceholderBorder(getAzulMetro(), "Senha:", 15));
        senhaTextField.setCaretColor(getAzulMetro());
        senhaTextField.setBackground(Color.WHITE);
        senhaTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (senhaTextField.getText().equals("Senha:")) {
                    senhaTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (senhaTextField.getText().isEmpty()) {
                    senhaTextField.setText("Senha:");
                }
            }
        });
        add(senhaTextField);

        // Botão Entrar - Castilho
        entrarButton = new JButton("Entrar");
        entrarButton.setBounds(90, 180, 100, 40);
        entrarButton.setOpaque(false);
        entrarButton.setContentAreaFilled(false);
        entrarButton.setBorder(new RoundedBorder(Color.WHITE, 3, 15));
        entrarButton.setForeground(Color.WHITE);
        entrarButton.addMouseListener(new MouseAdapter() {
            Color originalBorderColor = Color.WHITE;
            int originalBorderThickness = 3;
            Color originalTextColor = Color.WHITE;
            Color hoverBorderColor = new Color(173, 216, 230);
            int hoverBorderThickness = 5;
            Color hoverTextColor = new Color(173, 216, 230);

            @Override
            public void mouseEntered(MouseEvent e) {
                entrarButton.setBorder(new RoundedBorder(hoverBorderColor, hoverBorderThickness, 15));
                entrarButton.setForeground(hoverTextColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                entrarButton.setBorder(new RoundedBorder(originalBorderColor, originalBorderThickness, 15));
                entrarButton.setForeground(originalTextColor);
            }
        });
        add(entrarButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    private Color getAzulMetro() {
        return Assets.Cores.AZUL_METRO;
    }

    class PlaceholderBorder implements Border {
        private Color focusColor;
        private String placeholder;
        private int radius;

        public PlaceholderBorder(Color focusColor, String placeholder, int radius) {
            this.focusColor = focusColor;
            this.placeholder = placeholder;
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(2));

            g2d.setColor(focusColor);
            g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));

            JTextField textField = (JTextField) c;
            if (textField.getText().equals(placeholder)) {
                g2d.setColor(Color.GRAY);
                FontMetrics fm = g.getFontMetrics();
                int placeholderX = x + 5;
                int placeholderY = y + fm.getAscent() + (height - fm.getHeight()) / 2;
                g2d.drawString(placeholder, placeholderX, placeholderY);
            }

            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(5, 5, 5, 5);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }

    class RoundedBorder implements Border {
        private int radius;
        private Color color;
        private int thickness;

        public RoundedBorder(Color color, int thickness, int radius) {
            this.color = color;
            this.thickness = thickness;
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(thickness));
            g2d.draw(new RoundRectangle2D.Double(x, y, width - thickness, height - thickness, radius, radius));
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + this.thickness, this.radius + this.thickness, this.radius + this.thickness,
                    this.radius + this.thickness);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }
}
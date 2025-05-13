package TelaMetro1.Entrada;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.Border;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.text.JTextComponent;

public class TelaInicial extends JPanel {
    public TelaInicial() {
        setOpaque(false);
        setLayout(null);
        initComponents();
    }

    private JTextField rgTextField;
    private JPasswordField passwordField;
    private JTextField senhaTextField;
    private JButton entrarButton;
    private JLabel perfilLabel;
    private JLabel rgIconLabel;
    private JLabel cadeadoLabel;
    private JButton togglePasswordButton; 
    private ImageIcon rgIcon;
    private ImageIcon cadeadoIcon;
    private ImageIcon eyeVisibleIcon;
    private Font helveticaFont;
    private boolean isPasswordPressed = false;

    private void initComponents() {
        helveticaFont = new Font("Helvetica", Font.PLAIN, 16);

        ImageIcon perfilIconOriginal = new ImageIcon(getClass().getResource("/Assets/Imagens/perfil.png"));
        int larguraPerfil = 80;
        int alturaPerfil = 80;
        Image imagemPerfilRedimensionada = perfilIconOriginal.getImage().getScaledInstance(larguraPerfil, alturaPerfil,
                Image.SCALE_SMOOTH);
        ImageIcon perfilIconRedimensionado = new ImageIcon(imagemPerfilRedimensionada);
        perfilLabel = new JLabel(perfilIconRedimensionado);
        perfilLabel.setBounds(100, 10, larguraPerfil, alturaPerfil);
        add(perfilLabel);

        ImageIcon rgIconOriginal = new ImageIcon(getClass().getResource("/Assets/Imagens/perfil2.png"));
        int larguraRgIcon = 20;
        int alturaRgIcon = 20;
        Image imagemRgIconRedimensionada = rgIconOriginal.getImage().getScaledInstance(larguraRgIcon, alturaRgIcon,
                Image.SCALE_SMOOTH);
        rgIcon = new ImageIcon(imagemRgIconRedimensionada);

        rgIconLabel = new JLabel(rgIcon);
        rgIconLabel.setBounds(5, 104, larguraRgIcon, alturaRgIcon);
        add(rgIconLabel);


        ImageIcon cadeadoIconOriginal = new ImageIcon(getClass().getResource("/Assets/Imagens/cadeado.png"));
        int larguraCadeadoIcon = 20;
        int alturaCadeadoIcon = 20;
        Image imagemCadeadoIconRedimensionada = cadeadoIconOriginal.getImage().getScaledInstance(larguraCadeadoIcon, alturaCadeadoIcon, Image.SCALE_SMOOTH);
        cadeadoIcon = new ImageIcon(imagemCadeadoIconRedimensionada);

        cadeadoLabel = new JLabel(cadeadoIcon);
        cadeadoLabel.setBounds(5, 144, larguraCadeadoIcon, alturaCadeadoIcon);
        add(cadeadoLabel);

        // Campo de Texto RG - Castilho
        rgTextField = new RoundedTextField("RG:", 15);
        rgTextField.setBounds(30, 100, 230, 28);
        rgTextField.setBorder(new PlaceholderBorder(getAzulMetro(), "RG:", 15));
        rgTextField.setForeground(Color.BLACK);
        rgTextField.setCaretColor(getAzulMetro());
        rgTextField.setBackground(Color.WHITE);
        rgTextField.setFont(helveticaFont);
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

        // Campo de Senha - Castilho
        passwordField = new RoundedPasswordField("Senha:", 15);
        passwordField.setBounds(30, 140, 200, 28);
        passwordField.setBorder(new PlaceholderBorder(getAzulMetro(), "Senha:", 15));
        passwordField.setCaretColor(getAzulMetro());
        passwordField.setBackground(Color.WHITE);
        passwordField.setFont(helveticaFont);
        add(passwordField);

        senhaTextField = new RoundedTextField("Senha:", 15);
        senhaTextField.setBounds(30, 140, 200, 28);
        senhaTextField.setBorder(new PlaceholderBorder(getAzulMetro(), "Senha:", 15));
        senhaTextField.setForeground(Color.BLACK);
        senhaTextField.setCaretColor(getAzulMetro());
        senhaTextField.setBackground(Color.WHITE);
        senhaTextField.setFont(helveticaFont);
        senhaTextField.setVisible(false);
        senhaTextField.setEditable(false);
        add(senhaTextField);

        // Larissa o Olho ta aqui - OLha aqui Larissaaaaaaaaaaaaaaaaaaaaaaaaaaa olhaa o olho
        eyeVisibleIcon = new ImageIcon(getClass().getResource("/Assets/Imagens/olho_visivel.png"));

        togglePasswordButton = new JButton(eyeVisibleIcon);  // Inicializa o botão AQUI!
        togglePasswordButton.setBounds(235, 140, 28, 28);
        togglePasswordButton.setBorderPainted(false);
        togglePasswordButton.setContentAreaFilled(false);
        togglePasswordButton.setFocusPainted(false);
        togglePasswordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                senhaTextField.setText(String.valueOf(passwordField.getPassword()));
                passwordField.setVisible(false);
                senhaTextField.setVisible(true);
                isPasswordPressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                passwordField.setVisible(true);
                senhaTextField.setVisible(false);
                isPasswordPressed = false;
            }
        });
        add(togglePasswordButton);

        // Botão Entrar - Castilho
        entrarButton = new JButton("Entrar");
        entrarButton.setBounds(65, 180, 150, 40);
        entrarButton.setOpaque(false);
        entrarButton.setContentAreaFilled(false);
        entrarButton.setBorder(new RoundedBorder(Color.WHITE, 3, 15));
        entrarButton.setForeground(Color.WHITE);
        entrarButton.setFont(helveticaFont);
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
                entrarButton.setBorder(new RoundedBorder(originalBorderColor, originalBorderThickness, 3));
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


            JTextComponent textField = (JTextComponent) c;
            String textToDraw = "";
            if (textField == passwordField && !isPasswordPressed) {
                textToDraw = placeholder;
            } else {
                textToDraw = (textField.getText().isEmpty()) ? placeholder : textField.getText();
            }
            g2d.setColor(textField.getText().isEmpty() && (textField != senhaTextField || isPasswordPressed) ? Color.GRAY : textField.getForeground());
            g2d.setFont(helveticaFont);
            FontMetrics fm = g.getFontMetrics();
            int textX = x + 5;
            int textY = y + fm.getAscent() + (height - fm.getHeight()) / 2;
            g2d.drawString(textToDraw, textX, textY);

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
    class RoundedPasswordField extends JPasswordField {
        private int radius;

        public RoundedPasswordField(String text, int radius) {
            super(text);
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(getForeground());
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
        }
    }
}
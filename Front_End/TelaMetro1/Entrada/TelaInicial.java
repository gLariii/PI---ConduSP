package TelaMetro1.Entrada;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.Border;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.text.JTextComponent;

import DAO.UsuarioDAO;
import Model.Usuario;
import TelaMetro1.telaMenu.Menu;

public class TelaInicial extends JPanel {

    public TelaInicial() {
        setOpaque(false);
        setLayout(null);
        initComponents();
        passwordField.setText("Senha:");
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0); // mostra o texto normal
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
    private ImageIcon eyeHiddenIcon;
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
        perfilLabel.setBounds(160, 70, larguraPerfil, alturaPerfil);
        add(perfilLabel);

        ImageIcon rgIconOriginal = new ImageIcon(getClass().getResource("/Assets/Imagens/perfil2.png"));
        int larguraRgIcon = 24;
        int alturaRgIcon = 30;
        Image imagemRgIconRedimensionada = rgIconOriginal.getImage().getScaledInstance(larguraRgIcon, alturaRgIcon,
                Image.SCALE_SMOOTH);
        rgIcon = new ImageIcon(imagemRgIconRedimensionada);

        rgIconLabel = new JLabel(rgIcon);
        rgIconLabel.setBounds(50, 180, larguraRgIcon, alturaRgIcon);
        add(rgIconLabel);


        ImageIcon cadeadoIconOriginal = new ImageIcon(getClass().getResource("/Assets/Imagens/cadeado.png"));
        int larguraCadeadoIcon = 24;
        int alturaCadeadoIcon = 30;
        Image imagemCadeadoIconRedimensionada = cadeadoIconOriginal.getImage().getScaledInstance(larguraCadeadoIcon, alturaCadeadoIcon, Image.SCALE_SMOOTH);
        cadeadoIcon = new ImageIcon(imagemCadeadoIconRedimensionada);

        cadeadoLabel = new JLabel(cadeadoIcon);
        cadeadoLabel.setBounds(50, 230, larguraCadeadoIcon, alturaCadeadoIcon);
        add(cadeadoLabel);

        // Campo de Texto RG - Castilho
        rgTextField = new RoundedTextField("RG:", 15);
        rgTextField.setBounds(80, 180, 240, 30);
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
                    rgTextField.setForeground(Color.BLACK); 
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (rgTextField.getText().isEmpty()) {
                    rgTextField.setText("RG:");
                    rgTextField.setForeground(Color.GRAY);
                }
            }
        });
        add(rgTextField);

        // Campo de Senha - Castilho
        passwordField = new RoundedPasswordField("Senha:", 15);
        passwordField.setBounds(80, 230, 210, 30);
        passwordField.setBorder(new PlaceholderBorder(getAzulMetro(), "Senha:", 15));
        passwordField.setCaretColor(getAzulMetro());
        passwordField.setBackground(Color.WHITE);
        passwordField.setFont(helveticaFont);
        passwordField.addFocusListener(new FocusListener() { 
            @Override 
            public void focusGained(FocusEvent e) { 
            String senhaAtual = new String(passwordField.getPassword());
            if (senhaAtual.equals("Senha:")) {
                passwordField.setText("");
                passwordField.setForeground(Color.BLACK);
                passwordField.setEchoChar('\u2022'); // bolinhas
            }
    }  
            @Override 
            public void focusLost(FocusEvent e) { 
                if (new String(passwordField.getPassword()).isEmpty()) { 
                    passwordField.setText("Senha:");
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0); // mostra o texto normal
                } 
            } 
        });
        
        add(passwordField); 
        
        senhaTextField = new RoundedTextField("", 15); // Inicial sem texto
        senhaTextField.setBounds(80, 230, 210, 30);
        senhaTextField.setBorder(new PlaceholderBorder(getAzulMetro(), "Senha:", 15));
        senhaTextField.setForeground(Color.BLACK);
        senhaTextField.setCaretColor(getAzulMetro());
        senhaTextField.setBackground(Color.WHITE);
        senhaTextField.setFont(helveticaFont);
        senhaTextField.setVisible(false);
        senhaTextField.setEditable(false);
        senhaTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (senhaTextField.getText().equals("Senha:")) {
                    senhaTextField.setText("");
                    senhaTextField.setForeground(Color.BLACK);
                }
            }
        
            @Override
            public void focusLost(FocusEvent e) {
                if (senhaTextField.getText().isEmpty()) {
                    senhaTextField.setText("Senha:");
                    senhaTextField.setForeground(Color.GRAY);
                }
            }
        });
        
        add(senhaTextField);

        // Larissa o Olho ta aqui - OLha aqui Larissaaaaaaaaaaaaaaaaaaaaaaaaaaa olhaa o olho
        // Ícones do olho

        ImageIcon eyeVisibleIconOriginal = new ImageIcon(getClass().getResource("/Assets/Imagens/olho_visivel.png"));
        Image eyeVisibleImage = eyeVisibleIconOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        eyeVisibleIcon = new ImageIcon(eyeVisibleImage);

        ImageIcon eyeHiddenIconOriginal = new ImageIcon(getClass().getResource("/Assets/Imagens/olho_escondido.png"));
        Image eyeHiddenImage = eyeHiddenIconOriginal.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        eyeHiddenIcon = new ImageIcon(eyeHiddenImage);


        // Botão do olho (mostrar/ocultar senha)
        togglePasswordButton = new JButton(eyeVisibleIcon);
        togglePasswordButton.setBounds(290, 230, 30, 30);
        togglePasswordButton.setBorderPainted(false);
        togglePasswordButton.setContentAreaFilled(false);
        togglePasswordButton.setFocusPainted(false);

        // Alterna entre mostrar/ocultar senha
        togglePasswordButton.addActionListener(e -> {
            if (isPasswordPressed) {
                // Ocultar senha
                passwordField.setText(senhaTextField.getText());
                if (passwordField.getText().equals("Senha:")) {
                    passwordField.setText((""));
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('\u2022');
                }
                passwordField.setVisible(true);
                senhaTextField.setVisible(false);
                togglePasswordButton.setIcon(eyeVisibleIcon);
                senhaTextField.setEditable(false);
                passwordField.requestFocusInWindow();
            } else {
                // Mostrar senha
                String password = new String(passwordField.getPassword());
                if (password.equals("Senha:")) {
                    password = "";
                }
                if (password.isEmpty()) {
                    senhaTextField.setText("Senha:");
                    senhaTextField.setForeground(Color.GRAY);
                } else {
                    senhaTextField.setText(password);
                    senhaTextField.setForeground(Color.BLACK);
                }

                senhaTextField.setText(password);                
                senhaTextField.setText(password);
                senhaTextField.setForeground(Color.BLACK);
                passwordField.setVisible(false);
                senhaTextField.setVisible(true);
                senhaTextField.setEditable(true);
                togglePasswordButton.setIcon(eyeHiddenIcon);
                senhaTextField.requestFocusInWindow();
            }
            
            isPasswordPressed = !isPasswordPressed;
        });

        add(togglePasswordButton);


        // Botão Entrar - Castilho
        entrarButton = new JButton("Entrar");
        entrarButton.setBounds(130, 290, 130, 40);
        entrarButton.setOpaque(false);
        entrarButton.setContentAreaFilled(false);
        entrarButton.setBorder(new RoundedBorder(Color.WHITE, 3, 15));
        entrarButton.setForeground(Color.WHITE);
        entrarButton.setFont(helveticaFont.deriveFont(Font.PLAIN, 25f));
        entrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String rg = rgTextField.getText();
                String password = new String(passwordField.getPassword());

                Usuario usuario = new Usuario(rg, password);
                UsuarioDAO dao = new UsuarioDAO();
                //System.out.println("RG: " + rg + " Pass: " + password);

                if(dao.autenticar(usuario)){ // Usuário encontrado
                    new AlertaBemSucedido(null, "Usuario Encontrado", "").setVisible(true);

                    
                    // Mudança de tela daqui para baixo
                    JFrame frame = new JFrame("Menu");
                    Menu menu = new Menu("/Assets/Imagens/TelaInicial4Corrigida.png"); // seu JLayeredPane
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    frame.setUndecorated(true);
                    frame.setContentPane(menu); // adiciona seu JLayeredPane
                    frame.setVisible(true);
                    // Fecha a tela atual, assumindo que estamos dentro de um JFrame
                    ((JFrame) SwingUtilities.getWindowAncestor(entrarButton)).dispose();
                }else{
                    new Alerta(null, "Erro de Login", "Usuário não encontrado ou senha incorreta.<br>Por favor, tente novamente.").setVisible(true);


                }
            }
        });
        entrarButton.addMouseListener(new MouseAdapter() {
            Color originalBorderColor = Color.WHITE;
            int originalBorderThickness = 3;
            Color originalTextColor = Color.WHITE;
            Color hoverBorderColor = new Color(173, 216, 230);
            int hoverBorderThickness = 4;
            Color hoverTextColor = new Color(173, 216, 230);

            @Override
            public void mouseEntered(MouseEvent e) {
                entrarButton.setBorder(new RoundedBorder(hoverBorderColor, hoverBorderThickness, 15));
                entrarButton.setForeground(hoverTextColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                entrarButton.setBorder(new RoundedBorder(originalBorderColor, originalBorderThickness, 5));
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
            if (textField == passwordField && !isPasswordPressed) { // Mostra placeholder no passwordField se não estiver mostrando a senha
                textToDraw = placeholder;
            } else if (textField == senhaTextField && !textField.getText().isEmpty()) { // Mostra o texto da senha no textField
                textToDraw = textField.getText();
            } else if (textField == passwordField && new String(passwordField.getPassword()).isEmpty() && !isPasswordPressed) {
                textToDraw = placeholder;
            } else {
                textToDraw = ""; // Não mostrar nada no textField inicialmente
            }
            g2d.setColor((textField == passwordField && new String(passwordField.getPassword()).isEmpty() && !isPasswordPressed) ? Color.GRAY : textField.getForeground());
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

    class RoundedTextField extends JTextField {
        private int radius;

        public RoundedTextField(String text, int radius) {
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
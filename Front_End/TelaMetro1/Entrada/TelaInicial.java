package TelaMetro1.Entrada;

// Importações: Ferramentas pra tela e segurança
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

import DAO.UsuarioDAO; // Acesso ao banco de dados de usuários
import Model.Usuario; // Modelo de usuário
import TelaMetro1.telaMenu.Menu; // Tela do menu principal
import Assets.Cores; // Cores usadas no projeto

import java.security.MessageDigest; // Para segurança (hash de senha)
import java.security.NoSuchAlgorithmException; // Erros de segurança

public class TelaInicial extends JPanel {

    // Construtor da Tela de Login
    public TelaInicial() {
        setOpaque(false);
        setLayout(null);
        initComponents(); // Inicia os componentes visuais
        passwordField.setText("Senha:"); // Texto inicial da senha
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0); // Exibe o texto (não bolinhas)
    }

    // Variáveis dos componentes da tela
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
    private boolean isPasswordPressed = false; // Controle de senha visível/oculta

    // Inicializa e organiza os componentes visuais
    private void initComponents() {
        helveticaFont = new Font("Helvetica", Font.PLAIN, 16);

        // Imagem de Perfil
        ImageIcon perfilIconOriginal = new ImageIcon(getClass().getResource("/Assets/Imagens/perfil.png"));
        int larguraPerfil = 80;
        int alturaPerfil = 80;
        Image imagemPerfilRedimensionada = perfilIconOriginal.getImage().getScaledInstance(larguraPerfil, alturaPerfil,
                Image.SCALE_SMOOTH);
        ImageIcon perfilIconRedimensionado = new ImageIcon(imagemPerfilRedimensionada);
        perfilLabel = new JLabel(perfilIconRedimensionado);
        perfilLabel.setBounds(160, 70, larguraPerfil, alturaPerfil);
        add(perfilLabel);

        // Ícone do RG
        ImageIcon rgIconOriginal = new ImageIcon(getClass().getResource("/Assets/Imagens/perfil2.png"));
        int larguraRgIcon = 24;
        int alturaRgIcon = 30;
        Image imagemRgIconRedimensionada = rgIconOriginal.getImage().getScaledInstance(larguraRgIcon, alturaRgIcon,
                Image.SCALE_SMOOTH);
        rgIcon = new ImageIcon(imagemRgIconRedimensionada);

        rgIconLabel = new JLabel(rgIcon);
        rgIconLabel.setBounds(50, 180, larguraRgIcon, alturaRgIcon);
        add(rgIconLabel);

        // Ícone do Cadeado
        ImageIcon cadeadoIconOriginal = new ImageIcon(getClass().getResource("/Assets/Imagens/cadeado.png"));
        Image imagemCadeadoIconRedimensionada = cadeadoIconOriginal.getImage().getScaledInstance(24, 30, Image.SCALE_SMOOTH);
        cadeadoIcon = new ImageIcon(imagemCadeadoIconRedimensionada);

        cadeadoLabel = new JLabel(cadeadoIcon);
        cadeadoLabel.setBounds(50, 230, 24, 30);
        add(cadeadoLabel);

        // Campo de Texto RG
        rgTextField = new RoundedTextField("RG:", 15);
        rgTextField.setBounds(80, 180, 240, 30);
        rgTextField.setBorder(new PlaceholderBorder(getAzulMetro(), "RG:", 15));
        rgTextField.setForeground(Color.BLACK);
        rgTextField.setCaretColor(getAzulMetro());
        rgTextField.setBackground(Color.WHITE);
        rgTextField.setFont(helveticaFont);
        // Comportamento de foco do campo RG
        rgTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) { // Ganhando foco
                if (rgTextField.getText().equals("RG:")) {
                    rgTextField.setText("");
                    rgTextField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) { // Perdendo foco
                if (rgTextField.getText().isEmpty()) {
                    rgTextField.setText("RG:");
                    rgTextField.setForeground(Color.GRAY);
                }
            }
        });
        add(rgTextField);

        // Campo de Senha Oculta (JPasswordField)
        passwordField = new RoundedPasswordField("Senha:", 15);
        passwordField.setBounds(80, 230, 210, 30);
        passwordField.setBorder(new PlaceholderBorder(getAzulMetro(), "Senha:", 15));
        passwordField.setCaretColor(getAzulMetro());
        passwordField.setBackground(Color.WHITE);
        passwordField.setFont(helveticaFont);
        // Comportamento de foco do campo de senha oculta
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) { // Ganhando foco
                String senhaAtual = new String(passwordField.getPassword());
                if (senhaAtual.equals("Senha:")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('\u2022'); // Bolinhas
                }
            }

            @Override
            public void focusLost(FocusEvent e) { // Perdendo foco
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("Senha:");
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0); // Exibe texto normal
                }
            }
        });

        add(passwordField);

        // Campo de Senha Visível (JTextField)
        senhaTextField = new RoundedTextField("", 15);
        senhaTextField.setBounds(80, 230, 210, 30);
        senhaTextField.setBorder(new PlaceholderBorder(getAzulMetro(), "Senha:", 15));
        senhaTextField.setForeground(Color.BLACK);
        senhaTextField.setCaretColor(getAzulMetro());
        senhaTextField.setBackground(Color.WHITE);
        senhaTextField.setFont(helveticaFont);
        senhaTextField.setVisible(false); // Começa escondido
        senhaTextField.setEditable(false);
        // Comportamento de foco do campo de senha visível
        senhaTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) { // Ganhando foco
                if (senhaTextField.getText().equals("Senha:")) {
                    senhaTextField.setText("");
                    senhaTextField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) { // Perdendo foco
                if (senhaTextField.getText().isEmpty()) {
                    senhaTextField.setText("Senha:");
                    senhaTextField.setForeground(Color.GRAY);
                }
            }
        });

        add(senhaTextField);

        // Ícones do Olho (visível/oculto)
        ImageIcon eyeVisibleIconOriginal = new ImageIcon(getClass().getResource("/Assets/Imagens/olho_visivel.png"));
        Image eyeVisibleImage = eyeVisibleIconOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        eyeVisibleIcon = new ImageIcon(eyeVisibleImage);

        ImageIcon eyeHiddenIconOriginal = new ImageIcon(getClass().getResource("/Assets/Imagens/olho_escondido.png"));
        Image eyeHiddenImage = eyeHiddenIconOriginal.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        eyeHiddenIcon = new ImageIcon(eyeHiddenImage);

        // Botão do Olho (oculta/desoculta senha)
        togglePasswordButton = new JButton(eyeVisibleIcon);
        togglePasswordButton.setBounds(290, 230, 30, 30);
        togglePasswordButton.setBorderPainted(false);
        togglePasswordButton.setContentAreaFilled(false);
        togglePasswordButton.setFocusPainted(false);

        togglePasswordButton.addActionListener(e -> {
            if (isPasswordPressed) {
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
            } else { // Se a senha está oculta, vai mostrar
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

                senhaTextField.setForeground(Color.BLACK);
                passwordField.setVisible(false);
                senhaTextField.setVisible(true);
                senhaTextField.setEditable(true);
                togglePasswordButton.setIcon(eyeHiddenIcon); 
                senhaTextField.requestFocusInWindow();
            }

            isPasswordPressed = !isPasswordPressed; // Inverte o estado
        });

        add(togglePasswordButton);

        // Botão Entrar
        entrarButton = new JButton("Entrar");
        entrarButton.setBounds(130, 290, 130, 40);
        entrarButton.setOpaque(false);
        entrarButton.setContentAreaFilled(false);
        entrarButton.setBorder(new RoundedBorder(Color.WHITE, 3, 15));
        entrarButton.setForeground(Color.WHITE);
        entrarButton.setFont(helveticaFont.deriveFont(Font.PLAIN, 25f));
        // Ação do botão Entrar
        entrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rg = rgTextField.getText();
                String password;

                // Pega a senha do campo correto (visível ou oculto)
                if (passwordField.isVisible()) {
                    password = new String(passwordField.getPassword());
                } else {
                    password = senhaTextField.getText();
                }

                // Validação de campos vazios
                if (rg.equals("RG:") || password.equals("Senha:") || rg.isEmpty() || password.isEmpty()) {
                    new Alerta(null, "Erro de Login", "Por favor, preencha todos os campos.").setVisible(true);
                    return;
                }

                // Hash da senha para segurança
                String hashedPassword = null;
                try {
                    hashedPassword = hashSHA256(password);
                } catch (NoSuchAlgorithmException ex) {
                    System.err.println("Erro ao gerar hash SHA-256: " + ex.getMessage());
                    new Alerta(null, "Erro", "Ocorreu um erro interno. Tente novamente.").setVisible(true);
                    return;
                }

                UsuarioDAO dao = new UsuarioDAO(); // Objeto para acessar dados de usuário

                Usuario usuarioAutenticado = dao.getUsuarioByRg(rg); // Busca usuário pelo RG

                // Verifica login (usuário e senha)
                if (usuarioAutenticado != null && usuarioAutenticado.getSenha().equals(hashedPassword)) {
                    // Login bem-sucedido
                    new AlertaBemSucedido(null, "Login Bem-Sucedido", "Bem-vindo, " + usuarioAutenticado.getNome() + "!").setVisible(true);

                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(entrarButton);

                    JFrame newFrame = new JFrame("Menu"); 

                    System.out.println("DEBUG: Criando Menu com ID do usuário: " + usuarioAutenticado.getId() + ", Tipo: " + usuarioAutenticado.gettipo_usuario());
                    Menu menu = new Menu(newFrame, "/Assets/Imagens/TelaInicial4Corrigida.png", usuarioAutenticado.gettipo_usuario(), usuarioAutenticado.getId());
                    newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    newFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
                    newFrame.setUndecorated(true); 
                    newFrame.setContentPane(menu); 
                    newFrame.setVisible(true); 
                    if (currentFrame != null) {
                        currentFrame.dispose();
                    }
                } else {
                    // Login falhou
                    new Alerta(null, "Erro de Login", "Usuário não encontrado ou senha incorreta.<br>Por favor, tente novamente.").setVisible(true);
                    System.out.println("DEBUG: Falha no login para RG: " + rg);
                }
            }
        });

        // Efeitos de mouse no botão Entrar
        entrarButton.addMouseListener(new MouseAdapter() {
            Color originalBorderColor = Color.WHITE;
            int originalBorderThickness = 3;
            Color originalTextColor = Color.WHITE;
            Color hoverBorderColor = new Color(173, 216, 230);
            int hoverBorderThickness = 4;
            Color hoverTextColor = new Color(173, 216, 230);


            // Place Holder 
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
        return Cores.AZUL_METRO;
    }

    // Logica de Esconder Senha
    private static String hashSHA256(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashInBytes = md.digest(password.getBytes());

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // Borda com placeholder 
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

            // Lógica para mostrar o placeholder ou o texto digitado 
            if (textField == passwordField) {
                if (!isPasswordPressed && new String(passwordField.getPassword()).isEmpty()) {
                    textToDraw = placeholder;
                }
            } else if (textField == senhaTextField) {
                if (isPasswordPressed && senhaTextField.getText().isEmpty()) {
                    textToDraw = placeholder;
                } else if (isPasswordPressed) {
                     textToDraw = senhaTextField.getText();
                }
            } else if (textField == rgTextField && (rgTextField.getText().isEmpty() || rgTextField.getText().equals("RG:"))) {
                textToDraw = placeholder;
            }

            g2d.setColor((textToDraw.equals(placeholder) && textField.getForeground() != Color.GRAY) ? Color.GRAY : textField.getForeground());
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

    // Borda Arredondada (para botões, etc.)
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

    // Campo de Senha Arredondado
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

    // Campo de Texto Arredondado
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
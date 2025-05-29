package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.Border;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.text.JTextComponent;

import Assets.Cores;
import TelaMetro1.*;
import DAO.UsuarioDAO;
import Model.Usuario;
import TelaMetro1.Entrada.Alerta;
import TelaMetro1.Entrada.AlertaAtualizado;
import TelaMetro1.Entrada.AlertaBemSucedido;

public class BuscarSupervisorPanel extends JPanel {
    private Image ImagemDeFundo;
    private Image logoOriginal, logoRedimensionada;
    private int logoWidth = 40;
    private int logoHeight = 40;
    private Runnable voltarAcao;

    private JTextField txtRGBusca;
    private JTextField txtNomeEncontrado;
    private JTextField txtRGEncontrado;
    private JTextField txtTipoUsuario;

    private JButton btnBuscar;
    private JButton btnAtualizarTipo;
    private JButton btnAtualizarTipoOperario; // New button for Operário

    private Usuario usuarioEncontrado;

    private Font helveticaFont = new Font("Helvetica", Font.PLAIN, 16);

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

        JLabel lblTituloForm = new JLabel("Buscar e Atualizar Supervisor", SwingConstants.CENTER);
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
        formPanel.add(criarLabel("RG para Busca:"), formGbc);
        formGbc.gridx = 1;
        txtRGBusca = criarCampoTextoComIcone("/Assets/Imagens/lupa.png", 20, 20);
        txtRGBusca.setFont(helveticaFont.deriveFont(Font.PLAIN, 18f));

        txtRGBusca.setBorder(new PlaceholderBorder(getAzulMetro(), "Digite o RG", 15, txtRGBusca));
        txtRGBusca.setForeground(Color.GRAY);
        txtRGBusca.setText("Digite o RG");
        txtRGBusca.addFocusListener(new PlaceholderFocusListener(txtRGBusca, "Digite o RG"));
        formPanel.add(txtRGBusca, formGbc);

        formGbc.gridx = 0;
        formGbc.gridy = 2;
        formGbc.gridwidth = 2;
        btnBuscar = botoes.criarBotaoPadrao("Buscar", null, 0, 0, SwingConstants.CENTER, new Dimension(180, 50), 20);

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarBuscaRG();
            }
        });
        formPanel.add(btnBuscar, formGbc);

        formGbc.gridwidth = 1;
        formGbc.gridx = 0;
        formGbc.gridy = 3;
        formGbc.insets = new Insets(20, 5, 10, 5);
        formPanel.add(criarLabel("Nome:"), formGbc);
        formGbc.gridx = 1;
        txtNomeEncontrado = criarCampoTextoSemIcone();
        txtNomeEncontrado.setEditable(false);
        txtNomeEncontrado.setFont(helveticaFont.deriveFont(Font.PLAIN, 18f));
        txtNomeEncontrado.setBorder(new RoundedBorder(Color.WHITE, 2, 10));
        formPanel.add(txtNomeEncontrado, formGbc);

        formGbc.gridx = 0;
        formGbc.gridy = 4;
        formGbc.insets = new Insets(10, 5, 10, 5);
        formPanel.add(criarLabel("RG:"), formGbc);
        formGbc.gridx = 1;
        txtRGEncontrado = criarCampoTextoSemIcone();
        txtRGEncontrado.setEditable(false);
        txtRGEncontrado.setFont(helveticaFont.deriveFont(Font.PLAIN, 18f));
        txtRGEncontrado.setBorder(new RoundedBorder(Color.WHITE, 2, 10));
        formPanel.add(txtRGEncontrado, formGbc);

        formGbc.gridx = 0;
        formGbc.gridy = 5;
        formPanel.add(criarLabel("Tipo de Usuário:"), formGbc);
        formGbc.gridx = 1;
        txtTipoUsuario = criarCampoTextoSemIcone();
        txtTipoUsuario.setFont(helveticaFont.deriveFont(Font.PLAIN, 18f));
        txtTipoUsuario.setBorder(new RoundedBorder(getAzulMetro(), 2, 10));
        txtTipoUsuario.setEditable(false);
        formPanel.add(txtTipoUsuario, formGbc);

        formGbc.gridx = 0;
        formGbc.gridy = 6;
        formGbc.gridwidth = 2;
        formGbc.insets = new Insets(30, 0, 0, 0);
        btnAtualizarTipo = botoes.criarBotaoPadrao("Atualizar Tipo para Supervisor", null, 0, 0, SwingConstants.CENTER, new Dimension(300, 50), 20);

        btnAtualizarTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarTipoUsuario();
            }
        });
        btnAtualizarTipo.setEnabled(false);
        formPanel.add(btnAtualizarTipo, formGbc);

        // New button for Operário
        formGbc.gridy = 7; // Next row
        formGbc.insets = new Insets(10, 0, 0, 0); // Smaller top inset for spacing
        btnAtualizarTipoOperario = botoes.criarBotaoPadrao("Atualizar Tipo para Operário", null, 0, 0, SwingConstants.CENTER, new Dimension(300, 50), 20);
        btnAtualizarTipoOperario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarTipoUsuarioParaOperario();
            }
        });
        btnAtualizarTipoOperario.setEnabled(false);
        formPanel.add(btnAtualizarTipoOperario, formGbc);

        painelCentralBuscar.add(formPanel);
        add(painelCentralBuscar, BorderLayout.CENTER);

        addHoverEffect(btnBuscar);
        addHoverEffect(btnAtualizarTipo);
        addHoverEffect(btnAtualizarTipoOperario); // Add hover effect to the new button
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField criarCampoTextoSemIcone() {
        JTextField campo = new JTextField(20);
        campo.setFont(new Font("Arial", Font.PLAIN, 18));
        campo.setBackground(new Color(255, 255, 255, 200));
        campo.setForeground(Color.BLACK);
        campo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return campo;
    }

    private JTextField criarCampoTextoComIcone(String iconPath, int iconWidth, int iconHeight) {
        JTextField campo = new JTextField(20);
        campo.setFont(new Font("Arial", Font.PLAIN, 18));
        campo.setBackground(new Color(255, 255, 255, 200));
        campo.setForeground(Color.BLACK);
        // Não defina a borda aqui, será definida pela PlaceholderBorder

        try (InputStream is = getClass().getResourceAsStream(iconPath)) {
            if (is != null) {
                Image img = ImageIO.read(is).getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
                JLabel iconLabel = new JLabel(new ImageIcon(img));
                campo.putClientProperty("iconLabel", iconLabel);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar ícone para campo de texto: " + iconPath);
        }
        return campo;
    }

    private void realizarBuscaRG() {
        String rg = txtRGBusca.getText().trim();

        if (rg.isEmpty() || rg.equals("Digite o RG")) {
            new Alerta(null, "Campo Vazio", "Por favor, digite o RG para buscar.").setVisible(true);
            limparCamposResultados();
            return;
        }

        // Validação de formato de RG
        if (!rg.matches("\\d{2}\\.\\d{3}\\.\\d{3}-\\d{1}")) {
            new Alerta(null, "Formato Inválido", "Formato de RG inválido. Use XX.XXX.XXX-X").setVisible(true);
            limparCamposResultados();
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();

        usuarioEncontrado = dao.buscarUsuarioPorRg(rg);

        if (usuarioEncontrado != null) {
            txtNomeEncontrado.setText(usuarioEncontrado.getNome());
            txtRGEncontrado.setText(usuarioEncontrado.getRg());
            txtTipoUsuario.setText(usuarioEncontrado.getTipoUsuario());
            txtTipoUsuario.setEditable(true); // Allow editing of type, if needed, though buttons will change it
            btnAtualizarTipo.setEnabled(!usuarioEncontrado.getTipoUsuario().equalsIgnoreCase("supervisor"));
            btnAtualizarTipoOperario.setEnabled(!usuarioEncontrado.getTipoUsuario().equalsIgnoreCase("operario"));
            new AlertaBemSucedido(null, "Usuário Encontrado", "Dados do usuário carregados com sucesso.").setVisible(true);
        } else {
            new Alerta(null, "Usuário Não Encontrado", "Nenhum usuário encontrado com o RG informado.").setVisible(true);
            limparCamposResultados();
        }
    }

    private void atualizarTipoUsuario() {
        if (usuarioEncontrado == null) {
            new Alerta(null, "Erro", "Nenhum usuário selecionado para atualização.").setVisible(true);
            return;
        }

        String novoTipo = "supervisor";

        if (usuarioEncontrado.getTipoUsuario().equalsIgnoreCase(novoTipo)) {
            new AlertaAtualizado(null, "Informação", "O usuário já é um supervisor.").setVisible(true);
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        boolean sucesso = dao.atualizarTipoUsuario(usuarioEncontrado.getRg(), novoTipo);

        if (sucesso) {
            usuarioEncontrado.setTipoUsuario(novoTipo);
            txtTipoUsuario.setText(novoTipo);
            btnAtualizarTipo.setEnabled(false); // Disable after update
            btnAtualizarTipoOperario.setEnabled(true); // Enable other option
            new AlertaAtualizado(null, "Atualização Concluída", "Tipo de usuário atualizado para 'supervisor' com sucesso!").setVisible(true);
        } else {
            new AlertaAtualizado(null, "Erro na Atualização", "Não foi possível atualizar o tipo de usuário.").setVisible(true);
        }
    }

    private void atualizarTipoUsuarioParaOperario() {
        if (usuarioEncontrado == null) {
            new AlertaAtualizado(null, "Erro", "Nenhum usuário selecionado para atualização.").setVisible(true);
            return;
        }

        String novoTipo = "operario";

        if (usuarioEncontrado.getTipoUsuario().equalsIgnoreCase(novoTipo)) {
            new AlertaAtualizado(null, "Informação", "O usuário já é um operário.").setVisible(true);
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        boolean sucesso = dao.atualizarTipoUsuario(usuarioEncontrado.getRg(), novoTipo);

        if (sucesso) {
            usuarioEncontrado.setTipoUsuario(novoTipo);
            txtTipoUsuario.setText(novoTipo);
            btnAtualizarTipoOperario.setEnabled(false); // Disable after update
            btnAtualizarTipo.setEnabled(true); // Enable other option
            new AlertaAtualizado(null, "Atualização Concluída", "Tipo de usuário atualizado para 'operario' com sucesso!").setVisible(true);
        } else {
            new Alerta(null, "Erro na Atualização", "Não foi possível atualizar o tipo de usuário.").setVisible(true);
        }
    }

    private void limparCamposResultados() {
        txtNomeEncontrado.setText("");
        txtRGEncontrado.setText("");
        txtTipoUsuario.setText("");
        txtTipoUsuario.setEditable(false);
        btnAtualizarTipo.setEnabled(false);
        btnAtualizarTipoOperario.setEnabled(false); // Disable new button as well
        usuarioEncontrado = null;
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
        navBar.setBackground(getAzulMetro());
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

        JLabel titulo = new JLabel("Buscar e Atualizar Supervisor", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);
        navBar.add(titulo, BorderLayout.CENTER);

        JPanel painelDireito = new JPanel();
        painelDireito.setOpaque(false);
        painelDireito.setPreferredSize(new Dimension(btnVoltar.getPreferredSize().width + 30, 60));
        navBar.add(painelDireito, BorderLayout.EAST);

        return navBar;
    }

    private Color getAzulMetro() {
        return Cores.AZUL_METRO;
    }


    private void addHoverEffect(JButton button) {
        Color originalBorderColor = Color.WHITE;
        int originalBorderThickness = 3;
        Color originalTextColor = Color.WHITE;
        Color clickedBorderColor = Color.DARK_GRAY;

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

                button.setBorder(new RoundedBorder(originalBorderColor, originalBorderThickness, 15));
                button.setForeground(originalTextColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                button.setBorder(new RoundedBorder(originalBorderColor, originalBorderThickness, 15));
                button.setForeground(originalTextColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBorder(new RoundedBorder(clickedBorderColor, originalBorderThickness + 1, 15));
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                button.setBorder(new RoundedBorder(originalBorderColor, originalBorderThickness, 15));
            }
        });
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

    class PlaceholderBorder implements Border {
        private Color focusColor;
        private String placeholder;
        private int radius;
        private JTextField textField;
        public PlaceholderBorder(Color focusColor, String placeholder, int radius, JTextField textField) {
            this.focusColor = focusColor;
            this.placeholder = placeholder;
            this.radius = radius;
            this.textField = textField;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(2));

            g2d.setColor(focusColor);
            g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));

            if (textField.getText().isEmpty() && !textField.isFocusOwner()) {
                g2d.setColor(Color.GRAY);
                g2d.setFont(helveticaFont);
                FontMetrics fm = g.getFontMetrics();
                int textX = x + 10;
                int textY = y + fm.getAscent() + (height - fm.getHeight()) / 2;
                g2d.drawString(placeholder, textX, textY);
            }


            JLabel iconLabel = (JLabel) textField.getClientProperty("iconLabel");
            if (iconLabel != null && iconLabel.getIcon() != null) {
                ImageIcon icon = (ImageIcon) iconLabel.getIcon();
                Image img = icon.getImage();

                int iconX = x + width - icon.getIconWidth() - 10;
                int iconY = y + (height - icon.getIconHeight()) / 2;
                g2d.drawImage(img, iconX, iconY, c);
            }
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            int iconWidth = 0;
            JLabel iconLabel = (JLabel) textField.getClientProperty("iconLabel");
            if (iconLabel != null && iconLabel.getIcon() != null) {
                iconWidth = iconLabel.getIcon().getIconWidth();
            }
            return new Insets(5, 10, 5, 10 + iconWidth + 5);
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

            int pad = Math.max(radius, thickness);
            return new Insets(pad, pad, pad, pad);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }

    class PlaceholderFocusListener implements FocusListener {
        private JTextField textField;
        private String placeholder;

        public PlaceholderFocusListener(JTextField textField, String placeholder) {
            this.textField = textField;
            this.placeholder = placeholder;
        }

        @Override
        public void focusGained(FocusEvent e) {
            if (textField.getText().equals(placeholder)) {
                textField.setText("");
                textField.setForeground(Color.BLACK);
            }
            textField.repaint();
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (textField.getText().isEmpty()) {
                textField.setText(placeholder);
                textField.setForeground(Color.GRAY);
            }
            textField.repaint();
        }
    }
}
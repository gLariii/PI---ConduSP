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
import TelaMetro1.Entrada.AlertaEncontrado; 

public class BuscarSupervisorPanel extends JPanel {
    private Image ImagemDeFundo; 
    private Image logoOriginal, logoRedimensionada; 
    private int logoWidth = 40; 
    private int logoHeight = 40;
    private Runnable voltarAcao;
    private JTextField txtRGBusca;
    private JTextField txtNomeEncontrado;
    private JTextField txtRGEncontrado;
    private JTextField txttipo_usuario;
    private JButton btnBuscar;
    private JButton btnAtualizarTipo;
    private JButton btnAtualizarTipoOperario;

    private Usuario usuarioEncontrado; 
    private Font helveticaFont = new Font("Helvetica", Font.PLAIN, 16); 

    private Border defaultTextFieldBorder;

    // Construtor do painel
    public BuscarSupervisorPanel(String imagemPath, Runnable voltarAcao) {
        this.voltarAcao = voltarAcao;
        carregarImagem(imagemPath); 
        carregarLogo("/Assets/Imagens/logoORG.png"); 
        setLayout(new BorderLayout()); 

        setOpaque(true); 
        defaultTextFieldBorder = new RoundedBorder(Color.WHITE, 2, 10); // Borda padrão

        add(criarNavBarBuscarSupervisor(), BorderLayout.NORTH); // Adiciona a barra superior

        // Painel central
        JPanel painelCentralBuscar = new JPanel(new GridBagLayout());
        painelCentralBuscar.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Painel do formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(0, 0, 0, 150));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(10, 5, 10, 5);
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;

        // Título do formulário
        JLabel lblTituloForm = new JLabel("Buscar e Atualizar Supervisor", SwingConstants.CENTER);
        lblTituloForm.setFont(new Font("Arial", Font.BOLD, 28));
        lblTituloForm.setForeground(Color.WHITE);
        formGbc.gridx = 0;
        formGbc.gridy = 0;
        formGbc.gridwidth = 2;
        formGbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(lblTituloForm, formGbc);

        // Campo de busca de RG
        formGbc.gridwidth = 1;
        formGbc.gridx = 0;
        formGbc.gridy = 1;
        formPanel.add(criarLabel("RG para Busca:"), formGbc);
        formGbc.gridx = 1;
        JPanel rgBuscaPanel = criarCampoTextoComIconeWrapper("/Assets/Imagens/lupa.png", 20, 20, getAzulMetro(), 15);
        txtRGBusca = (JTextField) rgBuscaPanel.getClientProperty("textField");
        txtRGBusca.setFont(helveticaFont.deriveFont(Font.PLAIN, 18f));
        txtRGBusca.setForeground(Color.GRAY);
        txtRGBusca.setText("Digite o RG");
        txtRGBusca.addFocusListener(new PlaceholderFocusListener(txtRGBusca, "Digite o RG"));
        formPanel.add(rgBuscaPanel, formGbc);

        // Botão Buscar
        formGbc.gridx = 0;
        formGbc.gridy = 2;
        formGbc.gridwidth = 2;
        btnBuscar = botoes.criarBotaoPadrao("Buscar", null, 0, 0, SwingConstants.CENTER, new Dimension(180, 50), 20);

        // Ação do botão Buscar
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarBuscaRG(); // Chama a função de busca
            }
        });
        formPanel.add(btnBuscar, formGbc);

        // Campo Nome Encontrado
        formGbc.gridwidth = 1;
        formGbc.gridx = 0;
        formGbc.gridy = 3;
        formGbc.insets = new Insets(20, 5, 10, 5);
        formPanel.add(criarLabel("Nome:"), formGbc);
        formGbc.gridx = 1;
        txtNomeEncontrado = criarCampoTextoSemIcone();
        txtNomeEncontrado.setEditable(false);
        txtNomeEncontrado.setFont(helveticaFont.deriveFont(Font.PLAIN, 18f));
        txtNomeEncontrado.setBorder(defaultTextFieldBorder);
        formPanel.add(txtNomeEncontrado, formGbc);

        // Campo RG Encontrado
        formGbc.gridx = 0;
        formGbc.gridy = 4;
        formGbc.insets = new Insets(10, 5, 10, 5);
        formPanel.add(criarLabel("RG:"), formGbc);
        formGbc.gridx = 1;
        txtRGEncontrado = criarCampoTextoSemIcone();
        txtRGEncontrado.setEditable(false);
        txtRGEncontrado.setFont(helveticaFont.deriveFont(Font.PLAIN, 18f));
        txtRGEncontrado.setBorder(defaultTextFieldBorder);
        formPanel.add(txtRGEncontrado, formGbc);

        // Campo "Tipo de Usuário"
        formGbc.gridx = 0;
        formGbc.gridy = 5;
        formPanel.add(criarLabel("Tipo de Usuário:"), formGbc);
        formGbc.gridx = 1;
        txttipo_usuario = criarCampoTextoSemIcone();
        txttipo_usuario.setFont(helveticaFont.deriveFont(Font.PLAIN, 18f));
        txttipo_usuario.setBorder(new RoundedBorder(getAzulMetro(), 2, 10));
        formPanel.add(txttipo_usuario, formGbc);

        // Botão Atualizar Tipo para Supervisor
        formGbc.gridx = 0;
        formGbc.gridy = 6;
        formGbc.gridwidth = 2;
        formGbc.insets = new Insets(30, 0, 0, 0);
        btnAtualizarTipo = botoes.criarBotaoPadrao("Atualizar Tipo para Supervisor", null, 0, 0, SwingConstants.CENTER, new Dimension(300, 50), 20);

        // Ação do botão "Atualizar para Supervisor"
        btnAtualizarTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizartipo_usuario(); // Chama a função de atualização
            }
        });
        btnAtualizarTipo.setEnabled(false); // Começa desativado
        formPanel.add(btnAtualizarTipo, formGbc);

        // Botão Atualizar Tipo para Operário
        formGbc.gridy = 7;
        formGbc.insets = new Insets(10, 0, 0, 0);
        btnAtualizarTipoOperario = botoes.criarBotaoPadrao("Atualizar Tipo para Operário", null, 0, 0, SwingConstants.CENTER, new Dimension(300, 50), 20);
        // Ação do botão Atualizar para Operário
        btnAtualizarTipoOperario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizartipo_usuarioParaOperario(); // Chama a função de atualização
            }
        });
        btnAtualizarTipoOperario.setEnabled(false); // Começa desativado
        formPanel.add(btnAtualizarTipoOperario, formGbc);

        painelCentralBuscar.add(formPanel);
        add(painelCentralBuscar, BorderLayout.CENTER);

        // Adiciona efeito de hover aos botões
        addHoverEffect(btnBuscar);
        addHoverEffect(btnAtualizarTipo);
        addHoverEffect(btnAtualizarTipoOperario);
    }

    // Cria um JLabel padrão
    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setForeground(Color.WHITE);
        return label;
    }

    // Cria um JTextField sem ícone
    private JTextField criarCampoTextoSemIcone() {
        JTextField campo = new JTextField(20);
        campo.setFont(new Font("Arial", Font.PLAIN, 18));
        campo.setBackground(Color.WHITE);
        campo.setForeground(Color.BLACK);
        campo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        campo.setOpaque(true);
        return campo;
    }

    // Cria um painel com JTextField e ícone
    private JPanel criarCampoTextoComIconeWrapper(String iconPath, int iconWidth, int iconHeight, Color borderColor, int borderRadius) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new RoundedBorder(borderColor, 2, borderRadius));

        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 18));
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
        textField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        textField.setOpaque(true);

        JLabel iconLabel = new JLabel();
        try (InputStream is = getClass().getResourceAsStream(iconPath)) {
            if (is != null) {
                Image img = ImageIO.read(is).getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
                iconLabel.setIcon(new ImageIcon(img));
                iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
            } else {
                System.err.println("Erro ao carregar ícone para campo de texto: " + iconPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar ícone para campo de texto: " + iconPath);
        }

        panel.add(textField, BorderLayout.CENTER);
        panel.add(iconLabel, BorderLayout.EAST);
        panel.putClientProperty("textField", textField); // Guarda o campo de texto no painel

        return panel;
    }

    // Lógica para buscar usuário por RG
    private void realizarBuscaRG() {
        String rg = txtRGBusca.getText().trim();

        // Validação do RG
        if (rg.isEmpty() || rg.equals("Digite o RG")) {
            new Alerta(null, "Campo Vazio", "Por favor, digite o RG para buscar.").setVisible(true);
            limparCamposResultados();
            return;
        }

        if (!rg.matches("\\d{2}\\.\\d{3}\\.\\d{3}-\\d{1}")) {
            new Alerta(null, "Formato Inválido", "Formato de RG inválido. Use XX.XXX.XXX-X").setVisible(true);
            limparCamposResultados();
            return;
        }

        UsuarioDAO dao = new UsuarioDAO(); // Objeto para acessar o banco

        usuarioEncontrado = dao.buscarUsuarioPorRg(rg); // Busca o usuário

        // Exibe resultados ou alerta de não encontrado
        if (usuarioEncontrado != null) {
            txtNomeEncontrado.setText(usuarioEncontrado.getNome());
            txtRGEncontrado.setText(usuarioEncontrado.getRg());
            txttipo_usuario.setText(usuarioEncontrado.gettipo_usuario());
            txttipo_usuario.setEditable(true);
            btnAtualizarTipo.setEnabled(!usuarioEncontrado.gettipo_usuario().equalsIgnoreCase("supervisor"));
            btnAtualizarTipoOperario.setEnabled(!usuarioEncontrado.gettipo_usuario().equalsIgnoreCase("operario"));
            new AlertaEncontrado(null, "Usuário Encontrado", "Dados do usuário carregados com sucesso.").setVisible(true);
        } else {
            new Alerta(null, "Usuário Não Encontrado", "Nenhum usuário encontrado com o RG informado.").setVisible(true);
            limparCamposResultados();
        }
    }

    // Atualiza o tipo de usuário para supervisor
    private void atualizartipo_usuario() {
        if (usuarioEncontrado == null) {
            new Alerta(null, "Erro", "Nenhum usuário selecionado para atualização.").setVisible(true);
            return;
        }

        String novoTipo = "supervisor";

        if (usuarioEncontrado.gettipo_usuario().equalsIgnoreCase(novoTipo)) {
            new AlertaAtualizado(null, "Informação", "O usuário já é um supervisor.").setVisible(true);
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        boolean sucesso = dao.atualizartipo_usuario(usuarioEncontrado.getRg(), novoTipo); // Atualiza no banco

        // Exibe resultado da atualização
        if (sucesso) {
            usuarioEncontrado.settipo_usuario(novoTipo);
            txttipo_usuario.setText(novoTipo);
            btnAtualizarTipo.setEnabled(false);
            btnAtualizarTipoOperario.setEnabled(true);
            new AlertaAtualizado(null, "Atualização Concluída", "Tipo de usuário atualizado para 'supervisor' com sucesso!").setVisible(true);
        } else {
            new AlertaAtualizado(null, "Erro na Atualização", "Não foi possível atualizar o tipo de usuário.").setVisible(true);
        }
    }

    // Atualiza o tipo de usuário para operário
    private void atualizartipo_usuarioParaOperario() {
        if (usuarioEncontrado == null) {
            new AlertaAtualizado(null, "Erro", "Nenhum usuário selecionado para atualização.").setVisible(true);
            return;
        }

        String novoTipo = "operario";

        if (usuarioEncontrado.gettipo_usuario().equalsIgnoreCase(novoTipo)) {
            new AlertaAtualizado(null, "Informação", "O usuário já é um operário.").setVisible(true);
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        boolean sucesso = dao.atualizartipo_usuario(usuarioEncontrado.getRg(), novoTipo); // Atualiza no banco

        // Exibe resultado da atualização
        if (sucesso) {
            usuarioEncontrado.settipo_usuario(novoTipo);
            txttipo_usuario.setText(novoTipo);
            btnAtualizarTipo.setEnabled(true);
            btnAtualizarTipoOperario.setEnabled(false);
            new AlertaAtualizado(null, "Atualização Concluída", "Tipo de usuário atualizado para 'operario' com sucesso!").setVisible(true);
        } else {
            new Alerta(null, "Erro na Atualização", "Não foi possível atualizar o tipo de usuário.").setVisible(true);
        }
    }

    // Limpa os campos de resultado
    private void limparCamposResultados() {
        txtNomeEncontrado.setText("");
        txtRGEncontrado.setText("");
        txttipo_usuario.setText("");
        txttipo_usuario.setEditable(false);
        btnAtualizarTipo.setEnabled(false);
        btnAtualizarTipoOperario.setEnabled(false);
        usuarioEncontrado = null;
    }

    // Carrega a imagem de fundo
    private void carregarImagem(String imagemPath) {
        try {
            InputStream isFundo = getClass().getResourceAsStream(imagemPath);
            if (isFundo != null) {
                ImagemDeFundo = ImageIO.read(isFundo);
            } else {
                System.err.println("Imagem de fundo não encontrada: " + imagemPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar imagem de fundo para BuscarSupervisorPanel: " + imagemPath);
        }
    }

    // Carrega a logo
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

    // Redimensiona a logo
    private void redimensionarLogo(int width, int height) {
        if (logoOriginal != null) {
            this.logoWidth = width;
            this.logoHeight = height;
            logoRedimensionada = logoOriginal.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        }
    }

    // Cria a barra de navegação superior
    private JPanel criarNavBarBuscarSupervisor() {
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(getAzulMetro());
        navBar.setPreferredSize(new Dimension(getWidth(), 60));

        // Botão "Voltar"
        JButton btnVoltar = botoes.criarBotaoVoltar();
        btnVoltar.addActionListener(e -> {
            if (voltarAcao != null) {
                voltarAcao.run(); // Executa a ação de voltar
            }
        });

        // Painel esquerdo 
        JPanel painelEsquerdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        painelEsquerdo.setOpaque(false);
        painelEsquerdo.add(btnVoltar);
        navBar.add(painelEsquerdo, BorderLayout.WEST);

        // Título da barra de navegação
        JLabel titulo = new JLabel("Buscar e Atualizar Supervisor", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);
        navBar.add(titulo, BorderLayout.CENTER);

        // Painel direito (espaçador)
        JPanel painelDireito = new JPanel();
        painelDireito.setOpaque(false);
        painelDireito.setPreferredSize(new Dimension(btnVoltar.getPreferredSize().width + 30, 60));
        navBar.add(painelDireito, BorderLayout.EAST);

        return navBar;
    }

    // Retorna a cor "Azul Metro"
    private Color getAzulMetro() {
        return Cores.AZUL_METRO;
    }

    // Adiciona efeito de "hover" (passar o mouse) aos botões
    private void addHoverEffect(JButton button) {
        Border originalBorder = button.getBorder();
        Color originalTextColor = button.getForeground();

        Color hoverBorderColor = new Color(255, 255, 255);
        int hoverBorderThickness = 3;
        Color pressedBorderColor = Color.DARK_GRAY;
        int pressedBorderThickness = 4;

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { // Mouse entrou no botão
                button.setBorder(new RoundedBorder(hoverBorderColor, hoverBorderThickness, 20));
                button.setForeground(hoverBorderColor);
            }

            @Override
            public void mouseExited(MouseEvent e) { // Mouse saiu do botão
                button.setBorder(originalBorder);
                button.setForeground(originalTextColor);
            }

            @Override
            public void mousePressed(MouseEvent e) { // Botão pressionado
                button.setBorder(new RoundedBorder(pressedBorderColor, pressedBorderThickness, 20));
            }

            @Override
            public void mouseReleased(MouseEvent e) { // Botão solto
                if (button.contains(e.getPoint())) {
                    button.setBorder(new RoundedBorder(hoverBorderColor, hoverBorderThickness, 20));
                } else {
                    button.setBorder(originalBorder);
                }
            }
        });
    }

    // Desenha o componente (fundo e logo)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Desenha a imagem de fundo
        if (ImagemDeFundo != null) {
            g.drawImage(ImagemDeFundo, 0, 0, getWidth(), getHeight(), this);
        }

        // Desenha a logo no canto inferior direito
        if (logoRedimensionada != null) {
            int x = getWidth() - logoWidth - 15;
            int y = getHeight() - logoHeight - 15;
            g.drawImage(logoRedimensionada, x, y, this);
        }

        // Desenha o texto "Condução SP"
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

    // Classe para bordas arredondadas
    public static class RoundedBorder implements Border {
        private int radius;
        private Color color;
        private int thickness;

        // Construtor
        public RoundedBorder(Color color, int thickness, int radius) {
            this.color = color;
            this.thickness = thickness;
            this.radius = radius;
        }

        // Desenha a borda
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(thickness));
            g2d.draw(new RoundRectangle2D.Double(x + thickness / 2.0, y + thickness / 2.0,
                    width - thickness, height - thickness, radius, radius));
            g2d.dispose();
        }

        // Define o espaçamento interno da borda
        @Override
        public Insets getBorderInsets(Component c) {
            int padding = thickness + 8;
            return new Insets(padding, padding, padding, padding);
        }

        // Indica se a borda é opaca
        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }
    class PlaceholderFocusListener implements FocusListener {
        private JTextField textField;
        private String placeholder;
        private Color originalForeground;

        // Construtor
        public PlaceholderFocusListener(JTextField textField, String placeholder) {
            this.textField = textField;
            this.placeholder = placeholder;
            this.originalForeground = textField.getForeground();
        }

        @Override
        public void focusGained(FocusEvent e) { // Quando ganha foco
            if (textField.getText().equals(placeholder)) {
                textField.setText("");
                textField.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) { // Quando perde foco
            if (textField.getText().isEmpty()) {
                textField.setText(placeholder);
                textField.setForeground(originalForeground);
            }
        }
    }
}
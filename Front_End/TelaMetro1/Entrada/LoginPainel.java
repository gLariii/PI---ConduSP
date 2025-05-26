// Ajustes da Palavra Confirme seus dados, Condução SP e o fundo do painel - Castilho
package TelaMetro1.Entrada;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import Assets.Cores;

public class LoginPainel extends JPanel {

    private JLabel tituloLabel;
    private JLabel rodapeLabel;

    public LoginPainel() {
        setOpaque(false);
        setLayout(null);
        initComponents();
    }

    private void initComponents() {
        tituloLabel = new JLabel("Confirme seus dados");
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setFont(new Font("Helvetica", Font.BOLD, 30));
        tituloLabel.setBounds(50, 445, 400, 50);
        

        TelaInicial telaInicial = new TelaInicial();
        telaInicial.setBounds(10, 50, 560, 460);

        rodapeLabel = new JLabel("Condução SP");
        rodapeLabel.setForeground(Color.WHITE);
        rodapeLabel.setFont(new Font("Helvetica", Font.BOLD, 30));
        rodapeLabel.setBounds(100, 20, 200, 40);

        add(tituloLabel);
        add(telaInicial);
        add(rodapeLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = 400;
        int height = 500;
        int arcWidth = 30;
        int arcHeight = 30;

        g2d.setColor(Cores.AZUL_METRO_TRANSPARENTE);
        g2d.fill(new RoundRectangle2D.Double(0, 0, width, height, arcWidth, arcHeight));

        int topoAltura = 70;
        g2d.setColor(Cores.AZUL_METRO);
        g2d.fill(new RoundRectangle2D.Double(0, 0, width, topoAltura, arcWidth, arcHeight));
        int rodapeAltura = 60;
        int rodapeY = height - rodapeAltura;
        g2d.setColor(Cores.AZUL_METRO);
        g2d.fill(new RoundRectangle2D.Double(0, rodapeY, width, rodapeAltura, arcWidth, arcHeight));

        g2d.dispose();
    }
}
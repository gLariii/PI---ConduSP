package BoteiraLateralTelas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import CabineDeControleTela.CabineDeControleTela;
import CabineDeControleTela.AreaLateral;

public class BoteiraLateralTela extends JPanel {

    private String[] backgrounds = {
        "Imagens/BarraLateralPortasFechadas.jpg",
        "Imagens/BarraLateralPortasAbertas.jpg"
    };

    private int index = 0;
    private BackgroundPanel backgroundPanel;
    private JButton btnTrocar;
    private JFrame parentFrame;

    public BoteiraLateralTela(JFrame frame) {
        this.parentFrame = frame;

        setLayout(new BorderLayout());
        backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(null);
        add(backgroundPanel, BorderLayout.CENTER);

        btnTrocar = new JButton("Trocar Background");

        // Estilo do botão
        
        btnTrocar.setForeground(new Color(0, 0, 0, 0));

        btnTrocar.addActionListener(e -> {
            index = (index + 1) % backgrounds.length;
            backgroundPanel.setImage(backgrounds[index]);
            if (index == 0){
            btnTrocar.setBounds(920, 520, 80, 80);
            }else{
            btnTrocar.setBounds(920, 710, 80, 80);
            }
        });
        btnTrocar.setBounds(920, 520, 80, 80);
        btnTrocar.setOpaque(false);
        btnTrocar.setContentAreaFilled(false);
        btnTrocar.setBorderPainted(false);
        btnTrocar.setFocusPainted(false);
        btnTrocar.setForeground(new Color(0, 0, 0, 0));
        btnTrocar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backgroundPanel.add(btnTrocar);
        backgroundPanel.setImage(backgrounds[index]);
        // Botão de voltar
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(10, 10, 100, 30);
        btnVoltar.addActionListener(e -> voltarParaLateral());
        backgroundPanel.add(btnVoltar);
    }

    private void voltarParaLateral() {
        parentFrame.setContentPane(new AreaLateral(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    // Painel interno com imagem
    class BackgroundPanel extends JPanel {
        private Image image;

        public void setImage(String path) {
            image = new ImageIcon(getClass().getResource(path)).getImage();
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}

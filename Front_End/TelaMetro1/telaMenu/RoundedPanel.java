package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class RoundedPanel extends JPanel {

    private Color backgroundColor;
    private Color hoverColor;
    private int cornerRadius;
    private boolean isHovering = false;

    public RoundedPanel(int radius, Color bg, Color hoverBg) {
        setOpaque(false); 
        this.cornerRadius = radius;
        this.backgroundColor = bg;
        this.hoverColor = hoverBg;
        setBackground(backgroundColor); 

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovering = true;
                setBackground(hoverColor);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovering = false;
                setBackground(backgroundColor);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

        super.paintComponent(g);
    }
}

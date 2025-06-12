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
    private boolean isHovering = false; // Indica se o mouse está sobre o painel

    // Cria um painel com cantos arredondados e efeito de hover
    public RoundedPanel(int radius, Color bg, Color hoverBg) {
        setOpaque(false); // Torna o painel não opaco para que o arredondamento seja visível
        this.cornerRadius = radius; // Define o raio dos cantos
        this.backgroundColor = bg; // Define a cor de fundo padrão
        this.hoverColor = hoverBg; // Define a cor de fundo ao passar o mouse
        setBackground(backgroundColor); // Define a cor de fundo inicial

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Define o layout para empilhar componentes verticalmente

        // Adiciona um listener para interações do mouse (entrada e saída)
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovering = true; // Mouse entrou no painel
                setBackground(hoverColor); // Muda a cor de fundo para a cor de hover
                repaint(); // Redesenha o painel para aplicar a nova cor
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovering = false; // Mouse saiu do painel
                setBackground(backgroundColor); // Retorna à cor de fundo padrão
                repaint(); // Redesenha o painel
            }
        });
    }

    // Desenha o componente
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // Ativa o anti-aliasing para suavizar as bordas arredondadas
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Define a cor de fundo (que pode ser a normal ou a de hover)
        g2.setColor(getBackground());
        // Preenche o painel com um retângulo arredondado usando a cor e o raio definidos
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

        // Chama o método paintComponent da superclasse para desenhar os componentes filhos (se houver)
        super.paintComponent(g);
    }
}
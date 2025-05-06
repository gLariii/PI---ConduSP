// RoundedTextField ( deixar a botão arredondado ) - Larissa Gomes
package TelaMetro1.Entrada;

import javax.swing.*;
import java.awt.*;

public class RoundedTextField extends JTextField {
    private int radius;

    public RoundedTextField(String text, int radius) {
        super(text); // Construtor que recebe o texto e o raio
        this.radius = radius; // Radius(quão arredontado em radius será)
        setOpaque(false); // Permite que o fundo seja desenhado manualmente, ou seja o Swing não vai pintar o fundo manualmente
    }
    //desenhar fundo arredondado
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create(); //desenho mais avançado ( cantos mais suaves)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //anatialiasing (suavização de bordas)

        // Fundo
        g2.setColor(getBackground()); //cor de fundo
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius); //desenha o retangulo com os cantos arredondados

        // Chamar o JTextField
        super.paintComponent(g);
        g2.dispose();
    }
    //desenhar a borda externa do campo mesmo com os cantos arredondados
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getForeground()); //cor da borda
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        g2.dispose();
    }
}

// resumidamente: essa classe  pinta o fundo de branco com os cantos arredondados, assim desenhando essa borda arredondada.
// e por cima ele mostra o texto, cursor e tudo mais normalmente como um jtextfield.
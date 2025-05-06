package TelaMetro1.Entrada;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Color;
import java.io.InputStream;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class ImagemPainel extends JPanel {
 private LoginPainel loginPainel; private JLabel logoLabel; private Image backgroundImage; private String imagePath;
 public ImagemPainel(String imagePath) {
this.imagePath = imagePath;
loadImage();
setLayout(null);
initComponents();
}

private void loadImage() {
try (InputStream is = getClass().getResourceAsStream(imagePath)) {
 if (is != null) {
backgroundImage = ImageIO.read(is);
 } else {
System.out.println("Imagem não encontrada: " + imagePath);
 }
} catch (IOException e) {
 e.printStackTrace();
System.out.println("Erro ao carregar a imagem");
}
}

private void initComponents() {
loginPainel = new LoginPainel();
loginPainel.setBounds(85, 170, 300, 320);
loginPainel.setLayout(null);
add(loginPainel);

ImageIcon logoIconOriginal = new ImageIcon(getClass().getResource("/Assets/Imagens/Logo2.png"));
 int largura = 300;
 int altura = 110;
 Image imagemRedimensionada = logoIconOriginal.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
 ImageIcon logoIconRedimensionado = new ImageIcon(imagemRedimensionada);
 logoLabel = new JLabel(logoIconRedimensionado);
 logoLabel.setBounds(85, 50, largura, altura);
 add(logoLabel);
}

@Override
protected void paintComponent(Graphics g) {
 super.paintComponent(g);
 Graphics2D g2d = (Graphics2D) g.create();
 g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

if (backgroundImage != null) {
 g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
 } else {
 g2d.setColor(Color.WHITE);
 g2d.fillRect(0, 0, getWidth(), getHeight());
 g2d.setColor(Color.RED);
 g2d.drawString("Erro ao carregar a imagem", 10, 20);
 }

 g2d.setColor(Color.WHITE);
 g2d.setFont(new Font("Arial", Font.PLAIN, 10));
 String versao = "Versão: 1.0";
 int x = 10;
 int y = getHeight() - 10;
g2d.drawString(versao, x, y);

g2d.dispose();
}
}
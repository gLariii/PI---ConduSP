package TelaMetro1.telaMenu;

import javax.swing.JFrame;

import TelaMetro1.Entrada.ImagemPainel;

public class menu {
    public static void main2(String[] args) {
        JFrame frame = new JFrame("Condução SP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        ImagemPainel backgroundPanel = new ImagemPainel("/Assets/Imagens/Fundo1.png"); // Mudar Imagem de fundo para desenho por aqui

        frame.getContentPane().add(backgroundPanel); 
        frame.setVisible(true);
    }
}

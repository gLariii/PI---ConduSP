package TelaMetro1.telaMenu;

import javax.swing.JFrame;

public class fundoMenu {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Condução SP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        menu painelMenu = new menu("/Assets/Imagens/TelaInicial4Corrigida.png");
        frame.setContentPane(painelMenu);
        frame.setVisible(true);
        painelMenu.revalidate();
        painelMenu.repaint();
    }
}
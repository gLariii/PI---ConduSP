package TelaMetro1.telaMenu;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class fundoMenu {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Condução SP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        Menu painelMenu = new Menu(frame, "/Assets/Imagens/TelaInicial4Corrigida.png", null, 0);
        frame.setContentPane(painelMenu);
        frame.setVisible(true);
        painelMenu.revalidate();
        painelMenu.repaint();
    }
}
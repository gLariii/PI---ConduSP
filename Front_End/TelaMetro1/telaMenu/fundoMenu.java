package TelaMetro1.telaMenu;

import javax.swing.JFrame;

public class fundoMenu {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Condução SP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        menu painelMenu = new menu("/Assets/Imagens/TelaInicial4Corrigida.png"); // Certifique-se de que o caminho da imagem está correto
        frame.getContentPane().add(painelMenu); // Adiciona o painel ao frame
        frame.setVisible(true); // Torna o frame visível
    }
}
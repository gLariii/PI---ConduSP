// Iniciador do Projeto - Caso queira verificar como está basta executar esse arquivo - Castilho
package TelaMetro1.Entrada;
import javax.swing.JFrame;

public class Inicial {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Condução SP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        ImagemPainel backgroundPanel = new ImagemPainel("/Assets/Imagens/TelaInicial4Corrigida.png"); 

        frame.getContentPane().add(backgroundPanel); 
        frame.setVisible(true);
    }}
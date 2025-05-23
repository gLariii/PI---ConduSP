package Back_End;
import Controller.LoginController;
//teste de login ( autenticar o usuario )
public class Main {
    public static void main(String[] args) {
        // Programar no Main
        /*TelaLogin tela = new TelaLogin();
        setVisible(true);*/

        // Daqui para baixo na Interface
        LoginController controller = new LoginController();
        // String rg = rgTextField.getText();
        // String password = new String(passwordTextField.getPassword());
        boolean resultado = controller.login(rg,password);

        System.out.println(resultado ? "Login bem-sucedido!" : "Falha no login.");
    }
}

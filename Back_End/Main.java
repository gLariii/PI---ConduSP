package Back_End;
import Controller.LoginController;
//teste de login ( autenticar o usuario )
public class Main {
    public static void main(String[] args) {
        LoginController controller = new LoginController();
        boolean resultado = controller.login("joao","1234");

        System.out.println(resultado ? "Login bem-sucedido!" : "Falha no login.");
    }
}

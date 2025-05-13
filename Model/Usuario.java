//package backend.Model;
package Model;
//classe usuario para o login ( login e senha)
public class Usuario {
    private String Login;
    private String Senha;

    public Usuario(String login, String senha) {
        this.Login = login;
        this.Senha = senha;
    }

    public void setLogin(String login) {
        this.Login = login;
    }

    public void setsenha(String senha) {
        this.Senha = senha;
    }

    public String getLogin() {
        return Login;
    }

    public String getSenha() {
        return Senha;
    }


}
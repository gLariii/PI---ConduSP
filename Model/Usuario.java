//package backend.Model;
package Model;
//classe usuario para o login ( Rg e senha)
public class Usuario {
    private String Rg; // rg
    private String Senha; // senha

    public Usuario(String rg, String senha) {
        this.Rg = rg;
        this.Senha = senha;
    }

    public void setLogin(String rg) {
        this.Rg = rg;
    }

    public void setsenha(String senha) {
        this.Senha = senha;
    }

    public String getLogin() {
        return Rg;
    }

    public String getSenha() {
        return Senha;
    }


}
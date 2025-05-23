package Model;

public class Usuario {
    private int id;
    private String rg;
    private String senha;
    private String nome;
    private String tipoUsuario;

    public Usuario(String rg, String senha) {
        this.rg = rg;
        this.senha = senha;
    }

    public Usuario(int id, String rg, String senha, String nome, String tipoUsuario) {
        this.id = id;
        this.rg = rg;
        this.senha = senha;
        this.nome = nome;
        this.tipoUsuario = tipoUsuario;
    }

    public int getId() {
        return id;
    }

    public String getRg() {
        return rg;
    }

    public String getSenha() {
        return senha;
    }

    public String getNome() {
        return nome;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}

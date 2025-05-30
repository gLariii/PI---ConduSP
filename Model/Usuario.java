package Model;

public class Usuario {
    private int id;
    private String rg;
    private String senha;
    private String nome;
    private String tipo_usuario;
    private int volume;

    public Usuario(String rg, String senha) {
        this.rg = rg;
        this.senha = senha;
    }

    public Usuario(String rg, String nome, String tipo_usuario) {
        this.rg = rg;
        this.nome = nome;
        this.tipo_usuario = tipo_usuario;
    }

    public Usuario(int id, String rg, String senha, String nome, String tipo_usuario, int volume) {
        this.id = id;
        this.rg = rg;
        this.senha = senha;
        this.nome = nome;
        this.tipo_usuario = tipo_usuario;
        this.volume = volume;
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

    public String gettipo_usuario() {
        return tipo_usuario;
    }

    public int getVolume() {
        return volume;
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

    public void settipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
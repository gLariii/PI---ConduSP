package Model;

import java.sql.Date;

public class FeedbackGeral {
    private int id;
    private String registro;
    private String nome;
    private int pontuacao;
    private String observacoes;
    private Date data;

    public FeedbackGeral(int id, String registro, String nome, int pontuacao, String observacoes, Date data) {
        this.id = id;
        this.registro = registro;
        this.nome = nome;
        this.pontuacao = pontuacao;
        this.observacoes = observacoes;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public String getRegistro() {
        return registro;
    }

    public String getNome() {
        return nome;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public Date getData() {
        return data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public void setData(Date data) {
        this.data = data;
    }
}

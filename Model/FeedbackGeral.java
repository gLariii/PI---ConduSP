package Model;

import java.sql.Date;

public class FeedbackGeral {
    private int id;
    private String rg;
    private String nome;
    private int pontuacao;
    private String observacoes;
    private Date data;

    public FeedbackGeral(int id, String rg, String nome, int pontuacao, String observacoes, Date data) {
        this.id = id;
        this.rg = rg;
        this.nome = nome;
        this.pontuacao = pontuacao;
        this.observacoes = observacoes;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public String getRg() {
        return rg;
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

    public void setRg(String rg) {
        this.rg = rg;
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

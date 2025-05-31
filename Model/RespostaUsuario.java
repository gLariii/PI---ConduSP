package Model;

import java.sql.Timestamp;

public class RespostaUsuario {
    private int idResposta;
    private int idUsuario;
    private int idFeedback;
    private int pontuacaoAtual;
    private Timestamp data;

    public RespostaUsuario(int idResposta, int idUsuario, int idFeedback, int pontuacaoAtual, Timestamp data) {
        this.idResposta = idResposta;
        this.idUsuario = idUsuario;
        this.idFeedback = idFeedback;
        this.pontuacaoAtual = pontuacaoAtual;
        this.data = data;
    }

    public RespostaUsuario() {
    }

    public int getIdResposta() {
        return idResposta;
    }

    public void setIdResposta(int idResposta) {
        this.idResposta = idResposta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(int idFeedback) {
        this.idFeedback = idFeedback;
    }

    public int getPontuacaoAtual() {
        return pontuacaoAtual;
    }

    public void setPontuacaoAtual(int pontuacaoAtual) {
        this.pontuacaoAtual = pontuacaoAtual;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }
}
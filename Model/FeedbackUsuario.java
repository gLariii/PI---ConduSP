package Model;

import java.sql.Timestamp;

public class FeedbackUsuario {
    private int idFeedback;
    private int idUsuario;
    private int pontuacao;
    private String observacoes;
    private Timestamp data;

    
    // Getter e Setter para idFeedback
    public int getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(int idFeedback) {
        this.idFeedback = idFeedback;
    }

    // Getter e Setter para idUsuario
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    // Getter e Setter para pontuacao
    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    // Getter e Setter para observacoes
    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    // Getter e Setter para data
    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FeedbackUsuario{" +
                "idFeedback=" + idFeedback +
                ", idUsuario=" + idUsuario +
                ", pontuacao=" + pontuacao +
                ", observacoes='" + observacoes + '\'' +
                ", data=" + data +
                '}';
}
}

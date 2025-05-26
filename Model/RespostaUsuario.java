package Model;

import java.sql.Timestamp;

public class RespostaUsuario {
    private int idResposta;
    private int idQuestao;
    private int idUsuario;
    private String acaoExecutada;
    private int ordemUsuario;
    private String tipoResposta; // 'correta', 'aceitavel', 'errada'
    private int pontuacaoRecebida;
    private Timestamp dataHora;

    // Construtor completo
    public RespostaUsuario(int idResposta, int idQuestao, int idUsuario, String acaoExecutada, int ordemUsuario, String tipoResposta, int pontuacaoRecebida, Timestamp dataHora) {
        this.idResposta = idResposta;
        this.idQuestao = idQuestao;
        this.idUsuario = idUsuario;
        this.acaoExecutada = acaoExecutada;
        this.ordemUsuario = ordemUsuario;
        this.tipoResposta = tipoResposta;
        this.pontuacaoRecebida = pontuacaoRecebida;
        this.dataHora = dataHora;
    }

    // Construtor para inserção (sem idResposta e dataHora)
    public RespostaUsuario(int idQuestao, int idUsuario, String acaoExecutada, int ordemUsuario, String tipoResposta, int pontuacaoRecebida) {
        this.idQuestao = idQuestao;
        this.idUsuario = idUsuario;
        this.acaoExecutada = acaoExecutada;
        this.ordemUsuario = ordemUsuario;
        this.tipoResposta = tipoResposta;
        this.pontuacaoRecebida = pontuacaoRecebida;
        this.dataHora = new Timestamp(System.currentTimeMillis()); // Seta a data/hora atual
    }

    // Getters e Setters
    public int getIdResposta() {
        return idResposta;
    }

    public void setIdResposta(int idResposta) {
        this.idResposta = idResposta;
    }

    public int getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(int idQuestao) {
        this.idQuestao = idQuestao;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getAcaoExecutada() {
        return acaoExecutada;
    }

    public void setAcaoExecutada(String acaoExecutada) {
        this.acaoExecutada = acaoExecutada;
    }

    public int getOrdemUsuario() {
        return ordemUsuario;
    }

    public void setOrdemUsuario(int ordemUsuario) {
        this.ordemUsuario = ordemUsuario;
    }

    public String getTipoResposta() {
        return tipoResposta;
    }

    public void setTipoResposta(String tipoResposta) {
        this.tipoResposta = tipoResposta;
    }

    public int getPontuacaoRecebida() {
        return pontuacaoRecebida;
    }

    public void setPontuacaoRecebida(int pontuacaoRecebida) {
        this.pontuacaoRecebida = pontuacaoRecebida;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }
}
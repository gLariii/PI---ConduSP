package Model;

import java.sql.Date;

//Atributos da classe feedbackGeral

public class FeedbackGeral {
    private int idUsuario;
    private String nomeUsuario;
    private String rg;
    private String tipoUsuario;

    private int idResposta;
    private int pontuacaoAtual;
    private Date dataResposta;
    private int idFeedback;
    private int idPasso;

    private String observacoes;
    private String tipoFeedbackEsperado;
    private Date dataFeedbackDefinido;
    
    private int idFase;
    private String nomeFase;
    private String descricaoFase;

//contrutor para criar os objetos do feedbackGeral

    public FeedbackGeral(int idUsuario, String nomeUsuario, String rg, String tipoUsuario,
                               int idResposta, int pontuacaoAtual, Date dataResposta,
                               int idFeedback, int idPasso, String observacoes,
                               String tipoFeedbackEsperado, Date dataFeedbackDefinido,
                               int idFase, String nomeFase, String descricaoFase) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.rg = rg;
        this.tipoUsuario = tipoUsuario;
        this.idResposta = idResposta;
        this.pontuacaoAtual = pontuacaoAtual;
        this.dataResposta = dataResposta;
        this.idFeedback = idFeedback;
        this.idPasso = idPasso;
        this.observacoes = observacoes;
        this.tipoFeedbackEsperado = tipoFeedbackEsperado;
        this.dataFeedbackDefinido = dataFeedbackDefinido;
        this.idFase = idFase;
        this.nomeFase = nomeFase;
        this.descricaoFase = descricaoFase;
    }

    // getters e setters(para puxar os dados do banco de dados e atualiza-los)

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getRg() {
        return rg;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public int getIdResposta() {
        return idResposta;
    }

    public int getPontuacaoAtual() {
        return pontuacaoAtual;
    }

    public Date getDataResposta() {
        return dataResposta;
    }

    public int getIdFeedback() {
        return idFeedback;
    }

    public int getIdPasso() {
        return idPasso;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public String getTipoFeedbackEsperado() {
        return tipoFeedbackEsperado;
    }

    public Date getDataFeedbackDefinido() {
        return dataFeedbackDefinido;
    }

    public int getIdFase() {
        return idFase;
    }

    public String getNomeFase() {
        return nomeFase;
    }

    public String getDescricaoFase() {
        return descricaoFase;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setIdResposta(int idResposta) {
        this.idResposta = idResposta;
    }

    public void setPontuacaoAtual(int pontuacaoAtual) {
        this.pontuacaoAtual = pontuacaoAtual;
    }

    public void setDataResposta(Date dataResposta) {
        this.dataResposta = dataResposta;
    }

    public void setIdFeedback(int idFeedback) {
        this.idFeedback = idFeedback;
    }

    public void setIdPasso(int idPasso) {
        this.idPasso = idPasso;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public void setTipoFeedbackEsperado(String tipoFeedbackEsperado) {
        this.tipoFeedbackEsperado = tipoFeedbackEsperado;
    }

    public void setDataFeedbackDefinido(Date dataFeedbackDefinido) {
        this.dataFeedbackDefinido = dataFeedbackDefinido;
    }

    public void setIdFase(int idFase) {
        this.idFase = idFase;
    }

    public void setNomeFase(String nomeFase) {
        this.nomeFase = nomeFase;
    }

    public void setDescricaoFase(String descricaoFase) {
        this.descricaoFase = descricaoFase;
    }
}
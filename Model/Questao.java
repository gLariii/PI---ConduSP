package Model;

public class Questao {
    private int idQuestao;
    private int idFase;
    private String descricao;
    private String feedbackErro;

    // Construtor completo
    public Questao(int idQuestao, int idFase, String descricao, String feedbackErro) {
        this.idQuestao = idQuestao;
        this.idFase = idFase;
        this.descricao = descricao;
        this.feedbackErro = feedbackErro;
    }

    // Getters e Setters
    public int getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(int idQuestao) {
        this.idQuestao = idQuestao;
    }

    public int getIdFase() {
        return idFase;
    }

    public void setIdFase(int idFase) {
        this.idFase = idFase;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFeedbackErro() {
        return feedbackErro;
    }

    public void setFeedbackErro(String feedbackErro) {
        this.feedbackErro = feedbackErro;
    }
}
package Model;

public class RespostaPossivelQuestao {
    private int idRespostaPossivel;
    private int idQuestao;
    private int ordem;
    private String acao;
    private String tipoResposta; // 'correta', 'aceitavel', 'errada'
    private int pontuacao;

    // Construtor completo
    public RespostaPossivelQuestao(int idRespostaPossivel, int idQuestao, int ordem, String acao, String tipoResposta, int pontuacao) {
        this.idRespostaPossivel = idRespostaPossivel;
        this.idQuestao = idQuestao;
        this.ordem = ordem;
        this.acao = acao;
        this.tipoResposta = tipoResposta;
        this.pontuacao = pontuacao;
    }

    // Getters e Setters
    public int getIdRespostaPossivel() {
        return idRespostaPossivel;
    }

    public void setIdRespostaPossivel(int idRespostaPossivel) {
        this.idRespostaPossivel = idRespostaPossivel;
    }

    public int getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(int idQuestao) {
        this.idQuestao = idQuestao;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getTipoResposta() {
        return tipoResposta;
    }

    public void setTipoResposta(String tipoResposta) {
        this.tipoResposta = tipoResposta;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }
}
package TelaMetro1.telaMenu; // Garanta que este é o pacote correto!

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Estatistica { // Nome da classe DEVE ser Estatistica
    private int acertos;
    private int erros;
    private int pontuacao;
    private String tempoDecorrido;
    private LocalDateTime dataHora;

    // Formatter para exibir a data e hora
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // Construtor
    public Estatistica(int acertos, int erros, int pontuacao, String tempoDecorrido, LocalDateTime dataHora) {
        this.acertos = acertos;
        this.erros = erros;
        this.pontuacao = pontuacao;
        this.tempoDecorrido = tempoDecorrido;
        this.dataHora = dataHora;
    }

    // Métodos Getters
    public int getAcertos() {
        return acertos;
    }

    public int getErros() {
        return erros;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public String getTempoDecorrido() {
        return tempoDecorrido;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getDataHoraFormatada() {
        return dataHora.format(FORMATTER);
    }
}
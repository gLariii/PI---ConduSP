package Controller;

import DAO.FeedbackUsuarioDAO;
import DAO.QuestaoDAO;
import DAO.RespostaUsuarioDAO;
import Model.Questao;
import Model.RespostaPossivelQuestao;
import Model.RespostaUsuario;
import Model.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController {

    private QuestaoDAO questaoDAO = new QuestaoDAO();
    private RespostaUsuarioDAO respostaUsuarioDAO = new RespostaUsuarioDAO();
    private FeedbackUsuarioDAO feedbackUsuarioDAO = new FeedbackUsuarioDAO();

    // Estado do jogo (por sessão de usuário, para suportar múltiplos jogadores)
    private Map<Integer, Integer> pontuacaoAtualPorUsuario = new HashMap<>();
    private Map<Integer, Integer> etapaAtualPorUsuario = new HashMap<>(); // Qual etapa do roteiro o usuário está
    private Map<Integer, Boolean> chaveReversoraFrentePorUsuario = new HashMap<>();
    private Map<Integer, Boolean> chaveCbtcRmPorUsuario = new HashMap<>();
    private Map<Integer, Boolean> chaveServicoColetadaPorUsuario = new HashMap<>();
    private Map<Integer, Boolean> paJaEmitidoPorUsuario = new HashMap<>(); // Para a regra de pontuação do PA

    // Construtor
    public GameController() {
        // Inicializações, se necessário
    }

    /**
     * Inicia uma nova sessão de jogo para um usuário.
     * @param idUsuario O ID do usuário logado.
     */
    public void iniciarNovoJogo(int idUsuario) {
        pontuacaoAtualPorUsuario.put(idUsuario, 0);
        etapaAtualPorUsuario.put(idUsuario, 1); // Começa na etapa 1 do roteiro
        chaveReversoraFrentePorUsuario.put(idUsuario, false);
        chaveCbtcRmPorUsuario.put(idUsuario, false);
        chaveServicoColetadaPorUsuario.put(idUsuario, false);
        paJaEmitidoPorUsuario.put(idUsuario, false);
        System.out.println("Jogo iniciado para o usuário: " + idUsuario);
    }

    /**
     * Processa uma ação do jogador e atualiza o estado do jogo e a pontuação.
     * @param idUsuario O ID do usuário logado.
     * @param acaoExecutada A string que descreve a ação do jogador (ex: "passar_chave_reversora_frente").
     * @param idQuestaoAtual O ID da questão atual que o front-end está exibindo.
     * @return Um objeto `GameResult` contendo o feedback, pontuação e se o jogo terminou.
     */
    public GameResult processarAcao(int idUsuario, String acaoExecutada, int idQuestaoAtual) {
        int pontuacaoGanha = 0;
        String feedbackParaArmazenamento = "Ação: " + acaoExecutada + " | "; // Mensagem detalhada para o BD
        boolean gameOver = false;
        String tipoResposta = "errada"; // Padrão, será atualizado se a ação for correta/aceitável

        // Recuperar o estado atual do usuário
        int pontuacaoAtual = pontuacaoAtualPorUsuario.getOrDefault(idUsuario, 0);
        int etapaAtual = etapaAtualPorUsuario.getOrDefault(idUsuario, 1);

        // --- Lógica de validação e pontuação baseada no roteiro ---

        // Recuperar a questão e suas respostas possíveis
        Questao questao = questaoDAO.getQuestaoById(idQuestaoAtual);
        List<RespostaPossivelQuestao> respostasPossiveis = questaoDAO.getRespostasPossiveisByQuestao(idQuestaoAtual);

        boolean acaoValidaParaQuestao = false;
        for (RespostaPossivelQuestao rp : respostasPossiveis) {
            if (rp.getAcao().equalsIgnoreCase(acaoExecutada)) {
                pontuacaoGanha = rp.getPontuacao();
                tipoResposta = rp.getTipoResposta();
                acaoValidaParaQuestao = true;
                feedbackParaArmazenamento += "Tipo: " + tipoResposta + " | Pontos ganhos: " + pontuacaoGanha + " | ";
                break;
            }
        }

        // Se a ação não for uma resposta possível para a questão atual, ainda podemos ter lógica global
        if (!acaoValidaParaQuestao) {
            pontuacaoGanha = -1; // Penalidade genérica para ação inválida se não coberta por uma regra específica
            feedbackParaArmazenamento += "Ação inesperada ou incorreta. | ";
            tipoResposta = "errada";
        }


        // Regras específicas do roteiro (passo a passo)
        switch (etapaAtual) {
            case 6: // O operador deve pegar o comando e para isso deve passar a chave reversora para frente
                if (acaoExecutada.equals("passar_chave_reversora_frente")) {
                    chaveReversoraFrentePorUsuario.put(idUsuario, true);
                    feedbackParaArmazenamento += "Chave reversora em frente. | ";
                    // Pontuação será dada na etapa 7 (em conjunto com a chave CBTC)
                    tipoResposta = "aceitavel";
                } else if (acaoExecutada.equals("passar_chave_reversora_re")) {
                    pontuacaoGanha = -3;
                    feedbackParaArmazenamento += "Chave reversora em ré. -3 pontos. | ";
                    gameOver = true; // Exemplo de Game Over aqui, se for crítico
                    tipoResposta = "errada";
                } else if (acaoExecutada.equals("passar_chave_cbtc_rm")) { // Inverter a ordem, pontua -1 na próxima etapa
                    chaveCbtcRmPorUsuario.put(idUsuario, true);
                    feedbackParaArmazenamento += "Chave CBTC para RM. Aguardando chave reversora. | ";
                    tipoResposta = "aceitavel";
                }
                break;
            case 7: // Mudar a chave CBTC para RM
                if (acaoExecutada.equals("passar_chave_cbtc_rm")) {
                    chaveCbtcRmPorUsuario.put(idUsuario, true);
                    if (chaveReversoraFrentePorUsuario.get(idUsuario)) { // Ordem correta
                        pontuacaoGanha = 1;
                        feedbackParaArmazenamento += "Comando assumido: +1 ponto. | ";
                        etapaAtualPorUsuario.put(idUsuario, 8); // Avança para a próxima etapa
                    } else { // Ordem invertida
                        pontuacaoGanha = -1; // -1 ponto como penalidade por inversão de ordem
                        feedbackParaArmazenamento += "Ordem das chaves invertida. -1 ponto. | ";
                        // Não avança a etapa se a ordem for crítica ou espera-se a reversora primeiro
                    }
                    tipoResposta = "correta";
                } else if (acaoExecutada.equals("passar_chave_reversora_frente")) { // Se chegou aqui e ainda não pegou a reversora
                    chaveReversoraFrentePorUsuario.put(idUsuario, true);
                    if (chaveCbtcRmPorUsuario.get(idUsuario)) { // Se o CBTC já foi pra RM
                        pontuacaoGanha = -1; // Penalidade por ordem invertida
                        feedbackParaArmazenamento += "Ordem das chaves invertida. -1 ponto. | ";
                        etapaAtualPorUsuario.put(idUsuario, 8); // Considera avançado para o próximo passo se ambas estão no lugar
                    } else {
                        feedbackParaArmazenamento += "Chave reversora em frente. Mude a chave CBTC para RM. | ";
                    }
                    tipoResposta = "aceitavel";
                }
                break;
            case 8: // Emitir um PA
                if (acaoExecutada.equals("emitir_pa")) {
                    if (!paJaEmitidoPorUsuario.get(idUsuario)) { // Pontua apenas na primeira vez
                        pontuacaoGanha = 1;
                        feedbackParaArmazenamento += "PA emitido: +1 ponto. | ";
                        paJaEmitidoPorUsuario.put(idUsuario, true);
                        pontuacaoAtualPorUsuario.put(idUsuario, pontuacaoAtual + pontuacaoGanha); // Atualiza antes do game over
                    } else {
                        feedbackParaArmazenamento += "PA já emitido. | ";
                    }
                    etapaAtualPorUsuario.put(idUsuario, 9);
                    tipoResposta = "correta";
                } else if (acaoExecutada.equals("abrir_portas_botoeira_direita") && chaveReversoraFrentePorUsuario.get(idUsuario)) {
                    gameOver = true;
                    feedbackParaArmazenamento += "Fim de jogo por APLOP! | ";
                    pontuacaoGanha = 0; // Zera pontos se for game over
                    pontuacaoAtualPorUsuario.put(idUsuario, 0); // Zera pontuação da falha
                    tipoResposta = "errada";
                }
                break;
            case 9: // Fechar portas pela botoeira lateral esquerda
                if (acaoExecutada.equals("fechar_portas_botoeira_esquerda")) {
                    if (chaveReversoraFrentePorUsuario.get(idUsuario) && chaveCbtcRmPorUsuario.get(idUsuario)) {
                        pontuacaoGanha = 2;
                        feedbackParaArmazenamento += "Portas fechadas pela botoeira lateral esquerda: +2 pontos. | ";
                        etapaAtualPorUsuario.put(idUsuario, 10);
                        tipoResposta = "correta";
                    } else {
                        feedbackParaArmazenamento += "Comando não assumido. Botoeiras não funcionarão. | ";
                        pontuacaoGanha = 0; // Não pontua se não pegou comando
                        tipoResposta = "errada";
                    }
                } else if (acaoExecutada.equals("abrir_portas_botoeira_direita") && chaveReversoraFrentePorUsuario.get(idUsuario)) {
                    gameOver = true;
                    feedbackParaArmazenamento += "Fim de jogo por APLOP! | ";
                    pontuacaoGanha = 0;
                    pontuacaoAtualPorUsuario.put(idUsuario, 0);
                    tipoResposta = "errada";
                }
                break;
            case 11: // Operador informa ao CCO
                if (acaoExecutada.equals("informar_cco")) {
                    pontuacaoGanha = 1;
                    feedbackParaArmazenamento += "CCO informado: +1 ponto. | ";
                    etapaAtualPorUsuario.put(idUsuario, 12);
                    tipoResposta = "correta";
                }
                break;
            case 12: // Operador emite mais um PA
                if (acaoExecutada.equals("emitir_pa_pos_falha")) { // Ação específica para o segundo PA
                    pontuacaoGanha = 1;
                    feedbackParaArmazenamento += "PA emitido novamente: +1 ponto. | ";
                    etapaAtualPorUsuario.put(idUsuario, 13);
                    tipoResposta = "correta";
                }
                break;
            case 13: // Operador deve pegar Cinturão, adesivo de portas e chave de serviço
                if (acaoExecutada.equals("pegar_cinturao_adesivo")) { // Supondo que sejam a mesma ação no jogo
                    pontuacaoGanha = 2;
                    feedbackParaArmazenamento += "Cinturão e adesivo pegos: +2 pontos. | ";
                    // Não avança etapa, espera a chave de serviço
                    tipoResposta = "correta";
                } else if (acaoExecutada.equals("pegar_chave_servico")) {
                    chaveServicoColetadaPorUsuario.put(idUsuario, true);
                    pontuacaoGanha = 2;
                    feedbackParaArmazenamento += "Chave de serviço pega: +2 pontos. | ";
                    etapaAtualPorUsuario.put(idUsuario, 14); // Avança após pegar a chave
                    tipoResposta = "correta";
                } else if (acaoExecutada.equals("sair_cabine_sem_chave_servico")) { // Exemplo de ação que causa game over
                    gameOver = true;
                    feedbackParaArmazenamento += "Fim de jogo! Saiu da cabine sem a chave de serviço. | ";
                    pontuacaoGanha = 0;
                    pontuacaoAtualPorUsuario.put(idUsuario, 0);
                    tipoResposta = "errada";
                }
                break;
            case 14: // Operador vai até a porta da Cabine e escolhe o carro que irá verificar (carro 5)
                if (acaoExecutada.equals("ir_para_carro_5")) {
                    if (chaveServicoColetadaPorUsuario.get(idUsuario)) {
                        pontuacaoGanha = 1;
                        feedbackParaArmazenamento += "No carro 5: +1 ponto. | ";
                        etapaAtualPorUsuario.put(idUsuario, 15); // Roteiro pula do 14 para o 15 (sinalização externa)
                        tipoResposta = "correta";
                    } else {
                        gameOver = true;
                        feedbackParaArmazenamento += "Fim de jogo! Saiu da cabine sem a chave de serviço. | ";
                        pontuacaoGanha = 0;
                        pontuacaoAtualPorUsuario.put(idUsuario, 0);
                        tipoResposta = "errada";
                    }
                } else if (acaoExecutada.startsWith("ir_para_carro_") && !acaoExecutada.equals("ir_para_carro_5")) {
                    // Penalidade por ir no carro errado, mas não Game Over
                    feedbackParaArmazenamento += "Carro errado. Verifique novamente. | ";
                    pontuacaoGanha = -1; // Ou outra penalidade apropriada
                    tipoResposta = "errada";
                }
                break;
            case 15: // Sinalização externa de portas (verificar se está acesa) - Implícito no roteiro, mas importante
                if (acaoExecutada.equals("verificar_sinalizacao_externa_portas_acesa")) { // Nova ação para verificar
                    // Não há pontuação direta para "verificar", mas é um passo
                    feedbackParaArmazenamento += "Sinalização externa de portas acesa verificada. | ";
                    etapaAtualPorUsuario.put(idUsuario, 16);
                    tipoResposta = "aceitavel";
                }
                break;
            case 16: // O operador deve olhar a porta de perto e procurar objetos obstruindo
                if (acaoExecutada.equals("olhar_soleira_porta")) {
                    pontuacaoGanha = 1;
                    feedbackParaArmazenamento += "Soleira da porta verificada: +1 ponto. | ";
                    etapaAtualPorUsuario.put(idUsuario, 17);
                    tipoResposta = "correta";
                }
                break;
            case 17: // Verificar dispositivos de emergência acionados dentro do carro
                if (acaoExecutada.equals("verificar_dispositivos_emergencia")) {
                    pontuacaoGanha = 1;
                    feedbackParaArmazenamento += "Dispositivos de emergência verificados: +1 ponto. | ";
                    etapaAtualPorUsuario.put(idUsuario, 18); // Roteiro pula para o painel externo
                    tipoResposta = "correta";
                }
                break;
            case 18: // Ir para o painel externo (ação implícita antes de isolar)
                if (acaoExecutada.equals("ir_para_painel_externo")) { // Nova ação para transição
                    feedbackParaArmazenamento += "Foi para o painel externo. | ";
                    etapaAtualPorUsuario.put(idUsuario, 19);
                    tipoResposta = "aceitavel";
                }
                break;
            case 19: // Isolar a porta correta no painel externo
                if (acaoExecutada.equals("isolar_porta_correta")) {
                    pontuacaoGanha = 3;
                    feedbackParaArmazenamento += "Porta correta isolada: +3 pontos. | ";
                    // Não avança etapa, espera fechar o painel
                    tipoResposta = "correta";
                } else if (acaoExecutada.equals("isolar_porta_errada")) {
                    pontuacaoGanha = -3;
                    feedbackParaArmazenamento += "Porta errada isolada: -3 pontos. | ";
                    tipoResposta = "errada";
                } else if (acaoExecutada.startsWith("acionar_chave_errada_painel")) {
                    pontuacaoGanha = -3;
                    feedbackParaArmazenamento += "Chave errada acionada no painel: -3 pontos. | ";
                    tipoResposta = "errada";
                }
                break;
            case 20: // Fechar o painel externo (após isolar)
                if (acaoExecutada.equals("fechar_painel_externo")) {
                    pontuacaoGanha = 1;
                    feedbackParaArmazenamento += "Painel externo fechado: +1 ponto. | ";
                    etapaAtualPorUsuario.put(idUsuario, 21); // Avança para fechar a porta manualmente
                    tipoResposta = "correta";
                } else if (acaoExecutada.equals("esquecer_painel_aberto")) { // Ação de penalidade
                    pontuacaoGanha = -3;
                    feedbackParaArmazenamento += "Painel externo esquecido aberto: -3 pontos. | ";
                    tipoResposta = "errada";
                }
                break;
            case 21: // Fechar a porta manualmente e confirmar o travamento
                if (acaoExecutada.equals("fechar_porta_manualmente")) {
                    pontuacaoGanha = 2;
                    feedbackParaArmazenamento += "Porta fechada manualmente: +2 pontos. | ";
                    // Não avança ainda, espera a confirmação
                    tipoResposta = "correta";
                } else if (acaoExecutada.equals("confirmar_fechamento_porta")) {
                    pontuacaoGanha = 2;
                    feedbackParaArmazenamento += "Fechamento da porta confirmado: +2 pontos. | ";
                    etapaAtualPorUsuario.put(idUsuario, 22);
                    tipoResposta = "correta";
                }
                break;
            case 22: // Colocar adesivo na porta
                if (acaoExecutada.equals("colocar_adesivo")) {
                    pontuacaoGanha = 2;
                    feedbackParaArmazenamento += "Adesivo colocado: +2 pontos. | ";
                    etapaAtualPorUsuario.put(idUsuario, 23);
                    tipoResposta = "correta";
                }
                break;
            case 23: // Voltar para a cabine
                if (acaoExecutada.equals("voltar_para_cabine")) {
                    feedbackParaArmazenamento += "Retornou para a cabine. | ";
                    etapaAtualPorUsuario.put(idUsuario, 24);
                    tipoResposta = "aceitavel";
                }
                break;
            case 24: // Verificar sinalizações na cabine
                if (acaoExecutada.equals("verificar_sinalizacoes_cabine")) {
                    pontuacaoGanha = 2;
                    feedbackParaArmazenamento += "Sinalizações da cabine verificadas: +2 pontos. | ";
                    etapaAtualPorUsuario.put(idUsuario, 25);
                    tipoResposta = "correta";
                }
                break;
            case 25: // Informar o CCO (segunda vez)
                if (acaoExecutada.equals("informar_cco_pos_falha")) {
                    pontuacaoGanha = 2;
                    feedbackParaArmazenamento += "CCO informado novamente: +2 pontos. | ";
                    etapaAtualPorUsuario.put(idUsuario, 26);
                    tipoResposta = "correta";
                }
                break;
            case 26: // Emitir PA (terceiro PA)
                if (acaoExecutada.equals("emitir_pa_pos_falha")) { // Reutiliza a ação, controller diferencia pelo contexto
                    pontuacaoGanha = 1;
                    feedbackParaArmazenamento += "PA emitido pós-falha: +1 ponto. | ";
                    etapaAtualPorUsuario.put(idUsuario, 27);
                    tipoResposta = "correta";
                }
                break;
            case 27: // Inserir a chave de serviço
                if (acaoExecutada.equals("inserir_chave_servico")) {
                    pontuacaoGanha = 1;
                    feedbackParaArmazenamento += "Chave de serviço inserida: +1 ponto. | ";
                    etapaAtualPorUsuario.put(idUsuario, 28);
                    tipoResposta = "correta";
                } else if (acaoExecutada.equals("inserir_chave_servico_errado")) {
                    pontuacaoGanha = -1;
                    feedbackParaArmazenamento += "Chave de serviço inserida no local errado: -1 ponto. Trem não seguirá viagem. | ";
                    tipoResposta = "errada";
                }
                break;
            case 28: // Mudar a chave CBTC para AM
                if (acaoExecutada.equals("mudar_cbtc_am")) {
                    feedbackParaArmazenamento += "Chave CBTC para AM. | ";
                    tipoResposta = "aceitavel";
                } else if (acaoExecutada.equals("colocar_reversora_neutro")) { // Ordem invertida
                    pontuacaoGanha = -1;
                    feedbackParaArmazenamento += "Ordem invertida (CBTC AM antes de Reversora Neutro): -1 ponto. Trem não seguirá viagem. | ";
                    tipoResposta = "errada";
                }
                break;
            case 29: // Colocar a chave reversora em neutro
                if (acaoExecutada.equals("colocar_reversora_neutro")) {
                    // Verifica se a etapa anterior (CBTC AM) foi concluída
                    if (etapaAtualPorUsuario.get(idUsuario) >= 28) { // Se CBTC já está em AM ou foi feita antes
                        pontuacaoGanha = 1;
                        feedbackParaArmazenamento += "Trem pronto para seguir viagem: +1 ponto. | ";
                        etapaAtualPorUsuario.put(idUsuario, 30); // Fim do jogo
                        tipoResposta = "correta";
                    } else { // Ordem invertida ou CBTC não em AM
                        pontuacaoGanha = -1;
                        feedbackParaArmazenamento += "Ordem invertida ou CBTC não está em AM: -1 ponto. Trem não seguirá viagem. | ";
                        tipoResposta = "errada";
                    }
                }
                break;
            case 30: // Seguir Viagem (fim do jogo)
                if (acaoExecutada.equals("seguir_viagem")) {
                    feedbackParaArmazenamento += "Trem pronto para seguir viagem. Fim da simulação. | ";
                    tipoResposta = "correta";
                    gameOver = true; // Marca o fim do jogo
                }
                break;
            default:
                // Caso a etapa não seja reconhecida ou não haja lógica específica
                feedbackParaArmazenamento += "Nenhuma lógica específica para esta etapa e ação. | ";
                break;
        }

        // Atualiza a pontuação total do usuário
        if (!gameOver) { // Só adiciona pontos se não for game over
            pontuacaoAtualPorUsuario.put(idUsuario, pontuacaoAtualPorUsuario.get(idUsuario) + pontuacaoGanha);
        }

        // Registrar a ação do usuário no banco de dados
        RespostaUsuario respostaUsuario = new RespostaUsuario(
                idQuestaoAtual,
                idUsuario,
                acaoExecutada,
                etapaAtual,
                tipoResposta,
                pontuacaoGanha
        );
        respostaUsuarioDAO.inserirRespostaUsuario(respostaUsuario);

        // Se o jogo terminar (game over ou fase concluída), gere o feedback final para o banco
        if (gameOver || etapaAtualPorUsuario.get(idUsuario) == 30) {
            int pontuacaoFinal = pontuacaoAtualPorUsuario.get(idUsuario);
            String resumoRespostas = "Simulação concluída para o usuário " + idUsuario + ". Pontuação final: " + pontuacaoFinal + ". Detalhes: " + feedbackParaArmazenamento;
            if (gameOver) {
                resumoRespostas += " - Game Over: " + feedbackParaArmazenamento; // Adiciona a mensagem de game over ao resumo
            }
            feedbackUsuarioDAO.gerarFeedback(idUsuario, pontuacaoFinal, resumoRespostas);
        }

        // Retorna o GameResult SEM a mensagem de feedback para exibição
        return new GameResult(pontuacaoGanha, pontuacaoAtualPorUsuario.get(idUsuario), gameOver, tipoResposta);
    }

    /**
     * Classe interna para encapsular o resultado de uma ação do jogo.
     * REMOVIDO: feedbackMensagem para não ser exibido no front-end.
     */
    public static class GameResult {
        private int pontosGanhosNestaAcao;
        private int pontuacaoTotalAtual;
        private boolean gameOver;
        private String tipoResposta; // 'correta', 'aceitavel', 'errada'

        // Construtor ajustado
        public GameResult(int pontosGanhosNestaAcao, int pontuacaoTotalAtual, boolean gameOver, String tipoResposta) {
            this.pontosGanhosNestaAcao = pontosGanhosNestaAcao;
            this.pontuacaoTotalAtual = pontuacaoTotalAtual;
            this.gameOver = gameOver;
            this.tipoResposta = tipoResposta;
        }

        // Getters
        public int getPontosGanhosNestaAcao() {
            return pontosGanhosNestaAcao;
        }

        public int getPontuacaoTotalAtual() {
            return pontuacaoTotalAtual;
        }

        public boolean isGameOver() {
            return gameOver;
        }

        public String getTipoResposta() {
            return tipoResposta;
        }
    }

    // Métodos auxiliares para o front-end consultar o estado do trem (se necessário)
    public boolean isChaveReversoraFrente(int idUsuario) {
        return chaveReversoraFrentePorUsuario.getOrDefault(idUsuario, false);
    }

    public boolean isChaveCbtcRm(int idUsuario) {
        return chaveCbtcRmPorUsuario.getOrDefault(idUsuario, false);
    }

    // Exemplo de estado da porta 53 (você precisará desenvolver a lógica completa)
    public boolean isPorta53FechadaIsolada(int idUsuario) {
        // Esta lógica deve ser mais complexa e baseada no progresso do jogo
        // Por exemplo, se a etapaAtualPorUsuario >= 22 (adesivo colocado)
        return etapaAtualPorUsuario.getOrDefault(idUsuario, 0) >= 22;
    }

    public boolean isLuzBotoeiraAcesa(int idUsuario) {
        // A luz da botoeira acende se as portas estão abertas e o comando está assumido
        // E ela apaga quando a porta é isolada e fechada
        boolean comandoAssumido = isChaveReversoraFrente(idUsuario) && isChaveCbtcRm(idUsuario);
        boolean portaNaoFechadaEIsolada = !isPorta53FechadaIsolada(idUsuario);
        return comandoAssumido && portaNaoFechadaEIsolada;
    }

    public boolean isSinalPortasFechadasAceso(int idUsuario) {
        // O sinal de portas fechadas no console só acende no final, após o isolamento
        return isPorta53FechadaIsolada(idUsuario) && etapaAtualPorUsuario.getOrDefault(idUsuario, 0) >= 24;
    }

    // Métodos para o estado da DDU (simplificado)
    public String getDduStatus(int idUsuario) {
        if (etapaAtualPorUsuario.getOrDefault(idUsuario, 0) < 6) {
            return "PORTAS ABERTAS (DDU)"; // Início do jogo
        } else if (!isPorta53FechadaIsolada(idUsuario)) {
            return "PORTA 53 ABERTA (DDU)"; // Durante a falha
        } else {
            return "PORTAS FECHADAS (DDU)"; // Após o isolamento
        }
    }
}

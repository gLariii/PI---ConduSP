// package Back_End;
// import java.sql.Connection;

// import Controller.GameController;
// import Controller.GameController.GameResult;
// import Controller.LoginController;
// import Model.Usuario; // Precisamos do Model.Usuario para simular o login
// import DAO.UsuarioDAO; // Para buscar o ID do usuário após o login

// import java.util.Scanner;

// public class MainTest {

//     public static void main(String[] args) {
//         // 1. Simular Login
//         LoginController loginController = new LoginController();
//         UsuarioDAO usuarioDAO = new UsuarioDAO(); // Para obter o ID do usuário

//         String testRg = "12.345.678-9"; // RG do usuário de teste que você cadastrou no BD
//         String testSenha = "senha123";  // Senha do usuário de teste

//         int idUsuarioLogado = -1;

//         System.out.println("--- Teste de Login ---");
//         if (loginController.login(testRg, testSenha)) {
//             System.out.println("Login bem-sucedido para o RG: " + testRg);
//             // Obter o ID do usuário logado para passar ao GameController
//             try {
//                 // Métodos para obter o ID do usuário:
//                 // Você pode adicionar um método `getIdByRg` no seu `UsuarioDAO`
//                 // Ou, idealmente, seu método `login` no `LoginController` retornaria o `Usuario` completo.
//                 // Por enquanto, vamos simular:
//                 Usuario usuarioTeste = usuarioDAO.getUsuarioByRg(testRg); // Você precisará adicionar este método
//                 if (usuarioTeste != null) {
//                     idUsuarioLogado = usuarioTeste.getId();
//                     System.out.println("ID do usuário logado: " + idUsuarioLogado);
//                 } else {
//                     System.err.println("Erro: Usuário não encontrado no banco de dados após login.");
//                     return;
//                 }
//             } catch (Exception e) {
//                 e.printStackTrace();
//                 System.err.println("Erro ao obter ID do usuário.");
//                 return;
//             }
//         } else {
//             System.out.println("Falha no login para o RG: " + testRg);
//             return; // Encerrar o teste se o login falhar
//         }

//         // Adicione este método ao seu UsuarioDAO.java
//         // public Usuario getUsuarioByRg(String rg) {
//         //    String sql = "SELECT id, rg, senha, nome, tipo_usuario FROM tb_usuario WHERE rg = ?";
//         //    try (Connection conn = Conexao.getConexao();
//         //         PreparedStatement stmt = conn.prepareStatement(sql)) {
//         //        stmt.setString(1, rg);
//         //        ResultSet rs = stmt.executeQuery();
//         //        if (rs.next()) {
//         //            return new Usuario(rs.getInt("id"), rs.getString("rg"), rs.getString("senha"), rs.getString("nome"), rs.getString("tipo_usuario"));
//         //        }
//         //    } catch (SQLException e) {
//         //        e.printStackTrace();
//         //    }
//         //    return null;
//         //}


//         // 2. Iniciar o Jogo
//         GameController gameController = new GameController();
//         gameController.iniciarNovoJogo(idUsuarioLogado);
//         System.out.println("\n--- Jogo Iniciado ---");
//         System.out.println("Simulando as ações do operador...");

//         // 3. Simular Ações do Roteiro (Passo a Passo)
//         // O idQuestaoAtual aqui deve ser o ID da Questão correspondente à etapa do roteiro no seu BD.
//         // Você precisará mapear o número da etapa do roteiro para o ID da Questao no BD.
//         // Por exemplo, a "Etapa 6" do roteiro pode ter o id_questao = 1 na sua tabela.
//         // Vamos usar IDs fixos para este teste, baseados na ordem de inserção do script SQL.
//         // Você pode ajustar esses IDs conforme os IDs reais gerados no seu BD.

//         GameResult result;
//         int currentQuestaoId = 0; // Vai ser atualizado conforme a progressão

//         // Etapa 6: Chave reversora para frente
//         System.out.println("\n>>> Ação: Passar Chave Reversora (Frente) - Etapa 6");
//         currentQuestaoId = 1; // Ajuste para o ID da Questão 6 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "passar_chave_reversora_frente", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         // Etapa 7: Chave CBTC para RM (ordem correta)
//         System.out.println("\n>>> Ação: Passar Chave CBTC (RM) - Etapa 7");
//         currentQuestaoId = 2; // Ajuste para o ID da Questão 7 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "passar_chave_cbtc_rm", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         // Etapa 8: Emitir PA
//         System.out.println("\n>>> Ação: Emitir PA - Etapa 8");
//         currentQuestaoId = 3; // Ajuste para o ID da Questão 8 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "emitir_pa", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         // Etapa 9: Fechar portas pela botoeira lateral esquerda
//         System.out.println("\n>>> Ação: Fechar Portas (Botoeira Esquerda) - Etapa 9");
//         currentQuestaoId = 4; // Ajuste para o ID da Questão 9 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "fechar_portas_botoeira_esquerda", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         // Etapa 11: Informar CCO
//         System.out.println("\n>>> Ação: Informar CCO - Etapa 11");
//         currentQuestaoId = 5; // Ajuste para o ID da Questão 11 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "informar_cco", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         // Etapa 12: Emitir PA (segundo PA)
//         System.out.println("\n>>> Ação: Emitir PA (segundo) - Etapa 12");
//         currentQuestaoId = 6; // Ajuste para o ID da Questão 12 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "emitir_pa_pos_falha", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         // Etapa 13: Pegar cinturão, adesivo e chave de serviço
//         System.out.println("\n>>> Ação: Pegar Cinturão e Adesivo - Etapa 13");
//         currentQuestaoId = 7; // Ajuste para o ID da Questão 13 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "pegar_cinturao_adesivo", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         System.out.println("\n>>> Ação: Pegar Chave de Serviço - Etapa 13");
//         result = gameController.processarAcao(idUsuarioLogado, "pegar_chave_servico", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         // Etapa 14: Ir para o carro correto (carro 5)
//         System.out.println("\n>>> Ação: Ir para Carro 5 - Etapa 14");
//         currentQuestaoId = 8; // Ajuste para o ID da Questão 14 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "ir_para_carro_5", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }
        
//         // Etapa 16: Olhar soleira da porta
//         System.out.println("\n>>> Ação: Olhar Soleira da Porta - Etapa 16");
//         currentQuestaoId = 9; // Ajuste para o ID da Questão 16 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "olhar_soleira_porta", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         // Etapa 17: Verificar dispositivos de emergência
//         System.out.println("\n>>> Ação: Verificar Dispositivos de Emergência - Etapa 17");
//         currentQuestaoId = 10; // Ajuste para o ID da Questão 17 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "verificar_dispositivos_emergencia", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         // Etapa 19: Isolar a porta correta
//         System.out.println("\n>>> Ação: Isolar Porta Correta - Etapa 19");
//         currentQuestaoId = 11; // Ajuste para o ID da Questão 19 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "isolar_porta_correta", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         // Etapa 19 (continuação): Fechar painel externo
//         System.out.println("\n>>> Ação: Fechar Painel Externo - Etapa 19 (continuação)");
//         currentQuestaoId = 12; // Ajuste para o ID da Questão "fechar_painel" no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "fechar_painel_externo", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         // Etapa 20: Verificar sinalização externa de portas (apagada)
//         System.out.println("\n>>> Ação: Verificar Sinalização Externa de Portas - Etapa 20");
//         currentQuestaoId = 13; // Ajuste para o ID da Questão 20 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "verificar_sinalizacao_externa_portas", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         // Etapa 21: Fechar porta manualmente
//         System.out.println("\n>>> Ação: Fechar Porta Manualmente - Etapa 21");
//         currentQuestaoId = 14; // Ajuste para o ID da Questão 21 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "fechar_porta_manualmente", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         System.out.println("\n>>> Ação: Confirmar Fechamento da Porta - Etapa 21 (continuação)");
//         result = gameController.processarAcao(idUsuarioLogado, "confirmar_fechamento_porta", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         // Etapa 22: Colocar adesivo
//         System.out.println("\n>>> Ação: Colocar Adesivo - Etapa 22");
//         currentQuestaoId = 15; // Ajuste para o ID da Questão 22 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "colocar_adesivo", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         // Etapa 23: Voltar para a cabine
//         System.out.println("\n>>> Ação: Voltar para Cabine - Etapa 23");
//         currentQuestaoId = 16; // Ajuste para o ID da Questão 23 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "voltar_para_cabine", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         // Etapa 24: Verificar sinalizações na cabine
//         System.out.println("\n>>> Ação: Verificar Sinalizações Cabine - Etapa 24");
//         currentQuestaoId = 17; // Ajuste para o ID da Questão 24 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "verificar_sinalizacoes_cabine", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         // Etapa 25: Informar CCO (pós-falha)
//         System.out.println("\n>>> Ação: Informar CCO Pós-Falha - Etapa 25");
//         currentQuestaoId = 18; // Ajuste para o ID da Questão 25 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "informar_cco_pos_falha", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         // Etapa 26: Emitir PA (pós-falha)
//         System.out.println("\n>>> Ação: Emitir PA Pós-Falha - Etapa 26");
//         currentQuestaoId = 19; // Ajuste para o ID da Questão 26 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "emitir_pa_pos_falha", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         // Etapa 27: Inserir chave de serviço
//         System.out.println("\n>>> Ação: Inserir Chave de Serviço - Etapa 27");
//         currentQuestaoId = 20; // Ajuste para o ID da Questão 27 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "inserir_chave_servico", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         // Etapa 28: Mudar CBTC para AM
//         System.out.println("\n>>> Ação: Mudar CBTC para AM - Etapa 28");
//         currentQuestaoId = 21; // Ajuste para o ID da Questão 28 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "mudar_cbtc_am", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         // Etapa 29: Colocar reversora em neutro
//         System.out.println("\n>>> Ação: Colocar Reversora em Neutro - Etapa 29");
//         currentQuestaoId = 22; // Ajuste para o ID da Questão 29 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "colocar_reversora_neutro", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("GAME OVER!"); return; }

//         // Etapa 30: Seguir Viagem (Fim da simulação)
//         System.out.println("\n>>> Ação: Seguir Viagem - Etapa 30");
//         currentQuestaoId = 23; // Ajuste para o ID da Questão 30 no seu BD
//         result = gameController.processarAcao(idUsuarioLogado, "seguir_viagem", currentQuestaoId);
//         System.out.println("Feedback: " + result.getFeedbackMensagem() + " | Pontos: " + result.getPontosGanhosNestaAcao() + " | Total: " + result.getPontuacaoTotalAtual());
//         if (result.isGameOver()) { System.out.println("FIM DA SIMULAÇÃO COM GAME OVER!"); }
//         else { System.out.println("SIMULAÇÃO CONCLUÍDA COM SUCESSO!"); }

//         System.out.println("\n--- Fim do Teste ---");
//     }
// }
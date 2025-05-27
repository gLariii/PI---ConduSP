package TelaMetro1.telaMenu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

// IMPORTANTE: Certifique-se que esta linha está presente e correta!
import TelaMetro1.telaMenu.Estatistica; 
import Assets.Cores; // Certifique-se que esta linha está presente e correta e que contém AZUL_METRO e AZUL_METRO_TRANSPARENTE


public class EstatisticasInternoPanel extends JPanel {

    private JTable tabelaEstatisticas;
    private DefaultTableModel modeloTabela;

    public EstatisticasInternoPanel() {
        setOpaque(false); // Mantém transparente para ver o background do pai
        setLayout(new BorderLayout(10, 10));

        String[] colunas = {"Data e Hora", "Acertos", "Erros", "Pontuação", "Tempo"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Não permite edição das células
            }
        };
        tabelaEstatisticas = new JTable(modeloTabela);
        tabelaEstatisticas.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaEstatisticas.setRowHeight(25);
        
        // Configurações do Cabeçalho da Tabela (categorias)
        tabelaEstatisticas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        // ESTA LINHA DEVE SER AZUL_METRO:
        tabelaEstatisticas.getTableHeader().setBackground(Cores.AZUL_METRO); 
        tabelaEstatisticas.getTableHeader().setForeground(Color.WHITE); // Cor do texto do cabeçalho: BRANCO
        tabelaEstatisticas.getTableHeader().setReorderingAllowed(false); // Impede reordenar colunas
        tabelaEstatisticas.getTableHeader().setResizingAllowed(false); // Impede redimensionar colunas
        
        // Configurações da Tabela em si (cor de fundo geral da área de dados)
        tabelaEstatisticas.setBackground(Cores.AZUL_METRO_TRANSPARENTE); // Fundo da tabela: AZUL CLARO/TRANSPARENTE
        tabelaEstatisticas.setSelectionBackground(new Color(Cores.AZUL_METRO.getRed(), Cores.AZUL_METRO.getGreen(), Cores.AZUL_METRO.getBlue(), 100)); // Cor de seleção com transparência
        tabelaEstatisticas.setGridColor(new Color(255, 255, 255, 150)); // Cor da grade da tabela (linhas divisórias)
        
        // Renderizador personalizado para centralizar e definir a cor do texto nas células de dados
        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                // Define a cor do texto para todas as células de dados: BRANCO para maior legibilidade
                c.setForeground(Color.WHITE); 
                setHorizontalAlignment(JLabel.CENTER); // Centraliza o texto
                return c;
            }
        };

        for (int i = 0; i < tabelaEstatisticas.getColumnCount(); i++) {
            tabelaEstatisticas.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }

        // Configurações do JScrollPane
        JScrollPane scrollPane = new JScrollPane(tabelaEstatisticas);
        scrollPane.setOpaque(false); // Torna o scroll pane transparente
        scrollPane.getViewport().setOpaque(false); // Torna o viewport do scroll pane transparente
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove a borda padrão do scroll pane

        // MUITO IMPORTANTE: Define o tamanho preferencial do JScrollPane (tabela)
        scrollPane.setPreferredSize(new Dimension(800, 500)); // Ajuste esse tamanho se necessário

        // Título do painel de estatísticas
        JLabel tituloEstatisticas = new JLabel("Histórico de Partidas", SwingConstants.CENTER);
        tituloEstatisticas.setFont(new Font("Arial", Font.BOLD, 20));
        tituloEstatisticas.setForeground(Color.WHITE); // Cor do título
        tituloEstatisticas.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        add(tituloEstatisticas, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        adicionarEstatisticasExemplo();
    }

    private void adicionarEstatisticasExemplo() {
        List<Estatistica> estatisticas = new ArrayList<>();
        estatisticas.add(new Estatistica(10, 2, 90, "01:23", LocalDateTime.now().minusHours(1)));
        estatisticas.add(new Estatistica(8, 4, 60, "01:50", LocalDateTime.now().minusDays(1)));
        estatisticas.add(new Estatistica(15, 1, 140, "00:55", LocalDateTime.now().minusDays(2)));
        estatisticas.add(new Estatistica(12, 3, 100, "01:10", LocalDateTime.now().minusDays(3)));
        estatisticas.add(new Estatistica(7, 5, 50, "02:00", LocalDateTime.now().minusWeeks(1)));

        for (Estatistica ep : estatisticas) {
            modeloTabela.addRow(new Object[]{
                ep.getDataHoraFormatada(),
                ep.getAcertos(),
                ep.getErros(),
                ep.getPontuacao(),
                ep.getTempoDecorrido()
            });
        }
    }

    public void adicionarEstatistica(Estatistica estatistica) {
        modeloTabela.addRow(new Object[]{
            estatistica.getDataHoraFormatada(),
            estatistica.getAcertos(),
            estatistica.getErros(),
            estatistica.getPontuacao(),
            estatistica.getTempoDecorrido()
        });
    }

    public void limparEstatisticas() {
        modeloTabela.setRowCount(0);
    }
}
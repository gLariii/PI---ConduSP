package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// IMPORTANTE: Certifique-se que esta linha está presente e correta!
import TelaMetro1.telaMenu.Estatistica;
import Assets.Cores; // Certifique-se que esta linha está presente e correta e que contém AZUL_METRO e AZUL_METRO_TRANSPARENTE

public class EstatisticasInternoPanel extends JPanel {

    private JPanel painelLista;

    public EstatisticasInternoPanel() {
        setOpaque(false); // Mantém transparente para ver o background do painel
        setLayout(new BorderLayout(10, 10));

        // Título do painel
        JLabel titulo = new JLabel("Histórico de Partidas", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // Painel de cards
        painelLista = new JPanel();
        painelLista.setLayout(new BoxLayout(painelLista, BoxLayout.Y_AXIS));
        painelLista.setOpaque(false);

        // Configurações do JScrollPane
        JScrollPane scrollPane = new JScrollPane(painelLista);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        // Dados exemplo
        adicionarEstatisticasExemplo();
    }

    // Criando card estilizado com borda arredondada e contorno
    private JPanel criarCardEstatistica(Estatistica estatistica) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fundo dos cards
                g2.setColor(Cores.AZUL_METRO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Bordas arredondadas

                // Contorno
                g2.setColor(new Color(0, 60, 200)); // Azul mais claro
                g2.setStroke(new BasicStroke(2f)); // Espessura do contorno
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);

                g2.dispose();
            }
        };

        card.setLayout(new BorderLayout());
        card.setOpaque(false); // Importante para usar o paintComponent
        card.setPreferredSize(new Dimension(800, 90)); // Tamanho do card
        card.setMaximumSize(new Dimension(800, 90)); // Garante que não vai expandir

        // Painel interno com Grid
        JPanel linhaInfo = new JPanel(new GridLayout(2, 3, 20, 8));
        linhaInfo.setOpaque(false);

        // Linha 1
        linhaInfo.add(criarLabel(estatistica.getDataHoraFormatada()));
        linhaInfo.add(criarLabel("Erros: " + estatistica.getErros()));
        linhaInfo.add(criarLabel("Acertos: " + estatistica.getAcertos()));

        // Linha 2
        linhaInfo.add(criarLabel("Pontuação: " + estatistica.getPontuacao()));
        linhaInfo.add(criarLabel("Tempo: " + estatistica.getTempoDecorrido()));
        linhaInfo.add(new JLabel()); // Espaço vazio para balancear

        card.add(linhaInfo, BorderLayout.CENTER);
        card.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20)); // Espaçamento interno

        return card;
    }

    // Cria label padrão estilizada
    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(Color.WHITE);
        return label;
    }

    // Dados de exemplo
    private void adicionarEstatisticasExemplo() {
        List<Estatistica> estatisticas = new ArrayList<>();
        estatisticas.add(new Estatistica(10, 2, 90, "01:23", LocalDateTime.now().minusHours(1)));
        estatisticas.add(new Estatistica(8, 4, 60, "01:50", LocalDateTime.now().minusDays(1)));
        estatisticas.add(new Estatistica(15, 1, 140, "00:55", LocalDateTime.now().minusDays(2)));
        estatisticas.add(new Estatistica(12, 3, 100, "01:10", LocalDateTime.now().minusDays(3)));


        for (Estatistica ep : estatisticas) {
            painelLista.add(criarCardEstatistica(ep));
            painelLista.add(Box.createVerticalStrut(15)); // Espaço entre os cards
        }
    }

    public void adicionarEstatistica(Estatistica estatistica) {
        painelLista.add(criarCardEstatistica(estatistica));
        painelLista.add(Box.createVerticalStrut(15));
        revalidate();
        repaint();
    }

    public void limparEstatisticas() {
        painelLista.removeAll();
        revalidate();
        repaint();
    }
}

package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import TelaMetro1.telaMenu.Estatistica;
import Assets.Cores; 

public class EstatisticasInternoPanel extends JPanel {

    private JPanel painelLista;

    public EstatisticasInternoPanel() {
        setOpaque(false); 
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Histórico de Partidas", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        painelLista = new JPanel();
        painelLista.setLayout(new BoxLayout(painelLista, BoxLayout.Y_AXIS));
        painelLista.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(painelLista);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        adicionarEstatisticasExemplo();
    }

    private JPanel criarCardEstatistica(Estatistica estatistica) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(Cores.AZUL_METRO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); 

                g2.setColor(new Color(0, 60, 200)); 
                g2.setStroke(new BasicStroke(2f)); 
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);

                g2.dispose();
            }
        };

        card.setLayout(new BorderLayout());
        card.setOpaque(false); 
        card.setPreferredSize(new Dimension(800, 90)); 
        card.setMaximumSize(new Dimension(800, 90)); 

        JPanel linhaInfo = new JPanel(new GridLayout(2, 3, 20, 8));
        linhaInfo.setOpaque(false);

        linhaInfo.add(criarLabel(estatistica.getDataHoraFormatada()));
        linhaInfo.add(criarLabel("Erros: " + estatistica.getErros()));
        linhaInfo.add(criarLabel("Acertos: " + estatistica.getAcertos()));

        linhaInfo.add(criarLabel("Pontuação: " + estatistica.getPontuacao()));
        linhaInfo.add(criarLabel("Tempo: " + estatistica.getTempoDecorrido()));
        linhaInfo.add(new JLabel());

        card.add(linhaInfo, BorderLayout.CENTER);
        card.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20)); 

        return card;
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(Color.WHITE);
        return label;
    }

    private void adicionarEstatisticasExemplo() {
        List<Estatistica> estatisticas = new ArrayList<>();
        estatisticas.add(new Estatistica(10, 2, 90, "01:23", LocalDateTime.now().minusHours(1)));
        estatisticas.add(new Estatistica(8, 4, 60, "01:50", LocalDateTime.now().minusDays(1)));
        estatisticas.add(new Estatistica(15, 1, 140, "00:55", LocalDateTime.now().minusDays(2)));
        estatisticas.add(new Estatistica(12, 3, 100, "01:10", LocalDateTime.now().minusDays(3)));


        for (Estatistica ep : estatisticas) {
            painelLista.add(criarCardEstatistica(ep));
            painelLista.add(Box.createVerticalStrut(15)); 
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

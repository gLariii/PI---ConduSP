package TelaMetro1.telaMenu;

import javax.swing.*;
import java.awt.*;

public class botoes {

    public static JButton criarBotaoMaquinario() {
        JButton botaoMaquinario = new JButton("Maquinário");
        botaoMaquinario.setFont(new Font("Arial", Font.PLAIN, 20)); 
        botaoMaquinario.setBounds(200, 250, 200, 50);
        return botaoMaquinario;
    }

    public static JButton criarBotaoAulas() {
        JButton botaoAulas = new JButton("Aulas Teóricas");
        botaoAulas.setFont(new Font("Arial", Font.PLAIN, 20)); 
        botaoAulas.setBounds(400, 250, 200, 50); 
        return botaoAulas;
    }
}
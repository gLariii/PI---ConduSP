package CabineDeControleTela;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class AudioPlayer {
    private static Clip clipAtual;

    /**
     * Toca um arquivo WAV localizado na pasta /ModuloDeComunicacaoTela/sons/
     * @param SomPorta do arquivo de som (ex: "SomDigitacao.wav")
     */
    public static void playSound(String SomPorta) {
        stopSound();  // Interrompe qualquer som anterior

        try {
            URL soundURL = AudioPlayer.class.getResource("sons/" + SomPorta);
            if (soundURL == null) {
                System.err.println("Não foi possível encontrar o som: " + SomPorta);
                return;
            }

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL);
            clipAtual = AudioSystem.getClip();
            clipAtual.open(ais);
            clipAtual.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Para o som atualmente em execução, se houver.
     */
    public static void stopSound() {
        if (clipAtual != null) {
            if (clipAtual.isRunning()) {
                clipAtual.stop();
            }
            clipAtual.close();
            clipAtual = null;
        }
    }
}

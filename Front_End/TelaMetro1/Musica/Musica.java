package TelaMetro1.Musica;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Musica {
    private Clip clip;
    private FloatControl gainControl;

    public Musica() {}
    
    // Método sobrecarregado para chamadas sem volume específico
    public void play(String audioFilePath, boolean loop) {
        play(audioFilePath, loop, 75.0f); // Volume padrão de 75%
    }

    // Método principal que faz o trabalho
    public void play(String audioFilePath, boolean loop, float initialVolume) {
        try {
            if (clip != null && clip.isOpen()) {
                clip.stop();
                clip.close();
            }

            // A linha abaixo é crítica para encontrar o arquivo de música
            InputStream audioStream = getClass().getResourceAsStream(audioFilePath);
            
            if (audioStream == null) {
                System.err.println("!!! ARQUIVO DE ÁUDIO NÃO ENCONTRADO: " + audioFilePath);
                System.err.println("Verifique se o caminho está correto e se o arquivo está na pasta de build.");
                return; // Não continua se o arquivo não for encontrado
            }

            InputStream bufferedIn = new BufferedInputStream(audioStream);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn);

            clip = AudioSystem.getClip();
            clip.open(audioIn);

            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                setVolume(initialVolume); 
            } else {
                gainControl = null;
            }

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }

        } catch (Exception e) {
            System.err.println("Ocorreu um erro crítico ao tentar tocar o áudio: " + audioFilePath);
            e.printStackTrace(); 
        }
    }
    
    // ... (resto dos métodos: stop, setVolume, isPlaying - mantenha a versão robusta anterior)
    public void stop() {
        if (clip != null && clip.isRunning()) { clip.stop(); }
        if (clip != null && clip.isOpen()) { clip.close(); }
    }

    public void setVolume(float volume) {
        if (gainControl != null) {
            if (volume < 0.0f || volume > 100.0f) {
                volume = Math.max(0.0f, Math.min(100.0f, volume));
            }
            float minDb = gainControl.getMinimum();
            float maxDb = gainControl.getMaximum();
            if (volume == 0.0f) {
                gainControl.setValue(minDb);
            } else {
                float gain = ((maxDb - minDb) * (volume / 100.0f)) + minDb;
                gainControl.setValue(gain);
            }
        }
    }

    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}
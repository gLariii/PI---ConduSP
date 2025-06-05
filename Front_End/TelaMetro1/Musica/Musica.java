package TelaMetro1.Musica;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class Musica { // A CLASSE AGORA SE CHAMA MUSICA
    private Clip clip;
    private FloatControl gainControl; // Para controle de volume

    public Musica() { // Construtor com o novo nome da classe
        // Construtor vazio, a inicialização do Clip acontece em play()
    }

    public void play(String audioFilePath, boolean loop) {
        try {
            // Se já houver um clip tocando, pare e feche-o antes de iniciar um novo
            if (clip != null && clip.isOpen()) {
                clip.stop();
                clip.close();
            }

            // Usando getResourceAsStream para carregar de dentro do JAR (se o Assets for um recurso)
            InputStream audioStream = getClass().getResourceAsStream(audioFilePath);
            if (audioStream == null) {
                System.err.println("Arquivo de áudio não encontrado: " + audioFilePath);
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioStream);

            clip = AudioSystem.getClip();
            clip.open(audioIn);

            // Obter o controle de volume
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            } else {
                System.err.println("Controle de ganho não suportado para este áudio.");
                gainControl = null;
            }

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Toca em loop contínuo
            } else {
                clip.start(); // Toca uma vez
            }

        } catch (UnsupportedAudioFileException e) {
            System.err.println("Formato de áudio não suportado: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erro de E/S ao ler o arquivo de áudio: " + e.getMessage());
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.err.println("Linha de áudio não disponível: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao reproduzir áudio: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
        if (clip != null && clip.isOpen()) {
            clip.close();
        }
    }

    public void setVolume(float volume) { // volume de 0.0 a 1.0 (ou 0 a 100 como no slider)
        if (gainControl != null) {
            float minDb = gainControl.getMinimum();
            float maxDb = gainControl.getMaximum();
            
            if (volume <= 0) {
                gainControl.setValue(minDb); // Mudo total
            } else {
                float scaledVolume = volume / 100.0f; // Mapeia 0-100 para 0-1
                float db = minDb + (maxDb - minDb) * (float) (Math.log10(scaledVolume * 9 + 1) / Math.log10(10));
                db = Math.max(minDb, Math.min(db, maxDb));
                gainControl.setValue(db);
            }
        }
    }

    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}
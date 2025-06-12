package TelaMetro1.Musica; 

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream; 

public class Musica {
    private Clip clip; 
    private FloatControl gainControl; 

    public Musica() {
    }

    // Toca a música
    public void play(String audioFilePath, boolean loop) {
        try {
            // Fecha o clipe se já estiver aberto
            if (clip != null && clip.isOpen()) {
                clip.stop();
                clip.close();
            }

            // Carrega o arquivo de áudio
            InputStream audioStream = getClass().getResourceAsStream(audioFilePath);
            if (audioStream == null) {
                System.err.println("Arquivo de áudio não encontrado: " + audioFilePath);
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioStream);

            clip = AudioSystem.getClip(); // Pega o clipe de áudio
            clip.open(audioIn); // Abre o arquivo

            // Tenta pegar o controle de volume
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            } else {
                System.err.println("Controle de ganho não suportado para este áudio.");
                gainControl = null;
            }

            // Inicia a reprodução 
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }

        // Captura e imprime erros que podem ocorrer
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

    // Para e fecha a música
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
        if (clip != null && clip.isOpen()) {
            clip.close();
        }
    }

    // Define o volume da música
    public void setVolume(float volume) {
        if (gainControl != null) {
            float minDb = gainControl.getMinimum();
            float maxDb = gainControl.getMaximum();

            // Faz o silenciamento da música se o volume for 0
            if (volume <= 0) { 
                gainControl.setValue(minDb);
            } else { 
                float scaledVolume = volume / 100.0f;
                float db = minDb + (maxDb - minDb) * (float) (Math.log10(scaledVolume * 9 + 1) / Math.log10(10));
                db = Math.max(minDb, Math.min(db, maxDb));
                gainControl.setValue(db);
            }
        }
    }

    // Verifica se a música está tocando
    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}
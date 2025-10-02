package TelaMetro1.Musica;

public class InicialMusica {

    private static Musica backgroundMusicPlayer;

    // ---> ESTE É O MÉTODO QUE SUA CLASSE MENU ESTÁ CHAMANDO <---
    // Ele deve existir! Ele age como um atalho para o método principal.
    public static void startBackgroundMusic(String audioFilePath, boolean loop) {
        // Ele chama a versão completa com um volume padrão (ex: 75.0f)
        startBackgroundMusic(audioFilePath, loop, 0.5f);
    }
    
    // Método principal que aceita o volume
    public static void startBackgroundMusic(String audioFilePath, boolean loop, float initialVolume) {
        if (backgroundMusicPlayer == null) {
            backgroundMusicPlayer = new Musica();
        }
        backgroundMusicPlayer.play(audioFilePath, loop, initialVolume);
    }

    public static void stopMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
    }

    public static Musica getBackgroundMusicPlayer() {
        return backgroundMusicPlayer;
    }
}
package TelaMetro1.Musica; 

public class InicialMusica {

    // Variável para tocar a música de fundo
    private static Musica backgroundMusicPlayer; 

    // Inicia a música de fundo
    public static void startBackgroundMusic(String audioFilePath, boolean loop) {
        if (backgroundMusicPlayer == null) {
            backgroundMusicPlayer = new Musica();
        }
        backgroundMusicPlayer.play(audioFilePath, loop);
    }

    // Cancela a música de fundo
    public static void stopMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
    }

    // Retorna o player de música 
    public static Musica getBackgroundMusicPlayer() {
        return backgroundMusicPlayer;
    }
}
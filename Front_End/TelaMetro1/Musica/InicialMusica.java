package TelaMetro1.Musica; 


public class InicialMusica {

    private static Musica backgroundMusicPlayer; 


    
    public static void startBackgroundMusic(String audioFilePath, boolean loop) {
        if (backgroundMusicPlayer == null) {
            backgroundMusicPlayer = new Musica();
        }
        backgroundMusicPlayer.play(audioFilePath, loop);
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
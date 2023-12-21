import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
public class MyRunnable implements Runnable{

    @Override public void run() {
        try {
//            MusicPlayer.playMusic("MyScoure/et9s0-eb3ho.wav");
        }catch (Exception e){
            System.out.println(e);
        }

    } public class MusicPlayer{
//        public static final String bgm="MyScoure/et9s0-eb3ho.wav";
//        static Clip music=null;
//        static File file=null;
//        public static void playMusic(String path){
//            try {
//                music= AudioSystem.getClip();
//                file=new File(path);
//                AudioInputStream audioInputStream=AudioSystem.getAudioInputStream(file);
//                music.open(audioInputStream);
//                music.loop(-1);
//                music.start();
//            }catch (LineUnavailableException | UnsupportedAudioFileException | IOException e){
//                e.printStackTrace();
//            }
//        }
    }
}

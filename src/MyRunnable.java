//import javax.sound.sampled.*;
//import java.io.File;
//import java.io.IOException;
//public class MyRunnable implements Runnable{
//
//    @Override public void run() {
//        try {
//            MusicPlayer.playMusic();
//        }catch (Exception e){
//            System.out.println(e);
//        }
//
//    } public class MusicPlayer{
//        public static final String bgm="";
//        static Clip music=null;
//        static File file=null;
//        public static void playMusic(String path){
//            try {
//                music= AudioSystem.getClip();
//                file=new File(path);
//                AudioInputStream audioInputStream=AudioSystem.getAudioInputStream(file);
//                music.open(audioInputStream);
//                music.start();
//            }catch (LineUnavailableException e){
//                e.printStackTrace();
//            }catch (UnsupportedAudioFileException e){
//                e.printStackTrace();
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//    }
//}

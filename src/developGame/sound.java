package developGame;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class sound {
    AudioStream audios;
    int length; 
    String path; 
    
    public sound(String path){
        this.path = path; 
    }
    
    public void OpenFileMusic() throws InterruptedException{
        InputStream music; 
        try{

            music = new FileInputStream(new File(path)); 
            audios = new AudioStream(music); 
            AudioPlayer.player.start(audios);
            length = audios.getLength(); 
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void CloseMusic(){
        AudioPlayer.player.stop(audios);
    }
    
    
    // play again.
    
    public void PlayAgain(){
        if (audios != null){
            AudioPlayer.player.start(audios);
        }
    }
    
    // test theard.
    public static void PlaySound(File Sound){
        try{
            Clip clip = AudioSystem.getClip(); 
            
            clip.open(AudioSystem.getAudioInputStream(Sound));
            
            clip.start();
            
            Thread.sleep(clip.getMicrosecondLength()/1000);
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

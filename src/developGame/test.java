package developGame;

import java.util.Scanner;


public class test {
    static Audios audio = new Audios("04_Level 1.wav", false);
    
    public static void main(String args[]){
        audio.Player();
        int num = 0;
        Scanner input = new Scanner(System.in); 
        num = input.nextInt();
        if (num > 0){
            audio.StopAudio();
        }
    }
}

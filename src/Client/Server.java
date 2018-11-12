/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javafx.scene.input.KeyCode;
 
public class Server {
 
   public static void main(String args[]) {
 
       ServerSocket listener = null;
       KeyCode key;
       BufferedReader is;
       BufferedWriter os;
       Socket socketOfServer = null;
 
       try {
           listener = new ServerSocket(9999);
       } catch (IOException e) {
           System.out.println(e);
           System.exit(1);
       }
 
       try {
           System.out.println("Server is waiting to accept user...");
 
           socketOfServer = listener.accept();
           System.out.println("Accept a client!");
      
           is = new BufferedReader(new InputStreamReader(socketOfServer.get));
           os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
 
           int line;
           while (true) {
               line = Integer.parseInt(is.readLine());
               
               os.newLine();

               os.flush();  
 
               if (line.equals("QUIT")) {
                   os.write(">> OK");
                   os.newLine();
                   os.flush();
                   break;
               }
           }
 
       } catch (IOException e) {
           System.out.println(e);
           e.printStackTrace();
       }
       System.out.println("Sever stopped!");
   }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clien;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.*;
import java.util.Scanner;
 
public class Client implements KeyListener{
 
   public static void main(String[] args) {
 
       // Địa chỉ máy chủ.
       final String serverHost = "127.0.0.1";
 
       Socket socketOfClient = null;
       BufferedWriter os = null;
       BufferedReader is = null;
 
       try {
           // Gửi yêu cầu kết nối tới Server đang lắng nghe
           // trên máy 'localhost' cổng 9999.
           socketOfClient = new Socket(serverHost, 9999);
 
           // Tạo luồng đầu ra tại client (Gửi dữ liệu tới server)
           os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
 
           // Luồng đầu vào tại Client (Nhận dữ liệu từ server).
           is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
 
       } catch (UnknownHostException e) {
           System.err.println("Don't know about host " + serverHost);
           return;
       } catch (IOException e) {
           System.err.println("Couldn't get I/O for the connection to " + serverHost);
           return;
       }
       
       String s=null;
       Scanner sc = new Scanner(System.in);
       try {
           // Ghi dữ liệu vào luồng đầu ra của Socket tại Client.
           do
           {
                s = sc.nextLine();
                os.write(s);
                os.newLine(); // kết thúc dòng
                os.flush();  // đẩy dữ liệu đi.

                // Đọc dữ liệu trả lời từ phía server
                // Bằng cách đọc luồng đầu vào của Socket tại Client.
                String responseLine;
                while ((responseLine = is.readLine()) != null) {
                    System.out.println("Server: " + responseLine);
                    if (responseLine.indexOf("OK") != -1) {
                        break;
                    }
                }
           }while(!s.equals("QUIT"));
 
           os.close();
           is.close();
           socketOfClient.close();
       } catch (UnknownHostException e) {
           System.err.println("Trying to connect to unknown host: " + e);
       } catch (IOException e) {
           System.err.println("IOException:  " + e);
       }
   }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void keyPressed(KeyEvent e) 
    {
                Character c = e.getKeyChar();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
    }
   
 
}
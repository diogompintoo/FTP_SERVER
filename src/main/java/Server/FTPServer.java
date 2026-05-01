package Server;

import Utility.Constants;
import java.io.*;
import java.net.*;

public class FTPServer {
    public static void main(String[] args) {
        try{
            ServerSocket serverSocket = new ServerSocket(Constants.DEFAULT_PORT);
            System.out.println("Server is running....................");

            while (true){
                Socket client = serverSocket.accept();
                    System.out.println("Client Connected.....................");

                new Thread(()->{
                    try{
                        FTPCommandHandler handler = new FTPCommandHandler(client);
                        handler.start();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}

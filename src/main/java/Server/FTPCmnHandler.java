package Server;


import Utility.FileManager;
import java.nio.file.*;
import java.io.*;
import java.net.*;

public class FTPCmnHandler {

    private final  Socket socket;
    private final FileManager fileManager;


    public FTPCmnHandler(Socket socket) {
        this.socket = socket;
        this.fileManager = new FileManager();
    }

    public void handle(Commands cmn, String args){

        switch (cmn) {
            case HELP:
                sendHelp();
                break;

            case LS:
                listFiles();
                break;

        }
    }

    private void sendHelp(){
        System.out.println("Commands:");
        System.out.println("--->  HELP ()");
        System.out.println("--->  LS ()");
        System.out.println("--->  MKDIR ()");
        System.out.println("--->  GET ()");
        System.out.println("--->  PUT ()");
        System.out.println("--->  DELETE ()");
        System.out.println("--->  QUIT / BYE / DISCONNECT ()");
    }

    private void listFiles(){

    }
}

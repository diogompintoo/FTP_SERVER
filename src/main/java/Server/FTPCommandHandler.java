package Server;


import Utility.FileManager;

import java.io.*;
import java.net.*;


public class FTPCommandHandler {

    private final  Socket socket;
    private final FileManager fileManager;
    private final PrintWriter out;


    public FTPCommandHandler(Socket socket, PrintWriter out, FileManager fileManager) {
        this.socket = socket;
        this.fileManager = new FileManager();
        this.out = out;
    }

    public void handle(Commands cmn, String arg) throws IOException {

        switch (cmn) {
            case HELP:
                sendHelp();
                break;

            case LS:
                listFiles();
                break;

            case MKDIR:
                createDir(arg);
                break;

            case GET:
                downloadFile(arg);
                break;

            case PUT:
                uploadFile(arg);
                break;

        }
    }

    private void sendHelp(){
        out.println("Commands:");
        out.println("--->  HELP ()");
        out.println("--->  LS ()");
        out.println("--->  MKDIR ()");
        out.println("--->  GET ()");
        out.println("--->  PUT ()");
        out.println("--->  DELETE ()");
        out.println("--->  QUIT / BYE / DISCONNECT ()");
    }

    private void listFiles(){
        String [] fileNames = fileManager.listFileNames();

        if (fileNames.length == 0){
            out.println("Sorry, no files found.");
        }else{
            for(String fileName : fileNames){
                out.println(fileName);
            }
        }
    }

    private void createDir(String dirName){
        if (dirName == null || dirName.trim().isEmpty()){
            out.println("No directory name provided.");
            return;
        }
        if (fileManager.createDirectory(dirName)){
            out.println("Directory " + dirName + " created.");
        }else{
            out.println("Directory " + dirName + " could not be created.");
        }
    }

    private void downloadFile(String fileName) throws IOException {
        if (fileName == null || fileName.trim().isEmpty()){
            out.println("No file name provided.");
            return;
        }
        if (!fileManager.exists(fileName) || !fileManager.isFile(fileName) ){
            out.println("File " + fileName + " does not exist.");
        }

        File file = fileManager.getFile(fileName);
            out.println("File" + fileName + "send");
        }

    private void uploadFile(String fileName) throws IOException {
        if (fileName == null || fileName.trim().isEmpty()){

        }
    }

    }


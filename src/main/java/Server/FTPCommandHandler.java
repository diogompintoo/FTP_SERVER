package Server;

import Utility.FileManager;
import java.io.*;
import java.net.*;

public class FTPCommandHandler {

    private final  Socket socket;
    private final FileManager fileManager;
    private final PrintWriter out;
    private final BufferedReader in;
    private final DataOutputStream dataOut;
    private final DataInputStream dataIn;


    public FTPCommandHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.fileManager = new FileManager();

        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        this.dataOut = new DataOutputStream(socket.getOutputStream());
        this.dataIn = new DataInputStream(socket.getInputStream());
    }

    public void start() throws IOException {
        out.println("Welcome to FTP server");
        out.println("Type HELP to see available commands.");

        String line;
        while ((line = in.readLine()) != null) {
            String[] command = line.split(" ", 2);
            Commands cmn = Commands.getCommand(command[0].trim());
            String arg = command.length > 1 ? command[1] : null;

            handle(cmn, arg);

            if (turnOff(cmn)){
                disconnect();
                break;
            }
        }
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

            case DELETE:
                deleteFile(arg);
                break;

            case QUIT:
            case BYE:
            case DISCONNECT:
                break;

            default:
                out.println("Unknown command. Type HELP to see available commands.");
                break;

        }
    }

    private void sendHelp(){
        out.println("Commands:");
        out.println("---->  HELP: " + Commands.HELP.getDescription());
        out.println("---->  LS: " + Commands.LS.getDescription());
        out.println("---->  MKDIR: " + Commands.MKDIR.getDescription());
        out.println("---->  GET: " + Commands.GET.getDescription());
        out.println("---->  PUT: " + Commands.PUT.getDescription());
        out.println("---->  DELETE: " + Commands.DELETE.getDescription());
        out.println("---->  QUIT / BYE / DISCONNECT: " + Commands.DISCONNECT.getDescription());
        out.println(".");
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
        if (!fileManager.exists(fileName) || !fileManager.isFile(fileName)) {
            out.println("No file found.");
            return;
        }
        File file = fileManager.getFile(fileName);

        out.println("OK");
        out.flush();
        dataOut.writeLong(file.length());
        dataOut.flush();

        FileInputStream fis = fileManager.readFile(fileName);
        byte[] buffer = new byte[1024];
        int bytes;

        while ((bytes = fis.read(buffer)) != -1) {
            dataOut.write(buffer, 0, bytes);
        }
        fis.close();
        }

    private void uploadFile(String fileName) throws IOException {

        out.println("READY");
        out.flush();
        long size = dataIn.readLong();

        FileOutputStream fos = new FileOutputStream(fileManager.getFile(fileName));

        byte[] buffer = new byte[1024];
        int bytesRead;
        long total = 0;

        while (total < size &&
                (bytesRead = dataIn.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
            total += bytesRead;
        }
        fos.close();

        out.println("Upload OK");
        out.flush();
        }
    private void deleteFile(String fileName) throws IOException {
       if (fileName == null || fileName.trim().isEmpty()){
           out.println("No file name provided.");
           return;
       }

       if (!fileManager.exists(fileName) || !fileManager.isFile(fileName)) {
           out.println("No file found.");
           return;
       }
       File file = fileManager.getFile(fileName);

       if (file.delete()) {
           out.println("Deleted :" + fileName);
       }else{
           out.println("Failed to delete :" + fileName);
       }
    }

    private boolean turnOff(Commands cmn) throws IOException {
        return  cmn == Commands.QUIT ||
                cmn == Commands.BYE ||
                cmn == Commands.DISCONNECT;
    }

    private void disconnect() throws IOException {
        out.println("Disconnecting... See you next time.");
        out.flush();
        out.close();
        System.out.println("Disconnected from FTP server.");
    }
}




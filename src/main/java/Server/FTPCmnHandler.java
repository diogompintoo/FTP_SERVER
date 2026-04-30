package Server;

import Utility.FileManager;

import java.io.*;
import java.net.*;

public class FTPCmnHandler {

    private final  Socket socket;
    public FTPCmnHandler(Socket socket) {
        this.socket = socket;
    }
}

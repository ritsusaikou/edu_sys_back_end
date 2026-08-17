package com.example.demo.javasocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main (String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(6666);
        System.out.println("server is running");
        for(;;){
            Socket socket = ss.accept();
            System.out.println("Connection from "+socket.getRemoteSocketAddress());
            Thread t = new Handler(socket);
            t.start();
        }


    }

}

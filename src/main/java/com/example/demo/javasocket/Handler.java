package com.example.demo.javasocket;


import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Handler extends Thread {
    Socket sock;

    public Handler(Socket sock) {
        this.sock = sock;
    }

    @Override
    public void run() {
        try {
            InputStream input = sock.getInputStream();
            OutputStream output = sock.getOutputStream();
            handle(input, output);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                this.sock.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handle(InputStream input, OutputStream output) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        BufferedReader br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));

        bw.write("hello this is server\n");
        bw.flush();

        for (; ; ) {
            String s = br.readLine();
            if (Objects.equals(s, "exit")) {
                bw.write("exit\n");
                bw.flush();
                break;
            }
            bw.write("receive from client: " + s);
            bw.flush();
        }


    }

}

package com.example.demo.javasocket;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket sock = new Socket("localhost", 6666);
        try {
            InputStream input = sock.getInputStream();
            OutputStream output = sock.getOutputStream();
            handle(input, output);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    private static void handle(InputStream input, OutputStream output) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        Scanner sc = new Scanner(System.in);
        System.out.println("[server]" + br.readLine());
        for (; ; ) {
            System.out.println(">>> ");
            String s = sc.nextLine();
            bw.write(s);
            bw.newLine();
            bw.flush();

            String resp = br.readLine();
            System.out.println("<<<" + resp);
            if (Objects.equals(resp, "exit")) {
                break;
            }


        }

    }

}

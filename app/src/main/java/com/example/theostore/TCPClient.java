package com.example.theostore;

import android.os.SystemClock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient {

    private static final String theostoreAddress = "10.0.2.2";
    private static final int theostorePort = 5000;

//    private static Socket getNewSocket() throws IOException {
//        return new Socket(theostoreAddress, theostorePort);
//    }

    public static class RetrieveTrayRunnable implements Runnable {
        String tray_id;

        RetrieveTrayRunnable(String tray_id) {
            this.tray_id = tray_id;
        }

        @Override
        public void run() {
            try {
//                Socket s = getNewSocket();
                Socket s = new Socket(theostoreAddress, theostorePort);
                PrintWriter writer = new PrintWriter(s.getOutputStream(),true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));

                writer.print("GET " + tray_id);
                writer.flush();

                boolean ack = false;
                while (!ack) {
                    SystemClock.sleep(500);
                    if (reader.ready()) {
                        System.out.println(reader.readLine());
                        ack = true;
                    }
                }
                s.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class StoreTrayRunnable implements Runnable {
        String tray_id;

        StoreTrayRunnable(String tray_id) {
            this.tray_id = tray_id;
        }

        @Override
        public void run() {
            try {
                Socket s = new Socket(theostoreAddress, theostorePort);
                PrintWriter writer = new PrintWriter(s.getOutputStream(),true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));

                SystemClock.sleep(600);

                writer.print("PUT " + tray_id);
                writer.flush();

                boolean ack = false;
                while (!ack) {
                    SystemClock.sleep(500);
                    if (reader.ready()) {
                        System.out.println(reader.readLine());
                        ack = true;
                    }
                }
                s.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
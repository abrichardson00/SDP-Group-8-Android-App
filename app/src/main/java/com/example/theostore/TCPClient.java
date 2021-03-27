package com.example.theostore;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class TCPClient {

//    private static final String theostoreAddress = "10.0.2.2";
    private static final String theostoreAddress = "192.168.1.210";
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

        ProgressBar progressBar;
        TextView progressText;
        TrayDatabase trayDatabase;

        StoreTrayRunnable(Context context, ProgressBar progressBar, TextView progressText) {
            this.progressBar = progressBar;
            this.progressText = progressText;
            this.trayDatabase = new TrayDatabase(context);
        }

        @Override
        public void run() {

            try {
                Socket s = new Socket(theostoreAddress, theostorePort);
                PrintWriter writer = new PrintWriter(s.getOutputStream(),true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));

                writer.print("PUT");
                writer.flush();

                while (true) {
                    SystemClock.sleep(100);
                    if (reader.ready()) {
                        if (reader.readLine().equals("ACK")) {
                            break;
                        } else {
                            System.out.println("Problem");
                        }
                    }

                }

                boolean done = false;

                String tray_code = null;

                while (!done) {
                    SystemClock.sleep(100);
                    if (reader.ready()) {
                        String command = reader.readLine();
                        switch (command) {
                            case "CHECKING":
                                updateProgress(20, "Checking tray");
                                break;
                            case "TO_SHELF":
                                updateProgress(40, "Moving to shelf");
                                break;
                            case "STORING":
                                updateProgress(60, "Storing tray");
                                break;
                            case "RETURNING":
                                updateProgress(80, "Returning");
                                List<String> extractedInfo = getObjectRecognitionString(tray_code);

                                if (extractedInfo == null){
                                    System.out.println("Couldn't read remote file.");
                                    break;
                                }

                                float capacity = Float.parseFloat(extractedInfo.get(0));
                                String info = extractedInfo.get(1);

                                trayDatabase.changeExtractedInfoWithTrayCode(tray_code, info, capacity);
                                trayDatabase.markTrayStored(tray_code);
                                break;
                            case "DONE":
                                updateProgress(100, "Done!");
                                done = true;
                                break;
                            case "BAD":
                                updateProgress(0, "No tray inserted");
                                done = true;
                                break;
                            default:
                                if (command.contains("OK")) {
                                    tray_code = command.substring(3);
                                } else {
                                    updateProgress(0, "Error");
                                    done = true;
                                }
                                break;
                        }
                    }
                }

                s.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void updateProgress(int percentDone, String infoText) {

            progressText.post(new Runnable() {
                @Override
                public void run() {
                    progressText.setText(infoText);
                }
            });

            progressBar.post(new Runnable() {
                @Override
                public void run() {
//                    progressBar.setProgress(percentDone);
                    ObjectAnimator.ofInt(progressBar, "progress", percentDone)
                            .setDuration(500)
                            .start();
                }
            });
        }

        private List<String> getObjectRecognitionString(String tray_code) {
            try {

                String adr = Tray.IMAGE_SERVER_ADDRESS + tray_code + ".txt";

                URL url = new URL(adr);

                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                InputStream input = c.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                List<String> trayInfo = new ArrayList<>();

                String line;
                while ((line = reader.readLine()) != null) {
                    trayInfo.add(line);
                }
                reader.close();
                c.disconnect();
                return trayInfo;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

    }

}
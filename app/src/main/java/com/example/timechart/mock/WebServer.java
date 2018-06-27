package com.example.timechart.mock;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;

public class WebServer implements Runnable {

    private final int INIT_PORT = 8080;
    private boolean mIsRunning;
    private int mPort;
    private ServerSocket mServerSocket;

    public String getLocalWebServer() {
        return "http://localhost:" + ((Integer) mPort).toString() + "/";
    }

    public void start() {
        mIsRunning = true;
        new Thread(this).start();
    }

    public void stop() {
        try {
            mIsRunning = false;
            if (null != mServerSocket) {
                mServerSocket.close();
                mServerSocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findFreePort(int initPort) throws IOException {
        mPort = initPort;
        try {
            mServerSocket = new ServerSocket(mPort);
        } catch (IOException e) {
            if (mPort > INIT_PORT + 10000) {
                throw new IOException();
            }
            findFreePort(mPort + 1);
        }
    }

    @Override
    public void run() {
        try {
            findFreePort(INIT_PORT);
            while (mIsRunning) {
                Socket socket = mServerSocket.accept();
                if (socket.getInetAddress().isLoopbackAddress()) {
                    handle(socket);
                }
                socket.close();
            }
        } catch (SocketException ignore) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handle(Socket socket) throws IOException {
        BufferedReader reader = null;
        PrintStream output = null;
        try {
            String route = null;
            // Read HTTP headers and parse out the route.
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while (!TextUtils.isEmpty(line = reader.readLine())) {
                if (line.startsWith("GET /")) {
                    int start = line.indexOf('/') + 1;
                    int end = line.indexOf(' ', start);
                    route = line.substring(start, end);
                    break;
                }
            }
            // Output stream that we send the response to
            output = new PrintStream(socket.getOutputStream());

            // Prepare the content to send.
            if (null == route) {
                writeServerError(output);
                return;
            }
            byte[] bytes = null;
            if (route.startsWith("timechart")) {
                bytes = loadContent(route);
            }

            if (null == bytes) {
                writeServerError(output);
                return;
            }

            // Send out the content.
            output.print("HTTP/1.0 200 OK\r\n");
            output.print("Content-Type: application/octet-stream\r\n");
            output.print("Content-Length: " + bytes.length + "\r\n");
            output.print("\r\n");
            output.write(bytes);
            output.flush();
        } finally {
            if (null != output) {
                output.close();
            }
            if (null != reader) {
                reader.close();
            }
        }
    }

    private byte[] loadContent(String route) throws IOException {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] body = getBody(route);
            output.write(body, 0, body.length);
            output.flush();
            return output.toByteArray();
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private byte[] getBody(String route) {
        long startTime = getStartTime(route);
        long endTime = getEndTime(route);
        StringBuilder bodyBuilder = new StringBuilder("");
        if (endTime > startTime) {
            long delta = (endTime - startTime) / 15;
            Random rand = new Random(startTime);
            for (long i = startTime; i <= endTime; i += delta) {
                bodyBuilder.append(i);
                bodyBuilder.append(":");
                bodyBuilder.append(rand.nextInt(100));
                bodyBuilder.append(",");
            }
            bodyBuilder.deleteCharAt(bodyBuilder.length()-1);
        }
        return bodyBuilder.toString().getBytes();
    }

    private long getStartTime(String route) {
        int start = "timechart?start_time=".length();
        int end = route.indexOf("&");
        if (end > start) {
            return Long.valueOf(route.substring(start, end));
        }
        return 0;
    }

    private long getEndTime(String route) {
        int start = route.indexOf("end_time=");
        if (start > -1) {
            start += "end_time=".length();
            return Long.valueOf(route.substring(start));
        }
        return 0;
    }

    private void writeServerError(PrintStream output) {
        output.println("HTTP/1.0 500 Internal Server Error");
        output.flush();
    }
}
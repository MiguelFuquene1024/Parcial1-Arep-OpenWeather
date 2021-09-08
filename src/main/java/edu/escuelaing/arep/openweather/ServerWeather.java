/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arep.openweather;

import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Miguel
 */
public class ServerWeather {

    private static final ServerWeather instance = new ServerWeather();
    private static final HashMap<String, String> contentList = new HashMap<String, String>();

    public static ServerWeather getInstance() {
        contentList.put("html", "text/html");
        contentList.put("css", "text/css");
        contentList.put("js", "text/javascript");
        contentList.put("jpeg", "image/jpeg");
        contentList.put("jpg", "image/jpg");
        contentList.put("png", "image/png");
        return instance;
    }

    public ServerWeather() {

    }

    public void start() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Ready to deploy");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            
        }
        serverSocket.close();

    }

    
    private int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35000; //returns default port if heroku-port isn't set (i.e. on localhost)    
    }

    public static void main(String[] args) throws IOException {
        HttpServer.getInstance().start();
    }

}

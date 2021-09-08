/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arep.openweather;

import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 *
 * @author Miguel Fuquene
 */
public class ServerWeather {

    private static final ServerWeather instance = new ServerWeather();
    private static final HashMap<String, String> contentList = new HashMap<String, String>();
    private static final String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?q=bogot%C3%A1&appid=" + "99993786b64ca9a49cabd7fe9b6cdcc1";

    private static final String TEXT_MESSAGE_OK = "HTTP/1.1 200 OK\n"
            + "Content-Type: text/html\r\n"
            + "\r\n";
    private static final String JSON_MESSAGE = "HTTP/1.1 200 OK\n"
            + "Content-Type: application/json\r\n"
            + "\r\n";
    private static final String HTTP_MESSAGE_NOT_FOUND = "HTTP/1.1 404 Not Found\n"
            + "Content-Type: text/html\r\n"
            + "\r\n";

    public static ServerWeather getInstance() {
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
                System.out.println("Listo para recibir...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            try {
                serverConnection(clientSocket);
            } catch (URISyntaxException e) {
                System.err.println("URI incorrect.");
                System.exit(1);
            }
        }
        serverSocket.close();

    }

    public void serverConnection(Socket clientSocket) throws IOException, URISyntaxException {
        OutputStream outStream = clientSocket.getOutputStream();
        PrintWriter out = new PrintWriter(outStream, true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        ArrayList<String> request = new ArrayList<>();

        while ((inputLine = in.readLine()) != null) {
            if (inputLine.startsWith("GET")) {
                System.out.println("Received: " + inputLine);
                generateResource(clientSocket.getOutputStream(), inputLine);
            }

            if (!in.ready()) {
                break;
            }
        }
        out.close();
        in.close();
        clientSocket.close();
    }

    private void generateResource(OutputStream out, String inputLine) {
        String type = inputLine.split(" ")[1].replace("/", "");
        if (type.length() == 0) {
            type = "clima";
        }
        if (type.equals("clima")) {
            computeDefaultResponse(out);
        } else if (type.contains("consulta?lugar=")) {
            computeDataResource(out, type);
        } else {

        }
    }

    public void computeDefaultResponse(OutputStream out) {
        String outputLine = TEXT_MESSAGE_OK;
        outputLine += "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<title>Weather Consult</title>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.8\">\n"
                + "<title>AREP</title>"
                + "<link rel=\"stylesheet\" href=\"./css/index.css\">\n"
                + "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We\" crossorigin=\"anonymous\">"
                + "</head>\n"
                + "<body>\n"
                + "<main class='main-container'>\n"
                + "<h1 class=\"title\">OpenWeather</h1>"
                + "<section class=\"mdv-list\">\n"
                + "<div class=\"mdv-card\">\n"
                + "<h4 class=\"label label-primary\">Ingrese la ciudad:</h4>\n"
                + "<input type=\"text\" id=\"city\" >\n"
                + "<br>\n"
                + "<button id=\"submit\"type=\"button\" class=\"btn btn-primary\">Buscar</button>\n"
                + "</h2>\n"
                + "<br>\n"
                + "</div>\n"
                + "</section>\n"
                + "</main>\n"
                + "</body>\n"
                + "<script>\n"
                + "document.addEventListener(\"DOMContentLoaded\", () => {\n"
                + "document.getElementById(\"submit\").addEventListener(\"click\", () => {\n"
                + "var request = \"consulta?lugar=\" + document.getElementById(\"city\").value\n"
                + "window.location.replace(request);\n"
                + "})\n"
                + "});\n"
                + "</script>\n"
                + "</html>";
        try {
            out.write(outputLine.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void computeDataResource(OutputStream out, String input) {
        System.out.println("RECEIVED: " + input);
        String content = JSON_MESSAGE;
        try {
            input = input.replace("consulta?lugar=", "");
            InputStream is = new URL(weatherUrl.replaceFirst("cityname", input)).openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = br.read()) != -1) {
                sb.append((char) cp);
            }
            content += sb.toString();
            br.close();
            out.write(content.getBytes());
        } catch (IOException e) {
            System.err.format("Response not found %s%n", e);
        }
    }

    private int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35000; //returns default port if heroku-port isn't set (i.e. on localhost)    
    }

    public static void main(String[] args) throws IOException {
        //System.out.println("Hello World");
        ServerWeather.getInstance().start();

    }

}

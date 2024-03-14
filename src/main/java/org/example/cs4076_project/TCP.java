package org.example.cs4076_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCP {
    private static int PORT = 9999;
    private static InetAddress IP;
    Socket link = null;
    BufferedReader in;
    PrintWriter out;

    public TCP() {
        try {
            IP = InetAddress.getLocalHost();
        }
        catch(UnknownHostException e) {
            System.out.println("IP of local machine not found!");
        }

        try {
            link = new Socket(IP,PORT);
            in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            out = new PrintWriter(link.getOutputStream(),true);
        }
        catch(IOException e)
        {
            System.out.println("Unable to establish socket to server!");
        }
    }

    public String send(String message) {
        String response = "";
        out.println(message);
        try {
            response = in.readLine();
        } catch (IOException e) {
            return "ERR: Response from server was not received!";
        }
        return response;
    }
}

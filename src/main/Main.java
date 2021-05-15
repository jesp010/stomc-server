package main;

import server.Server;

public class Main {

    static Server server = new Server();

    public static void main (String [] args) {

        Thread serverThread = new Thread(server);
        serverThread.start();

    }
}

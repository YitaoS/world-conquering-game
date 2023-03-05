package edu.duke.team8.riskgame;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ServerTest {
    @Test
    public void testConstructor() throws IOException {
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));

        Server s = new Server(1236, m);
        assertEquals(1236, s.getPort());
    }
    @Test()
    public void testIOException() throws Exception {
        ServerSocket ss = new ServerSocket(1239);
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Thread serverThread = new Thread(() -> {
            Server s = new Server(ss, m, output, 1);
            s.run();
        });
        serverThread.start();

        ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
        PrintStream output1 = new PrintStream(bytes1, true);
        Socket cliSocket = new Socket("localhost", 1239);
        Client cli = new Client(cliSocket, output1);
        cliSocket.close();
        cli.run();

        serverThread.join();
        ss.close();
        assertEquals("Out/Input stream error\n", bytes1.toString());
    }
    private void checkClientHelper(String expected) throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Client cli = new Client(1216, "localhost", output);
        cli.run();
        assertEquals(expected, bytes.toString());
    }
    @Test
    public void testRun() throws Exception {

        ServerSocket ss = new ServerSocket(1216);
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));

        Thread serverThread = new Thread(() -> {
            Server s = new Server(ss, m);
            s.run();
        });
        serverThread.start();

        checkClientHelper("Green\nPlanto\n");
        checkClientHelper("Red\nPlanto\n");
        checkClientHelper("Blue\nPlanto\n");
        checkClientHelper("Yellow\nPlanto\n");

        serverThread.join();
        ss.close();

    }
    @Test
    public void testConnectOneClient() throws Exception {

        ServerSocket ss = new ServerSocket(1256);
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));


        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);

        Client cli = new Client(1256, "localhost", output);
        Thread clientThread = new Thread(() -> cli.run());
        clientThread.start();

        Server s = new Server(ss, m);
        s.connectOneClient(1);

        clientThread.join();
        ss.close();
        assertEquals("Red\nPlanto\n", bytes.toString());

    }
    @Test
    public void testSend() throws Exception {
        ServerSocket ss = new ServerSocket(1230);
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);

        Client cli = new Client(1230, "localhost", output);
        Thread clientThread = new Thread(() -> cli.run());
        clientThread.start();

        Server s = new Server(ss, m);
        System.out.println("Waiting for client connection...");
        Socket client = ss.accept();
        System.out.println("Client connected!");
        s.send(client, 0);
        client.close();

        clientThread.join();
        ss.close();
        assertEquals("Green\nPlanto\n", bytes.toString());

    }

}
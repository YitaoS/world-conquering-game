package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ServerTest {
    @Test
    public void testConstructor() throws IOException {
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));

        Server s = new Server(1236, m, 4);
        assertEquals(1236, s.getPort());
    }
    @Test()
    public void testIOException() throws Exception {
        AbstractMapFactory factory = new V1MapFactory();
        Map m = factory.createMap(1);
        View mapView = new MapTextView(m);

        // Create mock objects
        ServerSocket mockSs = mock(ServerSocket.class);
        Socket mockSocket = mock(Socket.class);
        // Set up mock socket
        when(mockSs.accept()).thenReturn(mockSocket);

        // Create client thread with mock socket
        Server s = new Server(mockSs, m, 1);
        doThrow(new IOException("Socket closed")).when(mockSs).accept();
        Thread serverThread = new Thread(() -> {
            s.run();
        });
        serverThread.start();
        Thread.sleep(100);
        s.stop();
        serverThread.interrupt();
        serverThread.join();
    }
    private void checkClientHelper(ByteArrayOutputStream bytes, Client cli, String expected) throws Exception {
        cli.run();
        String actual = bytes.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
        assertEquals(expected, actual);
    }
    @Test
    public void testRun() throws Exception {

        ServerSocket ss = new ServerSocket(1216);

        AbstractMapFactory factory = new V1MapFactory();
        Map m = factory.createMap(2);

        Server s = new Server(ss, m, 2);
        Thread serverThread = new Thread(() -> {
            s.run();
        });
        serverThread.start();


        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Client cli = new Client(1216, "localhost", output);

        ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
        PrintStream output1 = new PrintStream(bytes1, true);
        Client cli1 = new Client(1216, "localhost", output1);

        checkClientHelper(bytes, cli, "Green\n" +
                "Green Player:\n" +
                "-------------\n" +
                "0 units in a1 (next to: b1, a2)\n" +
                "0 units in a2 (next to: b2, a3)\n" +
                "0 units in a3 (next to: b3, a4)\n" +
                "0 units in a4 (next to: b4, a5)\n" +
                "0 units in a5 (next to: b5, a6)\n" +
                "0 units in a6 (next to: b6)\n" +
                "Red Player:\n" +
                "-------------\n" +
                "0 units in b1 (next to: b2)\n" +
                "0 units in b2 (next to: b3)\n" +
                "0 units in b3 (next to: b4)\n" +
                "0 units in b4 (next to: b5)\n" +
                "0 units in b5 (next to: b6)\n" +
                "0 units in b6 (next to: )\n");
        checkClientHelper(bytes1, cli1, "Red\n" +
                "Green Player:\n" +
                "-------------\n" +
                "0 units in a1 (next to: b1, a2)\n" +
                "0 units in a2 (next to: b2, a3)\n" +
                "0 units in a3 (next to: b3, a4)\n" +
                "0 units in a4 (next to: b4, a5)\n" +
                "0 units in a5 (next to: b5, a6)\n" +
                "0 units in a6 (next to: b6)\n" +
                "Red Player:\n" +
                "-------------\n" +
                "0 units in b1 (next to: b2)\n" +
                "0 units in b2 (next to: b3)\n" +
                "0 units in b3 (next to: b4)\n" +
                "0 units in b4 (next to: b5)\n" +
                "0 units in b5 (next to: b6)\n" +
                "0 units in b6 (next to: )\n");

        s.stop();
        serverThread.join();
        ss.close();

    }
//    @Test
//    public void testHasSocket() throws Exception {
//
//        ServerSocket ss = new ServerSocket(1219);
//        Map m = new Game1Map();
//        m.addTerritory(new BasicTerritory("Planto"));
//
//        Server s = new Server(ss, m, 1);
//        Thread serverThread = new Thread(() -> {
//            s.run();
//        });
//        serverThread.start();
//
//        ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
//        PrintStream output1 = new PrintStream(bytes1, true);
//        Socket cliSocket = new Socket("localhost", 1219);
//        Client cli = new Client(cliSocket, output1);
//        cli.run();
//
//        s.stop();
//        serverThread.join();
//        ss.close();
//        String actual = bytes1.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
//        assertEquals("Green\nGreen Player:\n-------------\n0 units in Planto (next to: )\n", actual);
//    }

}

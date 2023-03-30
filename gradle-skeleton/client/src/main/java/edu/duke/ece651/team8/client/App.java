/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.team8.client;
import edu.duke.ece651.team8.shared.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.application.Application;
import javafx.stage.Stage;

public class App  extends Application{

  @Override
  public void start(Stage stage) throws IOException {
    GUI gui=new GUI(960, 720);
    gui.LoginScene(stage);
    new Thread(() -> {
      try {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String hostname = "localhost";//"vcm-32232.vm.duke.edu";//"localhost";
        int port = 8080; // set the port number
        ClientGUI clientGUI = new ClientGUI(port, hostname, in, gui); // create a new client instance
        clientGUI.run(); // connect the client to the server
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }).start();
  }


  //text player
  /*
  @Override
  public void start(Stage stage) throws IOException {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String hostname = "localhost";//"vcm-32232.vm.duke.edu";//"localhost";
    int port = 8080; // set the port number
    Client client = new Client(port, hostname,in); // create a new client instance
    client.run(); // connect the client to the server
  }*/

  /**
   * This main method runs the client, on localhost and port 1651.
   * Specifically, it creates an instance and calls run.
   * @param args is the command line arguments.  These are currently ignored.
   * @throws IOException if creation of the ServerSocket fails.
  */
  public static void main(String[] args) throws IOException {
    //use this part to have UI
    launch();

    //use this part to have text output
    /*BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String hostname = "localhost";//"vcm-32232.vm.duke.edu";//"localhost";
    int port = 8080; // set the port number
    Client client = new Client(port, hostname,in); // create a new client instance
    client.run(); // connect the client to the server*/
  }

}

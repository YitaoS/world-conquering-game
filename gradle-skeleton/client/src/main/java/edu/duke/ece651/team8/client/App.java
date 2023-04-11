/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.team8.client;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.ServerError;
import javafx.application.Platform;
import edu.duke.ece651.team8.client.controller.StartPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


//public class App  extends Application{
//
//
//    private Stage stage;
//
//    //GUI
////*
//    private ServerStream serverStream;
//    @Override
//    public void start(Stage stage) throws IOException {
//        try{
//            String hostname = "localhost";//"vcm-32232.vm.duke.edu";//"localhost";
//            int port = 8080; // set the port number
//            this.stage = stage;
//            this.serverStream = new ServerStream(hostname,port);
//
//            //FXMLLoader loaderStart = new FXMLLoader(App.class.getResource("/fxml/StartPage.fxml"));
//            //loaderStart.setControllerFactory(c -> new StartPageController(stage,serverStream));
//            FXMLLoader loaderStart = new FXMLLoader(App.class.getResource("/fxml/StartPage.fxml"));
//            loaderStart.setControllerFactory(c -> new StartPageController(stage,serverStream));
//            Scene scene = new Scene(loaderStart.load());
//            stage.setScene(scene);
//            stage.show();
//
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//        }
//    }//*/
//
//    //text player
///*
//    @Override
//    public void start(Stage stage) throws IOException {
//        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//        String hostname = "localhost";//"vcm-32232.vm.duke.edu";//"localhost";
//        int port = 8080; // set the port number
//        Client client = new Client(port, hostname,in); // create a new client instance
//        client.run(); // connect the client to the server
//    }//*/
//
//
//
//    //there are two start functions
//    //one for text one for gui
//    /**
//     * This main method runs the client, on localhost and port 1651.
//     * Specifically, it creates an instance and calls run.
//     * @param args is the command line arguments.  These are currently ignored.
//     * @throws IOException if creation of the ServerSocket fails.
//     */
//    public static void main(String[] args) throws IOException {
//        launch();
//    }
public class App{


    private Stage stage;

    //GUI
/*
    private ServerStream serverStream;
    @Override
    public void start(Stage stage) throws IOException {
        try{
            String hostname = "localhost";//"vcm-32232.vm.duke.edu";//"localhost";
            int port = 8080; // set the port number
            this.stage = stage;
            this.serverStream = new ServerStream(hostname,port);

            //FXMLLoader loaderStart = new FXMLLoader(App.class.getResource("/fxml/StartPage.fxml"));
            //loaderStart.setControllerFactory(c -> new StartPageController(stage,serverStream));
            FXMLLoader loaderStart = new FXMLLoader(App.class.getResource("/fxml/StartPage.fxml"));
            loaderStart.setControllerFactory(c -> new StartPageController(stage,serverStream));
            Scene scene = new Scene(loaderStart.load());
            stage.setScene(scene);
            stage.show();

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }//*/

    //text player
//*
    @Override
    public void start(Stage stage) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String hostname = "localhost";//"vcm-32232.vm.duke.edu";//"localhost";
        int port = 8080; // set the port number
        Client client = new Client(port, hostname,in); // create a new client instance
        client.run(); // connect the client to the server
    }//*/



    //there are two start functions
    //one for text one for gui
    /**
     * This main method runs the client, on localhost and port 1651.
     * Specifically, it creates an instance and calls run.
     * @param args is the command line arguments.  These are currently ignored.
     * @throws IOException if creation of the ServerSocket fails.
     */
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String hostname = "localhost";//"vcm-32232.vm.duke.edu";//"localhost";
        int port = 8080; // set the port number
        Client client = new Client(port, hostname,in); // create a new client instance
        client.run(); // connect the client to the server
    }


}

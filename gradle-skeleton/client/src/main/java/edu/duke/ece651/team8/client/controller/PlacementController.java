package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.control.*;
import javafx.application.Platform;

import javafx.fxml.Initializable;

import java.io.*;
import java.util.concurrent.LinkedBlockingQueue;

public class PlacementController {

    public ServerStream serverStream;
    private Stage stage;

    private int placementCount = 0;
    private int total=36;
    @FXML
    Label color;

    @FXML
    TextField input;

    @FXML
    Label message;
    @FXML
    Label errorMessage;

    @FXML
    Button enter;


    private void setColor(String colors){
        Platform.runLater(() -> {
            color.setText("Player: " + colors);
        });
    }

    private void setMessage(String messages){
        Platform.runLater(() -> {
            message.setText("Message: " + messages);
        });
    }

    private void setErrorMessage(String errorMessages){
        Platform.runLater(() -> {
            errorMessage.setText("Error: " + errorMessages);
        });
    }

    public PlacementController(Stage stage, ServerStream ss, String colors, String messages) {
        this.stage = stage;
        this.serverStream = ss;
        setColor(colors);
        setMessage(messages);
    }

    @FXML
    public void enter() throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/ActionPage.fxml"));
        try {
            String s =input.getText();
            input.clear();
            setErrorMessage("");
            if(Integer.parseInt(s) <= 0){
                throw new IllegalArgumentException("Units number should be non_negative number");
            }else if(Integer.parseInt(s) > total-5+placementCount){
                throw new IllegalArgumentException("Units number too big");
            }
            else{
                serverStream.send(s);
                total-=Integer.parseInt(s);
            }
            setErrorMessage(serverStream.read());
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }

        if(serverStream.getBuffer().equals("valid\n")){
            placementCount+=1;
            setMessage(serverStream.read());
        }
        if(placementCount>=5){
            String map = serverStream.read();
            System.out.println("map: "+map);

            loaderStart.setControllerFactory(c->{
                return new ActionController(stage,serverStream,color.getText(), "Please enter action");
            });
            Scene scene = new Scene(loaderStart.load());
            stage.setScene(scene);
            stage.show();
        }
    }
}


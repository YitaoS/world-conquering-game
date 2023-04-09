package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public abstract class GameController {
    public ServerStream serverStream;
    public Stage stage;
    public String colorS, messageS, mapS;
    public int playerNum;
    public String territoryNames[]={"a1","a2","a3","a4","a5","a6","b1","b2","b3","b4","b5","b6","c1","c2","c3","c4","c5","c6","d1","d2","d3","d4","d5","d6"};


    public ArrayList<String> territoryColors = new ArrayList<>();
    public HashMap<String,String> territoryArmys = new HashMap<>();
    @FXML
    Label color, message, errorMessage;
    @FXML
    Button enter;
    @FXML
    Circle a1,a2,a3,a4,a5,a6,b1,b2,b3,b4,b5,b6,c1,c2,c3,c4,c5,c6,d1,d2,d3,d4,d5,d6;

    public Circle circles[];

    public GameController(Stage stage, ServerStream ss, String colorS, String messageS, String mapS, int playerNum){
        this.stage = stage;
        this.serverStream = ss;
        this.colorS=colorS;
        this.messageS=messageS;
        this.mapS=mapS;
        this.playerNum=playerNum;
    }
    public void initialize() {
        setColor(colorS);
        setMessage(messageS);
        circles=new Circle[]{a1,a2,a3,a4,a5,a6,b1,b2,b3,b4,b5,b6,c1,c2,c3,c4,c5,c6,d1,d2,d3,d4,d5,d6};
        disappearCircle();
        setMap(mapS);
    }

    public void setColor(String colors){
        Platform.runLater(() -> {
            color.setText("Player: " + colors);
        });
    }
    public void setMessage(String messages){
        Platform.runLater(() -> {
            message.setText("Message: " + messages);
        });
    }
    public void setErrorMessage(String errorMessages){
        Platform.runLater(() -> {
            errorMessage.setText("Error: " + errorMessages);
        });
    }
    private void parseMap(){
        JSONObject jsonObj = new JSONObject(mapS);
        JSONObject map = jsonObj.getJSONObject("map");
        territoryColors=new ArrayList<>();
        int i=0;
        for (String territoryName: territoryNames){
            JSONObject t = map.getJSONObject(territoryName);
            String armyT = t.getString("army");
            String colorT = t.getString("color");
            territoryArmys.put(territoryName,armyT);
            territoryColors.add(colorT);
            i++;
            if(i>=6*playerNum){
                break;
            }
        }
    }
    public void setMap(String map){
        mapS=map;
        parseMap();
        int i=0;
        for(Circle c: circles){
            if(territoryColors.get(i).equals("Green")){
                c.setFill(Color.GREEN);
            }
            else if(territoryColors.get(i).equals("Red")){
                c.setFill(Color.RED);
            }
            else if(territoryColors.get(i).equals("Blue")){
                c.setFill(Color.BLUE);
            }
            else if(territoryColors.get(i).equals("Yellow")){
                c.setFill(Color.YELLOW);
            }
            i++;
            if(i>=6*playerNum){
                break;
            }
        }
        System.out.println(map);
    }
    public void disappearCircle(){
        int i=0;
        for(Circle c: circles){
            if(i>=6*playerNum){
                c.setVisible(false);
            }
            i++;
        }
    }

}

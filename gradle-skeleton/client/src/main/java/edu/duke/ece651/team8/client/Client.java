package edu.duke.ece651.team8.client;


import java.io.*;
import java.net.*;

/** Client pattern of the game*/
public class Client {
    protected ServerStream serverStream;
    /** Client socket for communicate with server */
    protected PrintStream out;
    /** Input stream of the client, like terminal input*/
    protected BufferedReader input;
    /** info to be transferred, entered by user */
    protected String mapInfo;
    /**client player color*/
    protected String combatOutcome;
    protected String color;
    /** Delimiter*/
    final String END_OF_TURN = "END_OF_TURN";

    protected boolean isDefeated = false;

    protected String winner;
    /**
     * Constructs a server with specified port
     *
     * @param port is the port of the socket
     * @param host is the address of the server
     */
    public Client(int port, String host,BufferedReader in) throws IOException {
        this.serverStream=new ServerStream(host,port);
        this.out = System.out;
        this.input = in;
        this.winner = "no winner";
    }


    /** execute the client */
    public void run() {
        try {
            receiveColor();
            receiveMap();
            displayColor();
            displayMap();
            doInitialPlacement();
            receivePlacementResult();
            doAllTurns();
            serverStream.close();
        } catch (IOException e) {
            out.println(e.getMessage());
        }
    }
    public void receivePlacementResult() throws IOException{
        out.println(serverStream.read());
        receiveMap();
        displayMap();
    }
    public void doAllTurns() throws IOException {
        while(!isOver()) {//keep running if no one wins
            if(!isDefeated){
                doOneTurn();
            }
            reportResult();
//            System.out.println("outcome reach");
        }
    }
    public void reportResult() throws IOException{
        receiveCombatOutcome();
        displayCombatOutcome();
        receiveMap();
        displayMap();

        receiveLoseStatus();
        if (isDefeated){
            out.println("You lose.");
        }

        receiveWinner();
        if(isOver()){
            if(color.equals(winner)){
                out.println("Congratulations! You win!");
            }else {
                out.println(winner+" wins.");
            }

        }

    }

    /**
     * receive color string from server
     * @throws IOException if something wrong with receive
     */
    public void receiveColor()throws  IOException{
        color = serverStream.read();
    }

    /**
     * receive map information from server
     * @throws IOException if something wrong with receive
     */
    public void receiveMap()throws  IOException{
        mapInfo = serverStream.read();
    }
    public void receiveCombatOutcome()throws  IOException{
        combatOutcome = serverStream.read();
    }

    public void receiveWinner()throws  IOException{
        winner = serverStream.read();
    }

    public void receiveLoseStatus()throws IOException{
        if(serverStream.read().equals("lose")){
            isDefeated = true;
        }
    }



    /**
     * do initial placement phase, user need to input
     * the number she wants to place in a specific territory
     * if input is invalid, she needs to re-input
     * @throws IOException if something wrong with receive
     */
    /*public void doInitialPlacement() throws IOException{
        int placementTimes = Integer.parseInt(serverStream.read());
        for(int i = 0; i < placementTimes;i++){
            do {
                serverStream.receive();
                while (true) {
                    try {
                        tryInputUnitNumberToPlace(serverStream.getBuffer());
                    } catch (Exception e) {
                        out.println(e.getMessage());
                        out.println("Please input a valid placement!");
                        continue;
                    }
                    break;
                }
                out.println(serverStream.read());
            } while (!serverStream.getBuffer().equals("valid\n"));
        }
    }*/
    public void doInitialPlacement() throws IOException{
        int placementTimes = Integer.parseInt(serverStream.read());
        serverStream.receive();
        int i=0;
        while(i < placementTimes-1){
            if(serverStream.getBuffer().equals("valid\n")){
                serverStream.receive();
                i++;
            }
            try {
                tryInputUnitNumberToPlace(serverStream.getBuffer());
                out.println(serverStream.read());
            } catch (Exception e) {
                out.println(e.getMessage());
            }
        }
    }

    /**
     * User input the unit number to place
     * @param prompt the prompt for placement
     * @throws Exception if something wrong with receive
     */
    public void tryInputUnitNumberToPlace(String prompt)throws Exception{
        out.print(prompt);
        String s = input.readLine();
        if(isPositiveInt(s)){
            serverStream.send(s);
        }else{
            throw new IllegalArgumentException("Units number should be non_negative number");
        }
    }

    /**
     * Determine if a string is a non-negative number string
     * @param number the string to be judged
     * @return true is >=0. Otherwise, false
     */
    public boolean isPositiveInt(String number){
        try{return Integer.parseInt(number) > 0;}
        catch(Exception e) {
            return false;
        }
    }

    /**
     * Player do her turn
     * @throws IOException if something wrong with receive
     */
    public void doOneTurn()throws IOException{
        String choice;
        label:
        while(true) {
            serverStream.receive();
            while (true) {
                try {
                    choice = tryChooseOneAction(serverStream.getBuffer());
                } catch (IllegalArgumentException e) {
                    out.println(e.getMessage());
                    System.out.println("Please input an valid action choice");
                    continue;
                }
                break;
            }
            switch (choice) {
                case "M":
                    if (doOneMove().equals("")) {
                        System.out.println("Successfully moved\n");
                    }
                    else{
                        System.out.println("Action failed\n");
                    }
                    break;
                case "A":
                    if (doOneAttack().equals("")) {
                        System.out.println("Successfully attacked\n");
                    }
                    else{
                        System.out.println("Action failed\n");
                    }
                    break;
                case "D":
                    break label;
            }
        }
    }

    /**
     * player choose one action order to do
     * @param prompt to prompt for action choices
     * @return input choice string
     * @throws IllegalArgumentException illegal input choice
     * @throws IOException if something wrong with receive
     */
    public String tryChooseOneAction(String prompt)throws IllegalArgumentException,IOException{
        out.print(prompt);
        String s = input.readLine();
        if(isValidChoice(s)){
//            System.out.println("xxxxxx"+s + "xxxxxx");
            serverStream.send(s);
            return s;
        }else{
            throw new IllegalArgumentException("Action should be \"M\"(move) \"A\"(attack) or \"D\"(done)");
        }
    }

    /**
     * Determine if a string is a valid choice string
     * @param s the string to be judged
     * @return true valid. Otherwise, false
     */
    public boolean isValidChoice(String s){
        return s.equals("M")||s.equals("A")||s.equals("D");
    }

    /**
     * Player do move phase
     * @throws IOException if something wrong with receive
     */
    public String doOneMove()throws IOException{
        trySendUnitNumber(serverStream.read());
        trySendTerritory(serverStream.read());
        trySendTerritory(serverStream.read());
        if(!serverStream.read().equals("")){
            out.println(serverStream.getBuffer());
        }
        return serverStream.getBuffer();
    }

    /**
     * try to send a valid unit number
     * @param prompt the prompt for input
     * @throws IllegalArgumentException invalid unit number input
     * @throws IOException if something wrong with receive
     */
    public void trySendUnitNumber(String prompt)throws IOException{
        out.println(prompt);
        String s = input.readLine();
        serverStream.send(s);
    }

    /**
     * try to send source territory
     * @param prompt the prompt for input
     * @throws IOException if something wrong with receive
     */
    public void trySendTerritory(String prompt)throws IOException{
        out.println(prompt);
        String s = input.readLine();
        serverStream.send(s);
    }


    /**
     * user do attack phase
     * @throws IOException if something wrong with receive
     * @return null if not actually conduct
     */
    public String doOneAttack()throws IOException{
        return doOneMove();
    }
    /**
     * Display map info
     */
    public void displayColor() {
        out.println(color);
    }

    /**
     * Display map to out
     */
    public void displayMap() {
        out.println(mapInfo);
    }

    public void displayCombatOutcome(){
        out.println(combatOutcome);
    }

    public boolean isOver(){
        return !winner.equals("no winner");
    }

}

package de.fhac.mazenet.server.userinterface.mazeFX;

import de.fhac.mazenet.server.config.Settings;
import de.fhac.mazenet.server.tools.Debug;
import de.fhac.mazenet.server.userinterface.mazeFX.util.BetterOutputStream;
import de.fhac.mazenet.server.userinterface.mazeFX.util.ImageResourcesFX;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Richard Zameitat on 25.05.2016.
 */
public class C_MainUI implements Initializable {

    @FXML
    private Pane rootPane;

    @FXML
    private StackPane parent3D;

    @FXML
    private SubScene sub3D;

    @FXML
    private Pane cntrls3D;

    @FXML
    private Slider camZoomSlide;

    @FXML
    private TextArea logArea;

    @FXML
    private Label serverStatusText;

    @FXML
    private Button serverStart;

    @FXML
    private Button serverStop;

    @FXML
    private Spinner<Number> maxPlayer;

    @FXML
    private VBox playerStatsContrainer;

    @FXML
    private Label playerStatsPlaceholder;
    
    public int getMaxPlayer(){
    	return maxPlayer.getValue().intValue();
    }

    @FXML
    private void camRotRightBtMousePress(MouseEvent evt){
        camRotateRightStartListeners.forEach(r->r.run());
    }

    @FXML
    private void camRotRightBtMouseRelease(MouseEvent evt){
        camRotateRightStopListeners.forEach(r->r.run());
    }

    @FXML
    private void camRotLeftBtMousePress(MouseEvent evt){
        camRotateLeftStartListeners.forEach(r->r.run());
    }

    @FXML
    private void camRotLeftBtMouseRelease(MouseEvent evt){
        camRotateLeftStopListeners.forEach(r->r.run());
    }

    @FXML
    private void serverStartAction(ActionEvent aevt){
    	//TODO
    	startServerListeners.forEach(r->r.run());
    }
    
    @FXML
    private void serverStopAction(ActionEvent aevt){
    	//TODO
    	stopServerListeners.forEach(r->r.run());
    }
    
    public Pane getParent3D() {
        return parent3D;
    }

    public SubScene getSub3D() {
        return sub3D;
    }

    public Slider getCamZoomSlide() {
        return camZoomSlide;
    }

    private List<Runnable> camRotateRightStartListeners = new LinkedList<>();
    public void addCamRotateRightStartListener(Runnable r){
        camRotateRightStartListeners.add(r);
    }

    private List<Runnable> camRotateRightStopListeners = new LinkedList<>();
    public void addCamRotateRightStopListener(Runnable r){
        camRotateRightStopListeners.add(r);
    }

    private List<Runnable> camRotateLeftStartListeners = new LinkedList<>();
    public void addCamRotateLeftStartListener(Runnable r){
        camRotateLeftStartListeners.add(r);
    }

    private List<Runnable> camRotateLeftStopListeners = new LinkedList<>();
    public void addCamRotateLeftStopListener(Runnable r){
        camRotateLeftStopListeners.add(r);
    }

    private List<Runnable> startServerListeners = new LinkedList<>();
    public void addStartServerListener(Runnable r){
    	startServerListeners.add(r);
    }
    
    private List<Runnable> stopServerListeners = new LinkedList<>();
    public void addStopServerListener(Runnable r){
    	stopServerListeners.add(r);
    }

    public void gameStarted(){
        serverStart.disableProperty().setValue(true);
        maxPlayer.disableProperty().setValue(true);
        serverStop.disableProperty().setValue(false);
        clearPlayerStats();
        ImageResourcesFX.reset();
        playerStatsPlaceholder.setVisible(true);
    }

    public void gameStopped(){
        serverStop.disableProperty().setValue(true);
        maxPlayer.disableProperty().setValue(false);
        serverStart.disableProperty().setValue(false);
    }

    public void addPlayerStat(Node statNode){
    	playerStatsPlaceholder.setVisible(false);
        playerStatsContrainer.getChildren().addAll(statNode);
        playerStatsContrainer.setPrefHeight(playerStatsContrainer.getChildren().size()*60);
    }

    public void clearPlayerStats(){
        playerStatsContrainer.getChildren().clear();
        playerStatsContrainer.setPrefHeight(playerStatsContrainer.getChildren().size()*60);

    }
    
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        Platform.runLater(()->Debug.addDebugger(new BetterOutputStream(s->Platform.runLater(()->logArea.appendText(s))), Settings.DEBUGLEVEL));
    }
}


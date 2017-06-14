package de.fhac.mazenet.server.userinterface.mazeFX;

import de.fhac.mazenet.server.userinterface.mazeFX.util.ImageResourcesFX;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * Created by Richard Zameitat on 26.05.2016.
 */
public class C_PlayerStat {
    @FXML
    private GridPane root;

    @FXML
    private Label teamId;

    @FXML
    private Label playerName;

    @FXML
    private ImageView treasureImage;

    @FXML
    private Label numFound;

    @FXML
    private Label numRemaining;
    
    @FXML
    private Label activePlayer;

    public void setTeamId(int playerId){
        this.teamId.textProperty().setValue(Integer.toString(playerId));
    }

    public void setPlayerName(String playerName){
        this.playerName.textProperty().setValue(playerName);
    }

	public void setTreasureImage(String treasureImage) {
		this.treasureImage.setImage(ImageResourcesFX.getImage(treasureImage));
	}

	public void setNumFound(int numFound) {
		this.numFound.setText(Integer.toString(numFound));
	}

	public void setNumRemaining(int numRemaining) {
		this.numRemaining.setText(Integer.toString(numRemaining));
	}
	
	public void setActive(boolean act){
		if(act){
			activePlayer.setText(">");
		}else{
			activePlayer.setText("");
		}
	}
}

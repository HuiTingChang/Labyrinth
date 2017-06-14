package de.fhac.mazenet.server.userinterface.mazeFX.objects;

import de.fhac.mazenet.server.Card;
import de.fhac.mazenet.server.config.Settings;
import de.fhac.mazenet.server.generated.CardType;
import de.fhac.mazenet.server.generated.TreasureType;
import javafx.beans.binding.Bindings;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;

import java.util.HashMap;

/**
 * Created by Richard Zameitat on 26.05.2016.
 */
public class CardFX extends Box {

    //private Box box;
    private TreasureFX treasure = null;
    private static final double TREASURE_OFFSET_Y = -0.1;

    private final static double
        SIZE_X = 1,
        SIZE_Y = 0.1,
        SIZE_Z = 1;

    private static final HashMap<Card.CardShape,Image> CARD_SHAPE_IMAGE_MAPPING;

    static {
        final String imgPre = Settings.IMAGEPATH;
        CARD_SHAPE_IMAGE_MAPPING = new HashMap<>();
        CARD_SHAPE_IMAGE_MAPPING.put(Card.CardShape.I,new Image(imgPre+"I0.png")); //$NON-NLS-1$
        CARD_SHAPE_IMAGE_MAPPING.put(Card.CardShape.L,new Image(imgPre+"L0.png")); //$NON-NLS-1$
        CARD_SHAPE_IMAGE_MAPPING.put(Card.CardShape.T,new Image(imgPre+"T0.png")); //$NON-NLS-1$

    }

    private PhongMaterial material;

    private CardFX(){
        super(SIZE_X,SIZE_Y,SIZE_Z);
        setDrawMode(DrawMode.FILL);
        setCullFace(CullFace.NONE);
        material = new PhongMaterial(Color.WHITE);
        setMaterial(material);
        setRotationAxis(new Point3D(0,1,0));
    }

    public CardFX(CardType ct, Group root3d){
        this();
        Card tC = new Card(ct);
        Card.CardShape cS = tC.getShape();
        material.setDiffuseMap(CARD_SHAPE_IMAGE_MAPPING.get(cS));
        switch (tC.getOrientation()){
            case D0:
                break;
            case D90:
                setRotate(90);
                break;
            case D180:
                setRotate(180);
                break;
            case D270:
                setRotate(270);
                break;
        }
        TreasureType tt = ct.getTreasure();
        if(tt!=null){
            treasure = new TreasureFX(tt);
            treasure.translateXProperty().bind(translateXProperty());
            treasure.translateYProperty().bind(Bindings.add(TREASURE_OFFSET_Y,translateYProperty()));
            treasure.translateZProperty().bind(translateZProperty());
            root3d.getChildren().add(treasure);
        }
    }

    public void removeFrom(Group root3d){
        root3d.getChildren().removeAll(this,treasure);
    }

	public TreasureFX getTreasure() {
		return treasure;
	}
    
    

    /*public void updateFromCardType(CardType ct){
        Card tC = new Card(ct);
        Card.CardShape cS = tC.getShape();
        material.setDiffuseMap(CARD_SHAPE_IMAGE_MAPPING.get(cS));
        switch (tC.getOrientation()){
            case D0:
                break;
            case D90:
                setRotate(90);
                break;
            case D180:
                setRotate(180);
                break;
            case D270:
                setRotate(270);
                break;
        }
    }/**/
}

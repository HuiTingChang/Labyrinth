package de.fhac.mazenet.server.userinterface.mazeFX.util;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.util.Duration;

/**
 * Created by Richard on 04.06.2016.
 */
public class ExecuteTransition extends Transition {
    private boolean alreadyExecuted = false;
    private Runnable runnable;

    public ExecuteTransition(Runnable r){
        this.runnable = r;
        Duration dur = Duration.millis(100);
        setCycleDuration(dur);
        setCycleCount(1);
        setInterpolator(Interpolator.LINEAR);
        getCuePoints().put("end",dur);
        this.statusProperty().addListener((v,o,n)->{
            System.out.printf("STATUS: %s -> %s%n",o,n);
        });
    }

    @Override
    protected void interpolate(double frac) {
        if(!alreadyExecuted){
            System.out.printf("ip: %f%n",frac);
            alreadyExecuted=true;
            runnable.run();
        }
    }
}

package de.fhac.mazenet.server.userinterface.mazeFX.util;

import javafx.scene.shape.Shape3D;

/**
 * Created by Richard Zameitat on 26.05.2016.
 */
public class Translate3D {
    public final double x;
    public final double y;
    public final double z;

    public Translate3D(double x, double y, double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public void applyTo(Shape3D s3d){
        s3d.setTranslateX(x);
        s3d.setTranslateY(y);
        s3d.setTranslateZ(z);
    }

    public Translate3D translateX(double dx){
        return new Translate3D(x+dx,y,z);
    }
    public Translate3D translateY(double dy){
        return new Translate3D(x,y+dy,z);
    }
    public Translate3D translateZ(double dz){
        return new Translate3D(x,y,z+dz);
    }
    public Translate3D translate(Translate3D d){
        return new Translate3D(x+d.x,y+d.y,z+d.z);
    }
    public Translate3D invert(){
        return new Translate3D(-x,-y,-z);
    }
}

package de.fhac.mazenet.server.userinterface.mazeFX.util;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by Richard on 04.06.2016.
 */
public class FakeDoubleBinding {
    private final DoubleProperty bind;
    private final ObservableValue<Number> source;
    private ChangeListener<Number> listener;

    public FakeDoubleBinding(DoubleProperty bind, ObservableValue<Number> source){
        this.bind = bind;
        this.source = source;
        listener = (observable, oldValue, newValue) -> {
            bind.setValue(newValue);
        };

    }

    public FakeDoubleBinding bind(){
        bind.setValue(source.getValue());
        source.addListener(listener);
        return this;
    }

    public FakeDoubleBinding unbind(){
        source.removeListener(listener);
        return this;
    }
}

package com.focustimer.desktoptimer.util;

import java.util.function.Consumer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class NewValueListener {
    public static <T> ChangeListener<T> listen(Consumer<T> callback){
        return new ChangeListener<T>() {
            @Override
            public void changed(ObservableValue<? extends T> observableValue, T oldValue, T newValue) {
                callback.accept(newValue);
            }
        };
    }

    public static <T> ChangeListener<T> listen(Runnable callback){
        return new ChangeListener<T>() {
            @Override
            public void changed(ObservableValue<? extends T> observableValue, T oldValue, T newValue) {
                callback.run();
            }
        };
    }
}

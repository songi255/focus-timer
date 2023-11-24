package com.focustimer.desktoptimer.common.store;

import com.focustimer.desktoptimer.common.DIContext;
import com.focustimer.desktoptimer.common.DIContext.Scope;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PersistingPropertyTest {
    KeyValueStorage storage = DIContext.INSTANCE.getInstance(KeyValueStorage.class, Scope.SINGLETON);

    @AfterEach
    void clearStorage(){
        storage.clear();
    }

    @AfterAll
    static void clearContext(){
        DIContext.INSTANCE.clear();
    }

    @DisplayName("Changed value is Persisting.")
    @Test
    void PersistTest(){
        LongProperty property = PersistingProperty.create(LongProperty.class, "testKey", "3");

        property.set(10);

        assertThat(storage.getData("testKey")).isEqualTo("10");
    }

    @DisplayName("Work On LongProperty.")
    @Test
    void LongPropertyTest(){
        LongProperty property = PersistingProperty.create(LongProperty.class, "testKey", "3");

        assertThat(property.get()).isEqualTo(3);
    }

    @DisplayName("Work On DoubleProperty.")
    @Test
    void DoublePropertyTest(){
        DoubleProperty property = PersistingProperty.create(DoubleProperty.class, "testKey", "3.6");

        assertThat(property.get()).isEqualTo(3.6);
    }

    @DisplayName("Work On StringProperty.")
    @Test
    void StringPropertyTest(){
        StringProperty property = PersistingProperty.create(StringProperty.class, "testKey", "testValue");

        assertThat(property.get()).isEqualTo("testValue");
    }
}

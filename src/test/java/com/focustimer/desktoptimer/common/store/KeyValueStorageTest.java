package com.focustimer.desktoptimer.common.store;

import com.focustimer.desktoptimer.common.store.KeyValueStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

public class KeyValueStorageTest {
    KeyValueStorage storage = new KeyValueStorage();

    @AfterEach
    void clearStorage(){
        storage.clear();
    }

    @DisplayName("Successful R/W with created config file.")
    @RepeatedTest(10)
    void readWriteTest(){
        storage.setData("key", "value");
        String value = storage.getData("key");

        assertThat(value).isEqualTo("value");
    }
}

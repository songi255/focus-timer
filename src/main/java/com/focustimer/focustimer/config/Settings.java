/**
 * Application Settings not in Model context
 *
 * @author Dave Shin
 */
package com.focustimer.focustimer.config;

import com.focustimer.focustimer.config.autoscan.Bean;
import com.focustimer.focustimer.config.store.DataInjector;
import com.focustimer.focustimer.config.store.Save;
import com.google.inject.Inject;
import lombok.Getter;
import lombok.Setter;

@Bean
@Getter
@Setter
public class Settings {
    private final DataInjector dataInjector;

    @Save("60") private int fps;
    @Save("0.2") private double overlayOpacity;
    @Save("100") private double overlayWidth;
    @Save("100") private double overlayHeight;
    @Save("200") private double overlayXGap;
    @Save("200") private double overlayYGap;

    @Inject
    public Settings(DataInjector dataInjector) {
        this.dataInjector = dataInjector;
        this.dataInjector.inject(this);
    }


}

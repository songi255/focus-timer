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
    @Save("60") private double fps;
    @Save("360") private double originalWidth;
    @Save("480") private double originalXHeight;
    @Save("0") private double originalX;
    @Save("0") private double originalY;


}

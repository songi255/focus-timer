package com.focustimer.focustimer.model.overlay;

import com.focustimer.focustimer.config.autoscan.Bean;
import com.focustimer.focustimer.config.store.Save;
import lombok.Getter;
import lombok.Setter;

@Bean
@Getter
@Setter
public class OverlayModel {
    @Save("0.2") private double overlayOpacity;
    @Save("100") private double overlayWidth;
    @Save("100") private double overlayHeight;
    @Save("200") private double overlayXGap;
    @Save("200") private double overlayYGap;

    private boolean isOverlayState = false;
}

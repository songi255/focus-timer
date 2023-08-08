/**
 * read / write Settings with DataManager
 *
 * @author Dave Shin
 */

package com.focustimer.focustimer.service;

import com.focustimer.focustimer.config.store.DataManager;
import com.focustimer.focustimer.config.store.TemplateModel;

//@ServiceBean
public class SettingService {
    private final TemplateModel templateModel;
    private final DataManager dataManager;

    public SettingService(TemplateModel templateModel, DataManager dataManager) {
        this.templateModel = templateModel;
        this.dataManager = dataManager;
    }


}

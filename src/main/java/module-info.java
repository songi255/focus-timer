module com.focustimer.desktoptimer {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.slf4j;
    requires org.controlsfx.controls;

    opens com.focustimer.desktoptimer to javafx.fxml;
    exports com.focustimer.desktoptimer;

    opens com.focustimer.desktoptimer.page.main to javafx.fxml;
    exports com.focustimer.desktoptimer.page.main;

    opens com.focustimer.desktoptimer.component.timercontrol to javafx.fxml;
    exports com.focustimer.desktoptimer.component.timercontrol;

    opens com.focustimer.desktoptimer.component.timerdisk to javafx.fxml;
    exports com.focustimer.desktoptimer.component.timerdisk;

    opens com.focustimer.desktoptimer.component.timertext to javafx.fxml;
    exports com.focustimer.desktoptimer.component.timertext;

    opens com.focustimer.desktoptimer.component.headerbar to javafx.fxml;
    exports com.focustimer.desktoptimer.component.headerbar;

    opens com.focustimer.desktoptimer.component.finishtime to javafx.fxml;
    exports com.focustimer.desktoptimer.component.finishtime;

    opens com.focustimer.desktoptimer.service to javafx.fxml;
    exports com.focustimer.desktoptimer.service;

    opens com.focustimer.desktoptimer.viewmodel to javafx.fxml;
    exports com.focustimer.desktoptimer.viewmodel;

    opens com.focustimer.desktoptimer.model to javafx.fxml;
    exports com.focustimer.desktoptimer.model;
    exports com.focustimer.desktoptimer.util;
    opens com.focustimer.desktoptimer.util to javafx.fxml;
}
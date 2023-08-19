module com.focustimer.focustimer {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires com.google.guice;
    requires org.reflections;
    requires aopalliance;
    requires org.slf4j;
    requires java.desktop;

    opens com.focustimer.focustimer to com.google.guice, javafx.fxml;
    exports com.focustimer.focustimer;

    opens com.focustimer.focustimer.utils to com.google.guice, javafx.fxml;
    exports com.focustimer.focustimer.utils;

    // temp
//    opens com.focustimer.focustimer.components to javafx.fxml, com.google.guice;
//    exports com.focustimer.focustimer.components;
    opens com.focustimer.focustimer.model.timer to com.google.guice, javafx.fxml;
    exports com.focustimer.focustimer.model.timer;

    opens com.focustimer.focustimer.model.overlay to com.google.guice, javafx.fxml;
    exports com.focustimer.focustimer.model.overlay;

    opens com.focustimer.focustimer.config to com.google.guice, javafx.fxml;
    exports com.focustimer.focustimer.config;

    opens com.focustimer.focustimer.config.store to com.google.guice, javafx.fxml;
    exports com.focustimer.focustimer.config.store;

    opens com.focustimer.focustimer.components.headerBar to com.google.guice, javafx.fxml;
    exports com.focustimer.focustimer.components.headerBar;

    opens com.focustimer.focustimer.components.timerControl to com.google.guice, javafx.fxml;
    exports com.focustimer.focustimer.components.timerControl;

    opens com.focustimer.focustimer.components.timerDiskView to com.google.guice, javafx.fxml;
    exports com.focustimer.focustimer.components.timerDiskView;

    opens com.focustimer.focustimer.components.timerTextView to com.google.guice, javafx.fxml;
    exports com.focustimer.focustimer.components.timerTextView;

    opens com.focustimer.focustimer.pages.main to com.google.guice, javafx.fxml;
    exports com.focustimer.focustimer.pages.main;





}
module com.focustimer.focustimer {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires com.google.guice;
    requires org.reflections;

    opens com.focustimer.focustimer to javafx.fxml;
    exports com.focustimer.focustimer;

    // temp
    opens com.focustimer.focustimer.components to javafx.fxml, com.google.guice;
    exports com.focustimer.focustimer.components;

    opens com.focustimer.focustimer.model.timer to com.google.guice;
    opens com.focustimer.focustimer.model.template to com.google.guice;
    opens com.focustimer.focustimer.config to com.google.guice;


}
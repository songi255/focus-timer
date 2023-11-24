module com.focustimer.desktoptimer {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.slf4j;

    opens com.focustimer.desktoptimer to javafx.fxml;
    exports com.focustimer.desktoptimer;

    opens com.focustimer.desktoptimer.page.main to javafx.fxml;
    exports com.focustimer.desktoptimer.page.main;
}
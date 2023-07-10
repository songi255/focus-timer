module com.focustimer.focustimer {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    opens com.focustimer.focustimer to javafx.fxml;
    exports com.focustimer.focustimer;
}
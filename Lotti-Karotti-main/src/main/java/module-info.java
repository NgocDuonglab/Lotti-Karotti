module com.example.lottikarotti_main {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.lottikarotti_main to javafx.fxml;
    exports com.example.lottikarotti_main;

    exports Demo;
    opens Demo to javafx.fxml;
}
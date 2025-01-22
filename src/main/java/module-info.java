module com.example.lottikarotti_main {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    exports GameHandler.lottikarotti_main;
    opens GameHandler.lottikarotti_main to javafx.fxml;
}

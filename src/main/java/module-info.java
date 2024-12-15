module com.example.lottikarotti_main {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    exports com.example.lottikarotti_main;
    opens com.example.lottikarotti_main to javafx.fxml;
}

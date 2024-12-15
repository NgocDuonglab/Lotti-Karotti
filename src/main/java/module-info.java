module com.example.lottikarotti_main {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    exports LottiKarottiGameHandler.Start.lottikarotti_main;
    opens LottiKarottiGameHandler.Start.lottikarotti_main to javafx.fxml;
}

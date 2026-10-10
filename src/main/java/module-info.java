module app.examenprogra3.arce.morales.estrada {
    requires javafx.controls;
    requires javafx.fxml;

    opens app.examenprogra3.arce.morales.estrada to javafx.fxml;
    exports app.examenprogra3.arce.morales.estrada;
}

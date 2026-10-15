module app.examenprogra3.arce.morales.estrada {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;            // JDBC: Connection, PreparedStatement, ResultSet...
    requires mysql.connector.j;   // driver de MySQL (mysql-connector-j)

    opens app.examenprogra3.arce.morales.estrada to javafx.fxml;
    exports app.examenprogra3.arce.morales.estrada;
}

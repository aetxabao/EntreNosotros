module edu.masanz.da.tx.txatrea {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    exports edu.masanz.da.en;
    opens edu.masanz.da.en to javafx.fxml;
}
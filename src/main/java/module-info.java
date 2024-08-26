module fes.aragon.unam.divisionsintetica {
    requires javafx.controls;
    requires javafx.fxml;


    opens fes.aragon.unam.divisionsintetica to javafx.fxml;
    exports fes.aragon.unam.divisionsintetica;
}
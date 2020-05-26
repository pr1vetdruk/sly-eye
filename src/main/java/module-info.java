module slyeye {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires java.xml.bind;

    opens ru.privetdruk.slyeye.controller to javafx.fxml, javafx.controls, javafx.graphics;

    exports ru.privetdruk.slyeye;
}
package utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertHelper {

        public static void mostrarInfo(String titulo, String mensaje) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(titulo);
            alert.setContentText(mensaje);
            alert.showAndWait();
        }

        public static void mostrarError(String titulo, String mensaje) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(titulo);
            alert.setContentText(mensaje);
            alert.showAndWait();
        }

        public static boolean mostrarConfirmacion(String titulo, String mensaje) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(titulo);
            alert.setContentText(mensaje);

            Optional<ButtonType> resultado = alert.showAndWait();
            return resultado.isPresent() && resultado.get() == ButtonType.OK;
        }
}

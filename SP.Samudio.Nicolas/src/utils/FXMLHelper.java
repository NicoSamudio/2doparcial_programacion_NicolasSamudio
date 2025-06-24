package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLHelper {

    // Abre una ventana normal
    public static void cargarEscena(Stage stage, String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLHelper.class.getResource(rutaFXML));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error al cargar la escena: " + rutaFXML);
            e.printStackTrace();
        }
    }

    // Abre una ventana modal y devuelve el controlador
    public static <T> T cargarVentanaModal(String rutaFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLHelper.class.getResource(rutaFXML));
            Parent root = loader.load();

            Stage modal = new Stage();
            modal.setTitle(titulo);
            modal.initModality(Modality.APPLICATION_MODAL); // Bloquea ventana anterior
            modal.setScene(new Scene(root));
            modal.showAndWait(); // Espera a que se cierre

            return loader.getController();
        } catch (Exception e) {
            System.out.println("Error al abrir ventana modal: " + rutaFXML);
            e.printStackTrace();
            return null;
        }
    }

    // Abre una ventana normal y devuelve el controlador
    public static <T> T cargarEscenaYDevolverControlador(Stage stage, String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLHelper.class.getResource(rutaFXML));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            return loader.getController();
        } catch (Exception e) {
            System.out.println("Error al cargar la escena: " + rutaFXML);
            e.printStackTrace();
            return null;
        }
    }
}

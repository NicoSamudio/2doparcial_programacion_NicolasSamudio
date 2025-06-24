
package utils;

import java.util.function.Consumer;
import java.util.function.Function;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Utils {

    /**
     * Método genérico para agregar un objeto usando un formulario modal.
     *
     * @param rutaFXML          Ruta al archivo FXML del formulario.
     * @param titulo            Título de la ventana modal.
     * @param claseControlador  Clase del controlador del formulario.
     * @param obtenerObjetoCreado Función para obtener el objeto creado desde el controlador.
     * @param handlerAgregar    Función para agregar el objeto creado a la colección o base de datos.
     * @param refrescarVista    Runnable para refrescar la vista después de agregar.
     * @param <T>               Tipo de objeto que se agrega.
     * @param <C>               Tipo del controlador del formulario.
     */
        public static <T, C> void agregarGenerico(
            String rutaFXML,
            String titulo,
            Class<C> claseControlador,
            java.util.function.Function<C, T> obtenerObjetoCreado,
            Consumer<T> handlerAgregar,
            Runnable refrescarVista
        ) {
            try {
                // Abrir la ventana modal con el formulario y obtener el controlador
                C controller = FXMLHelper.cargarVentanaModal(rutaFXML, titulo);

                if (controller != null) {
                    // Obtener el objeto creado del controlador (ejemplo: el vehículo creado)
                    T nuevo = obtenerObjetoCreado.apply(controller);

                    if (nuevo != null) {
                        // Intentar agregar el objeto (puede lanzar excepciones, se maneja afuera)
                        handlerAgregar.accept(nuevo);

                        // Refrescar la vista para mostrar el nuevo objeto agregado
                        refrescarVista.run();
                    }
                }
            } catch (Exception e) {
                // Si algo falla, mostrar un error genérico en una alerta
                e.printStackTrace();
                javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText(null);
                alerta.setContentText("No se pudo agregar el elemento.");
                alerta.showAndWait();
            }
        }
        /****************************************************************************************************************************************/
        /****************************************************************************************************************************************/
        /****************************************************************************************************************************************/
         /**
        * Método genérico para modificar un objeto seleccionado de una lista,
        * abriendo un formulario modal que recibe el objeto para precargar datos,
        * luego obtiene el objeto modificado y ejecuta una acción sobre él.
        *
        * @param listView           Lista JavaFX con los objetos a modificar.
        * @param rutaFXML           Ruta al formulario FXML para modificar.
        * @param titulo             Título de la ventana modal.
        * @param claseControlador   Clase del controlador del formulario.
        * @param setearDatos        Función para pasar el objeto seleccionado al formulario (precargar datos).
        * @param obtenerObjetoModificado Función para obtener el objeto modificado del controlador.
        * @param handlerModificar   Acción que recibe el objeto modificado y lo actualiza en la colección.
        * @param refrescarVista     Código para refrescar la UI después de modificar.
        * @param <T>                Tipo de objeto que modificás (ej: Vehiculo).
        * @param <C>                Tipo del controlador del formulario.
        */
        public static <T, C> void modificarGenerico(
            javafx.scene.control.ListView<T> listView,
            String rutaFXML,
            String titulo,
            Class<C> claseControlador,
            java.util.function.BiConsumer<C, T> setearDatos,
            java.util.function.Function<C, T> obtenerObjetoModificado,
            Consumer<T> handlerModificar,
            Runnable refrescarVista
        ) {
            try {
                // Obtener el elemento seleccionado de la lista
                T seleccionado = listView.getSelectionModel().getSelectedItem();

                if (seleccionado == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Debes seleccionar un elemento para modificar.");
                    alert.showAndWait();
                    return;
                }

                // Cargar el FXMLLoader y la vista
                FXMLLoader loader = new FXMLLoader(Utils.class.getResource(rutaFXML));
                Parent root = loader.load();

                // Obtener el controlador
                C controller = loader.getController();

                // Pasar el objeto seleccionado para precargar datos en el formulario
                setearDatos.accept(controller, seleccionado);

                // Crear ventana modal
                Stage modal = new Stage();
                modal.setTitle(titulo);
                modal.initModality(Modality.APPLICATION_MODAL);
                modal.setScene(new Scene(root));
                modal.showAndWait();

                // Obtener el objeto modificado luego de cerrar la ventana
                T modificado = obtenerObjetoModificado.apply(controller);

                // Si no es nulo, ejecutar acción para modificarlo en la colección
                if (modificado != null) {
                    handlerModificar.accept(modificado);
                    refrescarVista.run();
                }

            } catch (IllegalArgumentException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al abrir ventana");
                alert.setHeaderText(null);
                alert.setContentText("No se pudo abrir la ventana de modificación.");
                alert.showAndWait();
            }
        }
        /****************************************************************************************************************************************/
        /****************************************************************************************************************************************/
        /****************************************************************************************************************************************/   
        /**
     * Método genérico para eliminar un objeto seleccionado de una lista mostrada en un ListView.
     * Muestra una ventana de confirmación antes de ejecutar la acción de eliminación.
     *
     * @param listView            Componente ListView que contiene los elementos a mostrar y seleccionar.
     * @param tituloConfirmacion  Título de la ventana de confirmación.
     * @param textoConfirmacion   Función que genera el mensaje de confirmación a partir del objeto seleccionado.
     * @param handlerEliminar     Acción que se ejecuta para eliminar el objeto una vez confirmada la operación.
     * @param <T>                 Tipo de objeto contenido en el ListView (ej: Vehiculo, Cliente, etc.).
     */
    
        public static <T> void eliminarGenerico(
        ListView<T> listView,
        String tituloConfirmacion,
        Function<T, String> textoConfirmacion,
        Consumer<T> handlerEliminar
    ) {
        T seleccionado = listView.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            AlertHelper.mostrarError("Error", "Debes seleccionar un elemento para eliminar.");
            return;
        }

        boolean confirmado = AlertHelper.mostrarConfirmacion(
            tituloConfirmacion,
            textoConfirmacion.apply(seleccionado)
        );

        if (confirmado) {
            handlerEliminar.accept(seleccionado);
            AlertHelper.mostrarInfo("Eliminado", "El elemento fue eliminado correctamente.");
        }
    }
    

}

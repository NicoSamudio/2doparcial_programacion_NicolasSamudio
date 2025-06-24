package entidades;

import controllers.viewController;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import utils.FXMLHelper;
import utils.SerializableGeneric;

public class main extends Application {

    private static final String ARCHIVO = "medicamentos.csv";
    private final SerializableGeneric<Medicamento> serializador = new SerializableGeneric<>();
    private final Inventario inventario = new Inventario();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        ArrayList<String> datosCSV = serializador.leerCSV(ARCHIVO);
        ArrayList<Medicamento> vehiculos = new ArrayList<>();

        for (String linea : datosCSV) {
            Medicamento m = Medicamento.fromCSV(linea);
            if (m != null) {
                vehiculos.add(m);
            }
        }
        inventario.setMedicamentos(vehiculos);

        viewController controller = FXMLHelper.cargarEscenaYDevolverControlador(stage, "/view/view.fxml");
        controller.setInventario(inventario);
    }

    @Override
    public void stop() throws Exception {
        serializador.escribirCSV(inventario.getMedicamento(), ARCHIVO);
        super.stop();
    }
}

package controllers;

import entidades.Inventario;
import entidades.Medicamento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import utils.Utils;
import utils.Validador;
import utils.VistaHelper;

public class viewController {

    private Inventario inventario = new Inventario();

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnModificar;

    @FXML
    private ListView<Medicamento> listViewMedicamentos;

    @FXML
    void agregar(ActionEvent event) {
        Utils.agregarGenerico(
                "/view/formulario.fxml",
                "Nuevo producto farmacéutico",
                formularioController.class,
                controller -> controller.getNuevoMedicamento(),
                this::intentarAgregarMedicamento,
                this::refrescarVista
        );
    }

    private void intentarAgregarMedicamento(Medicamento nuevoMedicamento) {
        if (nuevoMedicamento != null) {
            inventario.agregarMedicamento(nuevoMedicamento);
            System.out.println("Medicamento agregado: " + nuevoMedicamento.getNombreComercial());
            System.out.println("Cantidad total en inventario: " + inventario.getMedicamento().size());
        } else {
            System.out.println("El nuevo medicamento es null, no se agregó.");
        }
    }

    @FXML
    void eliminar(ActionEvent event) {
        Utils.eliminarGenerico(
                listViewMedicamentos,
                "Confirmar eliminación",
                this::mensajeConfirmacion,
                medicamento -> {
                    inventario.borrarMedicamento(medicamento);
                    refrescarVista();
                }
        );
    }

    @FXML
    void modificar(ActionEvent event) {
        Utils.modificarGenerico(
                listViewMedicamentos,
                "/view/modificacion.fxml",
                "Modificar vehículo",
                modificacionController.class,
                (controller, seleccionado) -> controller.setearDatos(seleccionado),
                controller -> controller.getMedicamentoModificado(),
                modificado -> inventario.modificarMedicamento(modificado),
                this::refrescarVista
        );
    }

    private void refrescarVista() {
        Validador.validarNoNulo(inventario, "El inventario no fue inicializado.");
        VistaHelper.refrescarLista(listViewMedicamentos, inventario.getMedicamento());
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
        refrescarVista();
    }

    private String mensajeConfirmacion(Medicamento m) {
        return "¿Seguro que querés eliminar el medicamento: " + m.getNombreComercial() + "?";
    }

}

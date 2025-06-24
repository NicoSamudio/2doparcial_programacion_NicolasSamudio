package controllers;

import entidades.Medicamento;
import entidades.Suplemento;
import entidades.productoFarmaceutico;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import utils.AlertHelper;
import utils.Validador;

public class modificacionController extends BaseController implements Initializable {

    public Medicamento medicamentoModificado;
    private productoFarmaceutico productoFarmaceuticoModificado;
    private productoFarmaceutico productoOriginal;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;
    
        @FXML
    private Label lblExtra;

    @FXML
    private ChoiceBox<String> cbTipo;

    @FXML
    private Label lblVencimientoSeteado;

    @FXML
    private TextField txtDatoExtra;

    @FXML
    private TextField txtDosis;

    @FXML
    private TextField txtProducto;

    @FXML
    private TextField txtVencimiento;

    @FXML
    void cancelar(ActionEvent event) {
        cerrarVentana(event);
    }

    @FXML
    void confirmar(ActionEvent event) {
        try {
            Validador.validarTextoNoVacio(txtProducto.getText(), "El nombre del producto no puede estar vacío.");
            Validador.validarTextoNoVacio(txtDosis.getText(), "La dosis no puede estar vacía.");
            Validador.validarTextoNoVacio(txtDatoExtra.getText(), "El campo extra no puede estar vacío.");
            Validador.validarTextoNoVacio(txtVencimiento.getText(), "La fecha de vencimiento no puede estar vacía.");

            boolean confirmado = AlertHelper.mostrarConfirmacion("Confirmar cambios", "¿Desea guardar los cambios?");
            if (!confirmado) {
                return;
            }

            actualizarProductoOriginal();

            if (productoOriginal instanceof Medicamento) {
                this.medicamentoModificado = (Medicamento) productoOriginal;
            }

            Stage stage = (Stage) btnConfirmar.getScene().getWindow();
            stage.close();

        } catch (IllegalArgumentException ex) {
            AlertHelper.mostrarError("Validación", ex.getMessage());
        }
    }

    private void actualizarProductoOriginal() {
        if (productoOriginal == null) {
            throw new IllegalStateException("No se ha seteado el producto original para modificar");
        }

        productoOriginal.setNombreComercial(txtProducto.getText());
        productoOriginal.setDosis(txtDosis.getText());

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            productoOriginal.setFechaVencimiento(formato.parse(txtVencimiento.getText()));
        } catch (ParseException e) {
            throw new IllegalArgumentException("La fecha debe tener formato dd/MM/yyyy");
        }

        if (productoOriginal instanceof Medicamento med) {
            med.setNecesitaReceta(txtDatoExtra.getText());
        } else if (productoOriginal instanceof Suplemento sup) {
            sup.setObjetivo(txtDatoExtra.getText());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cbTipo.getItems().addAll("MEDICAMENTO", "SUPLEMENTO");
        cbTipo.setDisable(true);
    }

@FXML
void cambiandoTipo(ActionEvent event) {
    switch (cbTipo.getValue()) {
        case "MEDICAMENTO" -> lblExtra.setText("Necesita receta");
        case "SUPLEMENTO" -> lblExtra.setText("Objetivo");
        default -> lblExtra.setText("Dato extra");
    }
}

    public void setearDatos(productoFarmaceutico producto) {
        if (producto == null) {
            return;
        }

        this.productoOriginal = producto;

        txtProducto.setText(producto.getNombreComercial());
        txtDosis.setText(producto.getDosis());
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        txtVencimiento.setText(formato.format(producto.getFechaVencimiento()));

        if (producto instanceof Medicamento med) {
            cbTipo.setValue("MEDICAMENTO");
            txtDatoExtra.setText(med.getNecesitaReceta().toString());
        } else if (producto instanceof Suplemento sup) {
            cbTipo.setValue("SUPLEMENTO");
            txtDatoExtra.setText(sup.getObjetivo());
        } else {
            cbTipo.setValue("DESCONOCIDO");
            txtDatoExtra.setText("");
        }

    }

    public productoFarmaceutico getProductoFarmaceuticoModificado() {
        return productoFarmaceuticoModificado;
    }

    public Medicamento getMedicamentoModificado() {
        return medicamentoModificado;
    }

}

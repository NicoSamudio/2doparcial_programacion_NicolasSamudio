package controllers;

import entidades.Medicamento;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.AlertHelper;
import utils.Validador;

public class formularioController extends AlertHelper implements Initializable {

    private Medicamento nuevoMedicamento;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;

    @FXML
    private ChoiceBox<String> cbTipo;

    @FXML
    private Label lblExtra;

    @FXML
    private Label lblDosis;

    @FXML
    private Label lblProducto;

    @FXML
    private Label lblVencimiento;

    @FXML
    private TextField txtDatoExtra;

    @FXML
    private TextField txtDosis;

    @FXML
    private TextField txtProducto;

    @FXML
    private TextField txtVencimiento;

    @FXML
    void cambiandoTipo(ActionEvent event) {
        actualizarEtiquetaDatoExtra();
    }

    @FXML
    void cancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    void confirmar(ActionEvent event) {
        try {
            validarCampos();
            nuevoMedicamento = crearMedicamentoDesdeCampos();
            cerrarVentana();
        } catch (IllegalArgumentException ex) {
            mostrarError("Validación", ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbTipo.getItems().addAll("MEDICAMENTO", "SUPLEMENTO");
        cbTipo.setValue("MEDICAMENTO");
        actualizarEtiquetaDatoExtra();

        cbTipo.setOnAction(this::cambiandoTipo);
    }

    private void actualizarEtiquetaDatoExtra() {
        lblExtra.setText(switch (cbTipo.getValue()) {
            case "MEDICAMENTO" ->
                "Necesita receta";
            case "SUPLEMENTO" ->
                "Objetivo";
            default ->
                "Dato extra";
        });
    }

    private Medicamento crearMedicamentoDesdeCampos() {
        String tipo = cbTipo.getValue();
        String nombre = txtProducto.getText().trim();
        String dosis = txtDosis.getText().trim();
        String fechaStr = txtVencimiento.getText().trim();
        String datoExtra = txtDatoExtra.getText().trim();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        Date fechaVencimiento;
        try {
            fechaVencimiento = sdf.parse(fechaStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("La fecha de vencimiento debe tener formato dd/MM/yyyy.");
        }

        switch (tipo) {
            case "MEDICAMENTO":
                return new Medicamento(datoExtra, nombre, dosis, fechaVencimiento);
            case "SUPLEMENTO":
                return new Medicamento(datoExtra, nombre, dosis, fechaVencimiento);
            default:
                throw new IllegalArgumentException("Tipo de medicamento no reconocido.");
        }
    }

    private void validarCampos() {
        Validador.validarTextoNoVacio(txtProducto.getText(), "El nombre del producto no puede estar vacío.");
        Validador.validarTextoNoVacio(txtDosis.getText(), "La dosis no puede estar vacía.");
        Validador.validarTextoNoVacio(txtVencimiento.getText(), "La fecha de vencimiento no puede estar vacía.");
        Validador.validarTextoNoVacio(txtDatoExtra.getText(), "El campo '" + lblExtra.getText() + "' no puede estar vacío.");

        if (!esFechaValida(txtVencimiento.getText())) {
            throw new IllegalArgumentException("La fecha de vencimiento debe tener formato dd/MM/yyyy.");
        }
    }

    private boolean esFechaValida(String fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            Date d = sdf.parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public Medicamento getNuevoMedicamento() {
        return nuevoMedicamento;
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

}

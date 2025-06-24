package entidades;

import java.util.ArrayList;
import utils.AlertHelper;

public class Inventario {

    private ArrayList<Medicamento> medicamentos;
    private int cantidadMaxima;

    public Inventario(int cantidadMaxima) {

        this.cantidadMaxima = cantidadMaxima;
        this.medicamentos = new ArrayList<>();
    }

    public Inventario() {
        this(15);
    }

    public ArrayList<Medicamento> getMedicamento() {
        return new ArrayList<>(medicamentos);
    }

    public void setMedicamentos(ArrayList<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public void agregarMedicamento(Medicamento nuevoMedicamento) {
        if (this.medicamentos.size() >= this.cantidadMaxima) {
            AlertHelper.mostrarError("Inventario lleno", "No se puede agregar más medicamentos. Capacidad máxima alcanzada.");
            return;
        }
        medicamentos.add(nuevoMedicamento);
        AlertHelper.mostrarInfo("Éxito", "El medicamento fue agregado correctamente.");
    }

    public void borrarMedicamento(Medicamento borrarMedicamento) {
        this.medicamentos.remove(borrarMedicamento);
    }

    public void modificarMedicamento(Medicamento medicamentoModificado) {
        int indice = medicamentos.indexOf(medicamentoModificado);
        if (indice >= 0) {
            medicamentos.set(indice, medicamentoModificado);
            AlertHelper.mostrarInfo("Modificado", "El medicamento fue modificado correctamente.");
        } else {
            AlertHelper.mostrarError("Error", "No se encontró el medicamento para modificar.");
        }
    }
}

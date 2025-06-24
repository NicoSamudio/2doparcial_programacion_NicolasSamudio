package entidades;

import Interfaces.ISerializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Suplemento extends productoFarmaceutico implements ISerializable {

    private String objetivo;

    public Suplemento(String objetivo, String nombreComercial, String dosis, Date fechaVencimiento) {
        super(nombreComercial, dosis, fechaVencimiento);
        this.objetivo = objetivo;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("Suplemento{");
        sb.append("objetivo=").append(objetivo);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public String toCSV() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append("SUPLEMENTO,");
        sb.append(getNombreComercial()).append(",");
        sb.append(getDosis()).append(",");
        sb.append(formato.format(getFechaVencimiento())).append(",");
        sb.append(objetivo);
        return sb.toString();
    }

}

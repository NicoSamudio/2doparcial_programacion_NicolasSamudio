package entidades;

import Interfaces.ISerializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Medicamento extends productoFarmaceutico implements ISerializable {

    private String necesitaReceta;

    public Medicamento(String necesitaReceta, String nombreComercial, String dosis, Date fechaVencimiento) {
        super(nombreComercial, dosis, fechaVencimiento);
        this.necesitaReceta = necesitaReceta;
    }

    public String getNecesitaReceta() {
        return necesitaReceta;
    }

    public void setNecesitaReceta(String necesitaReceta) {
        this.necesitaReceta = necesitaReceta;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("necesitaReceta=").append(necesitaReceta);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public String toCSV() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append("MEDICAMENTO,");
        sb.append(getNecesitaReceta()).append(",");
        sb.append(getNombreComercial()).append(",");
        sb.append(getDosis()).append(",");
        sb.append(formato.format(getFechaVencimiento()));
        return sb.toString();
    }

    public static Medicamento fromCSV(String linea) throws ParseException {
        String[] datos = linea.split(",");

        String tipo = datos[0];
        String necesitaReceta = datos[1];
        String nombreComercial = datos[2];
        String dosis = datos[3];
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaVencimiento = formato.parse(datos[4]);

        return new Medicamento(necesitaReceta, nombreComercial, dosis, fechaVencimiento);
    }
}

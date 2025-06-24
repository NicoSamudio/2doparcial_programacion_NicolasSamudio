package entidades;

import Interfaces.ISerializable;
import java.util.Date;
import java.util.Objects;

public abstract class productoFarmaceutico implements ISerializable {

    protected String nombreComercial;
    protected String dosis;
    protected Date fechaVencimiento;

    public productoFarmaceutico(String nombreComercial, String dosis, Date fechaVencimiento) {
        this.nombreComercial = nombreComercial;
        this.dosis = dosis;
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final productoFarmaceutico other = (productoFarmaceutico) obj;
        return Objects.equals(this.nombreComercial, other.nombreComercial);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append("{");
        sb.append("nombreComercial=").append(nombreComercial);
        sb.append(", dosis=").append(dosis);
        sb.append(", fechaVencimiento=").append(fechaVencimiento);
        sb.append('}');
        return sb.toString();
    }

}

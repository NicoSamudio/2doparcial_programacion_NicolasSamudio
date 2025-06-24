package utils;

import static utils.AlertHelper.mostrarError;

public class Parseador extends AlertHelper {

    public static Integer parsearInt(String texto, String mensajeError) {
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            mostrarError("Error de formato", mensajeError);
            return null; 
        }
    }

    public static Double parsearDouble(String texto, String mensajeError) {
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            mostrarError("Error de formato", mensajeError);
            return null;
        }
    }
}
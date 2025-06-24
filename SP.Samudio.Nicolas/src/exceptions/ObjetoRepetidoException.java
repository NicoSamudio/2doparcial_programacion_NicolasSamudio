package exceptions;

public class ObjetoRepetidoException extends Exception {

    public ObjetoRepetidoException(String mensaje) {
        super(mensaje);
    }

    // Método de fábrica estático
    public static ObjetoRepetidoException para(Object obj) {
        return new ObjetoRepetidoException("El objeto ya existe: " + obj.toString());
    }
}

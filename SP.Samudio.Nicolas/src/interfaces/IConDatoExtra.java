package interfaces;


public interface IConDatoExtra {
    int getDatoExtra();              
    void setDatoExtra(int valor);    
    String getNombreDatoExtra();    
}

//ACORDATE DE TENER ESTE METODO EN LA CLASE  O LO QUE SEA PARA PODER USAR ESTA INTERFAZ
/*

    @Override
    public int getDatoExtra() {
        return cantidadPuertas;    <- ES EL DATO QUE NO SE INICA EN LA CLASE PADRE
    }
*/
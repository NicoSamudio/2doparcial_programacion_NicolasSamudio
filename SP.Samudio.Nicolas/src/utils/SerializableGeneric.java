
package utils;

import Interfaces.ISerializable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SerializableGeneric<T extends ISerializable> {

        public void escribirCSV(ArrayList<T> lista, String ruta) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
                for (T item : lista) {
                    bw.write(item.toCSV());
                    bw.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error al escribir el CSV: " + e.getMessage());
            }
        }

        public ArrayList<String> leerCSV(String ruta) {
            ArrayList<String> lista = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    lista.add(linea);
                }
            } catch (IOException e) {
                System.err.println("Error al leer el CSV: " + e.getMessage());
            }
            return lista;
        }
}

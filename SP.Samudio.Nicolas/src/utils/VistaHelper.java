package utils;

import javafx.scene.control.ListView;
import java.util.ArrayList;

public class VistaHelper {
    public static <T> void refrescarLista(ListView<T> listView, ArrayList<T> datos) {
        listView.getItems().clear();
        listView.getItems().addAll(datos);
    }
}
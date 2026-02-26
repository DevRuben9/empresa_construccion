package pruebafx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tecnico {
    private final StringProperty nombreCompleto;
    private final IntegerProperty contacto;

    public Tecnico(String nombre, String apellido, int contacto) {
        this.nombreCompleto = new SimpleStringProperty(nombre + " " + apellido);
        this.contacto = new SimpleIntegerProperty(contacto);
    }
    public String getNombreCompleto() { return nombreCompleto.get(); }
    public int getContacto() { return contacto.get(); }
}
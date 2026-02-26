package pruebafx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Reporte {
    private final IntegerProperty id;
    private final StringProperty tecnico;
    private final StringProperty vehiculo;
    private final StringProperty operador;
    private final StringProperty fecha;

    public Reporte(int id, String tecnico, String placa, String tipo, String operador, String fecha) {
        this.id = new SimpleIntegerProperty(id);
        this.tecnico = new SimpleStringProperty(tecnico);
        this.vehiculo = new SimpleStringProperty(placa + " (" + tipo + ")");
        this.operador = new SimpleStringProperty(operador);
        this.fecha = new SimpleStringProperty(fecha);
    }
    public int getId() { return id.get(); }
    public String getTecnico() { return tecnico.get(); }
    public String getVehiculo() { return vehiculo.get(); }
    public String getOperador() { return operador.get(); }
    public String getFecha() { return fecha.get(); }
}
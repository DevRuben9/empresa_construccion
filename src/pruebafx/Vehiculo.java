package pruebafx;

import javafx.beans.property.*;

public class Vehiculo {
    private final StringProperty placa;
    private final DoubleProperty odometro;
    private final StringProperty modelo;
    private final StringProperty tipo;

    public Vehiculo(String placa, double odometro, String modelo, String tipo) {
        this.placa = new SimpleStringProperty(placa);
        this.odometro = new SimpleDoubleProperty(odometro);
        this.modelo = new SimpleStringProperty(modelo);
        this.tipo = new SimpleStringProperty(tipo);
    }

    public String getPlaca() { return placa.get(); }
    public double getOdometro() { return odometro.get(); }
    public String getModelo() { return modelo.get(); }
    public String getTipo() { return tipo.get(); }
    
    public StringProperty placaProperty() { return placa; }
    public DoubleProperty odometroProperty() { return odometro; }
    public StringProperty modeloProperty() { return modelo; }
    public StringProperty tipoProperty() { return tipo; }
}
package pruebafx;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import java.sql.*;

public class PruebaFX extends Application {

    private final String URL = "jdbc:mysql://localhost:3306/empresa_construccion";
    private final String USER = "root";
    private final String PASS = "";
    
    private TextArea txtResultado;
    
    @Override
    public void start(Stage stage) {
        txtResultado = new TextArea();
        txtResultado.setEditable(false);
        txtResultado.setPrefHeight(400);
        txtResultado.setStyle("-fx-font-family: 'Courier New';"); // Fuente monoespaciada para mejor orden

        Button btnA = new Button("A. Vehículos más nuevos");
        Button btnB = new Button("B. Técnicos por contacto");
        Button btnC = new Button("C. Reporte de Revisiones");

        btnA.setMaxWidth(Double.MAX_VALUE);
        btnB.setMaxWidth(Double.MAX_VALUE);
        btnC.setMaxWidth(Double.MAX_VALUE);

        // Eventos
        btnA.setOnAction(e -> mostrarVehiculosNuevos());
        btnB.setOnAction(e -> mostrarTecnicosDesc());
        btnC.setOnAction(e -> mostrarReporteRevisiones());

        VBox root = new VBox(10, btnA, btnB, btnC, txtResultado);
        root.setStyle("-fx-padding: 20; -fx-background-color: #f4f4f4;");

        Scene scene = new Scene(root, 700, 550);
        stage.setTitle("Panel de Control - Empresa de Construcción (MySQL)");
        stage.setScene(scene);
        stage.show();
    }

    // MÉTODOS DE CONSULTA
    
    public void mostrarVehiculosNuevos() {
        txtResultado.clear();
        // Probamos con una consulta más amplia para descartar fallos del LIMIT
        String sql = "SELECT placa, odometro, modelo FROM vehiculo ORDER BY odometro ASC";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            txtResultado.appendText("--- LISTADO DE VEHÍCULOS ---\n");

            int contador = 0;
            while (rs.next()) {
                // Usamos índices: 1 es placa, 2 es odometro, 3 es modelo
                String placa = rs.getString(1); 
                String odo = rs.getString(2);
                String mod = rs.getString(3);

                txtResultado.appendText(placa + " | " + mod + " | " + odo + "\n");
                contador++;
            }

            if (contador == 0) {
                txtResultado.appendText("La base de datos respondió, pero la tabla está VACÍA.");
            }

        } catch (SQLException e) {
            txtResultado.setText("ERROR DE SQL: " + e.getMessage());
        }
    }

    public void mostrarTecnicosDesc() {
        txtResultado.clear();
        String sql = "SELECT * FROM tecnico ORDER BY contacto DESC";
        ejecutarConsulta(sql, "--- TÉCNICOS ORDENADOS POR CONTACTO (DESC) ---");
    }

    public void mostrarReporteRevisiones() {
        txtResultado.clear();
        // MySQL usa CONCAT() para unir textos
        String sql = "SELECT r.id, CONCAT(t.nombre, ' ', t.apellido) as tecnico, " +
                     "v.placa, v.tipo, CONCAT(o.nombre, ' ', o.apellido) as operador, " +
                     "r.fecha_revision FROM revision r " +
                     "JOIN tecnico t ON r.encargado = t.id " +
                     "JOIN vehiculo v ON r.vehiculo = v.placa " +
                     "JOIN operador o ON r.chofer = o.id";
        
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            txtResultado.appendText("--- REPORTE DETALLADO DE REVISIONES ---\n");
            txtResultado.appendText(String.format("%-5s | %-20s | %-10s | %-15s | %-10s\n", "ID", "TÉCNICO", "PLACA", "OPERADOR", "FECHA"));
            txtResultado.appendText("----------------------------------------------------------------------\n");
            
            while (rs.next()) {
                txtResultado.appendText(String.format("%-5d | %-20s | %-10s | %-15s | %-10s\n",
                    rs.getInt("id"), rs.getString("tecnico"), rs.getString("placa"), 
                    rs.getString("operador"), rs.getDate("fecha_revision")));
            }
        } catch (SQLException e) {
            txtResultado.setText("Error en Reporte MySQL: " + e.getMessage());
        }
    }

    // Método auxiliar para evitar repetir código en A y B
    private void ejecutarConsulta(String sql, String titulo) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            txtResultado.appendText(titulo + "\n\n");
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    txtResultado.appendText(metaData.getColumnName(i) + ": " + rs.getString(i) + "  ");
                }
                txtResultado.appendText("\n");
            }
        } catch (SQLException e) {
            txtResultado.setText("Error MySQL: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
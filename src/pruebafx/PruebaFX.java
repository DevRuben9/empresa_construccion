package pruebafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import java.sql.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PruebaFX extends Application {

    private final String URL = "jdbc:mysql://localhost:3306/empresa_construccion";
    private final String USER = "root";
    private final String PASS = ""; 

    private TableView tablaGenerica; // Tabla que cambia de contenido

    @Override
    public void start(Stage stage) {
        tablaGenerica = new TableView<>();
        tablaGenerica.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button btnA = new Button("3 Vehículos Más Nuevos");
        Button btnB = new Button("Técnicos Ordenados por Descendentemente");
        Button btnC = new Button("Reporte de Revisiones");

        String estilo = "-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 10;";
        btnA.setStyle(estilo); btnB.setStyle(estilo); btnC.setStyle(estilo);
        btnA.prefWidthProperty().bind(stage.widthProperty().divide(2));
        btnB.prefWidthProperty().bind(stage.widthProperty().divide(2));
        btnC.prefWidthProperty().bind(stage.widthProperty().divide(2));

        
        btnA.setOnAction(e -> resolverPuntoA());
        btnB.setOnAction(e -> resolverPuntoB());
        btnC.setOnAction(e -> resolverPuntoC());

        VBox root = new VBox(15, btnA, btnB, btnC, tablaGenerica);
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Sistema de Construcción");
        stage.show();
    }

    private void resolverPuntoA() {
        tablaGenerica.getColumns().clear();
        TableColumn<Vehiculo, String> c1 = new TableColumn<>("Placa");
        c1.setCellValueFactory(new PropertyValueFactory<>("placa"));
        TableColumn<Vehiculo, Double> c2 = new TableColumn<>("Odómetro");
        c2.setCellValueFactory(new PropertyValueFactory<>("odometro"));
        tablaGenerica.getColumns().addAll(c1, c2);

        ObservableList<Vehiculo> datos = FXCollections.observableArrayList();
        String sql = "SELECT * FROM vehiculo ORDER BY odometro ASC LIMIT 3";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                datos.add(new Vehiculo(rs.getString("placa"), rs.getDouble("odometro"), rs.getString("modelo"), rs.getString("tipo")));
            }
            tablaGenerica.setItems(datos);
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    private void resolverPuntoB() {
        tablaGenerica.getColumns().clear();
        TableColumn<Tecnico, String> c1 = new TableColumn<>("Nombre Completo");
        c1.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        TableColumn<Tecnico, Integer> c2 = new TableColumn<>("Contacto");
        c2.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        tablaGenerica.getColumns().addAll(c1, c2);

        ObservableList<Tecnico> datos = FXCollections.observableArrayList();
        String sql = "SELECT nombre, apellido, contacto FROM tecnico ORDER BY contacto DESC";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                datos.add(new Tecnico(rs.getString("nombre"), rs.getString("apellido"), rs.getInt("contacto")));
            }
            tablaGenerica.setItems(datos);
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    // --- PUNTO C: Reporte de Revisiones ---
    private void resolverPuntoC() {
        tablaGenerica.getColumns().clear();
        TableColumn<Reporte, Integer> c1 = new TableColumn<>("ID");
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Reporte, String> c2 = new TableColumn<>("Técnico");
        c2.setCellValueFactory(new PropertyValueFactory<>("tecnico"));
        TableColumn<Reporte, String> c3 = new TableColumn<>("Vehículo");
        c3.setCellValueFactory(new PropertyValueFactory<>("vehiculo"));
        TableColumn<Reporte, String> c4 = new TableColumn<>("Operador");
        c4.setCellValueFactory(new PropertyValueFactory<>("operador"));
        TableColumn<Reporte, String> c5 = new TableColumn<>("Fecha");
        c5.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tablaGenerica.getColumns().addAll(c1, c2, c3, c4, c5);

        ObservableList<Reporte> datos = FXCollections.observableArrayList();
        String sql = "SELECT r.id, CONCAT(t.nombre, ' ', t.apellido) as tec, " +
                     "v.placa, v.tipo, CONCAT(o.nombre, ' ', o.apellido) as ope, r.fecha_revision " +
                     "FROM revision r " +
                     "JOIN tecnico t ON r.encargado = t.id " +
                     "JOIN vehiculo v ON r.vehiculo = v.placa " +
                     "JOIN operador o ON r.chofer = o.id";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                datos.add(new Reporte(rs.getInt("id"), rs.getString("tec"), rs.getString("placa"), 
                                     rs.getString("tipo"), rs.getString("ope"), rs.getString("fecha_revision")));
            }
            tablaGenerica.setItems(datos);
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public static void main(String[] args) { launch(args); }
}
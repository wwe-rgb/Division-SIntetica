package fes.aragon.unam.divisionsintetica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HelloApplication extends Application {
    private GridPane coeficientesGrid = new GridPane(); // Para los coeficientes del dividendo
    private TextField[] coeficientesFields; // Campos para los coeficientes
    private TextField divisorField = new TextField();
    private TextField resultadoField = new TextField();
    private TextArea procesoArea = new TextArea(); // Área de texto para mostrar el proceso

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("División Sintética");

        // Hora actual en la parte superior izquierda
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        Label horaLabel = new Label("Hora actual: " + now.format(hourFormatter));

        // Fecha específica en la parte inferior derecha
        Label fechaLabel = new Label("28 de agosto de 2024");

        // Labels
        Label nombreLabel = new Label("Nombre del usuario:");
        Label gradoLabel = new Label("Grado de la función:");
        Label divisorLabel = new Label("Divisor:");
        Label resultadoLabel = new Label("Resultado:");

        // TextFields
        TextField nombreField = new TextField();
        TextField gradoField = new TextField();

        // Botones
        Button generarCoefBtn = new Button("Generar Coeficientes");
        Button dividirBtn = new Button("Dividir");
        Button adiosBtn = new Button("Adiós");

        // Evento al presionar el botón para generar los coeficientes según el grado
        generarCoefBtn.setOnAction(e -> {
            try {
                int grado = Integer.parseInt(gradoField.getText());
                coeficientesFields = new TextField[grado + 1];
                coeficientesGrid.getChildren().clear();

                for (int i = 0; i <= grado; i++) {
                    Label coefLabel = new Label("Coeficiente x^" + (grado - i) + ":");
                    coeficientesFields[i] = new TextField();
                    coeficientesGrid.add(coefLabel, 0, i);
                    coeficientesGrid.add(coeficientesFields[i], 1, i);
                }
            } catch (NumberFormatException ex) {
                resultadoField.setText("Grado no válido");
            }
        });

        // Evento al presionar el botón de dividir
        dividirBtn.setOnAction(e -> {
            try {
                int grado = coeficientesFields.length - 1;
                int[] dividendos = new int[grado + 1];
                for (int i = 0; i <= grado; i++) {
                    dividendos[i] = Integer.parseInt(coeficientesFields[i].getText());
                }
                int divisor = Integer.parseInt(divisorField.getText());

                int[] cocientes = new int[grado];
                cocientes[0] = dividendos[0];

                // Mostrar el proceso de la división sintética
                StringBuilder proceso = new StringBuilder();
                proceso.append("División Sintética:\n\n");

                // Mostrar coeficientes iniciales
                proceso.append("Coeficientes: ");
                for (int i = 0; i <= grado; i++) {
                    proceso.append(dividendos[i]).append(" ");
                }
                proceso.append("\n\n");

                // Realizar el proceso de la división
                for (int i = 1; i < grado; i++) {
                    cocientes[i] = dividendos[i] + divisor * cocientes[i - 1];
                    proceso.append("Paso ").append(i).append(":\n");
                    proceso.append(cocientes[i - 1]).append(" * ").append(divisor).append(" + ")
                            .append(dividendos[i]).append(" = ").append(cocientes[i]).append("\n\n");
                }

                proceso.append("Resultado Final: ");
                for (int i = 0; i < grado; i++) {
                    proceso.append(cocientes[i]).append(" ");
                }

                procesoArea.setText(proceso.toString()); // Mostrar el proceso en el área de texto
                resultadoField.setText(String.valueOf(cocientes[grado - 1])); // Mostrar solo el último cociente en el campo de resultado

            } catch (NumberFormatException ex) {
                resultadoField.setText("Error en los datos de entrada");
            }
        });

        // Evento al presionar el botón de adiós
        adiosBtn.setOnAction(e -> primaryStage.close());

        // Layouts
        coeficientesGrid.setAlignment(Pos.CENTER);
        coeficientesGrid.setHgap(10);
        coeficientesGrid.setVgap(10);
        coeficientesGrid.setPadding(new Insets(25, 25, 25, 25));

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(nombreLabel, 0, 0);
        grid.add(nombreField, 1, 0);
        grid.add(gradoLabel, 0, 1);
        grid.add(gradoField, 1, 1);
        grid.add(coeficientesGrid, 0, 2, 2, 1);
        grid.add(divisorLabel, 0, 3);
        grid.add(divisorField, 1, 3);
        grid.add(resultadoLabel, 0, 4);
        grid.add(resultadoField, 1, 4);

        HBox hbox = new HBox(10, generarCoefBtn, dividirBtn, adiosBtn);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);

        VBox vbox = new VBox(10, grid, hbox);

        // Área de texto para el proceso en el centro de la pantalla
        procesoArea.setEditable(false);
        procesoArea.setPrefHeight(200);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(horaLabel);
        borderPane.setLeft(vbox);
        borderPane.setCenter(procesoArea);
        borderPane.setBottom(fechaLabel);
        BorderPane.setAlignment(horaLabel, Pos.TOP_LEFT);
        BorderPane.setAlignment(fechaLabel, Pos.BOTTOM_RIGHT);

        // Establecer el color de fondo azul
        borderPane.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        // Escena
        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
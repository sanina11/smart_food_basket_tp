package app.ui;

import app.model.Model;
import app.model.BulkFood;
import app.model.FoodItem;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import java.io.IOException;

public class HelloApplication extends Application implements View {

    private Model model;
    private FlowPane grid;
    private VBox sidebar;
    private Label totalPriceLabel;
    private Label totalCaloriesLabel;

    @Override
    public void start(Stage stage) {
        this.model = new Model();
        this.model.setView(this);

        this.loadData();
        this.buildUI(stage);
    }

    private void loadData() {
        try {
            this.model.loadFromCsv("src/main/resources/foods.csv");
        } catch (IOException e) {
            this.showError("Erro ao carregar o ficheiro: " + e.getMessage());
        }
    }

    private void buildUI(Stage stage) {
        BorderPane root = new BorderPane();

        this.grid = new FlowPane();
        this.grid.setHgap(10);
        this.grid.setVgap(10);
        this.grid.setPadding(new javafx.geometry.Insets(14));

        this.sidebar = this.createSidebar();

        root.setCenter(this.grid);
        root.setRight(this.sidebar);

        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Smart Food Basket — ODS Edition");
        stage.setScene(scene);
        stage.show();

        for (FoodItem item : this.model.getCatalog()) {
            this.grid.getChildren().add(this.createTile(item));
        }

        this.updateView();
    }

    private VBox createSidebar() {
        VBox box = new VBox(10);
        box.setPadding(new javafx.geometry.Insets(14));
        box.setPrefWidth(220);
        box.setStyle("-fx-background-color: #F5F4F0; -fx-border-color: #DDD; -fx-border-width: 0 0 0 0.5;");

        Label title = new Label("Dashboard");
        title.setStyle("-fx-font-size: 11px; -fx-text-fill: #888; -fx-font-weight: bold;");

        this.totalPriceLabel = new Label("Custo total: 0.00 €");
        this.totalCaloriesLabel = new Label("Calorias: 0 kcal");

        box.getChildren().addAll(title, this.totalPriceLabel, this.totalCaloriesLabel);
        return box;
    }

    @Override
    public void updateView() {
        this.totalPriceLabel.setText(String.format("Custo total: %.2f €", this.model.getTotalPrice()));
        this.totalCaloriesLabel.setText(String.format("Calorias: %.0f kcal", this.model.getTotalCalories()));
    }

    @Override
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private VBox createTile(FoodItem item) {
        VBox tile = new VBox(4);
        tile.setAlignment(Pos.CENTER);
        tile.setPrefWidth(108);
        tile.setPrefHeight(118);
        tile.setPadding(new javafx.geometry.Insets(10, 6, 10, 6));

        String bgColor = item.getGroup().getColor();
        tile.setStyle(
                "-fx-background-color: " + bgColor + ";" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: rgba(0,0,0,0.07);" +
                        "-fx-border-width: 0.5;" +
                        "-fx-cursor: hand;"
        );

        Label emoji = new Label(item.getGroup().getEmoji());
        emoji.setStyle("-fx-font-size: 28px; -fx-font-family: 'Segoe UI Emoji';");

        Label name = new Label(item.getName());
        name.setStyle("-fx-font-size: 11px; -fx-font-weight: bold;");

        Label price = new Label(String.format("%.2f %s", item.getBasePrice(), item.getUnitLabel()));
        price.setStyle("-fx-font-size: 10px; -fx-text-fill: #555;");

        tile.getChildren().addAll(emoji, name, price);

        tile.setOnMouseClicked(e -> this.handleTileClick(item));

        return tile;
    }

    private void handleTileClick(FoodItem item) {
        if (item.getUnitLabel().equals("€/un.")) {
            this.model.addItem(item);
            return;
        }
        this.askWeightAndAdd(item);
    }

    private void askWeightAndAdd(FoodItem item) {
        TextInputDialog dialog = new TextInputDialog("300");
        dialog.setTitle("Adicionar " + item.getName());
        dialog.setHeaderText(item.getName() + " — " + item.getBasePrice() + " €/kg");
        dialog.setContentText("Quantas gramas deseja adicionar?");

        Optional<String> response = dialog.showAndWait();

        if (response.isEmpty()) {
            return;
        }

        try {
            double grams = Double.parseDouble(response.get());
            BulkFood bulk = new BulkFood(
                    item.getName(), item.getGroup(),
                    item.getBasePrice(), item.getBaseCalories(),
                    grams
            );
            this.model.addItem(bulk);
        } catch (NumberFormatException e) {
            this.showError("Peso inválido: " + response.get());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
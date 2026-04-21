package app.model;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import app.ui.View;



public class Model {

    private final List<FoodItem> catalog;
    private final List<FoodItem> items;
    private final List<String> errorLines;
    private View view;

    public Model (){
        this.catalog = new ArrayList<>();
        this.items = new ArrayList<>();
        this.errorLines = new ArrayList<>();
    }

    public List<FoodItem> getCatalog() {
        return List.copyOf(this.catalog);
    }

    public void addItem(FoodItem item){
        this.items.add(item);
        if (this.view != null){
            this.view.updateView();
        }
    }

    public void removeLastItem(){
        if (!this.items.isEmpty()){
             this.items.remove(this.items.size() - 1);
            if (this.view != null){
                this.view.updateView();
            }
        }
    }

    public List<FoodItem> getItems(){
        return List.copyOf(this.items);
    }

    public double getTotalPrice(){
        double total = 0.0;
        for (FoodItem item : this.items) {
            total = total + item.getPrice();
        }
        return total;
    }

    public double getTotalCalories(){
        double total = 0.0;
        for (FoodItem item : this.items) {
            total = total + item.getCalories();
        }
        return total;
    }

    public boolean hasMetVegetableGoal(){
        double totalGrams = 0.0;
        for (FoodItem item : this.items){
            if (item.getGroup() == FoodGroup.GREEN){
                totalGrams = totalGrams + item.getWeightInGrams();
            }
        }
        return totalGrams >= 400.0;
    }
    public void loadFromCsv(String path) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(path));
        String linha = reader.readLine();

        while (linha != null) {
            try {
                String[] parts = linha.split(",");

                String type = parts[0];
                String name = parts[1];

                if (name.isEmpty() || type.isEmpty()) {
                    throw new IllegalArgumentException();
                }

                FoodGroup group = FoodGroup.valueOf(parts[2]);
                double price = Double.parseDouble(parts[3]);
                double calories = Double.parseDouble(parts[4]);
                String extra = parts[5];

                if (price < 0 || calories < 0) {
                    throw new IllegalArgumentException();
                }

                if (type.equals("PACKAGED")) {
                    this.catalog.add(new PackagedFood(name, group, price, calories, extra));
                } else if (type.equals("BULK")) {
                    this.catalog.add(new BulkFood(name, group, price, calories, 0.0));
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (Exception e) {
                this.errorLines.add(linha);
            }
            linha = reader.readLine();
        }

        reader.close();
    }

    public List<String> getErrorLines() {
        return List.copyOf(this.errorLines);
    }

    public void setView(View view) {
        this.view = view;
    }
}

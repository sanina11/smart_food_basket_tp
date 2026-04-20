package app.model;
import java.util.ArrayList;
import java.util.List;
public class Model {

    private final List<FoodItem> items;
    private final List<String> errorLines;

    public Model (){
        this.items = new ArrayList<>();
        this.errorLines = new ArrayList<>();
    }

    public void addItem(FoodItem item){
        this.items.add(item);
    }

    public void removeLastItem(){
        if (!this.items.isEmpty()){
             this.items.remove(this.items.size() - 1);
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
}

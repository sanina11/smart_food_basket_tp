package app.model;

public abstract class FoodItem {
    private final String name;
    private final FoodGroup group;
    private final double basePrice;
    private final double baseCalories;

    public FoodItem(String name, FoodGroup group, double basePrice, double baseCalories){
        this.name = name;
        this.group = group;
        this.basePrice= basePrice;
        this.baseCalories = baseCalories;
    }

    public abstract double getPrice();
    public abstract double getCalories();

    public double getWeightInGrams(){
        return 0.0;
    }

    public String getName() {
        return this.name;
    }

    public FoodGroup getGroup() {
        return this.group;
    }

    public double getBasePrice() {
        return this.basePrice;
    }

    public double getBaseCalories() {
        return this.baseCalories;
    }
}

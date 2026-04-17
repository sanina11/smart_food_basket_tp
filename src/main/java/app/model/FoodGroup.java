package app.model;

public enum FoodGroup {
    GREEN("#4CAF50"),
    YELLOW("#FFC107"),
    BLUE("#2196F3"),
    BROWN("#795548"),
    RED("#F44336"),
    ORANGE("#FF9800");

    private final String color;

    FoodGroup(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

}
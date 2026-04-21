package app.model;

public enum FoodGroup {
    GREEN ("#EAF3DE", "🥦"),
    YELLOW("#FEF3DC", "🌾"),
    BLUE  ("#E3F0FB", "🥛"),
    BROWN ("#FBEEE8", "🫘"),
    RED   ("#FCEAEA", "🍗"),
    ORANGE("#FFF0DC", "🫒");

    private final String color;
    private final String emoji;

    FoodGroup(String color, String emoji) {
        this.color = color;
        this.emoji = emoji;
    }

    public String getColor() { return this.color; }
    public String getEmoji() { return this.emoji; }
}
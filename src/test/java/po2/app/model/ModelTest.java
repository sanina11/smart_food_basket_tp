package po2.app.model;

import app.model.BulkFood;
import app.model.FoodGroup;
import app.model.Model;
import app.model.PackagedFood;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    public void testPackagedPrice(){
        PackagedFood food = new PackagedFood(
                "Arroz",
                FoodGroup.YELLOW,
                2.50,
                350.0,
                "Cartao"
        );
        assertEquals(2.50, food.getPrice(), 0.001);
    }

    @Test
    public void testBulkPriceCalculation(){
        BulkFood broccoli = new BulkFood(
                "Brócolos",
                FoodGroup.GREEN,
                2.00,
                34.0,
                500.0
        );
        assertEquals(1.00, broccoli.getPrice(), 0.001);

        BulkFood apple = new BulkFood(
                "Maçã",
                FoodGroup.GREEN,
                4.00,
                52.0,
                250.0
        );
        assertEquals(1.00, apple.getPrice(), 0.001);
    }

    @Test
    public void testTotalBasketCalculation(){
        Model model = new Model();

        PackagedFood rice = new PackagedFood("Arroz", FoodGroup.YELLOW, 1.89, 350.0, "Cartao");
        BulkFood broccoli = new BulkFood("Brócolos", FoodGroup.GREEN, 2.00, 34.0, 500.0);
        PackagedFood milk = new PackagedFood("Leite", FoodGroup.BLUE, 0.79, 42.0, "Plastico");

        model.addItem(rice);
        model.addItem(broccoli);
        model.addItem(milk);

        assertEquals(3.68, model.getTotalPrice(), 0.001);
    }

    @Test
    public void testSDGGoalCondition(){
        Model modelA = new Model();
        BulkFood broccoli = new BulkFood("Brócolos", FoodGroup.GREEN, 2.00, 34.0, 500.0);
        modelA.addItem(broccoli);

        assertTrue(modelA.hasMetVegetableGoal());

        Model modelB = new Model();
        BulkFood apple = new BulkFood("Maçã", FoodGroup.GREEN, 1.50, 52.0, 200.0);
        modelB.addItem(apple);

        assertFalse(modelB.hasMetVegetableGoal());

        Model modelC = new Model();
        BulkFood carrots = new BulkFood("Cenoura", FoodGroup.GREEN, 0.90, 41.0, 450.0);
        BulkFood chicken = new BulkFood("Frango", FoodGroup.RED, 5.20, 239.0, 500.0);
        modelC.addItem(carrots);
        modelC.addItem(chicken);

        assertTrue(modelC.hasMetVegetableGoal());
    }
}
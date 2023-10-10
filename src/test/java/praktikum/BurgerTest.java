package praktikum;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {
    private final static double DELTA = 0.01;

    @Mock
    private Bun bun;
    @Mock
    private Ingredient ingredient;

    @Test
    public void setBunsTest() {
        Burger burger = new Burger();
        burger.setBuns(bun);
        Assert.assertEquals(bun, burger.bun);
    }

    @Test
    public void addIngredientTest() {
        Burger burger = new Burger();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient);
        burger.addIngredient(ingredient);
        Assert.assertEquals(ingredients, burger.ingredients);
    }

    @Test
    public void removeIngredientTest() {
        Burger burger = new Burger();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        burger.addIngredient(ingredient);
        burger.removeIngredient(0);
        Assert.assertEquals(ingredients, burger.ingredients);
    }

    @Test
    public void moveIngredientTest() {
        Burger burger = new Burger();
        Ingredient ingredient0 = new Ingredient(IngredientType.SAUCE, "Бульбаш", (float) 20.0);
        Ingredient ingredient1 = new Ingredient(IngredientType.FILLING, "Огурец", (float) 50.0);
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient0);
        burger.addIngredient(ingredient0);
        burger.addIngredient(ingredient1);
        burger.moveIngredient(0, 1);
        Assert.assertEquals(ingredients, burger.ingredients);
    }

    @Test
    public void getPriceTest() {
        Burger burger = new Burger();
        Mockito.when(bun.getPrice()).thenReturn((float) 111.2);
        burger.setBuns(bun);
        Mockito.when(ingredient.getPrice()).thenReturn((float) 20.0);
        burger.addIngredient(ingredient);
        Assert.assertEquals((float) 242.4, burger.getPrice(), DELTA);
    }

    @Test
    public void getReceiptTest() {
        Burger burger = new Burger();
        Mockito.when(bun.getPrice()).thenReturn((float) 111.2);
        Mockito.when(bun.getName()).thenReturn("КебабТести");
        burger.setBuns(bun);
        Mockito.when(ingredient.getPrice()).thenReturn((float) 20.0);
        Mockito.when(ingredient.getName()).thenReturn("Бульбаш");
        Mockito.when(ingredient.getType()).thenReturn(IngredientType.SAUCE);
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient);
        burger.addIngredient(ingredient);
        StringBuilder receipt = new StringBuilder(String.format("(==== %s ====)%n", bun.getName()));
        for (Ingredient i : ingredients) {
            receipt.append(String.format("= %s %s =%n", i.getType().toString().toLowerCase(),
                    i.getName()));
        }
        receipt.append(String.format("(==== %s ====)%n", bun.getName()));
        receipt.append(String.format("%nPrice: %f%n", (float) 242.4));
        Assert.assertEquals(receipt.toString(), burger.getReceipt());
    }

}
package praktikum;

import org.junit.Test;
import org.junit.Assert;

import static praktikum.BunTest.DELTA;


public class IngredientTest {
    private IngredientType ingredientType;

    @Test
    public void getPriceTest() {
        Ingredient ingredient = new Ingredient(ingredientType, "первый", 2.7f);
        Assert.assertEquals(2.7f, ingredient.getPrice(), DELTA);
    }

    @Test
    public void getNameTest() {
        Ingredient ingredient = new Ingredient(ingredientType, "второй", 3.3f);
        Assert.assertEquals("второй", ingredient.getName());
    }

    @Test
    public void getTypeTest() {
        Ingredient ingredient = new Ingredient(ingredientType, "третий",7.7f);
        Assert.assertEquals(ingredientType, ingredient.getType());
    }
}
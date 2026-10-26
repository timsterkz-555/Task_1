package praktikum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IngredientTest {

    @DisplayName("Ингредиент возвращает заданные тип, имя и цену")
    @ParameterizedTest
    @CsvSource({
            "SAUCE, hot sauce, 100",
            "FILLING, cutlet, 25.5",
            "SAUCE, free sauce, 0"
    })
    void testGetTypeNameAndPrice(IngredientType type, String name, float price) {
        Ingredient ingredient = new Ingredient(type, name, price);

        assertEquals(type, ingredient.getType(), "Тип ингредиента должен совпадать с заданным");
        assertEquals(name, ingredient.getName(), "Имя ингредиента должно совпадать с заданным");
        assertEquals(price, ingredient.getPrice(), "Цена ингредиента должна совпадать с заданной");
    }
}

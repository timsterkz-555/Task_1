package praktikum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BurgerTest {

    private final Burger burger = new Burger();
    private final Bun bun = mock(Bun.class);
    private final Ingredient sauce = mock(Ingredient.class);
    private final Ingredient filling = mock(Ingredient.class);

    @DisplayName("Установка булочки сохраняет переданный объект")
    @Test
    void testSetBuns() {
        burger.setBuns(bun);

        assertSame(bun, burger.bun, "Бургер должен содержать переданную булочку");
    }

    @DisplayName("Добавление ингредиентов сохраняет их порядок")
    @Test
    void testAddIngredient() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        assertIterableEquals(Arrays.asList(sauce, filling), burger.ingredients,
                "Ингредиенты должны располагаться в порядке добавления");
    }

    @DisplayName("Удаление ингредиента по индексу сохраняет остальные ингредиенты")
    @Test
    void testRemoveIngredient() {
        Ingredient cheese = mock(Ingredient.class);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.addIngredient(cheese);

        burger.removeIngredient(1);

        assertIterableEquals(Arrays.asList(sauce, cheese), burger.ingredients,
                "Должен удалиться только ингредиент с указанным индексом");
    }

    @DisplayName("Перемещение ингредиента сохраняет состав и устанавливает ожидаемый порядок")
    @ParameterizedTest
    @CsvSource({
            "0, 2, 1, 2, 0",
            "2, 0, 2, 0, 1",
            "1, 1, 0, 1, 2"
    })
    void testMoveIngredient(int index, int newIndex, int expectedFirstIndex,
                            int expectedSecondIndex, int expectedThirdIndex) {
        Ingredient cheese = mock(Ingredient.class);
        List<Ingredient> initialOrder = Arrays.asList(sauce, filling, cheese);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.addIngredient(cheese);

        burger.moveIngredient(index, newIndex);

        List<Ingredient> expectedOrder = Arrays.asList(initialOrder.get(expectedFirstIndex),
                initialOrder.get(expectedSecondIndex), initialOrder.get(expectedThirdIndex));
        assertIterableEquals(expectedOrder, burger.ingredients,
                "Порядок ингредиентов после перемещения должен соответствовать ожидаемому");
    }

    @DisplayName("Цена бургера без ингредиентов равна стоимости двух булочек")
    @Test
    void testGetPriceWithoutIngredients() {
        when(bun.getPrice()).thenReturn(100F);
        burger.setBuns(bun);

        assertEquals(200F, burger.getPrice(), "В цену должны входить две булочки");
    }

    @DisplayName("Цена бургера включает две булочки и каждый ингредиент")
    @ParameterizedTest
    @CsvSource({
            "100, 25.5, 50, 275.5",
            "25.5, 10.25, 4.75, 66",
            "0, 0, 0, 0"
    })
    void testGetPriceWithIngredients(float bunPrice, float saucePrice, float fillingPrice, float expectedPrice) {
        when(bun.getPrice()).thenReturn(bunPrice);
        when(sauce.getPrice()).thenReturn(saucePrice);
        when(filling.getPrice()).thenReturn(fillingPrice);
        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        assertEquals(expectedPrice, burger.getPrice(), "Цена бургера должна учитывать булочки и все ингредиенты");
    }

    @DisplayName("Чек содержит булочки, ингредиенты в порядке добавления и итоговую цену")
    @Test
    void testGetReceipt() {
        when(bun.getName()).thenReturn("black bun");
        when(bun.getPrice()).thenReturn(100F);
        when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        when(sauce.getName()).thenReturn("hot sauce");
        when(sauce.getPrice()).thenReturn(25.5F);
        when(filling.getType()).thenReturn(IngredientType.FILLING);
        when(filling.getName()).thenReturn("cutlet");
        when(filling.getPrice()).thenReturn(50F);
        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        String expectedReceipt = String.format("(==== black bun ====)%n"
                + "= sauce hot sauce =%n"
                + "= %s cutlet =%n"
                + "(==== black bun ====)%n"
                + "%nPrice: %f%n", "FILLING".toLowerCase(), 275.5F);

        assertEquals(expectedReceipt, burger.getReceipt(), "Содержимое и формат чека должны соответствовать ожидаемым");
    }
}

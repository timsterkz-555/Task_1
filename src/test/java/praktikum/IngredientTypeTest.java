package praktikum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class IngredientTypeTest {

    @DisplayName("Типы ингредиентов содержат соус и начинку")
    @Test
    void testValues() {
        IngredientType[] expectedTypes = {IngredientType.SAUCE, IngredientType.FILLING};

        assertArrayEquals(expectedTypes, IngredientType.values(),
                "Типы ингредиентов должны содержать SAUCE и FILLING в указанном порядке");
    }
}

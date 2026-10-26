package praktikum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BunTest {

    @DisplayName("Булочка возвращает заданные имя и цену")
    @ParameterizedTest
    @CsvSource({
            "black bun, 100",
            "white bun, 25.5",
            "free bun, 0"
    })
    void testGetNameAndPrice(String name, float price) {
        Bun bun = new Bun(name, price);

        assertEquals(name, bun.getName(), "Имя булочки должно совпадать с заданным");
        assertEquals(price, bun.getPrice(), "Цена булочки должна совпадать с заданной");
    }
}

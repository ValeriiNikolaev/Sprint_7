import ModelOrder.CreateOrder;
import ModelOrder.OrderGeneration;
import Steps.OrderSteps;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTests {
    OrderSteps orderSteps = new OrderSteps();
    OrderGeneration generation = new OrderGeneration();

    @Parameterized.Parameters(name = "color: {0}")
    public static Object[][] getColorForOrder() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}},
        };
    }

    @Parameterized.Parameter()
    public String[] color;

    @Test
    @DisplayName("Create order")
    public void createOrderTests() {
        CreateOrder createOrder = generation.newOrder(color);
        orderSteps.createOrder(createOrder)
                .statusCode(SC_CREATED)
                .body("track", notNullValue());
    }
}

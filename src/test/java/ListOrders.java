import steps.OrderSteps;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class ListOrders {

    OrderSteps orderSteps = new OrderSteps();

    @Test
    @DisplayName("Get list orders")
    public void getOrderListTests() {

        orderSteps.getOrderList()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}

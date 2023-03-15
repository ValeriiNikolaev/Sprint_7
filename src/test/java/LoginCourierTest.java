import models_courier.Courier;
import models_courier.CourierGeneration;
import models_courier.CreateCourier;
import steps.ClientSteps;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {
    ClientSteps clientStep = new ClientSteps();
    CourierGeneration generation = new CourierGeneration();
    private int courierId;

    @After
    public void cleanUp() {
        clientStep.deleteClient(courierId);
    }

    @Test
    @DisplayName("Authorization successful")
    public void successfulAuthorization () {
        CreateCourier createCourier = generation.newCourier();
        Courier courierCredentials = new Courier(createCourier.getLogin(),createCourier.getPassword());
        clientStep.createCourier(createCourier);
        clientStep.loginClient(courierCredentials)
                .statusCode(SC_OK)
                .body("id", notNullValue());
        courierId = clientStep.loginClientGetId(courierCredentials);
    }

    @Test
    @DisplayName("Authorization no successful = not field login")
    public void cannotAuthorizationNotFieldLoginTest() {
        CreateCourier createCourier = generation.newCourier();
        Courier courierCredentials = new Courier("", createCourier.getPassword());
        clientStep.createCourier(createCourier);
        clientStep.loginClient(courierCredentials)
                .statusCode(SC_BAD_REQUEST)
                .body("message",equalTo("Недостаточно данных для входа"));
        courierCredentials.setLogin(createCourier.getLogin());
        courierId = clientStep.loginClientGetId(courierCredentials);

    }

    @Test
    @DisplayName("Authorization no successful = not field password")
    public void cannotAuthorizationNotFieldPasswordTest() {
        CreateCourier createCourier = generation.newCourier();
        Courier courierCredentials = new Courier(createCourier.getLogin(), "");
        clientStep.createCourier(createCourier);
        clientStep.loginClient(courierCredentials)
                .statusCode(SC_BAD_REQUEST)
                .body("message", Matchers.equalTo("Недостаточно данных для входа"));
        courierCredentials.setPassword(createCourier.getPassword());
        courierId = clientStep.loginClientGetId(courierCredentials);
    }

    @Test
    @DisplayName("Authorization no successful = not created login")
    public void loginWithUnknownLogin() {
        CreateCourier createCourier = generation.newCourier();
        Courier courierCredentials = new Courier(createCourier.getLogin(), createCourier.getPassword());
        clientStep.loginClient(courierCredentials)
                .statusCode(SC_NOT_FOUND)
                .body("message", Matchers.equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Authorization no successful = incorrect password ")
    public void loginWithIncorrectPassword() {
        CreateCourier createCourier = generation.newCourier();
        Courier courierCredentials = new Courier(createCourier.getLogin(), createCourier.getIncorrectPassword());
        clientStep.createCourier(createCourier);
        clientStep.loginClient(courierCredentials)
                .statusCode(SC_NOT_FOUND)
                .body("message", Matchers.equalTo("Учетная запись не найдена"));
        courierCredentials.setPassword(createCourier.getPassword());
        courierId = clientStep.loginClientGetId(courierCredentials);
    }


}

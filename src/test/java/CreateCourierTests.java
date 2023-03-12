import ModelsCourier.Courier;
import ModelsCourier.CourierGeneration;
import ModelsCourier.CreateCourier;
import Steps.ClientSteps;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTests {
    ClientSteps clientStep = new ClientSteps();
    CourierGeneration  generation = new CourierGeneration();
    private int courierId;


    @After
    public void cleanUp() {
        clientStep.deleteClient(courierId);
    }

    @Test
    @DisplayName("Create courier")
    public void createClientTest() {
        CreateCourier createCourier = generation.newCourier();
        Courier courierCredentials = new Courier(createCourier.getLogin(), createCourier.getPassword());
        clientStep.createCourier(createCourier)
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));
        courierId = clientStep.loginClientGetId(courierCredentials);
    }

    @Test
    @DisplayName("Create courier duplicate = error")
    public void createDuplicateCourierTest() {
        CreateCourier createCourier = generation.newCourier();
        Courier courierCredentials = new Courier(createCourier.getLogin(), createCourier.getPassword());
        clientStep.createCourier(createCourier);
        clientStep.createCourier(createCourier)
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        courierId = clientStep.loginClientGetId(courierCredentials);

    }

    @Test
    @DisplayName("Create courier not enter field password")
    public void notCreateCourierNoWitchFieldPasswordTest() {
        CreateCourier createCourier = generation.newCourier();
        createCourier.setPassword("");
        clientStep.createCourier(createCourier)
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Not cannot create courier no enter field login")
    public void cannotCreateCourierNoEnterFieldLoginTest() {
        CreateCourier createCourier = generation.newCourier();
        createCourier.setLogin("");
        clientStep.createCourier(createCourier)
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

}

package Steps;


import ModelOrder.CreateOrder;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    public final String BASE_URI = "http://qa-scooter.praktikum-services.ru";
    public final String Api_Order = "/api/v1/orders";


    @Step ("Get order list")
    public ValidatableResponse getOrderList() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get(Api_Order)
                .then();
    }


    @Step ("Post Create order")
    public ValidatableResponse createOrder(CreateOrder order) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(order)
                .when()
                .post(Api_Order)
                .then();
    }

}


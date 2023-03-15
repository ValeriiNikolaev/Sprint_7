package steps;


import model_order.CreateOrder;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    public static final String baseUri = "http://qa-scooter.praktikum-services.ru";
    public static final String apiOrder = "/api/v1/orders";


    @Step ("Get order list")
    public ValidatableResponse getOrderList() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(baseUri)
                .when()
                .get(apiOrder)
                .then();
    }


    @Step ("Post Create order")
    public ValidatableResponse createOrder(CreateOrder order) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(baseUri)
                .body(order)
                .when()
                .post(apiOrder)
                .then();
    }

}


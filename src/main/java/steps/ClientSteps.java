package steps;

import models_courier.Courier;
import models_courier.CreateCourier;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class ClientSteps {

    public static final String base_uri = "http://qa-scooter.praktikum-services.ru";

    public static final String api_courier = "/api/v1/courier";

    @Step ("Create courier")
    public ValidatableResponse createCourier(CreateCourier client) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(base_uri)
                .body(client)
                .when()
                .post(api_courier)
                .then();
    }

    @Step ("Login Courier")
    public ValidatableResponse loginClient(Courier client) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(base_uri)
                .body(client)
                .when()
                .post(api_courier + "/login")
                .then();
    }

    public int loginClientGetId (Courier client) {
        return loginClient(client).extract().path("id");
    }

    @Step ("Delete Courier")
    public void deleteClient (int id) {
        given()
                .contentType(ContentType.JSON)
                .baseUri(base_uri)
                .when()
                .delete(api_courier + "/" +id)
                .then();
    }

}

package Steps;

import ModelsCourier.Courier;
import ModelsCourier.CreateCourier;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class ClientSteps {

    public final String BASE_URI = "http://qa-scooter.praktikum-services.ru";

    public final String api_courier = "/api/v1/courier";

    @Step ("Create courier")
    public ValidatableResponse createCourier(CreateCourier client) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(client)
                .when()
                .post(api_courier)
                .then();
    }

    @Step ("Login Courier")
    public ValidatableResponse loginClient(Courier client) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
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
                .baseUri(BASE_URI)
                .when()
                .delete(api_courier + "/id")
                .then();
    }

}

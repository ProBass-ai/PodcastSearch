package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class Services {

    private RequestSpecification requestSpec;
    private final String baseURL;
    private String endPoint = "";
    private Map<String, String> headers;
    private String method;


    public Services(String baseURL) {
        this.baseURL = baseURL;
    }

    public void setEndpoint(String endpoint) {
        this.endPoint = endpoint;
    }

    public void build() {
        requestSpec = new RequestSpecBuilder().setBaseUri(baseURL).build();
    }

    private RequestSpecification request() {
        RequestSpecification requestModifier = given().spec(requestSpec);
        if (headers != null) {
            requestModifier.headers(headers);
        }
        return requestModifier;
    }

    public void setHTTPMethod(String method) {
        this.method = method.toUpperCase();
    }

    public Response send() {
        return request().get(endPoint);
    }

}

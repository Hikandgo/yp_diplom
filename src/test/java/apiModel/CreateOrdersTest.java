package praktikum;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class CreateOrdersTest {

    private final List<String> ingridients;
    private final int statusCode;

    public CreateOrdersTest(List<String> ingridients, int statusCode, String testsNames) {
        this.ingridients = ingridients;
        this.statusCode = statusCode;
    }

    @Parameterized.Parameters(name = "{2}")
    public static Object[][] params() {
        return new Object[][]{
                {List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"), 200, "есть ингредиентами"},
                {List.of(), 400, "нет ингредиентов"},
                {List.of("no", "qq"), 500, "ошибка хеша ингредиентов"},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    @Test
    public void checkCreateOrdersResponseBodyTest() {
        Random random = new Random();
        String email = "something" + random.nextInt(10000000) + "@yandex.ru";
        String password = "password" + random.nextInt(10000000);
        CreateUser createUser = new CreateUser(email, password, "TestNaming");
        Response responseCreate = UserClient.postApiAuthRegister(createUser);
        responseCreate.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
        String responseString = responseCreate.body().asString();
        Gson gson = new Gson();
        CreateUserResponse createUserResponse = gson.fromJson(responseString, CreateUserResponse.class);
        String accessToken = createUserResponse.getAccessToken();
        Ingredients ingredientsReq = new Ingredients(ingridients);
        OrderClient.postApiOrders(accessToken, ingredientsReq).then().assertThat()
                .statusCode(statusCode);
        UserClient.deleteApiAuthUser(accessToken).then().assertThat().body("success", equalTo(true))
                .and()
                .body("message", equalTo("User successfully removed"))
                .and()
                .statusCode(202);
    }

    @Test
    public void checkCreateOrdersNoAuthResponseBodyTest() {
        Ingredients ingredientsReq = new Ingredients(ingridients);
        OrderClient.postApiOrders(ingredientsReq).then().assertThat()
                .statusCode(statusCode);
    }
}

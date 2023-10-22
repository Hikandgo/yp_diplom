package apiModel;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTest {
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = BaseConstants.BASE_URL.getStr();
    }

    @Test
    public void checkCreateUserResponseBodyTest() {
        Random random = new Random();
        String email = "something" + random.nextInt(10000000) + "@yandex.ru";
        CreateUser createUser = new CreateUser(email, "test15", "testName");
        Response response = UserClient.postApiAuthRegister(createUser);
        String responseString = response.body().asString();
        Gson gson = new Gson();
        CreateUserResponse createUserResponse = gson.fromJson(responseString, CreateUserResponse.class);
        this.accessToken = createUserResponse.getAccessToken();
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    public void checkCreateUserAlreadyRegisteredResponseBodyTest() {
        Random random = new Random();
        String email = "something" + random.nextInt(10000000) + "@yandex.ru";
        CreateUser createUser = new CreateUser(email, "test15", "testName");
        Response response = UserClient.postApiAuthRegister(createUser);
        String responseString = response.body().asString();
        Gson gson = new Gson();
        CreateUserResponse createUserResponse = gson.fromJson(responseString, CreateUserResponse.class);
        this.accessToken = createUserResponse.getAccessToken();
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    public void checkCreateUserWithoutFieldResponseBodyTest() {
        Random random = new Random();
        String email = "something" + random.nextInt(10000000) + "@yandex.ru";
        CreateUser createUser = new CreateUser(email,"test15", null);
        Response response = UserClient.postApiAuthRegister(createUser);
        String responseString = response.body().asString();
        Gson gson = new Gson();
        CreateUserResponse createUserResponse = gson.fromJson(responseString, CreateUserResponse.class);
        this.accessToken = createUserResponse.getAccessToken();
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            UserClient.deleteApiAuthUser(accessToken).then().assertThat().body("success", equalTo(true))
                    .and()
                    .body("message", equalTo("User successfully removed"))
                    .and()
                    .statusCode(202);
        }
    }
}

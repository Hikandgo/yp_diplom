package apiModel;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;

public class UpdateUserTest {
    private String email;
    private String password;
    private String name;

    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = BaseConstants.BASE_URL.getStr();
        Random random = new Random();
        this.email = "something" + random.nextInt(10000000) + "@yandex.ru";
        this.password = "password" + random.nextInt(10000000);
        CreateUser createUser = new CreateUser(email, password, "TestName");
        Response responseCreate = UserClient.postApiAuthRegister(createUser);
        String responseString = responseCreate.body().asString();
        Gson gson = new Gson();
        CreateUserResponse createUserResponse = gson.fromJson(responseString, CreateUserResponse.class);
        this.accessToken = createUserResponse.getAccessToken();
    }

    @Test
    public void checkUpdateUserResponseBodyTest() {
        Random random = new Random();
        this.email = "somebody" + random.nextInt(10000000) + "@yandex.ru";
        this.name = "somebody" + random.nextInt(10000000);
        this.password = "password" + random.nextInt(10000000);
        CreateUser createUser = new CreateUser(email, password, name);
        UserClient.patchApiAuthUser(accessToken, createUser).then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    public void checkUpdateUserNoAuthResponseBodyTest() {
        UserClient.getApiAuthUser().then().assertThat().body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }

    @After
    public void cleanUp() {
        UserClient.deleteApiAuthUser(accessToken).then().assertThat().body("success", equalTo(true))
                .and()
                .body("message", equalTo("User successfully removed"))
                .and()
                .statusCode(202);
    }
}

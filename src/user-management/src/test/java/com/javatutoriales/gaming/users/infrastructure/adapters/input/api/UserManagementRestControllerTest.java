package com.javatutoriales.gaming.users.infrastructure.adapters.input.api;

import com.javatutoriales.gaming.users.domain.valueobjects.AccountId;
import com.javatutoriales.gaming.users.domain.valueobjects.Profile;
import com.javatutoriales.gaming.users.infrastructure.adapters.input.api.handlers.RestExceptionHandler;
import com.javatutoriales.gaming.users.infrastructure.adapters.input.api.register.services.RegisterAccountService;
import com.javatutoriales.gaming.users.infrastructure.adapters.input.api.register.RegisterAccountRequest;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;

@WebMvcTest(controllers = UserManagementRestController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class UserManagementRestControllerTest {
    @Autowired
    private UserManagementRestController restController;

    @Autowired
    private RestExceptionHandler restExceptionHandler;

    @MockBean
    private RegisterAccountService registerAccountService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(restController, restExceptionHandler);
    }

    @Test
    @DisplayName("Valid account registration request")
    void givenAValidAccountDto_whenRequestToRegisterTheAccount_thenA201StatusCodeAndLocationHeaderWithAccountIdAndBodyWithAccountId(){

        RegisterAccountRequest registerAccountRequest = new RegisterAccountRequest("user", "password", "firstName", "lastName", "email@email.com", Profile.STAFF);
        String expectedUUID = UUID.randomUUID().toString();

        BDDMockito.given(registerAccountService.registerAccount(registerAccountRequest)).willReturn(AccountId.withId(expectedUUID));

        given()
            .contentType(ContentType.JSON)
            .body(registerAccountRequest)
        .when()
            .post("/v1/users", registerAccountRequest)
        .then()
                .assertThat()
                    .body("value", is(expectedUUID))
                    .contentType(ContentType.JSON)
                    .status(HttpStatus.CREATED)
                    .header(HttpHeaders.LOCATION, endsWith(expectedUUID));
    }

    @Test
    @DisplayName("Registration request with missing required fields")
    void givenAnAccountDtoWithoutARequiredField_whenSendingARequestToRegisterTheAccount_thenAnErrorWithTheListOfMissingFieldsShouldBeReceived() throws UnsupportedEncodingException {
        RegisterAccountRequest registerAccountRequest = new RegisterAccountRequest(null, "password", "", "lastName", "email@email.com", Profile.STAFF);

        MockMvcResponse response = given()
            .contentType(ContentType.JSON)
            .body(registerAccountRequest)
        .when()
            .post("/v1/users", registerAccountRequest)
        .then()
            .assertThat()
                .status(HttpStatus.BAD_REQUEST).extract().response();


        var mvcResult = response.getMvcResult();
        var contentType = mvcResult.getResponse().getContentType();
        var contentBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonPath jsonPath = JsonPath.from(contentBody);

        assertThat(contentType).isEqualTo(APPLICATION_PROBLEM_JSON.toString());
        assertThat(jsonPath.getString("title")).isEqualTo("Bad Request");
        assertThat(jsonPath.getString("detail")).isEqualTo("The provided parameters are invalid");
        assertThat(jsonPath.getInt("fields.size()")).isEqualTo(2);

        var errorsList = jsonPath.getMap("fields");
        assertThat(errorsList)
                .containsEntry("firstName", "must not be blank")
                .containsEntry("username", "must not be blank")
                .doesNotContainKey("password")
                .doesNotContainKey("lastName")
                .doesNotContainKey("email")
                .doesNotContainKey("profile");


        var resolvedException = mvcResult.getResolvedException();
        assertThat(resolvedException).isInstanceOf(MethodArgumentNotValidException.class);

        String message = resolvedException.getMessage();

        assertThat(message)
                .contains("with 2 errors")
                .contains("firstName")
                .contains("username")
                .contains("must not be blank")
                .doesNotContain("password")
                .doesNotContain("lastName")
                .doesNotContain("email")
                .doesNotContain("profile");
    }
}
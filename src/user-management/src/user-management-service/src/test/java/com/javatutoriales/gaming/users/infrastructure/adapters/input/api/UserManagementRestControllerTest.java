package com.javatutoriales.gaming.users.infrastructure.adapters.input.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatutoriales.gaming.users.domain.valueobjects.AccountId;
import com.javatutoriales.gaming.users.domain.valueobjects.Profile;
import com.javatutoriales.gaming.users.infrastructure.adapters.input.api.register.RegisterAccountRequest;
import com.javatutoriales.gaming.users.infrastructure.adapters.input.api.register.services.RegisterAccountService;
import com.javatutoriales.gaming.users.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = UserManagementRestController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class UserManagementRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegisterAccountService registerAccountService;


    private final static String API_PATH = "/v1/users";

    @Test
    @DisplayName("Valid account registration request")
    void givenAValidAccountDto_whenRequestToRegisterTheAccount_thenA201StatusCodeAndLocationHeaderWithAccountIdAndBodyWithAccountId() throws Exception {

        RegisterAccountRequest registerAccountRequest = new RegisterAccountRequest("user", "password", "firstName", "lastName", "email@email.com", Profile.STAFF);

        final String expectedUUID = UUID.randomUUID().toString();
        final String expectedLocation = "%s%s/%s".formatted(TestUtils.HOST, API_PATH, expectedUUID);

        BDDMockito.given(registerAccountService.registerAccount(registerAccountRequest)).willReturn(AccountId.withId(expectedUUID));

        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH).contentType(MediaType.APPLICATION_JSON).content(asJsonString(registerAccountRequest)))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.LOCATION, expectedLocation))
                .andExpect(MockMvcResultMatchers.jsonPath("$.value", Matchers.is(expectedUUID)));
    }

    @Test
    @DisplayName("Registration request with missing required fields")
    void givenAnAccountDtoWithoutARequiredField_whenSendingARequestToRegisterTheAccount_thenAnErrorWithTheListOfMissingFieldsShouldBeReceived() throws Exception {
        RegisterAccountRequest registerAccountRequest = new RegisterAccountRequest(null, "password", "", "lastName", "email@email.com", Profile.STAFF);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(API_PATH).contentType(MediaType.APPLICATION_JSON).content(asJsonString(registerAccountRequest)))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Bad Request")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.detail", Matchers.is("The provided parameters are invalid")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fields.firstName", Matchers.is("must not be blank")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fields.username", Matchers.is("must not be blank")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fields.password").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fields.lastName").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fields.email").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fields.profile").doesNotExist())
                .andReturn();

        var resolvedException = mvcResult.getResolvedException();
        AssertionsForClassTypes.assertThat(resolvedException).isInstanceOf(MethodArgumentNotValidException.class);

        String message = resolvedException.getMessage();

        AssertionsForClassTypes.assertThat(message)
                .contains("with 2 errors")
                .contains("firstName")
                .contains("username")
                .contains("must not be blank")
                .doesNotContain("password")
                .doesNotContain("lastName")
                .doesNotContain("email")
                .doesNotContain("profile");
    }


    private String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
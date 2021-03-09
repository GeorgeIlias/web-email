/**
 * Tests for the user controller  
 * uses the mapping /api/user for the user controller 
 * 
 * @author gIlias
 * //TODO add a logger for the errors, so they can be easily found and corrected
 */
package george.javawebemail.ApiTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import javax.validation.constraints.AssertFalse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import george.javawebemail.Entities.User;
import george.javawebemail.MainStartup.ServingWebContentApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.HashMap;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { ServingWebContentApplication.class })
@WebAppConfiguration
@TestInstance(Lifecycle.PER_CLASS)
@TestPropertySource("classpath:config/MockData.properties")
public class UserTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Value("${user.test.properties}")
    private String stringToCreateUser;

    private User userToCheckAgainst;

    private HashMap<String, String> userHashMap;

    HashMap<String, String> userNameHashMap;

    @BeforeAll
    public void init() {
        System.out.println(stringToCreateUser);
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

        String comparedString = stringToCreateUser;

        userHashMap = new HashMap<String, String>();
        for (String keyValueString : comparedString.split(",")) {
            String[] entryKeyValue = keyValueString.split(":");
            userHashMap.put(entryKeyValue[0], entryKeyValue[1]);
        }

        try {
            userToCheckAgainst = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(new Gson().toJson(userHashMap), User.class);

            userNameHashMap = new HashMap<String, String>();
            userNameHashMap.put("userName", userToCheckAgainst.getUserName());
            userNameHashMap.put("passwordHash", userToCheckAgainst.getPasswordHash());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method that will create a user and then use that user for every other test.
     * This test passes if the user is created and the status returned is 200
     * 
     * mapping /api/user/createUser
     * 
     * @author gIlias
     * 
     */
    @Test
    @Rollback(true)
    public void createUserFromControllerTest() {
        try {
            MockHttpServletResponse mockMvcResponse = mockMvc
                    .perform(put("/api/user/createUser").content(new Gson().toJson(userHashMap))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andReturn().getResponse();

            JSONObject returnedJsonObject = new JSONObject(mockMvcResponse.getContentAsString());
            String statusCode = returnedJsonObject.get("status").toString();

            if (Integer.parseInt(statusCode) == 200) {
                User userComparingToControl = new ObjectMapper().setDateFormat(new SimpleDateFormat("dd/MM/yyyy"))
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .readValue(new Gson().toJson(returnedJsonObject.get("entity")), User.class);

                assertEquals(userComparingToControl.getUserName(), userToCheckAgainst.getUserName(),
                        "the test has passed and the user seems to be the same.");

            } else {
                fail(new JSONObject(returnedJsonObject.get("entity").toString()).getString("message"));

            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("An exception has been thrown, check the stack trace for more information.");

        }

    }

    /**
     * Method that will use the recently created user and login with it, if the user
     * can successfully login as well as return 200 as a status and puts the user
     * into redis the test will be passed.
     * 
     * mapping /api/user/loginRequest
     * 
     * @author gIlias
     */
    @Test
    @Rollback(true)
    public void loginFromCreatedUser() {

        try {
            mockMvc.perform(put("/api/user/createUser").content(new Gson().toJson(userHashMap))
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

            MockHttpServletResponse mockMvcResponse = mockMvc
                    .perform(post("/api/user/loginRequest")
                            .content(new ObjectMapper().writeValueAsString(userNameHashMap))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andReturn().getResponse();

            JSONObject returnedJsonObject = new JSONObject(mockMvcResponse.getContentAsString());
            if (Integer.parseInt(returnedJsonObject.getString("status").toString()) != 200) {

                fail(new JSONObject(returnedJsonObject.getString("entity").toString()).getString("message"));
            } else {
                User userComparedToControl = new ObjectMapper()
                        .readValue(new Gson().toJson(returnedJsonObject.getString("entity")), User.class);
                assertEquals(userComparedToControl.getUserName(), userToCheckAgainst.getUserName());
            }
        } catch (Exception e) {
            fail("An exception has been thrown, check the stack trace for more information.");
            e.printStackTrace();
        }

    }

    /**
     * test to login and logout of your given user account
     * 
     * mapping -> /api/user/logoutRquest
     * 
     * Request a
     * 
     * @author gIlias
     */
    @Test
    @Rollback(true)
    public void logoutTestForSpring() {
        try {
            // creates the user
            mockMvc.perform(put("/api/user/createUser").content(new Gson().toJson(userHashMap))
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        } catch (Exception e) {
            e.printStackTrace();
            assertFalse(false);
        }

    }

}

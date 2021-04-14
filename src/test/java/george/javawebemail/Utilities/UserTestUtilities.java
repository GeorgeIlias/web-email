package george.javawebemail.Utilities;

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.springframework.mock.web.MockHttpServletResponse;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class UserTestUtilities {

    /**
     * static helper method, since the database gets reset every time then i have to
     * repate code or, in this case make a method and use it instead.
     * 
     * 
     * 
     * @param mockMvc
     * @param userToCreate
     * @return
     * @author gIlias
     * @throws Exception
     */
    public static MockHttpServletResponse registerUserForTests(HashMap<String, String> userTocreate, MockMvc mockMvc)
            throws Exception {
        MockHttpServletResponse createMockMvcResponseUser = null;
        try {
            createMockMvcResponseUser = mockMvc
                    .perform(put("/api/user/createUser").content(new Gson().toJson(userTocreate))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andReturn().getResponse();

        } catch (Exception e) {
            assertFalse(false,
                    "An error has been caught when registering a user, please check the stack trace for more information");
            throw e;

        }
        return createMockMvcResponseUser;
    }

    /**
     * static helper method to allow for the login of a test user after the creation
     * of that same user
     * 
     * @param userNameHashMap
     * @param mockMvc
     * @return MockHttpServletResponse
     * @author gIlias
     * @throws Exception
     */
    public static MockHttpServletResponse LoginUser(HashMap<String, String> userNameHashMap, MockMvc mockMvc)
            throws Exception {
        MockHttpServletResponse mockMvcResponse = null;
        try {
            mockMvcResponse = mockMvc
                    .perform(post("/api/user/loginRequest")
                            .content(new ObjectMapper().writeValueAsString(userNameHashMap))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andReturn().getResponse();
        } catch (Exception e) {
            assertFalse(false,
                    "An error has been caught when logging the user in, please check the stack trace for more information");
            throw e;
        }
        return mockMvcResponse;
    }

    /**
     * Static helper method to allow for the logoug of a test user after the
     * creation of the same user
     * 
     * @param mockMvc
     * @return MockHttpServletResponse
     * @throws Exception
     */
    public static MockHttpServletResponse logoutUser(MockMvc mockMvc, Long id) throws Exception {
        MockHttpServletResponse mockMvcResponse = null;
        try {
            mockMvcResponse = mockMvc.perform(get("/api/user/logoutRequest").param("id", String.valueOf(id)))
                    .andExpect(status().isOk()).andReturn().getResponse();

        } catch (Exception e) {
            assertFalse(false,
                    "An error has been caught when logging out of the user, please check the stack trace for more information");
            throw e;
        }
        return mockMvcResponse;
    }

}

package upgrad.movieapp.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import upgrad.movieapp.api.model.UserDetailsResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserAdminControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getUser() throws Exception {
        UserDetailsResponse userDetailsResponse = mock();

        mvc.perform(MockMvcRequestBuilders.get("/v1/users/7d174a25-ba31-45a8-85b4-b06ffc9d5f8f"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(userDetailsResponse.getId()));
    }

    private UserDetailsResponse mock() {
        UserDetailsResponse response = new UserDetailsResponse();
        response.setId("7d174a25-ba31-45a8-85b4-b06ffc9d5f8f");
        return response;
    }

}

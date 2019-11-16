// package upgrad.movieapp.api.controller;

// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// import java.nio.charset.Charset;

// import org.apache.commons.codec.binary.Base64;
// import org.junit.Ignore;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.junit4.SpringRunner;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.ResultActions;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import upgrad.movieapp.api.model.UserDetailsResponse;

// @RunWith(SpringRunner.class)
// @SpringBootTest
// @AutoConfigureMockMvc
// public class UserAdminControllerTest {

//     @Autowired
//     private MockMvc mvc;
    
//     private final String testUserEmail = "srishti@upgrad.com";
//     private final String testUserPassword = "upgrad";

//     @Test
//     public void getUser() throws Exception {
//     	HttpHeaders headers = new HttpHeaders();
//         headers.setContentType(MediaType.APPLICATION_JSON);
//     	String auth = testUserEmail + ":" + testUserPassword;
//         byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")) );
//         String loginAuthToken = "Basic " + new String( encodedAuth );
//         headers.add("Authorization", loginAuthToken);
// 		ResultActions response = mvc.perform(MockMvcRequestBuilders.post("/v1/auth/login")
// 				.headers(headers))
// 				.andExpect(status().isOk());
// 		String userAdminAuthToken = response.andReturn().getResponse().getHeader("access-token");
    	
//         UserDetailsResponse userDetailsResponse = mock();
//         mvc.perform(MockMvcRequestBuilders.get("/v1/admin/users/7d174a25-ba31-45a8-85b4-b06ffc9d5f8f")
//         		.header("Authorization", "Bearer " + userAdminAuthToken))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("id").value(userDetailsResponse.getId()));
//     }

//     private UserDetailsResponse mock() {
//         UserDetailsResponse response = new UserDetailsResponse();
//         response.setId("7d174a25-ba31-45a8-85b4-b06ffc9d5f8f");
//         return response;
//     }

// }

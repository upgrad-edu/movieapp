// package upgrad.movieapp.api.controller;

// import static org.junit.Assert.assertEquals;

// import java.net.URI;
// import java.net.URL;
// import java.nio.charset.Charset;

// import org.apache.commons.codec.binary.Base64;
// import org.json.JSONObject;
// import org.junit.Before;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.web.client.TestRestTemplate;
// import org.springframework.boot.web.server.LocalServerPort;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpMethod;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.test.context.junit4.SpringRunner;
// import upgrad.movieapp.api.model.UserDetailsResponse;

// @RunWith(SpringRunner.class)
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// public class UserAdminControllerIntegrationTest {

//     @LocalServerPort
//     private int port;

//     private URI base;
    
//     private String accessToken;
//     private final String testUserEmail = "srishti@upgrad.com";
//     private final String testUserPassword = "upgrad";

//     @Autowired
//     private TestRestTemplate template;

//     @Before
//     public void setUp() throws Exception {
//         this.base = new URI("http://localhost:" + port + "/api/v1/admin/users/7d174a25-ba31-45a8-85b4-b06ffc9d5f8f");
        
//         /* Hitting Login API to get the access token to hit /admin/users API */
//         HttpHeaders headers = new HttpHeaders();
//         headers.setContentType(MediaType.APPLICATION_JSON);
//         String auth = testUserEmail + ":" + testUserPassword;
//         byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")) );
//         String loginAuthToken = "Basic " + new String( encodedAuth );
//         headers.add("Authorization", loginAuthToken);
//         URI loginUrl = new URI("http://localhost:" + port + "/api/v1/auth/login");
//         HttpEntity<Object> entity = new HttpEntity<Object>(headers);
//         ResponseEntity<String> out = template.exchange(loginUrl, HttpMethod.POST, entity, String.class);
//         JSONObject jsonObject = new JSONObject(out.getHeaders());
//         String initialToken = jsonObject.getString("access-token");
//         this.accessToken = initialToken.toString().replace("]","").replace("[","");
//     }

//     @Test
//     public void getUser() throws Exception {
//     	HttpHeaders headers = new HttpHeaders();
//         headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
// 		String userAdminAuthToken = "Bearer " + new String( this.accessToken );
// 		userAdminAuthToken = userAdminAuthToken.toString().replace("\"",""); 
//         headers.add("Authorization", userAdminAuthToken);
//         HttpEntity<Object> entity = new HttpEntity<Object>(headers);
//         final ResponseEntity<UserDetailsResponse> response = template.exchange(
//         		base, HttpMethod.GET, entity, UserDetailsResponse.class);
//         assertEquals(HttpStatus.OK,response.getStatusCode());
//         final UserDetailsResponse responseBody = response.getBody();
//         assertEquals(responseBody.getId(), expected().getId());
//     }

//     private UserDetailsResponse expected() {
//         UserDetailsResponse response = new UserDetailsResponse();
//         response.setId("7d174a25-ba31-45a8-85b4-b06ffc9d5f8f");
//         return response;
//     }

// }

package se.klartext.app.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import se.klartext.app.KlartextApplication;

import javax.json.Json;
import javax.json.JsonObject;
import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by suchuan on 2017-05-20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KlartextApplication.class)
@WebAppConfiguration
public class UserControllerTest {

    private MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    );

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webAppContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
                .build();
    }

    @Test
    public void createUser() throws Exception {

        JsonObject payload = Json.createObjectBuilder()
                .add("name","Chuan")
                .add("email","test@gamil.com")
                .add("password","mysecretcredentials")
                .build();

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
                .contentType(contentType)
                .content(payload.toString()));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name",is(payload.getString("name"))));
    }

}


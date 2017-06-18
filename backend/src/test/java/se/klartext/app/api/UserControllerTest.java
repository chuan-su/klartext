package se.klartext.app.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import se.klartext.app.KlartextApplication;
import se.klartext.app.entity.Post;
import se.klartext.app.entity.User;

import java.nio.charset.Charset;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Created by suchuan on 2017-05-20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KlartextApplication.class)
@WebAppConfiguration
public class UserControllerTest {
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
                .build();
    }

    @Test
    public void createUser() throws Exception {

        User user = new User("tony ivanov","tony@inserve.se","tony",new ArrayList<Post>());

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
        .contentType(contentType)
        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

}


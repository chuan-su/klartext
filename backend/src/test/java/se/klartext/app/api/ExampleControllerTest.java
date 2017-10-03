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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import se.klartext.app.KlartextApplication;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = KlartextApplication.class)
@WebAppConfiguration
public class ExampleControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webAppContext;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
                .build();
    }

    @Test
    public void testPostSearch() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/examples/search")
                .param("query","hoppa,hoppade,hoppat,hoppar")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].body").exists())
                .andExpect(jsonPath("$[0].interp").exists());

    }
}

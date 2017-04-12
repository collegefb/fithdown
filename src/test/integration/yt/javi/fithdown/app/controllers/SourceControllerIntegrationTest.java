package yt.javi.fithdown.app.controllers;

import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import yt.javi.fithdown.app.Application;
import yt.javi.fithdown.app.requests.CreateSourceRequestBody;
import yt.javi.fithdown.core.model.source.SourceFactory;
import yt.javi.fithdown.core.model.source.SourceRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class SourceControllerIntegrationTest {

  private static final String SOURCE_ID = randomUUID().toString();

  private static final String SOURCE_NAME = "Test";

  private static final String SOURCE_URL = "http://www.test.ing";

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private SourceRepository sourceRepository;

  @Autowired
  private SourceFactory sourceFactory;

  @Before
  public void setup() throws Exception {
    mockMvc = webAppContextSetup(webApplicationContext).build();

    sourceRepository.save(sourceFactory.getSource(SOURCE_ID, SOURCE_NAME, SOURCE_URL));
  }

  @Test
  public void getAllSources() throws Exception {
    mockMvc
        .perform(get("/sources").contentType(APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].name", is(SOURCE_NAME)))
        .andExpect(jsonPath("$[0].url", is(SOURCE_URL)));
  }

  @Test
  public void getSourceById() throws Exception {
    mockMvc
        .perform(get("/sources/" + SOURCE_ID).contentType(APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(SOURCE_NAME)))
        .andExpect(jsonPath("$.url", is(SOURCE_URL)));
  }

  @Test
  public void getSourceByIdNotFound() throws Exception {
    mockMvc
        .perform(
            get("/sources/" + randomUUID().toString()).contentType(APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isNotFound())
        .andExpect(content().string(""));
  }

  @Test
  public void createASource() throws Exception {
    CreateSourceRequestBody requestBody = new CreateSourceRequestBody(SOURCE_NAME, SOURCE_URL);

    mockMvc
        .perform(
            post("/sources")
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .content(new ObjectMapper().writeValueAsString(requestBody)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", is(SOURCE_NAME)))
        .andExpect(jsonPath("$.url", is(SOURCE_URL)));
  }
}

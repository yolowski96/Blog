package softuni.blog.web;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import softuni.blog.web.apiModel.TravelTipCreateResponseModel;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TravelTipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private TravelTipCreateResponseModel createTip = new TravelTipCreateResponseModel("New tip","tips");

    Gson gson = new Gson();
    String json = gson.toJson(createTip);


    @Test
    @WithMockUser(username = "ivan567",password = "ivan")
    public void getAllTipsOk() throws Exception {
        mockMvc.perform(get("/tips")).andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "ivan567",password = "ivan")
    public void getFormToAddTip() throws Exception {
        mockMvc.perform(get("/tips/add")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "ivan567",password = "ivan")
    public void createTipPost() throws Exception {
        mockMvc.perform(post("/tips/add")
        .contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());
    }
}

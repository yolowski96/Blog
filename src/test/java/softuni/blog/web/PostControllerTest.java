package softuni.blog.web;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import softuni.blog.web.apiModel.PostCreateResponseModel;
import softuni.blog.web.apiModel.UserCreateRegisterModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private UserCreateRegisterModel userCreateRegisterModel = new UserCreateRegisterModel("ivan","ivan","ivan567","ivan","ivan","iva37n@abv.bg");
    private PostCreateResponseModel newPost = new PostCreateResponseModel("Hints and Tips for Middle East1"
            ,"Due to volatile politics"
            , LocalDateTime.parse("2020-04-02 11:45"
            , DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            ,"In predominantly Muslim countrie"
            ,"ivan567"
            ,"Summer"
            ,"google.bg");
    Gson gson = new Gson();
    String json = gson.toJson(newPost);
    String user = gson.toJson(userCreateRegisterModel);

    @Test
    @WithMockUser(username = "ivan567",password = "ivan")
    public void getAllPosts() throws Exception {
        mockMvc.perform(get("/posts").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "ivan567",password = "ivan")
    public void getFormToAddPost() throws Exception {
        mockMvc.perform(get("/posts/add").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}

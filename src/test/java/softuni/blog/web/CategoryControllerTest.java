package softuni.blog.web;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import softuni.blog.web.apiModel.CategoryResponseModel;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    CategoryResponseModel categoryResponseModel = new CategoryResponseModel("New Category","New Description");

    Gson gson = new Gson();
    String json = gson.toJson(categoryResponseModel);


    @Test
    @WithMockUser(username = "ivan",password = "ivan")
    public void findAllCategories() throws Exception {
        mockMvc.perform(get("/categories/all")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "ivan",password = "ivan")
    public void getAddFormToAddCategory() throws Exception {
        mockMvc.perform(get("/categories/add")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "ivan",password = "ivan")
    public void addCategory() throws Exception {
        mockMvc.perform(post("/categories/add").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "ivan",password = "ivan")
    public void addSameCategory() throws Exception {
        mockMvc.perform(post("/categories/add").contentType(MediaType.APPLICATION_JSON).content(json));
        mockMvc.perform(post("/categories/add").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isImUsed());
    }
}

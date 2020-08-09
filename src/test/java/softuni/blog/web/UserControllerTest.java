package softuni.blog.web;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import softuni.blog.web.apiModel.UserCreateRegisterModel;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private UserCreateRegisterModel userCreateRegisterModel = new UserCreateRegisterModel("ivan","ivan","ivan567","ivan","ivan","iva37n@abv.bg");

    Gson gson = new Gson();
    String json = gson.toJson(userCreateRegisterModel);

    @Test
    public void testLoginGetPageStatus() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterGetPageStatus() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterPostWithBadRequest() throws Exception {
        mockMvc.perform(post("/register"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postLoginUnauthorized() throws Exception {
        mockMvc.perform(post("/login").header("gosho","gosho"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void postRegisterConfirm() throws Exception {
        mockMvc.perform(
                post("/register")
                        .contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());
    }

    @Test
    public void postLoginConfirm() throws Exception {
        mockMvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username","ivan567")
                .param("password","ivan")
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "ivan567",password = "ivan")
    public void getAllUsers() throws Exception {
        mockMvc.perform(
                post("/register")
                        .contentType(MediaType.APPLICATION_JSON).content(json));
        mockMvc.perform(
                get("/all")
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "ivan567",password = "ivan")
    public void deleteUser() throws Exception {
        mockMvc.perform(
                post("/register")
                        .contentType(MediaType.APPLICATION_JSON).content(json));
        mockMvc.perform(delete("/delete/ivan567")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "ivan567",password = "ivan")
    public void deleteNonExistUser() throws Exception {
        mockMvc.perform(
                delete("/delete/ivan")
        ).andExpect(status().isNotFound());
    }
}

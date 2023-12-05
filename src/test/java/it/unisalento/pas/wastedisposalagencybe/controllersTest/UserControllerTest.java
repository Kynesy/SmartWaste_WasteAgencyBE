package it.unisalento.pas.wastedisposalagencybe.controllersTest;

import com.nimbusds.jose.shaded.gson.Gson;
import it.unisalento.pas.wastedisposalagencybe.domains.User;
import it.unisalento.pas.wastedisposalagencybe.dto.UserDTO;
import it.unisalento.pas.wastedisposalagencybe.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void existUserTest() throws Exception {
        String userID = "mockUserID";

        when(userService.existUser(userID)).thenReturn(1);

        mockMvc.perform(get("/api/user/exist/{userID}", userID)
                        .with(user("operator").authorities(new SimpleGrantedAuthority("ROLE_OPERATOR"))))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void createUserTest() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John");
        userDTO.setSurname("Doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setBdate("1990-01-01");

        Gson gson = new Gson();
        String json = gson.toJson(userDTO);

        when(userService.createUser(any())).thenReturn(0);

        mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(user("operator").authorities(new SimpleGrantedAuthority("ROLE_OPERATOR"))))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"message\": \"User created successfully\"}"));
    }

    @Test
    void updateUserTest() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("UpdatedName");
        userDTO.setSurname("UpdatedSurname");
        userDTO.setEmail("updated.email@example.com");
        userDTO.setBdate("1995-01-01");

        Gson gson = new Gson();
        String json = gson.toJson(userDTO);

        when(userService.updateUser(any())).thenReturn(0);

        mockMvc.perform(post("/api/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(user("operator").authorities(new SimpleGrantedAuthority("ROLE_OPERATOR"))))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"message\": \"User updated successfully\"}"));
    }

    @Test
    void deleteUserTest() throws Exception {
        String userID = "mockUserID";

        when(userService.deleteUser(userID)).thenReturn(0);

        mockMvc.perform(delete("/api/user/delete/{userID}", userID)
                        .with(user("operator").authorities(new SimpleGrantedAuthority("ROLE_OPERATOR"))))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"message\": \"User deleted successfully\"}"));
    }

    @Test
    void getUserTest() throws Exception {
        String userID = "mockUserID";
        User user = new User();
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john.doe@example.com");
        user.setBdate("1990-01-01");

        when(userService.findByID(userID)).thenReturn(user);

        mockMvc.perform(get("/api/user/get/{userID}", userID)
                        .with(user("operator").authorities(new SimpleGrantedAuthority("ROLE_OPERATOR"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.bdate").value("1990-01-01"));
    }
}

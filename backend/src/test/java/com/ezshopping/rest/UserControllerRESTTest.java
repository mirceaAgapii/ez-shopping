package com.ezshopping.rest;

import com.ezshopping.fixture.UserDTOFixture;
import com.ezshopping.user.model.dto.PasswordChangeDTO;
import com.ezshopping.user.model.dto.UserDTO;
import com.ezshopping.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerRESTTest {

    @MockBean
    private UserServiceImpl userService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private UserDTO testUserDTO;
    private PasswordChangeDTO passwordChangeDTO;

    @BeforeEach
    public void setup()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        testUserDTO = UserDTOFixture.userDTO();

        passwordChangeDTO = new PasswordChangeDTO("oldPassword", "newPassword");
    }

    @Test
    @WithMockUser(username = "test",roles = "ADMINISTRATOR")
    void getAllEntities_whenInvoked_return200() throws Exception {
        mockMvc.perform(get("/api/users")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMINISTRATOR")
    void getUserById_whenInvoked_return200() throws Exception {
        when(userService.getUserDTOById(testUserDTO.getId())).thenReturn(testUserDTO);
        mockMvc.perform(get("/api/users/user")
                        .param("id", "testId1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMINISTRATOR")
    void deleteUserById_whenInvoked_return200() throws Exception {
        when(userService.deleteUserById(testUserDTO.getId())).thenReturn(testUserDTO);
        mockMvc.perform(delete("/api/users/user")
                        .param("id", "testId1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMINISTRATOR")
    void registerUser_whenInvoked_return201() throws Exception {
        doNothing().when(userService).registerUser(testUserDTO);
        String body = "{\n" +
                "    \"id\":\"testId1\",\n" +
                "    \"username\":\"TestUser1\",\n" +
                "    \"password\":\"test\",\n" +
                "    \"email\":\"test1@mail.com\",\n" +
                "    \"role\":\"CLIENT\"\n" +
                "}";
        mockMvc.perform(post("/api/users/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMINISTRATOR")
    void updateUser_whenInvoked_return204() throws Exception {
        doNothing().when(userService).updateUser(testUserDTO);
        String body = "{\n" +
                "    \"id\":\"testId1\",\n" +
                "    \"username\":\"TestUser1\",\n" +
                "    \"password\":\"test\",\n" +
                "    \"email\":\"test1@mail.com\",\n" +
                "    \"role\":\"CLIENT\"\n" +
                "}";
        mockMvc.perform(patch("/api/users/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "test", roles = "ADMINISTRATOR")
    void changePassword_whenInvoked_returns204() throws Exception {
        doNothing().when(userService).changePassword(testUserDTO.getId(), passwordChangeDTO);
        String body = "{ \n" +
                "    \"oldPassword\":\"oldPassword\",\n" +
                "    \"newPassword\":\"newPassword\"\n" +
                "}";
        mockMvc.perform(patch("/api/users/user/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .param("id", "testId"))
                .andExpect(status().isNoContent());
    }
}
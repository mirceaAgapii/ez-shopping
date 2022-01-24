package com.ezshopping.service;

import com.ezshopping.common.Mapper;
import com.ezshopping.fixture.UserDTOFixture;
import com.ezshopping.fixture.UserFixture;
import com.ezshopping.user.exceptions.UserAlreadyInDatabaseException;
import com.ezshopping.user.exceptions.WrongPasswordProvidedException;
import com.ezshopping.user.model.dto.PasswordChangeDTO;
import com.ezshopping.user.model.entity.User;
import com.ezshopping.user.model.dto.UserDTO;
import com.ezshopping.user.repository.UserRepository;

import com.ezshopping.user.service.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private Mapper<User, UserDTO> mapper;
    @InjectMocks
    private UserServiceImpl userService;


    private User testUser1;
    private UserDTO testUserDTO;
    private List<User> testUsers;
    private PasswordChangeDTO testPasswordChangeDTO = new PasswordChangeDTO("oldPassword", "newPassword");

    @BeforeEach
    void setup() {
        testUser1 = UserFixture.user();
        testUsers = UserFixture.userList();
        testUserDTO = UserDTOFixture.userDTO();
    }

    @Test
    void getAll_whenInvoked_returnsAListWithTwoUsers() {
        when(userRepository.findAll()).thenReturn(testUsers);

        assertThat(userService.getAll())
                .isNotEmpty()
                .containsExactlyInAnyOrderElementsOf(testUsers);
    }

    @Test
    void getUserByUsername_whenInvoked_returnsUser() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.ofNullable(testUser1));

        User user = userService.getUserByUsername("TestUser1");
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(testUser1.getUsername());
    }


    @Test
    void getUserByEmail_whenInvoked_returnsUser() {
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.ofNullable(testUser1));
        when(mapper.map(testUser1)).thenReturn(testUserDTO);

        UserDTO user = userService.getUserByEmail("test1@mail.com");

        assertThat(user.getEmail()).isEqualTo(testUser1.getEmail());
    }

    @Test
    void registerUser_withExistingUsername_willThrowUserAlreadyInDatabaseException() {
        when(userRepository.existsByUsername(any())).thenReturn(true);

        assertThrows(UserAlreadyInDatabaseException.class, () -> userService.registerUser(testUserDTO));
    }

    @Test
    void registerUser_withExistingEmail_willThrowUserAlreadyInDatabaseException() {
        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(UserAlreadyInDatabaseException.class, () -> userService.registerUser(testUserDTO));
    }

    @Test
    void changePassword_whenInvokedWithAWrongPassword_willThrowWrongPasswordProvidedException() {
        when(userRepository.findById("testId1")).thenReturn(Optional.ofNullable(testUser1));

        assertThrows(WrongPasswordProvidedException.class, () ->userService.changePassword("testId1", testPasswordChangeDTO));
    }
}
package com.example.demo.controller

import com.example.demo.model.User
import com.example.demo.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.NoSuchElementException

@WebMvcTest(UserController::class)
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userService: UserService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should return all users`() {
        // Arrange
        val users = listOf(
            User(1, "John Doe", "john@example.com"),
            User(2, "Jane Smith", "jane@example.com")
        )
        `when`(userService.getAllUsers()).thenReturn(users)

        // Act & Assert
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("John Doe"))
            .andExpect(jsonPath("$[0].email").value("john@example.com"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].name").value("Jane Smith"))
            .andExpect(jsonPath("$[1].email").value("jane@example.com"))
    }

    @Test
    fun `should return user by id when user exists`() {
        // Arrange
        val user = User(1, "John Doe", "john@example.com")
        `when`(userService.getUserById(1)).thenReturn(user)

        // Act & Assert
        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("John Doe"))
            .andExpect(jsonPath("$.email").value("john@example.com"))
    }

    @Test
    fun `should return 404 when user does not exist`() {
        // Arrange
        `when`(userService.getUserById(999)).thenThrow(NoSuchElementException("User not found"))

        // Act & Assert
        mockMvc.perform(get("/api/users/999"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should create new user`() {
        // Arrange
        val newUser = User(0, "New User", "new@example.com")
        val createdUser = User(1, "New User", "new@example.com")
        
        `when`(userService.createUser(any(User::class.java))).thenReturn(createdUser)

        // Act & Assert
        mockMvc.perform(
            post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("New User"))
            .andExpect(jsonPath("$.email").value("new@example.com"))
    }

    @Test
    fun `should update existing user`() {
        // Arrange
        val userToUpdate = User(1, "Updated User", "updated@example.com")
        val updatedUser = User(1, "Updated User", "updated@example.com")
        
        `when`(userService.updateUser(eq(1L), any(User::class.java))).thenReturn(updatedUser)

        // Act & Assert
        mockMvc.perform(
            put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userToUpdate))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Updated User"))
            .andExpect(jsonPath("$.email").value("updated@example.com"))
    }

    @Test
    fun `should return 404 when updating non-existent user`() {
        // Arrange
        val userToUpdate = User(999, "Updated User", "updated@example.com")
        
        `when`(userService.updateUser(eq(999L), any(User::class.java)))
            .thenThrow(NoSuchElementException("User not found"))

        // Act & Assert
        mockMvc.perform(
            put("/api/users/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userToUpdate))
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should delete existing user`() {
        // Arrange
        doNothing().`when`(userService).deleteUser(1)

        // Act & Assert
        mockMvc.perform(delete("/api/users/1"))
            .andExpect(status().isNoContent)
    }

    @Test
    fun `should return 404 when deleting non-existent user`() {
        // Arrange
        doThrow(NoSuchElementException("User not found"))
            .`when`(userService).deleteUser(999)

        // Act & Assert
        mockMvc.perform(delete("/api/users/999"))
            .andExpect(status().isNotFound)
    }
}

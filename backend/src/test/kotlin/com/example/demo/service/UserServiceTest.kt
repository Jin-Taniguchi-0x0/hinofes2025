package com.example.demo.service

import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var userService: UserService

    private val testUser1 = User(1, "John Doe", "john@example.com")
    private val testUser2 = User(2, "Jane Smith", "jane@example.com")

    @Test
    fun `getAllUsers should return all users from repository`() {
        // Arrange
        val userList = listOf(testUser1, testUser2)
        `when`(userRepository.findAll()).thenReturn(userList)

        // Act
        val result = userService.getAllUsers()

        // Assert
        assertEquals(2, result.size)
        assertEquals(testUser1, result[0])
        assertEquals(testUser2, result[1])
        verify(userRepository, times(1)).findAll()
    }

    @Test
    fun `getUserById should return user when user exists`() {
        // Arrange
        `when`(userRepository.findById(1L)).thenReturn(Optional.of(testUser1))

        // Act
        val result = userService.getUserById(1L)

        // Assert
        assertEquals(testUser1, result)
        verify(userRepository, times(1)).findById(1L)
    }

    @Test
    fun `getUserById should throw exception when user does not exist`() {
        // Arrange
        `when`(userRepository.findById(999L)).thenReturn(Optional.empty())

        // Act & Assert
        val exception = assertThrows(NoSuchElementException::class.java) {
            userService.getUserById(999L)
        }
        
        assertEquals("User not found with id: 999", exception.message)
        verify(userRepository, times(1)).findById(999L)
    }

    @Test
    fun `createUser should save and return user`() {
        // Arrange
        val newUser = User(0, "New User", "new@example.com")
        val savedUser = User(3, "New User", "new@example.com")
        
        `when`(userRepository.save(newUser)).thenReturn(savedUser)

        // Act
        val result = userService.createUser(newUser)

        // Assert
        assertEquals(savedUser, result)
        verify(userRepository, times(1)).save(newUser)
    }

    @Test
    fun `updateUser should update and return user when user exists`() {
        // Arrange
        val updatedUserData = User(1, "Updated Name", "updated@example.com")
        val existingUser = User(1, "John Doe", "john@example.com")
        val expectedUpdatedUser = User(1, "Updated Name", "updated@example.com")
        
        `when`(userRepository.findById(1L)).thenReturn(Optional.of(existingUser))
        `when`(userRepository.save(any(User::class.java))).thenReturn(expectedUpdatedUser)

        // Act
        val result = userService.updateUser(1L, updatedUserData)

        // Assert
        assertEquals(expectedUpdatedUser, result)
        verify(userRepository, times(1)).findById(1L)
        verify(userRepository, times(1)).save(any(User::class.java))
    }

    @Test
    fun `updateUser should throw exception when user does not exist`() {
        // Arrange
        val updatedUserData = User(999, "Updated Name", "updated@example.com")
        
        `when`(userRepository.findById(999L)).thenReturn(Optional.empty())

        // Act & Assert
        val exception = assertThrows(NoSuchElementException::class.java) {
            userService.updateUser(999L, updatedUserData)
        }
        
        assertEquals("User not found with id: 999", exception.message)
        verify(userRepository, times(1)).findById(999L)
        verify(userRepository, never()).save(any(User::class.java))
    }

    @Test
    fun `deleteUser should delete user when user exists`() {
        // Arrange
        `when`(userRepository.existsById(1L)).thenReturn(true)
        doNothing().`when`(userRepository).deleteById(1L)

        // Act
        userService.deleteUser(1L)

        // Assert
        verify(userRepository, times(1)).existsById(1L)
        verify(userRepository, times(1)).deleteById(1L)
    }

    @Test
    fun `deleteUser should throw exception when user does not exist`() {
        // Arrange
        `when`(userRepository.existsById(999L)).thenReturn(false)

        // Act & Assert
        val exception = assertThrows(NoSuchElementException::class.java) {
            userService.deleteUser(999L)
        }
        
        assertEquals("User not found with id: 999", exception.message)
        verify(userRepository, times(1)).existsById(999L)
        verify(userRepository, never()).deleteById(anyLong())
    }
}

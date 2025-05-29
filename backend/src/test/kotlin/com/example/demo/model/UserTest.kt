package com.example.demo.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class UserTest {

    @Test
    fun `User data class should have correct properties`() {
        // Arrange
        val id = 1L
        val name = "Test User"
        val email = "test@example.com"
        
        // Act
        val user = User(id, name, email)
        
        // Assert
        assertEquals(id, user.id)
        assertEquals(name, user.name)
        assertEquals(email, user.email)
    }
    
    @Test
    fun `User data class should implement equals correctly`() {
        // Arrange
        val user1 = User(1L, "Test User", "test@example.com")
        val user2 = User(1L, "Test User", "test@example.com")
        val user3 = User(2L, "Test User", "test@example.com")
        
        // Assert
        assertEquals(user1, user2)
        assertNotEquals(user1, user3)
    }
    
    @Test
    fun `User data class should implement copy correctly`() {
        // Arrange
        val originalUser = User(1L, "Original Name", "original@example.com")
        
        // Act
        val copiedUser = originalUser.copy(name = "New Name")
        
        // Assert
        assertEquals(originalUser.id, copiedUser.id)
        assertEquals("New Name", copiedUser.name)
        assertEquals(originalUser.email, copiedUser.email)
    }
}

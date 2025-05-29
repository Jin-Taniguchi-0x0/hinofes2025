package com.example.demo.service

import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun getUserById(id: Long): User {
        return userRepository.findById(id).orElseThrow { 
            NoSuchElementException("User not found with id: $id") 
        }
    }

    @Transactional
    fun createUser(user: User): User {
        return userRepository.save(user)
    }

    @Transactional
    fun updateUser(id: Long, updatedUser: User): User {
        val existingUser = getUserById(id)
        
        val userToUpdate = existingUser.copy(
            name = updatedUser.name,
            email = updatedUser.email
        )
        
        return userRepository.save(userToUpdate)
    }

    @Transactional
    fun deleteUser(id: Long) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
        } else {
            throw NoSuchElementException("User not found with id: $id")
        }
    }
}

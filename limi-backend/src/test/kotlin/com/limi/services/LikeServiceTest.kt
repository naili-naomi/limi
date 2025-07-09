
package com.limi.services

import com.limi.repositories.LikeRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class LikeServiceTest {

    private val likeRepository: LikeRepository = mockk()
    private val likeService = LikeService(likeRepository)

    @Test
    fun `addLike should add like`() {
        // Given
        every { likeRepository.addLike(1, 1) } returns true

        // When
        val result = likeService.addLike(1, 1)

        // Then
        assertTrue(result)
    }

    @Test
    fun `removeLike should remove like`() {
        // Given
        every { likeRepository.removeLike(1, 1) } returns true

        // When
        val result = likeService.removeLike(1, 1)

        // Then
        assertTrue(result)
    }

    @Test
    fun `isLiked should return true when like exists`() {
        // Given
        every { likeRepository.isLiked(1, 1) } returns true

        // When
        val result = likeService.isLiked(1, 1)

        // Then
        assertTrue(result)
    }
}

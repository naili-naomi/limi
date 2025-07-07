package com.limi.services

import com.limi.models.Livro
import com.limi.repositories.FavoriteRepository

class FavoriteService(private val favoriteRepository: FavoriteRepository) {
    fun addFavorite(userId: Int, livroId: Int): Boolean {
        return favoriteRepository.addFavorite(userId, livroId)
    }

    fun removeFavorite(userId: Int, livroId: Int): Boolean {
        return favoriteRepository.removeFavorite(userId, livroId)
    }

    fun getFavorites(userId: Int): List<Livro> {
        return favoriteRepository.getFavorites(userId)
    }

    fun isFavorite(userId: Int, livroId: Int): Boolean {
        return favoriteRepository.isFavorite(userId, livroId)
    }
}

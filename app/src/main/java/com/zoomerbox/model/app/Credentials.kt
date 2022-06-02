package com.zoomerbox.model.app

/**
 *
 * Модель с данными для аутентификации
 *
 * @param userId id пользователя
 * @param token токен пользователя
 */
data class Credentials(private val userId: String, private val token: String)

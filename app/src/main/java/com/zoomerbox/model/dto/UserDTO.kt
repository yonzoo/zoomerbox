package com.zoomerbox.model.dto

/**
 *
 * DTO с данными пользователя
 *
 * @param uid id пользователя
 * @param username юзернейм пользователя
 * @param phone номер телефон пользователя
 * @param avatarUrl url аватара
 */
data class UserDTO(
    val uid: String,
    val username: String,
    val phone: String,
    val avatarUrl: String
)

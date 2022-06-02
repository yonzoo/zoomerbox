package com.zoomerbox.model.app

import android.os.Parcelable
import com.zoomerbox.model.dto.UserDTO
import kotlinx.android.parcel.Parcelize

/**
 *
 * Модель с данными пользователя
 *
 * @param uid id пользователя
 * @param username юзернейм пользователя
 * @param phone номер телефон пользователя
 * @param avatarUrl url аватара
 */
@Parcelize
class User(
    val uid: String,
    val username: String,
    val phone: String,
    val avatarUrl: String
) : Parcelable {

    companion object {

        fun buildFromDTO(userDTO: UserDTO): User {
            return User(
                userDTO.uid,
                userDTO.username,
                userDTO.phone,
                userDTO.avatarUrl
            )
        }
    }
}
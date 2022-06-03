package com.zoomerbox.model.app

import android.os.Parcelable
import com.zoomerbox.model.dto.TokenDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
class Token(
    val token: String
) : Parcelable {

    companion object {

        fun buildFromDTO(userToken: TokenDTO): Token {
            return Token(
                userToken.token
            )
        }
    }
}
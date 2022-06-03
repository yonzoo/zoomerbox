package com.zoomerbox.data.repository

import com.zoomerbox.model.app.Token
import com.zoomerbox.model.app.User
import io.reactivex.Single

interface IUserRepository {

    fun getUser(): Single<User>

    fun getToken(token: String, phoneNumber: String): Single<Token>

    fun getUserFromPreferences(uid: String?, phoneNumber: String?): Single<User>

    fun getImplName(): String
}

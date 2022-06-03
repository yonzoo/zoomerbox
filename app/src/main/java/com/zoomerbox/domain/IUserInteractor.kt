package com.zoomerbox.domain

import com.zoomerbox.model.app.Token
import com.zoomerbox.model.app.User
import io.reactivex.Single

interface IUserInteractor {

    fun getUser(): Single<User>

    fun getToken(token: String, phone: String): Single<Token>
}
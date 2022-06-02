package com.zoomerbox.domain

import com.zoomerbox.model.app.User
import io.reactivex.Single

interface IUserInteractor {

    fun getUserCredentials(token: String, phone: String): Single<User>
}
package com.zoomerbox.data.repository

import com.zoomerbox.data.exception.RequestFailedException
import com.zoomerbox.model.app.User
import io.reactivex.Single

interface IUserRepository {

    @Throws(RequestFailedException::class)
    fun getUser(uid: String, phoneNumber: String): Single<User>

    fun getImplName(): String
}

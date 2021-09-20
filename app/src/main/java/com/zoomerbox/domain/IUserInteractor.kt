package com.zoomerbox.domain

import com.zoomerbox.model.app.User
import io.reactivex.Single

interface IUserInteractor {

    fun getUserCredentials(uid: String, phone: String): Single<User>
}
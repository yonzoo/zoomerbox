package com.zoomerbox.data.repository

import com.zoomerbox.data.exception.RequestFailedException
import com.zoomerbox.model.item.User

interface IUserRepository {

    @Throws(RequestFailedException::class)
    fun getUser(): User

    fun getImplName(): String
}
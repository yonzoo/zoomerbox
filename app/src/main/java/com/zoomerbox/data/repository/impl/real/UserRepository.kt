package com.zoomerbox.data.repository.impl.real

import com.zoomerbox.data.repository.IUserRepository
import com.zoomerbox.data.service.UserApiService
import com.zoomerbox.model.app.User
import io.reactivex.Single
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApiService: UserApiService) :
    IUserRepository {

    override fun getUser(uid: String, phoneNumber: String): Single<User> {
        return userApiService.getUser(uid, phoneNumber)
            .map { userDTO -> User.buildFromDTO(userDTO) }
    }

    override fun getImplName(): String {
        return this::class.java.simpleName
    }
}
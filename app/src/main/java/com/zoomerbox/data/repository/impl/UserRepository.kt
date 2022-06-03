package com.zoomerbox.data.repository.impl

import android.content.SharedPreferences
import com.zoomerbox.data.repository.IUserRepository
import com.zoomerbox.data.provider.remote.service.UserApiService
import com.zoomerbox.model.app.Token
import com.zoomerbox.model.app.User
import com.zoomerbox.model.util.PrefsKeys
import io.reactivex.Single
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApiService: UserApiService,
    private val prefs: SharedPreferences
) : IUserRepository {

    override fun getUser(): Single<User> {
        return userApiService.getUser()
            .map { userDTO -> User.buildFromDTO(userDTO) }
    }

    override fun getToken(token: String, phoneNumber: String): Single<Token> {
        return userApiService.getToken(token, phoneNumber)
            .map { tokenDTO -> Token.buildFromDTO(tokenDTO) }
    }

    override fun getUserFromPreferences(uid: String?, phoneNumber: String?): Single<User> {
        return Single.fromCallable {
            User(
                prefs.getString(PrefsKeys.UID, "")!!,
                prefs.getString(PrefsKeys.USERNAME, "")!!,
                prefs.getString(PrefsKeys.PHONE, "")!!,
                prefs.getString(PrefsKeys.AVATAR_URL, "")!!
            )
        }
    }

    override fun getImplName(): String {
        return this::class.java.simpleName
    }
}
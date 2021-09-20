package com.zoomerbox.data.repository.impl

import android.content.SharedPreferences
import com.zoomerbox.data.repository.IUserRepository
import com.zoomerbox.data.service.UserApiService
import com.zoomerbox.model.app.User
import com.zoomerbox.model.util.PrefsKeys
import io.reactivex.Single
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApiService: UserApiService,
    private val prefs: SharedPreferences
) : IUserRepository {

    override fun getUser(uid: String, phoneNumber: String): Single<User> {
        return userApiService.getUser(uid, phoneNumber)
            .map { userDTO -> User.buildFromDTO(userDTO) }
    }

    override fun getUserFromPreferences(uid: String, phoneNumber: String): Single<User> {
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
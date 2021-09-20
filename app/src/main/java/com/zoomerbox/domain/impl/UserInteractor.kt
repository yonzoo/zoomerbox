package com.zoomerbox.domain.impl

import android.content.SharedPreferences
import androidx.annotation.NonNull
import com.zoomerbox.data.repository.IUserRepository
import com.zoomerbox.domain.IUserInteractor
import com.zoomerbox.model.app.User
import com.zoomerbox.model.util.PrefsKeys
import io.reactivex.Single
import javax.inject.Inject

class UserInteractor @Inject constructor(
    @NonNull private val repository: IUserRepository,
    @NonNull private val prefs: SharedPreferences
) : IUserInteractor {

    override fun getUserCredentials(uid: String, phone: String): Single<User> {
        return repository.getUser(uid, phone).map { user ->
            prefs
                .edit()
                .putString(PrefsKeys.UID, user.uid)
                .putString(PrefsKeys.USERNAME, user.username)
                .putString(PrefsKeys.PHONE, user.phone)
                .putString(PrefsKeys.AVATAR_URL, user.avatarUrl)
                .apply()
            user
        }
    }
}
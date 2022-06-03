package com.zoomerbox.domain.impl

import android.content.SharedPreferences
import androidx.annotation.NonNull
import com.zoomerbox.data.repository.IUserRepository
import com.zoomerbox.domain.IUserInteractor
import com.zoomerbox.model.app.Token
import com.zoomerbox.model.app.User
import com.zoomerbox.model.util.PrefsKeys
import io.reactivex.Single
import javax.inject.Inject

class UserInteractor @Inject constructor(
    @NonNull private val repository: IUserRepository,
    @NonNull private val prefs: SharedPreferences
) : IUserInteractor {

    override fun getUser(): Single<User> {
        return repository.getUser().map { user ->
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

    override fun getToken(token: String, phone: String): Single<Token> {
        return repository.getToken(token, phone).map { tokenModel ->
            prefs
                .edit()
                .putString(PrefsKeys.TOKEN, tokenModel.token)
                .apply()
            tokenModel
        }
    }
}
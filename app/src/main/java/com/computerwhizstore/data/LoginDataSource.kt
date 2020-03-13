package com.computerwhizstore.data

import com.computerwhizstore.data.model.LoggedInUser
import com.computerwhizstore.models.UserModel
import com.computerwhizstore.utils.StaticUtils
import java.io.IOException

class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            var userModel = UserModel()
            userModel.userId = username
            userModel.password = password
            if (StaticUtils.getStoreUsersList().contains(userModel)) {
                userModel = StaticUtils.getUserModel(username)!!
                val fakeUser = LoggedInUser(userModel.userId.toString(), userModel.userName!!)
                return Result.Success(fakeUser)
            } else
                return Result.Error(Exception("User Doesn't exist in our System"))
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {

    }
}


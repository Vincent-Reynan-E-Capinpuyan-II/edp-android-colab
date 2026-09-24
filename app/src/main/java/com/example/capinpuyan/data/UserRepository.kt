package com.example.capinpuyan.data

import com.example.capinpuyan.core.AppResult
import com.example.capinpuyan.data.network.NetworkModule
import com.example.capinpuyan.data.network.UserApiService
import com.example.capinpuyan.data.network.dto.NewUserDto
import com.example.capinpuyan.data.network.dto.UserDto
import com.example.capinpuyan.data.network.dto.toDomain
import com.example.capinpuyan.domain.model.User
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class UserRepository(
    private val api: UserApiService = NetworkModule.userApi
) {

    private suspend fun findUsers(email: String): List<UserDto> =
        try {
            api.findByEmail(email)
        } catch (e: HttpException) {
            if (e.code() == 404) emptyList() else throw e
        }

    private inline fun <T> safeCall(block: () -> AppResult<T>): AppResult<T> =
        try {
            block()
        } catch (e: UnknownHostException) {
            AppResult.Failure.NoInternet
        } catch (e: SocketTimeoutException) {
            AppResult.Failure.Timeout
        } catch (e: HttpException) {
            AppResult.Failure.Unknown("Server error ${e.code()}")
        } catch (e: SerializationException) {
            AppResult.Failure.Unknown("The server sent data we could not read.")
        } catch (e: IOException) {
            AppResult.Failure.NoInternet
        }

    suspend fun login(email: String, password: String): AppResult<User> = safeCall {
        val trimmedEmail = email.trim()
        val matches = findUsers(trimmedEmail)
        val found = matches.firstOrNull {
            it.email.equals(trimmedEmail, ignoreCase = true) && it.password == password
        }

        if (found == null) {
            AppResult.Failure.WrongLogin
        } else {
            AppResult.Success(found.toDomain())
        }
    }

    suspend fun register(
        fullName: String,
        email: String,
        password: String,
        birthdate: String
    ): AppResult<User> = safeCall {
        val trimmedEmail = email.trim()
        val taken = findUsers(trimmedEmail).any {
            it.email.equals(trimmedEmail, ignoreCase = true)
        }

        if (taken) {
            AppResult.Failure.EmailTaken
        } else {
            val saved = api.createUser(
                NewUserDto(
                    fullname = fullName.trim(),
                    email = trimmedEmail,
                    password = password,
                    birthdate = birthdate.trim()
                )
            )
            AppResult.Success(saved.toDomain())
        }
    }
}

package com.example.capinpuyan.data.network.dto

import com.example.capinpuyan.domain.model.User

fun UserDto.toDomain(): User = User(
    id = id ?: "",
    fullName = fullname?.trim() ?: "(no name)",
    email = email?.trim() ?: "",
    birthdate = birthdate ?: "(not set)"
)

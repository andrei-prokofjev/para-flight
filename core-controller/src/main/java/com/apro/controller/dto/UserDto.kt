package com.apro.controller.dto

import com.google.gson.annotations.SerializedName

class UserDto(
  @SerializedName("id") val id: Long,
  @SerializedName("name") val name: String,
  @SerializedName("email") val email: String,
  @SerializedName("created_at") val createdAt: String,
  @SerializedName("updated_at") val updatedAt: String
)
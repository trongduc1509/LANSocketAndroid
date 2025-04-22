package com.example.lansocketandroid.utils.byte

import com.google.gson.Gson
import java.nio.charset.StandardCharsets

object GsonByteUtils {
    private val gson = Gson()

    fun <T> parse(bytes: ByteArray, clazz: Class<T>): T {
        val json = String(bytes, StandardCharsets.UTF_8)
        return gson.fromJson(json, clazz)
    }

    fun toByteArray(model: Any): ByteArray {
        val json = gson.toJson(model)
        return json.toByteArray(StandardCharsets.UTF_8)
    }
}
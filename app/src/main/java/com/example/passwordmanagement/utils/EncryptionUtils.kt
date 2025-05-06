package com.example.passwordmanagement.utils

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionUtils {
    private const val SECRET_KEY = "1234567890123456" // 16 chars for AES-128
    private const val INIT_VECTOR = "abcdefghijklmnop" // 16 chars

    fun encrypt(strToEncrypt: String): String {
        val iv = IvParameterSpec(INIT_VECTOR.toByteArray(Charsets.UTF_8))
        val skeySpec: SecretKey = SecretKeySpec(SECRET_KEY.toByteArray(Charsets.UTF_8), "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
        val encrypted = cipher.doFinal(strToEncrypt.toByteArray())
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }

    fun decrypt(strToDecrypt: String): String {
        val iv = IvParameterSpec(INIT_VECTOR.toByteArray(Charsets.UTF_8))
        val skeySpec: SecretKey = SecretKeySpec(SECRET_KEY.toByteArray(Charsets.UTF_8), "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
        val original = cipher.doFinal(Base64.decode(strToDecrypt, Base64.DEFAULT))
        return String(original)
    }
}
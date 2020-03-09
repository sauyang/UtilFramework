package com.example.myapplication.framework

import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import java.nio.charset.StandardCharsets
import java.security.spec.KeySpec
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class UtilAES256 {
    companion object{
        @RequiresApi(Build.VERSION_CODES.KITKAT)
        fun encrypt(
            strToEncrypt: String,
            secret: String,
            salt: String
        ): String? {
            try {
                val iv =
                    byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                val ivspec =
                    IvParameterSpec(iv)
                val factory =
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                val spec: KeySpec = PBEKeySpec(
                    secret.toCharArray(),
                    salt.toByteArray(),
                    65536,
                    256
                )
                val tmp = factory.generateSecret(spec)
                val secretKey =
                    SecretKeySpec(tmp.encoded, "AES")
                val cipher =
                    Cipher.getInstance("AES/CBC/PKCS5Padding")
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec)
                return Base64.encodeToString(
                    cipher.doFinal(
                        strToEncrypt.toByteArray(
                            StandardCharsets.UTF_8
                        )
                    ), Base64.NO_WRAP
                )
            } catch (e: Exception) {
                println("Error while encrypting: $e")
            }
            return null
        }

        fun decrypt(
            strToDecrypt: String?,
            secret: String,
            salt: String
        ): String? {
            try {
                val iv =
                    byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                val ivspec =
                    IvParameterSpec(iv)
                val factory =
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                val spec: KeySpec = PBEKeySpec(
                    secret.toCharArray(),
                    salt.toByteArray(),
                    65536,
                    256
                )
                val tmp = factory.generateSecret(spec)
                val secretKey =
                    SecretKeySpec(tmp.encoded, "AES")
                val cipher =
                    Cipher.getInstance("AES/CBC/PKCS5PADDING")
                cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec)
                return String(
                    cipher.doFinal(
                        Base64.decode(
                            strToDecrypt,
                            Base64.NO_WRAP
                        )
                    )
                )
            } catch (e: Exception) {
                println("Error while decrypting: $e")
            }
            return null
        }
    }
}
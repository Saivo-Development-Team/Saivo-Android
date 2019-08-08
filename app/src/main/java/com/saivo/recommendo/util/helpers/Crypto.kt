package com.saivo.recommendo.util.helpers

import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher


object Crypto {
    private const val algol = "RSA"

    @Throws(Exception::class)
    fun encrypt(publicKey: ByteArray, data: ByteArray): ByteArray {
        val key = KeyFactory.getInstance(algol).generatePublic(X509EncodedKeySpec(publicKey))
        val cipher = Cipher.getInstance(algol)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        return cipher.doFinal(data)
    }

    @Throws(Exception::class)
    fun decrypt(privateKey: ByteArray, data: ByteArray): ByteArray {
        val key = KeyFactory.getInstance(algol).generatePrivate(PKCS8EncodedKeySpec(privateKey))
        val cipher = Cipher.getInstance(algol)
        cipher.init(Cipher.DECRYPT_MODE, key)
        return cipher.doFinal(data)
    }
}
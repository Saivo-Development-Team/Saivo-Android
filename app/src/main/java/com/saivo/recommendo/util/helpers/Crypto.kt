package com.saivo.recommendo.util.helpers

import java.security.*
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException


object Crypto {
    private const val algol = "RSA"
    private lateinit var generator: KeyPairGenerator
    private lateinit var keyPair: KeyPair
    private lateinit var publicKey: PublicKey
    private lateinit var privateKey: PrivateKey
    private lateinit var cipher: Cipher
    private lateinit var cipher1: Cipher

    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    fun encrypt(plain: String): ByteArray {
        generator = KeyPairGenerator.getInstance(algol)
        generator.initialize(2048)
        keyPair = generator.genKeyPair()
        publicKey = keyPair.public
        privateKey = keyPair.private

        cipher = Cipher.getInstance(algol)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        return cipher.doFinal(plain.toByteArray())
    }

    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    fun decrypt(encryptedBytes: ByteArray): String {
        cipher1 = Cipher.getInstance(algol)
        cipher1.init(Cipher.DECRYPT_MODE, privateKey)
        return String(cipher1.doFinal(encryptedBytes))
    }
}
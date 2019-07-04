package com.rental.clentverification

import android.util.Base64
import java.io.UnsupportedEncodingException
import java.security.InvalidKeyException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESedeKeySpec
import javax.crypto.spec.SecretKeySpec
import java.util.Base64.getEncoder




class Utils {

    companion object {
        @Throws(NoSuchAlgorithmException::class)
        fun SHA256(text: String): String {

            val md = MessageDigest.getInstance("SHA-256")

            md.update(text.toByteArray())
            val digest = md.digest()

            return Base64.encodeToString(digest, Base64.DEFAULT)
        }

        fun sha1Hash(toHash: String): String? {
            var hash: String? = null
            try {
                val digest = MessageDigest.getInstance("SHA-1")
                var bytes = toHash.toByteArray(charset("UTF-8"))
                digest.update(bytes, 0, bytes.size)
                bytes = digest.digest()

                // This is ~55x faster than looping and String.formating()
                hash = bytesToHex(bytes)
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

            return hash
        }


        @Throws(NoSuchAlgorithmException::class, InvalidKeyException::class)
        public fun generateHashWithHmac(key: String, message: String): String? {
            try {
                val hashingAlgorithm = "HmacSHA1" //or "HmacSHA256","HmacSHA1", "HmacSHA512"

                val mac = Mac.getInstance(hashingAlgorithm)
                val secretKey = SecretKeySpec(key.toByteArray(), mac.algorithm)
                mac.init(secretKey)
                val bytes = mac.doFinal(message.toByteArray())
                val messageDigest = bytesToHex(bytes)

                return messageDigest
                // Log.i(TAG, "message digest: $messageDigest")

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null

        }

        fun bytesToHex(bytes: ByteArray): String {


            /* val hexArray = "0123456789abcdef".toCharArray()
             val hexChars = CharArray(bytes.size * 2)
             var j = 0
             var v: Int
             while (j < bytes.size) {
                 v = (bytes[j] and 0xFF.toByte()).toInt()
                 hexChars[j * 2] = hexArray[v.ushr(4)]
                 hexChars[j * 2 + 1] = hexArray[v and 0x0F]
                 j++
             }
             return String(hexChars)*/
            return bytes.toHex()
        }

        private val HEX_CHARS = "0123456789ABCDEF".toCharArray()

        fun ByteArray.toHex(): String {
            val result = StringBuffer()

            forEach {
                val octet = it.toInt()
                val firstIndex = (octet and 0xF0).ushr(4)
                val secondIndex = octet and 0x0F
                result.append(HEX_CHARS[firstIndex])
                result.append(HEX_CHARS[secondIndex])
            }

            return result.toString()
        }

        @Throws(Exception::class)
        fun encryptionDecryption(key: String, message: String): String {
            val keyBytes = key.toByteArray()
            val keySpec = DESedeKeySpec(keyBytes)
            val factory = SecretKeyFactory.getInstance("DESede")
            val key = factory.generateSecret(keySpec)
            val text = message.toByteArray()
            val cipher = Cipher.getInstance("DESede/CFB/NoPadding")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val encrypted = cipher.doFinal(text)
            val base64encodedSecretData = Base64.encode(encrypted, Base64.DEFAULT)
            var encodedString = String(base64encodedSecretData)
            val decodedValue = Base64.decode(encodedString.toByteArray(), Base64.DEFAULT)

            return encodedString


            /*  cipher = Cipher.getInstance("DESede")
              cipher.init(Cipher.DECRYPT_MODE, key)
              val decrypted = cipher.doFinal(decodedValue)
              return String(decrypted, StandardCharsets.UTF_8)*/
        }


    }
}
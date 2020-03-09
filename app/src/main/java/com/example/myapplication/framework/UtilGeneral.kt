package com.example.myapplication.framework

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.webkit.WebView
import java.io.*
import java.util.*

class UtilGeneral{

    companion object{

        /**
         * Do not initialze instance for static class only
         */
        private fun CBUtil() {}

        /**
         * Scale size
         *
         * @param _resources
         * @param size
         * @return
         */
        fun scaleSize(_resources: Resources, size: Int): Float {
            val scale = _resources.displayMetrics.scaledDensity
            val textSizeP = (size / scale).toInt()
            return textSizeP.toFloat()
        }

        /**
         * Convert Byte Array to Bitmap
         * @param imageByteArray
         * @return
         */
        fun convertByteArrayToBitmap(imageByteArray: ByteArray): Bitmap? {
            return BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
        }

        /**
         * Store Image to local phone Cache (Without Storage Permission)
         * @param context
         * @param imageBitmap
         * @param fileName
         * @param isResize
         * @return
         */
        fun storeImageToCacheDir(
            context: Context,
            imageBitmap: Bitmap?,
            fileName: String?,
            isResize: Boolean
        ): String? {
            var imageBitmap = imageBitmap
            var imageFile: File? = null
            var normalBitmap: Bitmap? = null
            try {
                normalBitmap = Bitmap.createScaledBitmap(
                    imageBitmap!!,
                    imageBitmap.width,
                    imageBitmap.height,
                    false
                )
                var weight = imageBitmap.width.toDouble()
                var height = imageBitmap.height.toDouble()
                // 750 x 450 = 301kb, 600 x 360= 197 kb, 400 x 240 = 101kb
                if (isResize) {
                    if (normalBitmap.width > normalBitmap.height) {
                        weight = 600.0
                        height =
                            weight * (normalBitmap.height.toDouble() / normalBitmap.width.toDouble())
                    } else {
                        height = 600.0
                        weight =
                            height * (normalBitmap.width.toDouble() / normalBitmap.height.toDouble())
                    }
                }
                imageBitmap =
                    Bitmap.createScaledBitmap(normalBitmap, weight.toInt(), height.toInt(), false)
                imageFile = File(context.cacheDir, fileName)
                val outputStream: OutputStream =
                    BufferedOutputStream(FileOutputStream(imageFile))
                outputStream.flush()
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
                outputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (npe: NullPointerException) {
                npe.printStackTrace()
                return ""
            } finally {
                normalBitmap!!.recycle()
                imageBitmap!!.recycle()
                normalBitmap = null
                imageBitmap = null
            }
            return Uri.fromFile(imageFile).path
        }

        /**
         * Update language in whole App
         *
         * @param _context
         * @param _languageCode
         */
        fun updateLanguageForApp(
            _context: Context,
            _languageCode: String?
        ) { //TODO Update Global variable if any
//ISubject.Companion.getManagerInstance().setLanguageCode(_languageCode);
//TODO Update Shared Preference Language
//SPreference.setAppLanguage(_context, _languageCode);
            val locale = Locale(_languageCode)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            _context.resources
                .updateConfiguration(config, _context.resources.displayMetrics)
            //For Nougat Version 24 & above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) WebView(_context).destroy()
        }
    }
}
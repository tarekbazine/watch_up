package com.example.tarekbaz.watch_up.Offline

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.tarekbaz.watch_up.R
import java.io.File
import java.io.FileOutputStream
import android.graphics.BitmapFactory
import android.util.Log


class ImageManager {

    companion object {
        private val IMG_Folder = "watch_up_images"


        fun saveImageBitmap(context: Context, image: ImageView, imgName: String): String?
        {
            val bitmapImage = convertImageViewToBitmap(image)
            return saveImage(context, bitmapImage, imgName)
        }
        fun saveImage(context: Context, image: Bitmap, imgName: String): String? {
            var savedImagePath: String? = null

            val imageFileName = "watchup_" + imgName + ".jpg"
            val storageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/" +IMG_Folder)
            var success = true
            if (!storageDir.exists()) {
                success = storageDir.mkdirs()
            }
            if (success) {
                val imageFile = File(storageDir, imageFileName)
                savedImagePath = imageFile.getAbsolutePath()
                try {
                    val fOut = FileOutputStream(imageFile)
                    image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                    fOut.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return savedImagePath
        }



        fun galleryAddPic(context: Context, imagePath: String?) {
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val f = File(imagePath)
            val contentUri = Uri.fromFile(f)
            mediaScanIntent.data = contentUri
            context.sendBroadcast(mediaScanIntent)
        }

        //function to convert imageView to Bitmap
        fun convertImageViewToBitmap(v: ImageView): Bitmap {
            val bm = (v.drawable as BitmapDrawable).bitmap
            return bm
        }

        fun deleteImage(imgName: String): Boolean {
            val file = File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/$IMG_Folder/watchup_${imgName}.jpg")
            if (file.exists()) {
                Log.i("path","${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/$IMG_Folder/watchup_${imgName}.jpg  exixts!!")
                return file.delete()
            } else {
                Log.i("path","${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/$IMG_Folder/watchup_${imgName}.jpg  not exixts!!")
                return false
            }
        }

        fun getImageFromPath(movieId: Int): Bitmap? {
            val imgFile = File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/$IMG_Folder/watchup_${movieId}.jpg")

            if (imgFile.exists()) {
                return  BitmapFactory.decodeFile(imgFile.absolutePath)
            }else{
                return null
            }
        }

//        fun saveAssociatedImage(context: Context , recyclerView : RecyclerView , filmId : Int, childCount:Int){
//            val view = (recyclerView.getChildAt(childCount+1).findViewById <CardView> (R.id.card_view_film))
//                    .findViewById<RelativeLayout>(R.id.card_film_sub_relative_layout).findViewById<ImageView>(R.id.image_card_film)
////            val viewid = recyclerView.layoutManager.findViewByPosition(childCount).id
////            val view = recyclerView.getChildAt(childCount) as ImageView
//            saveImageBitmap(context,view,filmId.toString())
//        }
    }

}
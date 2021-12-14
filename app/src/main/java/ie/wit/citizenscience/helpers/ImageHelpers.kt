package ie.wit.citizenscience.helpers

import android.content.Intent
import android.graphics.Color
import androidx.activity.result.ActivityResultLauncher
import ie.wit.citizenscience.R
import com.squareup.picasso.Transformation
import com.makeramen.roundedimageview.RoundedTransformationBuilder

fun showImagePicker(intentLauncher : ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_sighting_image.toString())
    intentLauncher.launch(chooseFile)
}

fun customTransformation() : Transformation =
    RoundedTransformationBuilder()
        .borderColor(Color.WHITE)
        .borderWidthDp(2F)
        .cornerRadiusDp(35F)
        .oval(false)
        .build()
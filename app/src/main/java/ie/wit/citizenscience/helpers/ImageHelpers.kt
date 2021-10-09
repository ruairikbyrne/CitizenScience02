package ie.wit.citizenscience.helpers

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import ie.wit.citizenscience.R

fun showImagePicker(intentLauncher : ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_sighting_image.toString())
    intentLauncher.launch(chooseFile)
}
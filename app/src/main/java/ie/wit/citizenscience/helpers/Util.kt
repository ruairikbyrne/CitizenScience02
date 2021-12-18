package ie.wit.citizenscience.helpers

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object Util {
    @JvmStatic
    @BindingAdapter("imageUri")
    fun loadImageWithUri(view: ImageView, image: String?){
        Picasso

            .get()
            .load(image)
            .resize(600,600)
            //.transform(customTransformation())
            .into(view)
    }
}
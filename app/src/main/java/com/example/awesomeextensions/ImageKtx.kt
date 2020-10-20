package com.example.awesomeextensions

import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import com.squareup.picasso.*


data class PicassoOption(
    @DrawableRes val errorHolder: Int,
    @DrawableRes val placeholder: Int? = null,
    val scaleType: PicassoScaleType = PicassoScaleType.Default,
    val cache: PicassoCache = PicassoCache.Default,
    val picassoCallback: Callback = Callback.EmptyCallback(),
    val enableDebug: Boolean = false
)

enum class PicassoCache {
    Default, // LRU->DISK->NETWORK
    Disk, // NO_LRU->DISK->NETWORK
    ForceLRU, // LRU->DISK->NO_NETWORK
    ForceDisk, // NO_LRU->DISK->NO_NETWORK
    ForceNetwork // NO_LRU->NO_DISK->NETWORK
}

sealed class PicassoScaleType {
    object Default : PicassoScaleType()
    object CenterCrop : PicassoScaleType()
    object Fit : PicassoScaleType()
    object CenterInside : PicassoScaleType()
    data class Custom(val height: Int, val width: Int) : PicassoScaleType()
    data class CustomDimen(@DimenRes val height: Int, @DimenRes val width: Int) : PicassoScaleType()
}

fun Picasso.loadOrError(imageUrl: String?, picassoOption: PicassoOption): RequestCreator {
    return if (imageUrl.isNullOrBlank()) {
        load(picassoOption.errorHolder)
    } else {
        load(imageUrl)
    }
}

fun ImageView.cancelImageLoading() {
    Picasso.get().cancelRequest(this)
}


data class PicassoImageBuilder(
    var imageUrl: String? = null,
    var isDebug: Boolean = false,
)


fun ImageView.loadImage(imageUrl: String?, picassoOption: PicassoOption) {
    with(picassoOption) {
        val picasso = Picasso.get()
        //enables logging
        picasso.isLoggingEnabled = enableDebug
        //green (memory, best performance)
        //blue (disk, good performance)
        //red (network, worst performance)
        picasso.setIndicatorsEnabled(enableDebug)
        picasso.loadOrError(imageUrl, picassoOption)
            .also {
                when (scaleType) {
                    is PicassoScaleType.Default -> {
                        //do nothing
                    }
                    is PicassoScaleType.CenterCrop -> it.fit().centerCrop()
                    is PicassoScaleType.Fit -> it.fit()
                    is PicassoScaleType.CenterInside -> it.centerInside()
                    is PicassoScaleType.Custom -> with(scaleType) {
                        val (height, width) = this
                        it.resize(height, width)
                    }
                    is PicassoScaleType.CustomDimen -> with(scaleType) {
                        val (height, width) = this
                        it.resizeDimen(height, width)
                    }
                }
            }
            .also {
                when (cache) {
                    PicassoCache.Default -> {
                        //do nothing
                    }
                    PicassoCache.ForceLRU -> {
                        it.networkPolicy(NetworkPolicy.OFFLINE)
                    }
                    PicassoCache.Disk -> {
                        it.memoryPolicy(MemoryPolicy.NO_CACHE)
                    }
                    PicassoCache.ForceDisk -> {
                        it.memoryPolicy(MemoryPolicy.NO_CACHE)
                        it.networkPolicy(NetworkPolicy.OFFLINE)
                    }
                    PicassoCache.ForceNetwork -> {
                        it.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        it.networkPolicy(NetworkPolicy.NO_CACHE)
                    }
                }
            }
            .error(errorHolder)
            .also { if (placeholder != null) it.placeholder(placeholder) }
            .into(this@loadImage, picassoCallback)
    }
}
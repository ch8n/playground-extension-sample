package com.example.awesomeextensions

import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import com.squareup.picasso.*


data class PicassoOption(
    @DrawableRes val errorHolder: Int,
    @DrawableRes val placeholder: Int? = null,
    val scaleType: PicassoScaleType = PicassoScaleType.Default,
    val cacheLevel: SkipCache = SkipCache.NONE,
    val picassoCallback: Callback? = null,
    val enableDebug: Boolean = false
)

data class PicassoSize(
    val height: Int,
    val width: Int
)

enum class SkipCache {
    NONE,
    ALL,
    MEMORY,
    DISK,
    NETWORK
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
                    is PicassoScaleType.CenterCrop -> it.centerCrop()
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
                when (cacheLevel) {
                    SkipCache.NONE -> {
                        //do nothing
                        //all cache enabled
                    }
                    SkipCache.ALL -> {
                        // all cache disabled
                        it.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        it.networkPolicy(NetworkPolicy.NO_CACHE)
                    }
                    SkipCache.MEMORY -> {
                        // memory cache skipped
                        it.memoryPolicy(MemoryPolicy.NO_CACHE)
                    }
                    SkipCache.DISK -> {
                        // disk cache skipped
                        it.memoryPolicy(MemoryPolicy.NO_STORE)
                    }
                    SkipCache.NETWORK -> {
                        // network cache skipped
                        it.networkPolicy(NetworkPolicy.NO_CACHE)
                    }
                }
            }
            .error(errorHolder)
            .also { if (placeholder != null) it.placeholder(placeholder) }
            .into(this@loadImage, picassoCallback)
    }
}
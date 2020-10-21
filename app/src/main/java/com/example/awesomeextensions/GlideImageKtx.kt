package com.example.awesomeextensions

import android.graphics.drawable.Drawable
import android.os.Debug
import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy


data class GlideOptions(
    @DrawableRes val errorHolder: Int,
    @DrawableRes val placeholder: Int = -1,
    val scaleType: GlideScaleType = GlideScaleType.Default,
    val transform: GlideTransform = GlideTransform.None,
    val isCircular: Boolean = false,
    val cache: GlideCache = GlideCache.Default
    //todo glide debug
    //todo target callback
)

sealed class GlideScaleType {
    object Default : GlideScaleType()
    object CenterCrop : GlideScaleType()
    object FitCenter : GlideScaleType()
    object CenterInside : GlideScaleType()
    data class Custom(val height: Int, val width: Int) : GlideScaleType()
    data class CustomDimen(@DimenRes val height: Int, @DimenRes val width: Int) : GlideScaleType()
}

enum class GlideCache {
    Default,
    All,
    Data,
    None,
    Resource,
    Automatic
}

enum class GlideTransform {
    None,
    Circle
}


fun RequestManager.loadOrError(imageUrl: String?, errorHolder: Int): RequestBuilder<Drawable> {
    return if (imageUrl.isNullOrBlank()) {
        load(errorHolder)
    } else {
        load(imageUrl)
    }
}

fun RequestBuilder<Drawable>.scaleType(scaleType: GlideScaleType): RequestBuilder<Drawable> {
    return this.apply {
        when (scaleType) {
            is GlideScaleType.Default -> {
                /**do nothing**/
            }
            is GlideScaleType.CenterCrop -> this.centerCrop()
            is GlideScaleType.FitCenter -> this.fitCenter()
            is GlideScaleType.CenterInside -> this.centerInside()
            is GlideScaleType.Custom -> with(scaleType) {
                val (height, width) = this@with
                this@apply.override(height, width)
            }
            is GlideScaleType.CustomDimen -> with(scaleType) {
                val (height, width) = this
                this@apply.override(height, width)
            }
        }
    }
}

fun RequestBuilder<Drawable>.cacheOption(cache: GlideCache): RequestBuilder<Drawable> {
    return this.apply {
        when (cache) {
            GlideCache.Default -> {
                /**do nothing**/
            }
            GlideCache.All -> {
                diskCacheStrategy(DiskCacheStrategy.ALL)
            }
            GlideCache.Automatic -> {
                diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            }
            GlideCache.None -> {
                diskCacheStrategy(DiskCacheStrategy.NONE)
            }
            GlideCache.Data -> {
                diskCacheStrategy(DiskCacheStrategy.DATA)
            }
            GlideCache.Resource -> {
                diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            }
        }
    }
}

fun RequestBuilder<Drawable>.placeHolder(@DrawableRes placeholder: Int): RequestBuilder<Drawable> {
    return this.also {
        if (placeholder != -1) {
            it.placeholder(placeholder)
        }
    }
}

fun RequestBuilder<Drawable>.transformations(transform: GlideTransform): RequestBuilder<Drawable> {
    return this.also {
        when (transform) {
            GlideTransform.None -> {
                /**do nothing**/
            }
            GlideTransform.Circle -> it.circleCrop()
        }
    }
}


fun ImageView.loadImage(url: String?, options: GlideOptions) {
    val glide = Glide.with(context)
        .loadOrError(url, options.errorHolder)
        .scaleType(options.scaleType)
        .cacheOption(options.cache)
        .error(options.errorHolder)
        .placeHolder(options.placeholder)
        .transformations(options.transform)
        .into(this)
}
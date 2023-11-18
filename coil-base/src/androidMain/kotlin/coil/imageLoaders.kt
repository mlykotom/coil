package coil

import android.graphics.BitmapFactory
import coil.decode.BitmapFactoryDecoder
import coil.decode.BitmapFactoryDecoder.Companion.DEFAULT_MAX_PARALLELISM
import coil.decode.ExifOrientationPolicy
import coil.decode.ExifOrientationPolicy.RESPECT_PERFORMANCE

/**
 * Sets the maximum number of parallel [BitmapFactory] decode operations at once.
 *
 * Increasing this number will allow more parallel [BitmapFactory] decode operations,
 * however it can result in worse UI performance.
 */
fun ImageLoader.Builder.bitmapFactoryMaxParallelism(maxParallelism: Int) = apply {
    require(maxParallelism > 0) { "maxParallelism must be > 0." }
    extras[bitmapFactoryMaxParallelismKey] = maxParallelism
}

internal val RealImageLoader.Options.bitmapFactoryMaxParallelism: Int
    get() = extras.getOrDefault(bitmapFactoryMaxParallelismKey)

private val bitmapFactoryMaxParallelismKey = Extras.Key(default = DEFAULT_MAX_PARALLELISM)

/**
 * Sets the policy for handling the EXIF orientation flag for images decoded by
 * [BitmapFactoryDecoder].
 */
fun ImageLoader.Builder.bitmapFactoryExifOrientationPolicy(policy: ExifOrientationPolicy) = apply {
    extras[bitmapFactoryExifOrientationPolicyKey] = policy
}

internal val RealImageLoader.Options.bitmapFactoryExifOrientationPolicy: ExifOrientationPolicy
    get() = extras.getOrDefault(bitmapFactoryExifOrientationPolicyKey)

private val bitmapFactoryExifOrientationPolicyKey = Extras.Key(default = RESPECT_PERFORMANCE)

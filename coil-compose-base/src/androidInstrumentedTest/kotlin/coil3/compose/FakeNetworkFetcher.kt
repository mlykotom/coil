package coil3.compose

import androidx.annotation.RawRes
import androidx.test.platform.app.InstrumentationRegistry
import coil3.ImageLoader
import coil3.Uri
import coil3.compose.base.test.R
import coil3.decode.DataSource
import coil3.decode.ImageSource
import coil3.fetch.FetchResult
import coil3.fetch.Fetcher
import coil3.fetch.SourceFetchResult
import coil3.request.Options
import okio.Buffer
import okio.BufferedSource

class FakeNetworkFetcher(
    private val url: String,
    private val options: Options,
) : Fetcher {

    override suspend fun fetch(): FetchResult {
        return when {
            url.endsWith("/image") -> SourceFetchResult(
                source = ImageSource(rawResourceAsSource(R.drawable.sample)),
                mimeType = "image/jpeg",
                dataSource = DataSource.NETWORK,
            )
            url.endsWith("/blue") -> SourceFetchResult(
                source = ImageSource(rawResourceAsSource(R.drawable.blue_rectangle)),
                mimeType = "image/png",
                dataSource = DataSource.NETWORK,
            )
            url.endsWith("/red") -> SourceFetchResult(
                source = ImageSource(rawResourceAsSource(R.drawable.red_rectangle)),
                mimeType = "image/png",
                dataSource = DataSource.NETWORK,
            )
            else -> {
                error("404 unknown resource: $url")
            }
        }
    }

    private fun rawResourceAsSource(
        @RawRes id: Int,
    ): BufferedSource {
        val resources = InstrumentationRegistry.getInstrumentation().targetContext.resources
        return Buffer().apply { readFrom(resources.openRawResource(id)) }
    }

    class Factory : Fetcher.Factory<Uri> {

        override fun create(
            data: Uri,
            options: Options,
            imageLoader: ImageLoader,
        ): Fetcher? {
            if (!isApplicable(data)) return null
            return FakeNetworkFetcher(
                url = data.toString(),
                options = options,
            )
        }

        private fun isApplicable(data: Uri): Boolean {
            return data.scheme == "http" || data.scheme == "https"
        }
    }
}

package coil3.fetch

import android.os.Build.VERSION.SDK_INT
import coil3.FileMediaDataSource
import coil3.ImageLoader
import coil3.request.Options
import coil3.test.assumeTrue
import coil3.test.context
import coil3.test.copyAssetToFile
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MediaDataSourceFetcherTest {

    private val fetcherFactory = MediaDataSourceFetcher.Factory()

    @Test
    fun basic() = runTest {
        assumeTrue(SDK_INT >= 23)

        val file = context.copyAssetToFile("video.mp4")

        val dataSource = FileMediaDataSource(file)
        val fetcher = assertIs<MediaDataSourceFetcher>(
            fetcherFactory.create(dataSource, Options(context), ImageLoader(context)),
        )

        val result = fetcher.fetch()

        assertIs<SourceFetchResult>(result)
        assertNull(result.mimeType)
        assertIs<MediaDataSourceFetcher.MediaSourceMetadata>(result.source.metadata)
    }
}

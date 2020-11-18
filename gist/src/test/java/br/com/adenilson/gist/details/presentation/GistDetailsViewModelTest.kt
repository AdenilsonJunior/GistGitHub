package br.com.adenilson.gist.details.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.adenilson.gist.details.presentation.adapter.FileItem
import br.com.adenilson.gist.details.presentation.adapter.FilesHeaderItem
import br.com.adenilson.gist.details.presentation.adapter.HeaderItem
import br.com.adenilson.gist.details.presentation.adapter.HtmlUrlItem
import br.com.adenilson.gist.common.domain.model.File
import br.com.adenilson.gist.common.domain.model.Gist
import br.com.adenilson.gist.common.domain.model.Owner
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GistDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: GistDetailsViewModel

    private val gistDetailsStateObserver: Observer<GistDetailsViewModel.GistDetailsState> = mock()

    @Before
    fun setup() {
        viewModel = GistDetailsViewModel()
        viewModel.gistDetailsState.observeForever(gistDetailsStateObserver)
    }

    @Test
    fun `should prepare details given a gist with success`() {
        viewModel.prepareGistDetails(gist)
        argumentCaptor<GistDetailsViewModel.GistDetailsState.Prepared> {
            verify(gistDetailsStateObserver, times(1)).onChanged(capture())

            with(this.firstValue.list) {
                assertEquals(1, filterIsInstance(br.com.adenilson.gist.details.presentation.adapter.HeaderItem::class.java).size)
                assertEquals(1, filterIsInstance(br.com.adenilson.gist.details.presentation.adapter.FilesHeaderItem::class.java).size)
                assertEquals(2, filterIsInstance(br.com.adenilson.gist.details.presentation.adapter.FileItem::class.java).size)
                assertEquals(1, filterIsInstance(br.com.adenilson.gist.details.presentation.adapter.HtmlUrlItem::class.java).size)

                assertEquals(expected, this)
            }
        }
    }

    private val expected = listOf(
        HeaderItem(
            "avatarUrl",
            "name",
            "filename",
            "description"
        ),
        FilesHeaderItem(2),
        FileItem(
            type = "type",
            size = 1,
            filename = "filename",
            language = "language",
            url = "rawUrl"
        ),
        FileItem(
            type = "type",
            size = 1,
            filename = "filename",
            language = "language",
            url = "rawUrl"
        ),
        HtmlUrlItem("htmlUrl")
    )

    private val gist = Gist(
        id = null,
        favorite = false,
        files = listOf(
            File(
                type = "type",
                rawUrl = "rawUrl",
                language = "language",
                filename = "filename",
                size = 1
            ),
            File(
                type = "type",
                rawUrl = "rawUrl",
                language = "language",
                filename = "filename",
                size = 1
            )
        ),
        owner = Owner(
            avatarUrl = "avatarUrl",
            name = "name"
        ),
        htmlUrl = "htmlUrl",
        description = "description",
        webId = "webId"
    )
}
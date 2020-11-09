package br.com.adenilson.gist.domain.mapper

import br.com.adenilson.data.model.FileModel
import br.com.adenilson.data.model.GistModel
import br.com.adenilson.data.model.OwnerModel
import br.com.adenilson.gist.presentation.model.File
import br.com.adenilson.gist.presentation.model.Gist
import br.com.adenilson.gist.presentation.model.Owner
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class GistModelMapperTest {

    private lateinit var mapper: GistModelMapper

    @Before
    fun setup() {
        mapper = GistModelMapperImpl()
    }

    @Test
    fun `should parse gist to model`() {
        mapper.mapTo(gist).run {
            assertEquals(expected, this)
        }
    }

    private val currentDate = Date()
    private val expected = GistModel(
        id = 1,
        description = "description",
        lastUpdate = currentDate,
        favorite = false,
        webId = "webId",
        owner = OwnerModel(
            id = 1,
            avatarUrl = "avatarUrl",
            name = "name"
        ),
        files = listOf(
            FileModel(
                id = 1,
                size = 1,
                filename = "filename",
                type = "type",
                rawUrl = "rawUrl",
                language = "language"
            )
        )
    )
    private val gist = Gist(
        id = 1,
        files = listOf(
            File(
                id = 1,
                size = 1,
                filename = "filename",
                language = "language",
                rawUrl = "rawUrl",
                type = "type"
            )
        ),
        owner = Owner(
            id = 1,
            name = "name",
            avatarUrl = "avatarUrl"
        ),
        webId = "webId",
        favorite = false,
        lastUpdate = currentDate,
        description = "description"
    )
}
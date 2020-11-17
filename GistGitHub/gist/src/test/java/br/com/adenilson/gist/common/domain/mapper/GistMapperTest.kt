package br.com.adenilson.gist.common.domain.mapper

import br.com.adenilson.core.extensions.SERVER_PATTERN
import br.com.adenilson.core.extensions.parseToString
import br.com.adenilson.gist.common.domain.model.File
import br.com.adenilson.gist.common.domain.model.Gist
import br.com.adenilson.gist.common.domain.model.Owner
import br.com.adenilson.network.response.FileResponse
import br.com.adenilson.network.response.GistResponse
import br.com.adenilson.network.response.OwnerResponse
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class GistMapperTest {

    private lateinit var mapper: GistRemoteMapper

    @Before
    fun setup() {
        mapper = GistRemoteMapperImpl()
    }

    @Test
    fun `should parse model to gist`() {
        mapper.mapTo(gistResponse).run {
            assertEquals(expected.id, id)
            assertEquals(expected.gistType, gistType)
            assertEquals(expected.webId, webId)
            assertEquals(expected.description, description)
            assertEquals(expected.files, files)
            assertEquals(expected.owner, owner)
            assertEquals(expected.favorite, favorite)
            assertEquals(expected.lastUpdate.toString(), lastUpdate.toString())
        }
    }

    private val currentDate = Date()
    private val gistResponse = GistResponse(
        description = "description",
        lastUpdate = currentDate.parseToString(SERVER_PATTERN),
        id = "webId",
        owner = OwnerResponse(
            avatarUrl = "avatarUrl",
            login = "name"
        ),
        files = mapOf(
            "filename" to FileResponse(
                size = 1,
                filename = "filename",
                type = "type",
                rawUrl = "rawUrl",
                language = "language"
            )
        )
    )
    private val expected = Gist(
        id = null,
        files = listOf(
            File(
                id = null,
                size = 1,
                filename = "filename",
                language = "language",
                rawUrl = "rawUrl",
                type = "type"
            )
        ),
        owner = Owner(
            id = null,
            name = "name",
            avatarUrl = "avatarUrl"
        ),
        webId = "webId",
        favorite = false,
        lastUpdate = currentDate,
        description = "description"
    )
}
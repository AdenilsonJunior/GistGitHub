package br.com.adenilson.infrastructure.mapper

import br.com.adenilson.core.extensions.SERVER_PATTERN
import br.com.adenilson.core.extensions.parseToString
import br.com.adenilson.data.model.FileModel
import br.com.adenilson.data.model.GistModel
import br.com.adenilson.data.model.OwnerModel
import br.com.adenilson.network.model.FileResponse
import br.com.adenilson.network.model.GistResponse
import br.com.adenilson.network.model.OwnerResponse
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.Date
import kotlin.math.exp

@RunWith(MockitoJUnitRunner::class)
class GistResponseMapperTest {

    private lateinit var mapper: GistResponseMapper

    @Before
    fun setup() {
        mapper = GistResponseMapperImpl()
    }

    @Test
    fun `should map response to model with success`() {
        val currentDate = Date()
        val response = GistResponse(
            owner = OwnerResponse(
                login = "name",
                avatarUrl = "avatarUrl"
            ),
            lastUpdate = currentDate.parseToString(SERVER_PATTERN),
            description = "description",
            files = mapOf(
                "filename" to FileResponse(
                    size = 1,
                    language = "language",
                    rawUrl = "rawUrl",
                    type = "type",
                    filename = "filename"
                )
            ),
            id = "webId"
        )

        val expected = GistModel(
            id = null,
            webId = "webId",
            favorite = false,
            owner = OwnerModel(
                id = null,
                name = "name",
                avatarUrl = "avatarUrl"
            ),
            lastUpdate = currentDate,
            description = "description",
            files = listOf(
                FileModel(
                    id = null,
                    size = 1,
                    language = "language",
                    rawUrl = "rawUrl",
                    type = "type",
                    filename = "filename"
                )
            )
        )

        mapper.mapTo(response).run {
            assertEquals(expected.owner, this.owner)
            assertEquals(expected.files, this.files)
            assertEquals(expected.webId, this.webId)
            assertEquals(expected.description, this.description)
            assertEquals(expected.favorite, this.favorite)
            assertEquals(expected.gistType, this.gistType)
            assertEquals(expected.id, this.id)
            assertEquals(expected.lastUpdate?.parseToString(), this.lastUpdate?.parseToString())
        }
    }
}
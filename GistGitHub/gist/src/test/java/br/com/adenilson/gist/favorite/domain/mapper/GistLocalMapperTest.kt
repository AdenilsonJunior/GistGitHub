package br.com.adenilson.gist.favorite.domain.mapper

import br.com.adenilson.database.entity.FileEntity
import br.com.adenilson.database.entity.GistEntity
import br.com.adenilson.database.entity.OwnerEntity
import br.com.adenilson.gist.common.domain.model.File
import br.com.adenilson.gist.common.domain.model.Gist
import br.com.adenilson.gist.common.domain.model.Owner
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class GistLocalMapperTest {

    private lateinit var mapper: GistLocalMapperImpl

    @Before
    fun setup() {
        mapper = GistLocalMapperImpl()
    }

    @Test
    fun `should map entity to model with success`() {
        val currentDate = Date()
        val entity = GistEntity(
            id = 1,
            description = "description",
            lastUpdate = currentDate,
            favorite = true,
            webId = "webId",
            files = listOf(
                FileEntity(
                    id = 1,
                    size = 1,
                    filename = "filename",
                    type = "type",
                    rawUrl = "rawUrl",
                    language = "language"
                )
            ),
            owner = OwnerEntity(
                id = 1,
                avatarUrl = "avatarUrl",
                name = "name"
            )
        )

        val expected = Gist(
            id = 1,
            webId = "webId",
            favorite = true,
            owner = Owner(
                id = 1,
                name = "name",
                avatarUrl = "avatarUrl"
            ),
            lastUpdate = currentDate,
            description = "description",
            files = listOf(
                File(
                    id = 1,
                    size = 1,
                    language = "language",
                    rawUrl = "rawUrl",
                    type = "type",
                    filename = "filename"
                )
            )
        )

        mapper.mapTo(entity).run {
            assertEquals(expected, this)
        }
    }
}
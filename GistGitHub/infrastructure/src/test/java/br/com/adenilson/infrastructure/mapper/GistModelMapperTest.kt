package br.com.adenilson.infrastructure.mapper

import br.com.adenilson.data.model.FileModel
import br.com.adenilson.data.model.GistModel
import br.com.adenilson.data.model.OwnerModel
import br.com.adenilson.database.entity.FileEntity
import br.com.adenilson.database.entity.GistEntity
import br.com.adenilson.database.entity.OwnerEntity
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

        val expected = GistModel(
            id = 1,
            webId = "webId",
            favorite = true,
            owner = OwnerModel(
                id = 1,
                name = "name",
                avatarUrl = "avatarUrl"
            ),
            lastUpdate = currentDate,
            description = "description",
            files = listOf(
                FileModel(
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
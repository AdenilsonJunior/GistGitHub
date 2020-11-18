package br.com.adenilson.gist.common.domain.mapper

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
class GistEntityMapperTest {

    private lateinit var mapper: GistEntityMapper

    @Before
    fun setup() {
        mapper = GistEntityMapperImpl()
    }

    @Test
    fun `should map model to entity`() {
        val currentDate = Date()
        val model = Gist(
            id = 1,
            webId = "webId",
            favorite = true,
            owner = Owner(
                id = 1,
                name = "name",
                avatarUrl = "avatarUrl"
            ),
            htmlUrl = "htmlUrl",
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

        val expected = GistEntity(
            id = 1,
            description = "description",
            htmlUrl = "htmlUrl",
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

        mapper.mapTo(model).run {
            assertEquals(expected, this)
        }
    }
}
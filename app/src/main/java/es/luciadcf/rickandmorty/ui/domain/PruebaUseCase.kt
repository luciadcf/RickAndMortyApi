package es.luciadcf.rickandmorty.ui.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.io.Closeable

class AggregateUserDataUseCase(
    private val resolveCurrentUser: suspend () -> UserEntity,
    private val fetchUserComments: suspend (UserId) -> List<CommentEntity>,
    private val fetchSuggestedFriends: suspend (UserId) -> List<FriendEntity>
) : Closeable, AggregateUseCase {

    private lateinit var user: UserEntity
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override suspend fun aggregateDataForCurrentUser(): AggregatedData {
        return getUserData()
    }

    suspend fun getUserData(): AggregatedData {
        var comments = emptyList<CommentEntity>()
        var suggestions = emptyList<FriendEntity>()
        scope.launch {
            withContext(Dispatchers.IO) {
                user = resolveCurrentUser()
                comments = getComments(user.id)
                suggestions = getSuggestedFriends(user.id)
            }
        }
        return AggregatedData(user, comments, suggestions)
    }

    suspend fun getComments(userId: String): List<CommentEntity> {
        var comments = emptyList<CommentEntity>()
        scope.launch {
            try {
                withTimeout(2000) {
                    comments = fetchUserComments(userId)
                }
            } catch (ex: TimeoutCancellationException) {
                comments = emptyList()
            }
        }
        return comments
    }

    suspend fun getSuggestedFriends(userId: String): List<FriendEntity> {
        var suggestedFriends = emptyList<FriendEntity>()
        scope.launch {
            try {
                withTimeout(2000) {
                    suggestedFriends = fetchSuggestedFriends(userId)
                }
            } catch (ex: TimeoutCancellationException) {
                suggestedFriends = emptyList()
            }
        }
        return suggestedFriends
    }

    override fun close() {
        //TODO("implement task")
    }
}

/**
 *
 * The following is already available on classpath.
 * Please do not uncomment this code or modify.
 * This is only for your convenience to copy-paste code into the IDE


package coroutines
 */
data class AggregatedData(
    val user: UserEntity,
    val comments: List<CommentEntity>,
    val suggestedFriends: List<FriendEntity>
)

typealias UserId = String

data class UserEntity(val id: UserId, val name: String)

data class CommentEntity(val id: String, val content: String)

data class FriendEntity(val id: String, val name: String)

interface AggregateUseCase {
    suspend fun aggregateDataForCurrentUser(): AggregatedData
}

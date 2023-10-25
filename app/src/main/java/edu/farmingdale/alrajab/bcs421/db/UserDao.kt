package edu.farmingdale.alrajab.bcs421.db

import androidx.room.*
import androidx.room.OnConflictStrategy.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
	@Query("SELECT * FROM users")
	suspend fun getAll(): List<User>

	@Query("SELECT * FROM users")
	fun getAllFlow(): Flow<List<User>>

	@Query("SELECT * FROM users WHERE uid IN (:userIds)")
	suspend fun loadAllByIds(userIds: IntArray): List<User>

	@Query(
		"SELECT * FROM users WHERE first_name LIKE :first AND " +
				"last_name LIKE :last LIMIT 1"
	)
	suspend fun findByName(first: String, last: String): User

	@Insert
	suspend fun insertAll(vararg users: User)

	@Insert(onConflict = REPLACE)
	suspend fun addUser(user: User): Long

	@Query("SELECT * FROM users WHERE uid = :id")
	suspend fun getUser(id: Long): User?

	@Delete
	suspend fun delete(user: User)

	@Update
	suspend fun update(user: User)
}
package edu.farmingdale.alrajab.bcs421.db

import androidx.room.*
import androidx.room.OnConflictStrategy.*
import androidx.room.OnConflictStrategy.Companion.REPLACE

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Query("SELECT * FROM users WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM users WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Insert(onConflict = REPLACE)
    fun addUser(user: User): Long

    @Query("SELECT * FROM users WHERE uid = :id")
    fun getUser(id: Long): User?

    @Delete
    fun delete(user: User)
}
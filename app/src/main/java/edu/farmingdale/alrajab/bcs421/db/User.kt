package edu.farmingdale.alrajab.bcs421.db

import androidx.room.*

@Entity(tableName = "users")
data class User(

    @PrimaryKey (autoGenerate = true) val uid: Int?,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?


){
@Ignore
        constructor(firstName: String?, lastName: String?) : this(null, firstName, lastName)
    }

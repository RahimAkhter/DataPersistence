package edu.farmingdale.alrajab.bcs421.repo

import android.content.Context
import androidx.room.Room
import edu.farmingdale.alrajab.bcs421.db.User
import edu.farmingdale.alrajab.bcs421.db.AppDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(DelicateCoroutinesApi::class)
class NameRepository private constructor(context: Context) {
	private val database: AppDatabase = Room.databaseBuilder(
		context.applicationContext,
		AppDatabase::class.java,
		"person.db"
	)
		.build()


	companion object {
		private var instance: NameRepository? = null

		fun getInstance(context: Context): NameRepository {
			if (instance == null) {
				instance = NameRepository(context)
			}
			return instance!!
		}
	}


	private val usrDao = database.userDao()

	init {
		GlobalScope.launch(Dispatchers.IO) {
			if (usrDao.getAll().isEmpty()) {
				addUser(User("Moaath", "Alrajab"))
				addUser(User("James", "Smith"))
				addUser(User("Ben", "Adams"))
			}
		}
	}

	suspend fun getUser(id: Long): User? = withContext(Dispatchers.IO) {
		usrDao.getUser(id)
	}

	suspend fun getAll(): List<User> = withContext(Dispatchers.IO) {
		usrDao.getAll()
	}

	fun getAllFlow(): Flow<List<User>> = usrDao.getAllFlow().flowOn(Dispatchers.IO)

	suspend fun addUser(usr: User) {
		withContext(Dispatchers.IO) {
			usrDao.addUser(usr)
		}
	}

	suspend fun update(user: User) {
		withContext(Dispatchers.IO) {
			usrDao.update(user)
		}
	}

	suspend fun deleteUser(usr: User) {
		withContext(Dispatchers.IO) {
			usrDao.delete(usr)
		}
	}
}
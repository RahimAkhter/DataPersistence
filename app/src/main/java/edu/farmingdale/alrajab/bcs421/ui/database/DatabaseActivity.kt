package edu.farmingdale.alrajab.bcs421.ui.database

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.farmingdale.alrajab.bcs421.db.User
import edu.farmingdale.alrajab.bcs421.repo.NameRepository
import edu.farmingdale.alrajab.bcs421.ui.theme.DataPersistenceTheme
import kotlinx.coroutines.launch

class DatabaseActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			DatabaseView()
		}
	}
}

@Composable
fun DatabaseView() {
	val context = LocalContext.current
	val scope = rememberCoroutineScope()
	val repo = remember { NameRepository.getInstance(context) }

	val users by repo.getAllFlow().collectAsState(emptyList())

	var nameFirst by remember { mutableStateOf("") }
	var nameLast by remember { mutableStateOf("") }

	DataPersistenceTheme {
		DatabaseContent(
			nameFirst = nameFirst,
			updateNameFirst = { nameFirst = it },
			nameLast = nameLast,
			updateNameLast = { nameLast = it },

			addUser = {
				scope.launch {
					repo.addUser(User(nameFirst, nameLast))
				}
			},

			users = users,
			updateUser = {
				scope.launch {
					repo.update(it)
				}
			},
			removeUser = {
				scope.launch {
					repo.deleteUser(it)
				}
			}
		)
	}
}

@Preview
@Composable
fun PreviewDatabaseContent() {
	DataPersistenceTheme {
		DatabaseContent(
			"First",
			{},
			"Last",
			{},
			{},
			listOf(
				User(1, "a", "a"),
				User(2, "b", "b"),
				User(3, "c", "c")
			),
			{},
			{}
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatabaseContent(
	nameFirst: String,
	updateNameFirst: (String) -> Unit,
	nameLast: String,
	updateNameLast: (String) -> Unit,

	addUser: () -> Unit,

	users: List<User>,

	updateUser: (User) -> Unit,
	removeUser: (User) -> Unit
) {
	Scaffold { paddingValues ->
		Column(
			Modifier
				.padding(paddingValues)
				.fillMaxSize(),
			verticalArrangement = Arrangement.spacedBy(4.dp)
		) {
			Row(
				Modifier.fillMaxWidth(),
				Arrangement.SpaceEvenly,
				Alignment.CenterVertically
			) {
				OutlinedTextField(
					nameFirst,
					updateNameFirst,
					Modifier.fillMaxWidth(.4f),
					label = { Text("First Name") },
				)

				OutlinedTextField(
					nameLast,
					updateNameLast,
					Modifier.fillMaxWidth(.6f),
					label = { Text("Last Name") }
				)

				Button(
					onClick = addUser
				) {
					Text("Add")
				}
			}

			Divider()

			LazyColumn(
				Modifier.fillMaxSize()
			) {
				items(users, key = { it.uid }) {
					UserItem(
						user = it,
						update = updateUser,
						delete = { removeUser(it) }
					)
				}
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserItem(user: User, update: (User) -> Unit, delete: () -> Unit) {
	var nameFirst by remember { mutableStateOf(user.firstName ?: "") }
	var nameLast by remember { mutableStateOf(user.lastName ?: "") }

	Row(
		Modifier.fillMaxWidth(),
		Arrangement.SpaceEvenly,
		Alignment.CenterVertically
	) {
		OutlinedTextField(
			nameFirst,
			{
				nameFirst = it
			},
			Modifier.fillMaxWidth(.3f),
			label = { Text("First Name") },
		)

		OutlinedTextField(
			nameLast,
			{
				nameLast = it
			},
			Modifier.fillMaxWidth(.4f),
			label = { Text("Last Name") }
		)

		IconButton(
			onClick = {
				update(
					user.copy(
						firstName = nameFirst,
						lastName = nameLast
					)
				)
			}
		) {
			Icon(Icons.Default.Edit, "Save")
		}

		IconButton(
			onClick = delete
		) {
			Icon(Icons.Default.Close, "Close")
		}
	}
}
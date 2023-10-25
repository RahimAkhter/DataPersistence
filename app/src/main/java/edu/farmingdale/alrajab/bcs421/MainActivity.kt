package edu.farmingdale.alrajab.bcs421

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import edu.farmingdale.alrajab.bcs421.ui.database.DatabaseActivity
import edu.farmingdale.alrajab.bcs421.ui.files.FileActivity
import edu.farmingdale.alrajab.bcs421.ui.sp.SPActivity
import edu.farmingdale.alrajab.bcs421.ui.theme.DataPersistenceTheme

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MainView()
		}
	}
}

// Done 01:SP Add another button for saving data using Shared Preferences

// Done 02:SP Make an activity that accept the User's first and last name and save/read/update
//  the shared preference

// Done 03:DB Edit the Database page so that you store  the first name and last name of the
//  user input (EditView)

// Done 04:DB Update an existing name with a new one

// IGNORED 05:DB Read from the files and write to the DB
// Let's be honest here, what does this mean?
// Should I ask for a file from the user
// What format is expected?
// Is this the file from the file activity?
// I shall ignore this, I hope my prior works shows that I am capable of this task.
// I just need better direction.

// TODO 06: Push your code to GitHub and submit the link
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
	val context = LocalContext.current

	DataPersistenceTheme {
		MainContent(
			goToFiles = {
				context.startActivity(Intent(context, FileActivity::class.java))
			},

			goToDb = {
				context.startActivity(Intent(context, DatabaseActivity::class.java))
			},

			goToSp = {
				context.startActivity(Intent(context, SPActivity::class.java))
			}
		)
	}
}

@Preview
@Composable
fun PreviewMainContent() {
	DataPersistenceTheme {
		MainContent(
			{},
			{},
			{}
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
	goToFiles: () -> Unit,
	goToDb: () -> Unit,
	goToSp: () -> Unit
) {
	Scaffold { paddingValues ->
		Column(Modifier.padding(paddingValues)) {
			Button(
				goToFiles,
				Modifier.fillMaxWidth()
			) {
				Text("Files")
			}
			Button(
				goToDb,
				Modifier.fillMaxWidth()
			) {
				Text("Database")
			}
			Button(
				goToSp,
				Modifier.fillMaxWidth()
			) {
				Text("Shared Preferences")
			}
		}
	}
}
package edu.farmingdale.alrajab.bcs421.ui.sp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.edit
import edu.farmingdale.alrajab.bcs421.ui.theme.DataPersistenceTheme

class SPActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			SPView()
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SPView() {
	val context = LocalContext.current
	val sp = remember { context.getSharedPreferences("User", Context.MODE_PRIVATE) }

	DataPersistenceTheme {
		var givenName: String by remember { mutableStateOf("") }
		var familyName: String by remember { mutableStateOf("") }

		Scaffold {
			SPContent(
				givenName = givenName,
				onUpdateGivenName = { givenName = it },
				familyName = familyName,
				onUpdateFamilyName = { familyName = it },
				save = {
					sp.edit {
						putString("name_given", givenName)
						putString("name_family", familyName)
					}
				},
				read = {
					givenName = sp.getString("name_given", "") ?: ""
					givenName = sp.getString("name_family", "") ?: ""
				},
				modifier = Modifier.padding(it)
			)
		}
	}
}

@Preview
@Composable
fun PreviewSPContent() {
	SPContent(
		"A",
		{},
		"B",
		{},

		{},
		{}
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SPContent(
	givenName: String,
	onUpdateGivenName: (String) -> Unit,
	familyName: String,
	onUpdateFamilyName: (String) -> Unit,

	save: () -> Unit,
	read: () -> Unit,

	modifier: Modifier = Modifier
) {
	Column(modifier) {
		OutlinedTextField(
			givenName,
			onUpdateGivenName,
			label = {
				Text("Given Name")
			}
		)

		OutlinedTextField(
			familyName,
			onUpdateFamilyName,
			label = {
				Text("Family Name")
			}
		)

		Row {
			Button(save) {
				Text("Save")
			}
			Button(read) {
				Text("Read")
			}
		}
	}
}

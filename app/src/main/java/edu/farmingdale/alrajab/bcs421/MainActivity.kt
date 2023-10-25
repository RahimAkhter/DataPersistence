package edu.farmingdale.alrajab.bcs421

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.farmingdale.alrajab.bcs421.ui.database.DatabaseActivity
import edu.farmingdale.alrajab.bcs421.databinding.ActivityMainBinding
import edu.farmingdale.alrajab.bcs421.ui.files.FileActivity
import edu.farmingdale.alrajab.bcs421.ui.sp.SPActivity

class MainActivity : AppCompatActivity() {

	// create binding view true - make sure to add it to the module build.gradle
	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		binding.databaseBtn.setOnClickListener { processDatabase() }

		binding.filesBtn.setOnClickListener { processFiles() }

		// Done 01:SP Add another button for saving data using Shared Preferences
		binding.buttonSp.setOnClickListener { processSharedPreference() }

		// Done 02:SP Make an activity that accept the User's first and last name and save/read/update
		//  the shared preference

		// Done 03:DB Edit the Database page so that you store  the first name and last name of the
		//  user input (EditView)

		// Done 04:DB Update an existing name with a new one
		// TODO 05:DB Read from the files and write to the DB

		// TODO 06: Push your code to GitHub and submit the link

	}

	/**
	 * Move to the file activity
	 */
	private fun processFiles() {
		startActivity(Intent(this, FileActivity::class.java))
	}

	/**
	 * Move to the database activity
	 */
	private fun processDatabase() {
		startActivity(Intent(this, DatabaseActivity::class.java))
	}

	/**
	 * Move to the shared preference activity
	 */
	private fun processSharedPreference() {
		startActivity(Intent(this, SPActivity::class.java))
	}
}
package edu.farmingdale.alrajab.bcs421.ui.files

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.farmingdale.alrajab.bcs421.databinding.ActivityFileBinding
import java.io.FileNotFoundException
import java.io.PrintWriter

class FileActivity : AppCompatActivity() {

	private lateinit var binding: ActivityFileBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityFileBinding.inflate(layoutInflater)
		setContentView(binding.root)


		binding.writeToFile.setOnClickListener { writeToInternalFile() }
		binding.readFromFile.setOnClickListener { readFromInternalFile() }

		binding.goBackBtn.setOnClickListener {
			finish()
		}
	}

	private fun readFromInternalFile() {
		try {
			val inputStream = openFileInput("myfile")
			val reader = inputStream.bufferedReader()
			val stringBuilder = StringBuilder()
			val lineSeparator = System.getProperty("line.separator")

			// Append each task to stringBuilder
			reader.forEachLine { stringBuilder.append(it).append(lineSeparator) }

			binding.textOfFile.text = stringBuilder.toString()
		} catch (e: FileNotFoundException) {
			Toast.makeText(this, "No file!", Toast.LENGTH_SHORT).show()
		}
	}

	private fun writeToInternalFile() {
		try {
			val outputStream = openFileOutput("myfile", MODE_PRIVATE)
			val writer = PrintWriter(outputStream)

			// Write each task on a separate line
			writer.println(binding.enterValue.text)

			writer.close()
		} catch (e: FileNotFoundException) {
			Toast.makeText(this, "No file!", Toast.LENGTH_SHORT).show()
		}
	}

}
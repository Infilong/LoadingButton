package com.udacity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.udacity.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        //get data with variable fileNameAndDownloadStatus from
        // NotificationUtil.kt putExtra("fileNameAndDownloadStatus", fileNameAndDownloadStatus)
        val fileNameAndDownloadStatus =
            intent.getParcelableExtra<FileNameAndDownloadStatus>("fileNameAndDownloadStatus")

        binding.fileNameAndDownloadStatus = fileNameAndDownloadStatus

        //Set status color
        if (fileNameAndDownloadStatus?.status == "Success") {
            binding.status.setTextColor(resources.getColor(R.color.colorPrimaryDark, null))
        } else {
            binding.status.setTextColor(Color.RED)
        }

        setContentView(binding.root)

        binding.confirmButtonButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}

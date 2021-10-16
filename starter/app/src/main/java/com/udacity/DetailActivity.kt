package com.udacity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.udacity.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val fileNameAndDownloadStatus =
            intent.getParcelableExtra<FileNameAndDownloadStatus>("fileNameAndDownLoadStatus")
        binding.fileNameAndDownloadStatus = fileNameAndDownloadStatus
        setContentView(binding.root)
    }
}

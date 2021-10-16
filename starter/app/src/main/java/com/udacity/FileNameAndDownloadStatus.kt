package com.udacity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FileNameAndDownloadStatus(var fileName: String, var status: String) : Parcelable
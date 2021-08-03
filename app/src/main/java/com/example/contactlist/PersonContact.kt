package com.example.contactlist

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonContact(
        var lastName : String,
        var name: String,
        var number: String
) : Parcelable
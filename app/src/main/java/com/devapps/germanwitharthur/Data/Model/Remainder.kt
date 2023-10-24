package com.devapps.germanwitharthur.Data.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("remainders")
data class Remainder(
    @PrimaryKey(autoGenerate = true)
    val remId: Int = 0,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("time")
    val time: String,
    @ColumnInfo("status")
    val status: Boolean = false
)

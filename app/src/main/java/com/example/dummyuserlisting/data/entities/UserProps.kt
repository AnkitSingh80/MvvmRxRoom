package com.example.dummyuserlisting.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_table")
data class UserProps(
    @PrimaryKey
    @ColumnInfo(name = "user_email") val email: String,
    @ColumnInfo(name = "user_accept") val accept: Boolean,
    @ColumnInfo(name = "user_reject") val reject: Boolean

)
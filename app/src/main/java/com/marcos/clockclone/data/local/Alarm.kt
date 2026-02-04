package com.marcos.clockclone.data.local

data class Alarm(
    val id: Int,
    val time: String,
    val label: String,
    val isActive: Boolean
)
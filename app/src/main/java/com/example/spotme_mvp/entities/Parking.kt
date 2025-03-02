package com.example.spotme_mvp.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Parking : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var title: String? = null
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var allowedTime: Long = 0
    var startTime: Long = 0
    var endTime: Long = 0
    var description: String? = null

    constructor()

    constructor(
        title: String?,
        latitude: Double,
        longitude: Double,
        allowedTime: Long,
        startTime: Long,
        endTime: Long,
        description: String?
    ) {
        this.title = title
        this.latitude = latitude
        this.longitude = longitude
        this.allowedTime = allowedTime
        this.startTime = startTime
        this.endTime = endTime
        this.description = description
    }
}

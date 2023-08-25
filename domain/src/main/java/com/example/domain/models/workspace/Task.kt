package com.example.domain.models.workspace

import java.time.LocalDateTime

class Task (
    var id: Long,
    var name: String,
    var description: String,
    var imageCoverName: String,
    var datetimeBegin: LocalDateTime?,
    var datetimeEnd: LocalDateTime?,
    var isComplete: Boolean,

    var projectId: Long,
    var projectName: String,
)
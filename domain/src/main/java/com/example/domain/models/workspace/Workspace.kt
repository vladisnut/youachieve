package com.example.domain.models.workspace

class Workspace (
    var id: Long,
    var shortName: String,
    var name: String,
    var description: String,
    var isPrivate: Boolean,
    var imageAvatarName: String,
    var imageCoverName: String,
)
package com.example.domain.repository

import com.example.domain.models.workspace.Project
import com.example.domain.models.base.Status

interface ProjectRepository {

    fun createProject(
        workspaceId: Long,
        name: String,
        description: String?,
        isPrivate: Boolean,
        imageAvatarName: String?,
        imageCoverName: String?,
        status: Status?
    )

    fun updateProject(
        projectId: Long,
        name: String?,
        description: String?,
        isPrivate: Boolean?,
        imageAvatarName: String?,
        imageCoverName: String?,
        status: Status?
    )

    fun getProject(
        projectId: Long,
        status: Status?
    ): Project?

    fun getProjectList(
        workspaceId: Long,
        format: String?,
        count: Int?,
        offset: Int,
        status: Status?
    ): List<Project>?

    fun deleteProject(
        projectId: Long,
        status: Status?
    )

}
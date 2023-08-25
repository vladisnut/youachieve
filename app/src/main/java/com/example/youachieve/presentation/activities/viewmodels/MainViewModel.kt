package com.example.youachieve.presentation.activities.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.display.SaveDisplayHeightUseCase
import com.example.domain.usecase.display.SaveDisplayWidthUseCase
import com.example.domain.usecase.workspace.project.CreateProjectUseCase
import com.example.domain.usecase.workspace.project.DeleteProjectUseCase
import com.example.domain.usecase.workspace.project.GetProjectListUseCase
import com.example.domain.usecase.workspace.task.CreateTaskUseCase
import com.example.domain.usecase.workspace.task.DeleteTaskUseCase
import com.example.domain.usecase.workspace.task.GetTaskListUseCase
import com.example.domain.usecase.workspace.workspace.CreateWorkspaceUseCase
import com.example.domain.usecase.workspace.workspace.DeleteWorkspaceUseCase
import com.example.domain.usecase.workspace.workspace.GetWorkspaceListUseCase
import com.example.domain.utils.LogData
import com.example.domain.utils.Random
import com.example.youachieve.presentation.base.BaseViewModel
import com.example.youachieve.presentation.utils.executeAsyncTask
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val saveDisplayWidthUseCase: SaveDisplayWidthUseCase,
    private val saveDisplayHeightUseCase: SaveDisplayHeightUseCase,

    private val createWorkspaceUseCase: CreateWorkspaceUseCase,
    private val getWorkspaceListUseCase: GetWorkspaceListUseCase,
    private val createProjectUseCase: CreateProjectUseCase,
    private val getProjectListUseCase: GetProjectListUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val getTaskListUseCase: GetTaskListUseCase,

    private val deleteWorkspaceUseCase: DeleteWorkspaceUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
): BaseViewModel() {

    fun saveDisplaySize(width: Int, height: Int) {
        saveDisplayWidthUseCase.execute(width)
        saveDisplayHeightUseCase.execute(height)
    }

    fun testDatabase() {

        fun clearDatabase() {
            deleteTaskUseCase.execute(0)
            deleteProjectUseCase.execute(0)
            deleteWorkspaceUseCase.execute(0)
        }

        fun initDatabase() {
            while (getWorkspaceListUseCase.execute()?.size!! < 4) {
                createWorkspaceUseCase.execute(
                    name = Random.getRandomString(6, 7),
                    description = Random.getRandomString(21, 101),
                    isPrivate = Random.getRandomBoolean(),
                )
            }

            val workspaceList = getWorkspaceListUseCase.execute()

            if (workspaceList != null) {
                for (workspace in workspaceList) {
                    for (i in 1..Random.getRandomInt(4, 5)) {
                        createProjectUseCase.execute(
                            workspaceId = workspace.id,
                            name = Random.getRandomString(7, 10),
                            description = Random.getRandomString(20, 100),
                            isPrivate = Random.getRandomBoolean(),
                        )
                    }

                    val projectList = getProjectListUseCase.execute(workspace.id)

                    if (projectList != null) {
                        for (project in projectList) {
                            for (i in 1..Random.getRandomInt(4, 5)) {
                                createTaskUseCase.execute(
                                    projectId = project.id,
                                    name = Random.getRandomString(6, 10),
                                    description = Random.getRandomString(24, 100),
                                )
                            }
                        }
                    }
                }
            }
        }

        fun printDatabase() {
            val workspaceList = getWorkspaceListUseCase.execute()

            if (workspaceList != null) {
                for (workspace in workspaceList) {
                    Log.d(LogData.TAG_DATABASE, "WORKSPACE name=\"${workspace.name}\" id=${workspace.id}")

                    val projectList = getProjectListUseCase.execute(workspace.id)
                    if (projectList != null) {
                        for (project in projectList) {
                            Log.d(LogData.TAG_DATABASE, "PROJECT name=\"${project.name}\" " +
                                    "id=${project.id} workspaceId=${workspace.id}")

                            val taskList = getTaskListUseCase.execute(project.id)
                            if (taskList != null) {
                                for (task in taskList) {
                                    Log.d(LogData.TAG_DATABASE, "TASK name=\"${task.name}\" " +
                                            "id=${task.id} workspaceId=${workspace.id} projectId=${project.id}")
                                }
                            }
                        }
                    }
                }
            }
        }

        viewModelScope.executeAsyncTask(onPreExecute = {
            Log.d(LogData.TAG_DATABASE, "==================== DATABASE START ====================")

        }, doInBackground = {
            //clearDatabase()
            //initDatabase()
            printDatabase()
            0

        }, onPostExecute = {
            Log.d(LogData.TAG_DATABASE, "==================== DATABASE END ====================")
        })
    }

}
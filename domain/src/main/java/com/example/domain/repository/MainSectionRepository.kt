package com.example.domain.repository

import com.example.domain.models.base.MainSectionType

interface MainSectionRepository {
    fun getSelected(): MainSectionType
    fun saveSelected(mainSectionType: MainSectionType)
}
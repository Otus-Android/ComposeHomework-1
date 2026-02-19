package ru.otus.common.di

import android.content.Context
import androidx.fragment.app.Fragment

inline fun <reified T> Context.findDependencies(): T {
    return ((applicationContext as DependenciesProvider).getDependencies() as T)
}

interface Dependencies

interface DependenciesProvider {
    fun getDependencies(): Dependencies
}
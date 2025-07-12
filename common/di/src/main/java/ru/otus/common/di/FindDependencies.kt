package ru.otus.common.di

import androidx.activity.ComponentActivity

inline fun <reified T> ComponentActivity.findDependencies(): T {
    return ((this.applicationContext as DependenciesProvider).getDependencies() as T)
}

interface Dependencies

interface DependenciesProvider {
    fun getDependencies(): Dependencies
}
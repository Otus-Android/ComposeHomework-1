package ru.otus.common.di

interface Dependencies

interface DependenciesProvider {
    fun getDependencies(): Dependencies
}
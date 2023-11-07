package com.udeldev.tasklab.di

import android.app.Application
import androidx.room.Room
import com.udeldev.tasklab.data.repository.TodoRepositoryImpl
import com.udeldev.tasklab.data.source.TodoDatabase
import com.udeldev.tasklab.domain.repository.TodoRepository
import com.udeldev.tasklab.domain.use_case.AddTodoUseCase
import com.udeldev.tasklab.domain.use_case.DeleteTodoUseCase
import com.udeldev.tasklab.domain.use_case.GetTodoUseCase
import com.udeldev.tasklab.domain.use_case.GetTodosUseCase
import com.udeldev.tasklab.domain.use_case.TodoUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesTodoDatabase(app : Application) : TodoDatabase{
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            TodoDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesTodoRepository(db : TodoDatabase) : TodoRepository{
        return TodoRepositoryImpl(db.todoDao)
    }

    @Provides
    @Singleton
    fun providesTodoUseCases (repository: TodoRepository) : TodoUseCases{
        return TodoUseCases(
            getTodosUseCase = GetTodosUseCase(repository),
            getTodoUseCase = GetTodoUseCase(repository),
            addTodoUseCase = AddTodoUseCase(repository),
            deleteTodoUseCase = DeleteTodoUseCase(repository)
        )
    }
}
package com.udeldev.tasklab.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.udeldev.tasklab.data.source.util.Converters
import com.udeldev.tasklab.domain.model.Todo


@Database(
    entities = [Todo::class],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase(){

    abstract val todoDao:TodoDao

    companion object{
        const val DATABASE_NAME = "todo_db"
    }

}
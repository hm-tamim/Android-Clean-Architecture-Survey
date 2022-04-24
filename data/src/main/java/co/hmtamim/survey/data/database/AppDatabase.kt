package co.hmtamim.survey.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import co.hmtamim.survey.data.database.dao.SurveysDao
import co.hmtamim.survey.domain.model.SurveyModel

@Database(
    entities = [SurveyModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun surveysDao(): SurveysDao

    companion object {
        const val DATABASE_NAME: String = "app_database"
    }

}

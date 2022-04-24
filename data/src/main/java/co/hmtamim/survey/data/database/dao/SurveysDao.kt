package co.hmtamim.survey.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import co.hmtamim.survey.domain.model.SurveyModel
import kotlinx.coroutines.flow.Flow

@Dao
interface SurveysDao {

    @Query("SELECT * FROM survey_list")
    fun getAllList(): Flow<List<SurveyModel>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(entity: SurveyModel)

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(entities: List<SurveyModel>)

    @Delete
    suspend fun delete(entity: SurveyModel)

    @Query("DELETE FROM survey_list")
    suspend fun deleteAll()

    @Query("DELETE FROM survey_list WHERE id NOT IN(:surveyIds)")
    suspend fun deleteOld(surveyIds: List<String>)

}

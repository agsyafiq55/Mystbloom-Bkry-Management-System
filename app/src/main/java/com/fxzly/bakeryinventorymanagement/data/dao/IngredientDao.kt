package com.fxzly.bakeryinventorymanagement.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fxzly.bakeryinventorymanagement.data.datamodel.Ingredient
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(ingredient: Ingredient)

    @Update
    suspend fun updateIngredient(ingredient: Ingredient)

    @Delete
    suspend fun deleteIngredient(ingredient: Ingredient)

    @Query("SELECT * FROM ingredients WHERE ingredient_id = :ingredientId")
    fun getIngredientById(ingredientId: Int): Flow<Ingredient>

    @Query("SELECT * FROM ingredients")
    fun getAllIngredients(): Flow<List<Ingredient>>

    @Query("SELECT * FROM ingredients WHERE ingredient_name LIKE '%' || :query || '%'")
    fun searchIngredientsByName(query: String): Flow<List<Ingredient>>
}

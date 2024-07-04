package com.fxzly.bakeryinventorymanagement.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fxzly.bakeryinventorymanagement.data.dao.IngredientDao
import com.fxzly.bakeryinventorymanagement.data.dao.ProductDao
import com.fxzly.bakeryinventorymanagement.data.dao.RecipeDao
import com.fxzly.bakeryinventorymanagement.data.dao.SupplierDao
import com.fxzly.bakeryinventorymanagement.data.dao.UserDao
import com.fxzly.bakeryinventorymanagement.data.datamodel.Ingredient
import com.fxzly.bakeryinventorymanagement.data.datamodel.Product
import com.fxzly.bakeryinventorymanagement.data.datamodel.Recipe
import com.fxzly.bakeryinventorymanagement.data.datamodel.Supplier
import com.fxzly.bakeryinventorymanagement.data.datamodel.User

@Database(
    entities = [
        User::class,
        Product::class,
        Ingredient::class,
        Recipe::class,
        Supplier::class
    ],
    version = 2,
    exportSchema = false
)
abstract class BakeryDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun recipeDao(): RecipeDao
    abstract fun supplierDao(): SupplierDao

    companion object {
        @Volatile
        private var INSTANCE: BakeryDatabase? = null

        fun getDatabase(context: Context): BakeryDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BakeryDatabase::class.java,
                "bakery_database"
            )
                .addMigrations(MIGRATION_1_2)
                .build()

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE users ADD COLUMN user_img TEXT NOT NULL DEFAULT ''")
            }
        }
    }
}
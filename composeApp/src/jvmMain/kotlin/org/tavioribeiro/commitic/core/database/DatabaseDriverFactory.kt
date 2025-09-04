package org.tavioribeiro.commitic.core.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.tavioribeiro.commitic.db.CommiticDatabase
import java.io.File

class DatabaseDriverFactory {
    fun createDriver(): SqlDriver {

        val databasePath = File(System.getProperty("user.home"), ".commitic/committic.db")

        if (!databasePath.parentFile.exists()) {
            databasePath.parentFile.mkdirs()
        }

        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:${databasePath.absolutePath}")

        CommiticDatabase.Schema.create(driver)

        return driver
    }
}
package org.tavioribeiro.commitic.core.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.tavioribeiro.commitic.db.CommiticDatabase
import java.io.File
import java.util.Properties

class DatabaseDriverFactory {
    fun createDriver(): SqlDriver {

        /*val userHome = System.getProperty("user.home")
        val dbFile = File(userHome, ".commitic.database.db")
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:${dbFile.absolutePath}", Properties(), CommiticDatabase.Schema)
        */

        val dataDir = getAppDataDirectory("Commitic")
        val dbFile = File(dataDir, "CommiticDatabase.db")
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:${dbFile.absolutePath}")

        if (!dbFile.exists()) {
            CommiticDatabase.Schema.create(driver)
        }


        //val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:test.db", Properties(), CommiticDatabase.Schema)

        return driver
    }


    private fun getAppDataDirectory(appName: String): File {
        val os = System.getProperty("os.name").uppercase()
        val baseDir: File = when {
            os.contains("WIN") -> {
                val appData = System.getenv("APPDATA")
                File(appData)
            }
            os.contains("MAC") -> {
                val userHome = System.getProperty("user.home")
                File(userHome, "Library/Application Support")
            }
            os.contains("NIX") || os.contains("NUX") || os.contains("AIX") -> {
                val userHome = System.getProperty("user.home")
                File(userHome, ".local/share")
            }
            else -> {
                val userHome = System.getProperty("user.home")
                File(userHome)
            }
        }
        return File(baseDir, appName).apply { mkdirs() }
    }

}
package org.tavioribeiro.commitic.core.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.tavioribeiro.commitic.db.CommiticDatabase
import java.util.Properties

class DatabaseDriverFactory {
    fun createDriver(): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:test.db", Properties(), CommiticDatabase.Schema)

        return driver
    }
}
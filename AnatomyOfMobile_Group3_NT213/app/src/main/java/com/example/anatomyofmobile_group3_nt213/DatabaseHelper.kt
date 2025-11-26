import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "User.db"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "users"
        private const val COL_ID = "id"
        private const val COL_USERNAME = "username"
        private const val COL_PASSWORD = "password"
    }

    //Khởi tạo database
    override fun onCreate (db: SQLiteDatabase?) {
        val createTableQuery = ("CREATE TABLE $TABLE_NAME (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_USERNAME TEXT, " +
                "$COL_PASSWORD TEXT)")
        db?.execSQL(createTableQuery)
    }

    //Cập nhật khi thay đổi version DB
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    //Đăng ký
    fun AddUser (username: String, password: String): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put (COL_USERNAME, username)
        values.put (COL_PASSWORD, password)

        val result = db.insert (TABLE_NAME, null, values)
        db.close()
        return result != -1L
    }

    //Đăng nhập
    fun CheckUser (username: String, password: String): Boolean {
        val db = this.readableDatabase
        val columns = arrayOf (COL_ID)

        val selection = "$COL_USERNAME = ? AND $COL_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)

        val cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null)
        val count = cursor.count
        cursor.close()
        db.close()

        return count > 0
    }
}
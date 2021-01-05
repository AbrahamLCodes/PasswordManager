package portafolio.apps.passwordmanager

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBController(context: Context) : SQLiteOpenHelper(context, "passDB", null, 1) {

    // This method will always be called when the App Starts. Here the DB is created
    override fun onCreate(db: SQLiteDatabase?) {
        val createUsuarios: String = "CREATE TABLE USUARIOS(" +
                "NOMBRE TEXT PRIMARY KEY NOT NULL," +
                "CONTRA TEXT NOT NULL)"
        val createContrasenias = "CREATE TABLE CONTRASENIAS(" +
                "ASUNTO PRIMARY KEY NOT NULL," +
                "CUENTA TEXT," +
                "CONTRA TEXT NOT NULL," +
                "NUSUARIO TEXT NOT NULL REFERENCES USUARIOS(NOMBRE)," +
                "LINK TEXT)"
        if (db != null) {
            db.execSQL(createUsuarios)
            db.execSQL(createContrasenias)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS USUARIOS")
            db.execSQL("DROP TABLE IF EXISTS CONTRASENIAS")
        }
        onCreate(db)
    }

    //Method to insert a user. The name and password is passed by value in parameters
    fun insertUser(nombre: String, contrasenia: String) {
        val db = writableDatabase
        if (db != null) {
            db.execSQL("INSERT INTO USUARIOS VALUES (" +
                    "'" + nombre + "','"
                    + contrasenia + "')");
        }
    }

    // Returns a MutableList of Usuario objects in order to get the info from USUARIOS rows in Kotlin
    fun usuariosList(): MutableList<Usuario> {
        val list: MutableList<Usuario> = ArrayList()
        val cursor: Cursor = readableDatabase.rawQuery(
                "SELECT * FROM USUARIOS",
                null
        )

        if (cursor.moveToFirst()) {
            do {
                list.add(Usuario(
                        cursor.getString(0),
                        cursor.getString(1)
                ))
            } while (cursor.moveToNext())
        }
        return list
    }

    // Returns true if a user is found or false if is not found
    fun findUser(name: String, password: String): Boolean {
        val cursor: Cursor = readableDatabase.rawQuery(
                "SELECT * FROM USUARIOS",
                null
        )
        var found = false

        if (cursor.moveToFirst()) {
            do {
                if (name.equals(cursor.getString(0)) && password.equals(cursor.getString(1))) {
                    Log.d("ESTADO DE LA VALIDACION", "CHIDOOO WEEE")
                    found = true
                }
            } while (cursor.moveToNext() && !found)
        }
        return found
    }

    // Inserts into the DB a new row in CONTRASENIAS table. It gets all the values by parameter
    fun insetContrasenia(
            asunto: String, cuenta: String, contra: String, nusuario: String, link: String) {
        writableDatabase?.execSQL("INSERT INTO CONTRASENIAS VALUES (" +
                "'" + asunto + "','"
                + cuenta + "','"
                + contra + "','"
                + nusuario + "','"
                + link + "')")
    }

    // Returns a MutableList of Contrasenia object in order to get the CONTRASENIAS rows in Kotlin
    fun contraseniasList(): MutableList<Contrasenia> {
        val list: MutableList<Contrasenia> = ArrayList()
        val cursor: Cursor = readableDatabase.rawQuery(
                "SELECT * FROM CONTRASENIAS",
                null)
        if (cursor.moveToFirst()) {
            do {
                list.add(Contrasenia(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                ))
            } while (cursor.moveToNext())
        }
        return list
    }

    //Updates a row of any table
    fun update(tableName: String, set: String, where: String, like: String) {
        val db = writableDatabase
        db?.execSQL("UPDATE $tableName SET $set WHERE $where = '$like'")
    }

    //Deletes any row from any table
    fun delete(tableName: String, where: String, like: String) {
        writableDatabase?.execSQL("DELETE FROM $tableName WHERE $where = '$like'")
    }
}
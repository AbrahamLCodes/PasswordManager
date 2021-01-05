package portafolio.apps.passwordmanager

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBController(context: Context) : SQLiteOpenHelper(context, "passDB", null, 1) {

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

    fun insertUser(nombre: String, contrasenia: String) {
        val db = writableDatabase
        if (db != null) {
            db.execSQL("INSERT INTO RECORDATORIOS VALUES (" +
                    "'" + nombre + "','"
                    + contrasenia + "')");
        }
    }

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

    fun insetContrasenia(
            asunto: String, cuenta: String, contra: String, nusuario: String, link: String) {
        writableDatabase?.execSQL("INSERT INTO RECORDATORIOS VALUES (" +
                "'" + asunto + "','"
                + cuenta + "','"
                + contra + "','"
                + nusuario + "','"
                + link + "')")
    }

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

    fun update(tableName: String, set: String, where: String, like: String) {
        val db = writableDatabase
        db?.execSQL("UPDATE $tableName SET $set WHERE $where = '$like'")
    }

    fun delete(tableName: String, where: String, like: String) {
        writableDatabase?.execSQL("DELETE FROM $tableName WHERE $where = '$like'")
    }
}
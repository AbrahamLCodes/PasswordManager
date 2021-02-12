package portafolio.apps.passwordmanager.database

import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import portafolio.apps.passwordmanager.datamodel.*

class DBController(context: Context) : SQLiteOpenHelper(context, "passDB", null, 1) {

    // This method will always be called when the App Starts. Here the DB is created
    override fun onCreate(db: SQLiteDatabase?) {

        if (db != null) {
            db.execSQL(CreateDB.createUsuarios())
            db.execSQL(CreateDB.createCorreos())
            db.execSQL(CreateDB.createCuentas())
            db.execSQL(CreateDB.createContrasenias())
            db.execSQL(CreateDB.createNotas())
            db.execSQL(CreateDB.createTarjetas())
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS USUARIOS")
            db.execSQL("DROP TABLE IF EXISTS CORREOS")
            db.execSQL("DROP TABLE IF EXISTS CUENTAS")
            db.execSQL("DROP TABLE IF EXISTS CONTRASENIAS")
            db.execSQL("DROP TABLE IF EXISTS NOTAS")
            db.execSQL("DROP TABLE IF EXISTS TARJETAS")
            onCreate(db)
        }
    }

    //Method to insert a user. The name and password is passed by value in parameters
    fun insertUsuario(nombre: String, contrasenia: String) {
        val db = writableDatabase
        if (db != null) {
            db.execSQL(
                "INSERT INTO USUARIOS VALUES (" +
                        "'" + nombre + "','"
                        + contrasenia + "')"
            )
        }
        db.close()
    }

    //Method to insert a user. The name and password is passed by value in parameters
    fun insertCorreo(
        nomusuario: String,
        asunto: String,
        correo: String,
        contrasenia: String,
        fecha: String
    ) {
        val db = writableDatabase
        if (db != null) {
            db.execSQL(
                "INSERT INTO CORREOS VALUES (" +
                        "'" + nomusuario + "'," +
                        "'" + asunto + "'," +
                        "'" + correo + "'," +
                        "'" + contrasenia + "'," +
                        "'" + fecha + "')"
            )
        }
        db.close()
    }

    //Method to insert a user. The name and password is passed by value in parameters
    fun insertCuenta(
        nomusuario: String, correo: String, website: String, contrasenia: String, categoria: String,
        nickname: String, fecha: String
    ) {
        val db = writableDatabase
        if (db != null) {
            db.execSQL(
                "INSERT INTO CUENTAS VALUES (" +
                        "'" + nomusuario + "'," +
                        "'" + correo + "'," +
                        "'" + website + "'," +
                        "'" + contrasenia + "'," +
                        "'" + categoria + "'," +
                        "'" + nickname + "'," +
                        "'" + fecha + "')"
            )
        }
        db.close()
    }

    fun insertContrasenia(nomusuario: String, asunto: String, contrasenia: String, fecha: String) {
        val db = writableDatabase
        if (db != null) {
            db.execSQL(
                "INSERT INTO CONTRASENIAS VALUES (" +
                        "'" + nomusuario + "'," +
                        "'" + asunto + "'," +
                        "'" + contrasenia + "'," +
                        "'" + fecha + "')"
            )
        }
    }

    fun insertNota(nomusuario: String, asunto: String, nota: String, fecha: String) {
        val db = writableDatabase
        if (db != null) {
            db.execSQL(
                "INSERT INTO NOTAS VALUES (" +
                        "'" + nomusuario + "'," +
                        "'" + asunto + "'," +
                        "'" + nota + "'," +
                        "'" + fecha + "')"
            )
        }
        db.close()
    }

    fun insertTarjeta(
        nomusuario: String,
        asunto: String,
        titular: String,
        ntarjeta: String,
        cadm: String,
        cady: String,
        codseg: String,
        banco: String,
        nip: String,
        fecha: String
    ) {
        val db = writableDatabase
        db?.execSQL(
            "INSERT INTO TARJETAS VALUES (" +
                    "'" + nomusuario + "'," +
                    "'" + asunto + "'," +
                    "'" + titular + "'," +
                    "'" + ntarjeta + "'," +
                    "'" + cadm + "'," +
                    "'" + cady + "'," +
                    "'" + codseg + "'," +
                    "'" + banco + "'," +
                    "'" + nip + "'," +
                    "'" + fecha + "')"
        )
        db.close()
    }

    fun customCorreoSelect(where: String, like: String): MutableList<Correo> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM CORREOS WHERE $where = '$like'", null)
        val list = mutableListOf<Correo>()
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    Correo(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                    )
                )
            } while (cursor.moveToNext())
        }
        db.close()
        return list
    }

    fun customMainCuentaSelect(where: String, like: String): MutableList<Cuenta> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM CUENTAS WHERE $where = '$like'", null)
        val list = mutableListOf<Cuenta>()
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    Cuenta(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                    )
                )
            } while (cursor.moveToNext())
        }
        db.close()
        return list
    }

    fun customContraseniaSelect(where: String, like: String): MutableList<Contrasenia> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM CONTRASENIAS WHERE $where = '$like'", null)
        val list = mutableListOf<Contrasenia>()
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    Contrasenia(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                    )
                )
            } while (cursor.moveToNext())
        }
        db.close()
        return list
    }

    fun customNotaSelect(where: String, like: String): MutableList<Nota> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM NOTAS WHERE $where = '$like'", null)
        val list = mutableListOf<Nota>()
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    Nota(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                    )
                )
            } while (cursor.moveToNext())
        }
        db.close()
        return list
    }

    fun customTarjetaSelect(where: String, like: String): MutableList<Tarjeta> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM TARJETAS WHERE $where = '$like'", null)
        val list = mutableListOf<Tarjeta>()
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    Tarjeta(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                    )
                )
            } while (cursor.moveToNext())
        }
        db.close()
        return list
    }

    // Returns true if a user is found or false if is not found
    fun findUsuario(name: String, password: String): Boolean {
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

    fun updateCorreo(
        nomusuario: String,
        asunto: String,
        correoNew: String,
        correoOld: String,
        contrasenia: String
    ) {
        val db = writableDatabase
        db?.execSQL("UPDATE CORREOS SET ASUNTO = '$asunto', CORREO = '$correoNew', CONTRASENIA = '$contrasenia' WHERE (NOMUSUARIO = '$nomusuario' AND CORREO = '$correoOld')")
        db.close()
    }

    fun updateCuenta(
        nomusuario: String,
        correoOld: String,
        correoNew: String,
        websiteOld: String,
        websiteNew: String,
        contrasenia: String,
        categoria: String,
        nickname: String
    ) {
        val db = writableDatabase
        db?.execSQL("UPDATE CUENTAS SET CORREO = '$correoNew', CUENTA = '$websiteNew', CONTRASENIA = '$contrasenia', CATEGORIA = '$categoria', NICKNAME = '$nickname' WHERE (NOMUSUARIO = '$nomusuario' AND CORREO = '$correoOld' AND CUENTA = '$websiteOld')")
        db.close()
    }

    fun updateContrasenia(
        nomusuario: String,
        asuntoOld: String,
        asuntoNew: String,
        contrasenia: String
    ) {
        val db = writableDatabase
        db?.execSQL("UPDATE CONTRASENIAS SET ASUNTO = '$asuntoNew', CONTRASENIA = '$contrasenia' WHERE (NOMUSUARIO = '$nomusuario' AND ASUNTO = '$asuntoOld')")
        db.close()
    }

    fun updateNota(nomusuario: String, tituloOld: String, tituloNew: String, body: String) {
        val db = writableDatabase
        db?.execSQL("UPDATE NOTAS SET ASUNTO = '$tituloNew', NOTA = '$body' WHERE (NOMUSUARIO = '$nomusuario' AND ASUNTO = '$tituloOld')")
        db.close()
    }

    fun updateTarjeta(
        nomusuario: String,
        asuntoOld: String,
        asuntoNew: String,
        titular: String,
        ntarjeta: String,
        cadm: String,
        cady: String,
        codseg: String,
        banco: String,
        nip: String
    ) {
        val db = writableDatabase
        db?.execSQL("UPDATE TARJETAS SET ASUNTO = '$asuntoNew', TITULAR = '$titular', NTARJETA = '$ntarjeta', NTARJETA = '$ntarjeta', CADM = '$cadm', CADY = '$cady', CODSEG = '$codseg', BANCO = '$banco', NIP = '$nip' WHERE (NOMUSUARIO = '$nomusuario' AND ASUNTO = '$asuntoOld')")
        db.close()
    }

    fun deleteCorreo(correo: String, nomusuario: String) {
        val db = writableDatabase
        db?.execSQL("DELETE FROM CORREOS WHERE (CORREO = '$correo' AND NOMUSUARIO = '$nomusuario')")
        deleteCuentaFromCorreo(nomusuario, correo)
        db.close()
    }

    private fun deleteCuentaFromCorreo(nomusuario: String, correo: String) {
        val db = writableDatabase
        db?.execSQL("DELETE FROM CUENTAS WHERE (CORREO = '$correo' AND NOMUSUARIO = '$nomusuario')")
        db.close()
    }

    fun deleteCuenta(nomusuario: String, correo: String, cuenta: String) {
        val db = writableDatabase
        db?.execSQL("DELETE FROM CUENTAS WHERE (NOMUSUARIO = '$nomusuario' AND CORREO = '$correo' AND CUENTA = '$cuenta')")
        db.close()
    }

    fun deleteContrasenia(nomusuario: String, asunto: String) {
        val db = writableDatabase
        db?.execSQL("DELETE FROM CONTRASENIAS WHERE (NOMUSUARIO = '$nomusuario' AND ASUNTO = '$asunto')")
        db.close()
    }

    fun deleteNotas(nomusuario: String, asunto: String) {
        val db = writableDatabase
        db?.execSQL("DELETE FROM NOTAS WHERE (NOMUSUARIO = '$nomusuario' AND ASUNTO = '$asunto')")
        db.close()
    }

    fun deleteTarjetas(nomusuario: String, asunto: String) {
        val db = writableDatabase
        db?.execSQL("DELETE FROM TARJETAS WHERE (NOMUSUARIO = '$nomusuario' AND ASUNTO = '$asunto')")
        db.close()
    }

    fun sortCorreos(nomusuario: String, column: String, asc: String): MutableList<Correo> {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM CORREOS WHERE NOMUSUARIO = '$nomusuario' ORDER BY $column $asc",
            null
        )
        val list = mutableListOf<Correo>()
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    Correo(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                    )
                )
            } while (cursor.moveToNext())
        }
        db.close()
        return list
    }

    fun sortCuentas(nomusuario: String, column: String, asc: String): MutableList<Cuenta> {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM CUENTAS WHERE NOMUSUARIO = '$nomusuario' ORDER BY $column $asc",
            null
        )
        val list = mutableListOf<Cuenta>()
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    Cuenta(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                    )
                )
            } while (cursor.moveToNext())
        }
        db.close()
        return list
    }

    fun sortContrasenias(
        nomusuario: String,
        column: String,
        asc: String
    ): MutableList<Contrasenia> {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM CONTRASENIAS WHERE NOMUSUARIO = '$nomusuario' ORDER BY $column $asc",
            null
        )
        val list = mutableListOf<Contrasenia>()
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    Contrasenia(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                    )
                )
            } while (cursor.moveToNext())
        }
        db.close()
        return list
    }

    fun sortNotas(nomusuario: String, column: String, asc: String): MutableList<Nota> {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM NOTAS WHERE NOMUSUARIO = '$nomusuario' ORDER BY $column $asc",
            null
        )
        val list = mutableListOf<Nota>()
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    Nota(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                    )
                )
            } while (cursor.moveToNext())
        }
        db.close()
        return list
    }

    fun sortTarjetas(nomusuario: String, column: String, asc: String): MutableList<Tarjeta> {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM TARJETAS WHERE NOMUSUARIO = '$nomusuario' ORDER BY $column $asc",
            null
        )
        val list = mutableListOf<Tarjeta>()
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    Tarjeta(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                    )
                )
            } while (cursor.moveToNext())
        }
        db.close()
        return list
    }

    fun correoContains(subString: String){

    }

    fun getRowCount(tableName: String): Long {
        val db = readableDatabase
        val count = DatabaseUtils.queryNumEntries(db, tableName)
        db.close()
        return count
    }

    fun getColumnCount(tableName: String): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM " + tableName, null)

        var i = 0
        while (i < cursor.columnCount) {
            Log.d("COLUMN NAME: ", cursor.getColumnName(i))
            i++
        }

        return cursor.columnCount
    }
}
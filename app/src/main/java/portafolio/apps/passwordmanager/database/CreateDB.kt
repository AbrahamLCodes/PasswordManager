package portafolio.apps.passwordmanager.database

class CreateDB {

    companion object {
        fun createUsuarios(): String {
            return "CREATE TABLE USUARIOS(" +
                    "NOMBRE TEXT NOT NULL PRIMARY KEY," +
                    "CONTRASENIA TEXT NOT NULL)"
        }

        fun createCorreos(): String {
            return "CREATE TABLE CORREOS(" +
                    "NOMUSUARIO TEXT NOT NULL REFERENCES USUARIOS(NOMBRE)," +
                    "ASUNTO TEXT NOT NULL," +
                    "CORREO TEXT PRIMARY KEY NOT NULL," +
                    "CONTRASENIA TEXT NOT NULL," +
                    "FECHA TEXT NOT NULL" +
                    ")"
        }

        fun createCuentas(): String {
            return "CREATE TABLE CUENTAS (" +
                    "CORREO TEXT NOT NULL REFERENCES CORREOS(CORREO)," +
                    "CUENTA TEXT NOT NULL," +
                    "CONTRASENIA TEXT NOT NULL," +
                    "NICKNAME TEXT NOT NULL," +
                    "FECHA TEXT NOT NULL," +
                    "PRIMARY KEY(CORREO, CUENTA)" +
                    ")"
        }

        fun createContrasenias(): String {
            return "CREATE TABLE CONTRASENIAS(" +
                    "NOMUSUARIO TEXT NOT NULL REFERENCES USUARIOS(NOMBRE)," +
                    "ASUNTO TEXT NOT NULL," +
                    "CONTRASENIA TEXT NOT NULL," +
                    "FECHA TEXT NOT NULL" +
                    ")"
        }

        fun createNotas(): String {
            return "CREATE TABLE NOTAS(" +
                    "NOMUSUARIO TEXT NOT NULL REFERENCES USUARIOS(USUARIO)," +
                    "ASUNTO TEXT NOT NULL," +
                    "NOTA TEXT NOT NULL," +
                    "FECHA TEXT NOT NULL" +
                    ")"
        }

        fun createTarjetas(): String {
            return "CREATE TABLE TARJETAS(" +
                    "NOMUSUARIO TEXT NOT NULL REFERENCES USUARIOS(NOMBRE)," +
                    "ASUNTO TEXT NOT NULL PRIMARY KEY," +
                    "NTARJETA TEXT NOT NULL," +
                    "CADUCIDAD TEXT NOT NULL," +
                    "CODSEG TEXT NOT NULL," +
                    "NIP TEXT NOT NULL," +
                    "FECHA TEXT NOT NULL" +
                    ")"
        }
    }
}
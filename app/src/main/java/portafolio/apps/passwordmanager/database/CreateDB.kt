package portafolio.apps.passwordmanager.database

class CreateDB {

    companion object {
        fun createUsuarios(): String {
            return "CREATE TABLE USUARIOS(" +
                    "NOMBRE TEXT NOT NULL PRIMARY KEY," +
                    "CONTRASENIA TEXT NOT NULL," +
                    "CORREO TEXT NOT NULL)"
        }

        fun createCorreos(): String {
            return "CREATE TABLE CORREOS(" +
                    "NOMUSUARIO TEXT NOT NULL REFERENCES USUARIOS(NOMBRE)," +
                    "ASUNTO TEXT NOT NULL," +
                    "CORREO TEXT NOT NULL," +
                    "CONTRASENIA TEXT NOT NULL," +
                    "FECHA TEXT NOT NULL," +
                    "PRIMARY KEY (NOMUSUARIO, CORREO)" +
                    ")"
        }

        fun createCuentas(): String {
            return "CREATE TABLE CUENTAS (" +
                    "NOMUSUARIO TEXT NOT NULL REFERENCES USUARIOS(NOMBRE)," +
                    "CORREO TEXT NOT NULL REFERENCES CORREOS(CORREO)," +
                    "CUENTA TEXT NOT NULL," +
                    "CONTRASENIA TEXT NOT NULL," +//website
                    "CATEGORIA TEXT NOT NULL," +
                    "NICKNAME TEXT NOT NULL," +
                    "FECHA TEXT NOT NULL," +
                    "PRIMARY KEY(NOMUSUARIO, CORREO, CUENTA)" +
                    ")"
        }

        fun createContrasenias(): String {
            return "CREATE TABLE CONTRASENIAS(" +
                    "NOMUSUARIO TEXT NOT NULL REFERENCES USUARIOS(NOMBRE)," +
                    "ASUNTO TEXT NOT NULL," +
                    "CONTRASENIA TEXT NOT NULL," +
                    "FECHA TEXT NOT NULL," +
                    "PRIMARY KEY (NOMUSUARIO, ASUNTO)" +
                    ")"
        }

        fun createNotas(): String {
            return "CREATE TABLE NOTAS(" +
                    "NOMUSUARIO TEXT NOT NULL REFERENCES USUARIOS(USUARIO)," +
                    "ASUNTO TEXT NOT NULL," +
                    "NOTA TEXT NOT NULL," +
                    "FECHA TEXT NOT NULL," +
                    "PRIMARY KEY (NOMUSUARIO, ASUNTO)" +
                    ")"
        }

        fun createTarjetas(): String {
            return "CREATE TABLE TARJETAS(" +
                    "NOMUSUARIO TEXT NOT NULL REFERENCES USUARIOS(NOMBRE)," +
                    "ASUNTO TEXT NOT NULL," +
                    "TITULAR TEXT NOT NULL ," +
                    "NTARJETA TEXT NOT NULL," +
                    "CADM TEXT NOT NULL," +
                    "CADY TEXT NOT NULL," +
                    "CODSEG TEXT NOT NULL," +
                    "BANCO TEXT NOT NULL," +
                    "NIP TEXT NOT NULL," +
                    "FECHA TEXT NOT NULL," +
                    "PRIMARY KEY (NOMUSUARIO, ASUNTO)" +
                    ")"
        }
    }
}
package com.example.iceenberg.Database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.iceenberg.Objects.Equipments
import com.example.iceenberg.Objects.Installation
import com.example.iceenberg.Objects.Location
import com.example.iceenberg.Objects.Maintenance
import com.example.iceenberg.Objects.Revision
import com.example.iceenberg.Objects.User
import com.example.iceenberg.R
import kotlin.coroutines.coroutineContext

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "iceEnberg.db"
        private const val DATABASE_VERSION = 1

        //Tabla de usuarios
        private const val TABLE_USERS = "users"
        private const val ID_USER = "id_users"
        private const val NAME_USER = "name"
        private const val LAST_USER = "last"
        private const val EMAIL_USER = "email"
        private const val PASSWORD_USER = "password"
        private const val PHONE_USER = "phone"
        private const val TYPE_USER = "type"

        //Admin

        //Tabla de mantenimiento
        private const val TABLE_MAINTENANCE = "maintenance"
        private const val ID_MAINTENANCE = "id_maintenance"
        private const val NAME_MAINTENANCE = "name"
        private const val PRICE_MAINTENANCE = "price"

        //Tabla de instalación
        private const val TABLE_INSTALLATION = "installation"
        private const val ID_INSTALLATION = "id_installation"
        private const val NAME_INSTALLATION = "name"
        private const val PRICE_INSTALLATION = "price"

        //Tabla de revisión
        private const val TABLE_REVISION = "revision"
        private const val ID_REVISION = "id_revision"
        private const val NAME_REVISION = "name"
        private const val PRICE_REVISION = "price"

        //Tabla de localizaciones
        private const val TABLE_LOCATIONS = "locations"
        private const val ID_LOCATIONS = "id_locations"
        private const val NAME_LOCATIONS = "name"
        private const val PERCENTAGE_LOCATIONS = "percentage"

        //Usuario

        //Tabla de equipos
        private const val TABLE_EQUIPMENT = "equipment"
        private const val ID_EQUIPMENT = "id_equipment"
        private const val USER_EQUIPMENT = "id_user"
        private const val NAME_EQUIPMENT = "name"
        private const val LOCATION_EQUIPMENT = "location"
        private const val DIRECTION_EQUIPMENT = "direction"
        private const val BRAND_EQUIPMENT = "brand"
        private const val MODEL_EQUIPMENT = "model"

        //Tabla de servicios
        private const val TABLE_SERVICES = "services"
        private const val ID_SERVICES = "id_services"
        private const val USER_SERVICES = "id_users"
        private const val EQUIPMENT_SERVICES = "id_equipment"
        private const val TYPE_SERVICES = "type"
        private const val PRICE_SERVICES = "price"
        private const val FINISHED_SERVICES = "finish"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val TABLE_USERS = ("CREATE TABLE " + TABLE_USERS + "(" +
                ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NAME_USER + " TEXT," +
                LAST_USER + " TEXT," +
                EMAIL_USER + " TEXT," +
                PASSWORD_USER + " TEXT," +
                PHONE_USER + " TEXT," +
                TYPE_USER + " INTEGER"
                +")")

        val TABLE_MAINTENANCE = ("CREATE TABLE " + TABLE_MAINTENANCE + "(" +
                ID_MAINTENANCE + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NAME_MAINTENANCE + " TEXT," +
                PRICE_MAINTENANCE + " REAL"
                +")")

        val TABLE_INSTALLATION = ("CREATE TABLE " + TABLE_INSTALLATION + "(" +
                ID_INSTALLATION + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NAME_INSTALLATION + " TEXT," +
                PRICE_INSTALLATION + " REAL"
                +")")

        val TABLE_REVISION = ("CREATE TABLE " + TABLE_REVISION + "(" +
                ID_REVISION + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NAME_REVISION + " TEXT," +
                PRICE_REVISION + " REAL"
                +")")

        val TABLE_LOCATIONS = ("CREATE TABLE " + TABLE_LOCATIONS + "(" +
                ID_LOCATIONS + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NAME_LOCATIONS + " TEXT," +
                PERCENTAGE_LOCATIONS + " REAL" +
                ")")

        val TABLE_EQUIPMENT = ("CREATE TABLE " + TABLE_EQUIPMENT + "(" +
                ID_EQUIPMENT + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USER_EQUIPMENT + " INTEGER," +
                NAME_EQUIPMENT + " TEXT," +
                LOCATION_EQUIPMENT + " TEXT," +
                DIRECTION_EQUIPMENT + " TEXT," +
                BRAND_EQUIPMENT + " TEXT," +
                MODEL_EQUIPMENT + " TEXT" +
                ")")

        val TABLE_SERVICES = ("CREATE TABLE " + TABLE_SERVICES + "(" +
                ID_SERVICES + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USER_SERVICES + " TEXT," +
                EQUIPMENT_SERVICES + " INTEGER," +
                TYPE_SERVICES + " TEXT," +
                PRICE_SERVICES + " REAL," +
                FINISHED_SERVICES + " INTEGER" +
                ")")

        db?.execSQL(TABLE_USERS)
        db?.execSQL(TABLE_MAINTENANCE)
        db?.execSQL(TABLE_INSTALLATION)
        db?.execSQL(TABLE_REVISION)
        db?.execSQL(TABLE_LOCATIONS)
        db?.execSQL(TABLE_EQUIPMENT)
        db?.execSQL(TABLE_SERVICES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_MAINTENANCE")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_INSTALLATION")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_REVISION")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_LOCATIONS")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_EQUIPMENT")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_SERVICES")
        onCreate(db)
    }

    //Functions

    //Usuarios
    //Traer los mantenimientos

    @SuppressLint("Range")
    fun getUserByEmail(email: String): User? {
        val db = this.readableDatabase
        var user: User? = null
        val selectQuery = "SELECT * FROM $TABLE_USERS WHERE $EMAIL_USER = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(email))
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(ID_USER))
            val name = cursor.getString(cursor.getColumnIndex(NAME_USER))
            val lastName = cursor.getString(cursor.getColumnIndex(LAST_USER))
            val email = cursor.getString(cursor.getColumnIndex(EMAIL_USER))
            val password = cursor.getString(cursor.getColumnIndex(PASSWORD_USER))
            val phone = cursor.getString(cursor.getColumnIndex(PHONE_USER))
            val type = cursor.getInt(cursor.getColumnIndex(TYPE_USER))

            user = User(id, name, lastName, email, password, phone, type)
        }
        cursor.close()
        db.close()
        return user
    }

    // Insertar usuario
    fun insertUser(name: String, last: String, email: String, password: String, phone: String, type: Int) :Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME_USER,name)
        values.put(LAST_USER,last)
        values.put(EMAIL_USER,email)
        values.put(PASSWORD_USER,password)
        values.put(PHONE_USER,phone)
        values.put(TYPE_USER,type)

        val result = db.insert(TABLE_USERS, null, values)


        //Regresar si fue exitosa o no la inserción
        return result != -1L
    }

    //Actualizar usuario
    fun updateUser(id: Int, newName: String, newLast: String, newEmail: String, newPassword: String, newPhone: String) : Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME_USER, newName)
        contentValues.put(LAST_USER, newLast)
        contentValues.put(EMAIL_USER, newEmail)
        contentValues.put(PASSWORD_USER, newPassword)
        contentValues.put(PHONE_USER, newPhone)

        val result = db.update(TABLE_USERS, contentValues, "$ID_USER = ?", arrayOf(id.toString()));

        return result > -1L
    }

    //eliminar usuario
    fun deleteUser(id: Int) : Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_USER, id)
        val result = db.delete(TABLE_USERS, ID_USER + "=" + id, null)

        return result != -1
    }

    //Mantenimientos

    //Insertar mantenimiento
    fun insertMaintenance(name: String, cost: Double) :Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME_MAINTENANCE,name)
        values.put(PRICE_MAINTENANCE,cost)
        val result = db.insert(TABLE_MAINTENANCE, null, values)


        //Regresar si fue exitosa o no la inserción
        return result != -1L
    }

    //Traer los mantenimientos

    @SuppressLint("Range")
    fun getMaintenances(): MutableList<Maintenance>{
        val maintenances = mutableListOf<Maintenance>()
        val db = this.readableDatabase

        val query = "SELECT * FROM $TABLE_MAINTENANCE"
        val cursor: Cursor = db.rawQuery(query,null)

        if (cursor.moveToFirst()){
            do {
                val id = cursor.getInt(cursor.getColumnIndex(ID_MAINTENANCE))
                val nombre = cursor.getString(cursor.getColumnIndex(NAME_MAINTENANCE))
                val costo = cursor.getInt(cursor.getColumnIndex(PRICE_MAINTENANCE))

                maintenances.add(Maintenance(id,nombre,costo.toDouble()))
            }while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return maintenances
    }

    //Actualizar mantenimiento
    fun updateMaintenance(id: Int, newName: String, newCost: Double) : Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME_MAINTENANCE, newName)
        contentValues.put(PRICE_MAINTENANCE, newCost)
        val result = db.update(TABLE_MAINTENANCE, contentValues, "$ID_MAINTENANCE = ?", arrayOf(id.toString()));

        return result > -1L
    }

    //eliminar mantenimiento
    fun deleteMaintenance(id: Int) : Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_MAINTENANCE, id)
        val result = db.delete(TABLE_MAINTENANCE, ID_MAINTENANCE + "=" + id, null)

        return result != -1
    }

    //Instalaciones

    // Insertar instalación
    fun insertInstallation(name: String, cost: Double) :Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME_INSTALLATION,name)
        values.put(PRICE_INSTALLATION,cost)
        val result = db.insert(TABLE_INSTALLATION, null, values)


        //Regresar si fue exitosa o no la inserción
        return result != -1L
    }

    //Actualizar instalacion
    fun updateInstallation(id: Int, newName: String, newCost: Double) : Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME_INSTALLATION, newName)
        contentValues.put(PRICE_INSTALLATION, newCost)
        val result = db.update(TABLE_INSTALLATION, contentValues, "$ID_INSTALLATION = ?", arrayOf(id.toString()));

        return result > -1L
    }

    //eliminar instalacion
    fun deleteInstallation(id: Int) : Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_INSTALLATION, id)
        val result = db.delete(TABLE_INSTALLATION, ID_INSTALLATION + "=" + id, null)

        //Regresar si fue exitosa o no la inserción
        return result != -1
    }

    //traer las instalaciones
    @SuppressLint("Range")
    fun getInstallation() : MutableList<Installation> {
        val installations = mutableListOf<Installation>()
        val db = this.readableDatabase

        val query = "SELECT * FROM $TABLE_INSTALLATION"
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(ID_INSTALLATION))
                val nombre = cursor.getString(cursor.getColumnIndex(NAME_INSTALLATION))
                val costo = cursor.getInt(cursor.getColumnIndex(PRICE_INSTALLATION))

                installations.add(Installation(id, nombre, costo.toDouble()))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return installations
    }

    //Localizaciones
    fun insertLocations(name: String, percentage: Int) : Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME_LOCATIONS, name)
        values.put(PERCENTAGE_LOCATIONS, percentage)
        val result = db.insert(TABLE_LOCATIONS, null, values)

        return result != -1L
    }

    @SuppressLint("Range")
    fun getLocations() : MutableList<Location> {
        val locations = mutableListOf<Location>()
        val db = this.readableDatabase

        val query = "SELECT * FROM $TABLE_LOCATIONS"
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(ID_LOCATIONS))
                val nombre = cursor.getString(cursor.getColumnIndex(NAME_LOCATIONS))
                val porcentaje = cursor.getInt(cursor.getColumnIndex(PERCENTAGE_LOCATIONS))

                locations.add(Location(id, nombre, porcentaje))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return locations
    }

    //Actualizar localizaciones
    fun updateLocation(id: Int, newName: String, newPercentage: Int) : Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME_LOCATIONS, newName)
        contentValues.put(PERCENTAGE_LOCATIONS, newPercentage)
        val result = db.update(TABLE_LOCATIONS, contentValues, "$ID_LOCATIONS = ?", arrayOf(id.toString()));

        return result > -1L
    }

    //eliminar localizaciones
    fun deleteLocation(id: Int) : Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_LOCATIONS, id)
        val result = db.delete(TABLE_LOCATIONS, ID_LOCATIONS + "=" + id, null)

        //Regresar si fue exitosa o no la inserción
        return result != -1
    }

    //Revisiones

    //insertar revisiones
    fun insertRevisions(name: String, cost: Double) : Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME_REVISION, name)
        values.put(PRICE_REVISION, cost)
        val result = db.insert(TABLE_REVISION, null, values)

        return result != -1L
    }

    //Traer las revisiones
    @SuppressLint("Range")
    fun getRevisions(): MutableList<Revision>{
        val revisions = mutableListOf<Revision>()
        val db = this.readableDatabase

        val query = "SELECT * FROM $TABLE_REVISION"
        val cursor: Cursor = db.rawQuery(query,null)

        if (cursor.moveToFirst()){
            do {
                val id = cursor.getInt(cursor.getColumnIndex(ID_REVISION))
                val nombre = cursor.getString(cursor.getColumnIndex(NAME_REVISION))
                val costo = cursor.getInt(cursor.getColumnIndex(PRICE_REVISION))

                revisions.add(Revision(id,nombre,costo.toDouble()))
            }while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return revisions
    }

    //Actualizar revision
    fun updateRevision(id: Int, newName: String, newCost: Double) : Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME_REVISION, newName)
        contentValues.put(PRICE_REVISION, newCost)
        val result = db.update(TABLE_REVISION, contentValues, "$ID_REVISION = ?", arrayOf(id.toString()));

        return result > -1L
    }

    //Eliminar revision
    fun deleteRevision(id: Int) : Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_REVISION, id)
        val result = db.delete(TABLE_REVISION, ID_REVISION + "=" + id, null)

        return result != -1
    }


    //Equipos

    //Obtener localización nombre
    @SuppressLint("Range")
    fun getNameLocations(context: Context) : MutableList<String> {
        val locations = mutableListOf<String>()
        val db = this.readableDatabase

        locations.add(context.getString(R.string.main_form_equipments_hint_location))

        val query = "SELECT * FROM $TABLE_LOCATIONS"
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(cursor.getColumnIndex(NAME_LOCATIONS))

                locations.add(nombre)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return locations
    }

    //Obtener equipos
    @SuppressLint("Range")
    fun getEquipments() : MutableList<Equipments> {
        val equipments = mutableListOf<Equipments>()
        val db = this.readableDatabase

        val query = "SELECT * FROM $TABLE_EQUIPMENT"
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(ID_EQUIPMENT))
                val user = cursor.getInt(cursor.getColumnIndex(USER_EQUIPMENT))
                val nombre = cursor.getString(cursor.getColumnIndex(NAME_LOCATIONS))
                val localizacion = cursor.getString(cursor.getColumnIndex(LOCATION_EQUIPMENT))
                val direccion = cursor.getString(cursor.getColumnIndex(DIRECTION_EQUIPMENT))
                val marca = cursor.getString(cursor.getColumnIndex(BRAND_EQUIPMENT))
                val modelo = cursor.getString(cursor.getColumnIndex(MODEL_EQUIPMENT))

                equipments.add(Equipments(id, user, nombre, localizacion, direccion, marca, modelo))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return equipments
    }

    //insertar revisiones
    fun insertEquipments(equipments: Equipments) : Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(USER_EQUIPMENT, equipments.user)
        values.put(NAME_EQUIPMENT, equipments.name)
        values.put(BRAND_EQUIPMENT, equipments.brand)
        values.put(MODEL_EQUIPMENT, equipments.model)
        values.put(LOCATION_EQUIPMENT, equipments.location)
        values.put(DIRECTION_EQUIPMENT, equipments.direction)
        val result = db.insert(TABLE_EQUIPMENT, null, values)

        return result != -1L
    }

    //Actualizar
    fun updateEquipment(equipments: Equipments) : Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_EQUIPMENT, equipments.id)
        contentValues.put(USER_EQUIPMENT, equipments.user)
        contentValues.put(NAME_EQUIPMENT, equipments.name)
        contentValues.put(BRAND_EQUIPMENT, equipments.brand)
        contentValues.put(MODEL_EQUIPMENT, equipments.model)
        contentValues.put(LOCATION_EQUIPMENT, equipments.location)
        contentValues.put(DIRECTION_EQUIPMENT, equipments.direction)
        val result = db.update(TABLE_EQUIPMENT, contentValues, "$ID_EQUIPMENT = ?", arrayOf(equipments.id.toString()));

        return result > -1L
    }

    //Eliminar
    fun deleteEquipment(id: Int) : Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID_EQUIPMENT, id)
        val result = db.delete(TABLE_EQUIPMENT, ID_EQUIPMENT + "=" + id, null)

        return result != -1
    }
}


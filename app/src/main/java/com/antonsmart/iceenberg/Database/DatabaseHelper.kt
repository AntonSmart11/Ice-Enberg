package com.antonsmart.iceenberg.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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
        private const val LOCATION_EQUIPMENT = "id_locations"
        private const val BRAND_EQUIPMENT = "brand"
        private const val MODEL_EQUIPMENT = "model"

        //Tabla de servicios
        private const val TABLE_SERVICES = "services"
        private const val ID_SERVICES = "id_services"
        private const val USER_SERVICES = "id_users"
        private const val EQUIPMENT_SERVICES = "id_equipment"
        private const val TYPE_SERVICES = "id_type"
        private const val PRICE_SERVICES = "price"
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
                LOCATION_EQUIPMENT + " INTEGER," +
                BRAND_EQUIPMENT + " TEXT," +
                MODEL_EQUIPMENT + " TEXT" +
                ")")

        val TABLE_SERVICES = ("CREATE TABLE " + TABLE_SERVICES + "(" +
                ID_SERVICES + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USER_SERVICES + " INTEGER," +
                EQUIPMENT_SERVICES + " INTEGER," +
                TYPE_SERVICES + " INTEGER," +
                PRICE_SERVICES + " REAL" +
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

}
package com.example.diego.base_datos;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;

import com.example.diego.base_datos.modelos.Contacto;

import java.util.ArrayList;

public class DBHelper {

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private final Context mCtx;


    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context){
            super(context, "Contactos", null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String tabla_contactos = "CREATE TABLE contactos(_id INTEGER primary key autoincrement," + "nombre TEXT, apellido TEXT," +
                    "cumpleaños TEXT, notas TEXT);";
            String tabla_telefonos = "CREATE TABLE telefonos(_id INTEGER primary key autoincrement," +
                    "telefono TEXT, tipo INT, contacto_id INTEGER);";
            String tabla_correos = "CREATE TABLE correos(_id INTEGER primary key autoincrement," +
                    "telefono TEXT, tipo INT, contacto_id INTEGER);";

            String tabla_direcciones = "CREATE TABLE direcciones(_id INTEGER primary key autoincrement," +
                    "direccion TEXT, tipo INT, contacto_id INTEGER);";

            String tabla_redes = "CREATE TABLE redes(_id INTEGER primary key autoincrement," +
                    "red_social,  tipo INT, contacto_id INTEGER);";

            db.execSQL(tabla_contactos);
            db.execSQL(tabla_telefonos);
            db.execSQL(tabla_correos);
            db.execSQL(tabla_direcciones);
            db.execSQL(tabla_redes);


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public DBHelper(Context mCtx) {
        this.mCtx = mCtx;
    };

    public DBHelper open() throws SQLException{
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;

    }

    public void close(){
        mDbHelper.close();
    }

    public ArrayList<Contacto> getContacts(){
        ArrayList<Contacto> contactList = new ArrayList<Contacto>();

        Cursor mCursor = mDb.query("contactos", new String[]{"id", "nombre","apellido", "cumpleaños", "notas"}, null, null, null, null,"_id desc");

        if(mCursor != null){
            if(mCursor.moveToFirst()){
                do{
                    Contacto data = new Contacto();

                    data.setId((int) mCursor.getLong(mCursor.getColumnIndex("_id")));
                    data.setNombre( mCursor.getString(mCursor.getColumnIndex("nombre")));
                    data.setApellido( mCursor.getString(mCursor.getColumnIndex("apellido")));
                    data.setCumpleaños( mCursor.getString(mCursor.getColumnIndex("cumpleaños")));
                    data.setNotas(mCursor.getString(mCursor.getColumnIndex("notas")));
                    contactList.add(data);

                }
                while(mCursor.moveToNext());
                mCursor.close();
            }
        }

        return contactList;

    }

    public long creatNewContact(Contacto info){
         ContentValues initialValues = new ContentValues();
        initialValues.put("nombre",  info.getNombre());
        initialValues.put("apellido", info.getApellido());
        initialValues.put("cumpleaños", info.getCumpleaños());
        initialValues.put("notas", info.getNotas());

        return mDb.insert("contactos", null, initialValues);



    }


}


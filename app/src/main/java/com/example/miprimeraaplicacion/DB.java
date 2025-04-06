package com.example.miprimeraaplicacion;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "productos";
    private static final int DATABASE_VERSION = 1;
    private static final String SQLdb = "CREATE TABLE productos (idProducto TEXT, codigo TEXT, descripcion TEXT, marca TEXT, presentacion TEXT,precio TEXT, urlFoto TEXT)";
    private static final String SQLdb1 = "CREATE TABLE actualizado (id TEX,actualizado TEXT)";
    private static final String incercion = "INSERT INTO actualizado (id,actualizado) VALUES ('0','0')";
    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLdb);
        db.execSQL(SQLdb1);
        db.execSQL(incercion);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Actualizar la estrucutra de la base de datos si es necesario
    }
    public String administrar_productos(String accion, String[] datos) {
        try{
            SQLiteDatabase db = getWritableDatabase();
            String mensaje = "ok", sql = "";
            switch (accion) {
                case "nuevo":
                    sql = "INSERT INTO productos (idProducto,codigo, descripcion, marca, presentacion, precio, urlFoto) VALUES ('"+ datos[0] +"','"+ datos[1] +"', '" + datos[2] + "', '" + datos[3] + "', '" + datos[4] + "', '" + datos[5] + "', '" + datos[6] + "')";
                    break;
                case "modificar":
                    sql = "UPDATE productos SET codigo = '" + datos[1] + "', descripcion = '" + datos[2] + "', marca = '" + datos[3] + "', presentacion = '" + datos[4] + "', precio = '" + datos[5] + "', urlFoto = '" + datos[6] + "' WHERE idProducto = " + '"'+ datos[0] + '"';
                    break;
                case "eliminar":
                    sql = "DELETE FROM productos WHERE idProducto = " + '"'+ datos[0] + '"';
                    break;
                case "eliminarTodo":
                    sql = "DELETE FROM productos";
                    break;
            }

            
            db.execSQL(sql);
            db.close();
            
            
            return mensaje;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String administrarActualizados(String mod,String datos) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            String mensaje = "ok", sql = "";
            switch (mod) {
                case "modificar":
                    sql = "UPDATE actualizado SET actualizado = '"+datos+"' WHERE id = '0'";
                    break;
            }


            db.execSQL(sql);
            db.close();


            return mensaje;
        } catch (Exception e) {
            return e.getMessage();
        }

    }
    public Cursor lista_productosActializados() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM actualizado", null);
    }

    public Cursor lista_productos() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM productos", null);
    }
}
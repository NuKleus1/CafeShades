package com.example.cafeshades.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.cafeshades.models.Product;

import java.util.ArrayList;
import java.util.HashSet;

public final class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "CafeShades.db";
    public static final String ITEMS_TABLE_NAME = "products";
    public static final String ITEMS_COLUMN_ID = "productId";
    public static final String ITEMS_COLUMN_NAME = "productName";
    public static final String ITEMS_COLUMN_DESCRIPTION = "productDescription";
    public static final String ITEMS_COLUMN_CATEGORY = "categoryId";
    public static final String ITEMS_COLUMN_IMAGE = "productImage";
    public static final String ITEMS_COLUMN_PRICE = "productPrice";
    public static final String ITEMS_COLUMN_FAVOURITE = "productIsFavourite";
    public static final String ITEMS_COLUMN_QUANTITY = "productQuantity";
    private static final String TAG = "DatabaseHelper";

    private static DatabaseHelper dbHelperInstance = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (dbHelperInstance == null) {
            dbHelperInstance = new DatabaseHelper(context);
        }
        return dbHelperInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create Item Table
        String queryItemTable = "CREATE TABLE " + ITEMS_TABLE_NAME + " (" + ITEMS_COLUMN_ID +
                " INTEGER PRIMARY KEY, " + ITEMS_COLUMN_NAME + " TEXT, " + ITEMS_COLUMN_DESCRIPTION +
                " TEXT, " + ITEMS_COLUMN_CATEGORY + " TEXT, " + ITEMS_COLUMN_IMAGE + " BLOB, " +
                ITEMS_COLUMN_PRICE + " INTEGER, " + ITEMS_COLUMN_FAVOURITE + " BOOLEAN, " +
                ITEMS_COLUMN_QUANTITY + " INTENGER)";

//        String queryCartTable = "CREATE TABLE " + CART_TABLE_NAME + " ( " + CART_COLUMN_ID +" INTEGER)";

        sqLiteDatabase.execSQL(queryItemTable);
        Log.d(TAG, "DB Created");
//        sqLiteDatabase.execSQL(queryCartTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    //ITEMS
    public void insertProduct(Product product) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();



            cv.put(ITEMS_COLUMN_ID, product.getProductId());
            cv.put(ITEMS_COLUMN_NAME, product.getProductName());
//            cv.put(ITEMS_COLUMN_IMAGE, getBytes(product.getItemImage()));
            cv.put(ITEMS_COLUMN_PRICE, product.getProductPrice());
            cv.put(ITEMS_COLUMN_CATEGORY, product.getProductCategory());
            cv.put(ITEMS_COLUMN_QUANTITY, product.getProductQuantity());
            cv.put(ITEMS_COLUMN_FAVOURITE, product.isProductFavourite());
            cv.put(ITEMS_COLUMN_DESCRIPTION, "product.getProductDescription");

            db.insert(ITEMS_TABLE_NAME, null, cv);

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(ITEMS_TABLE_NAME, ITEMS_COLUMN_ID + " = ? ", new String[]{String.valueOf(id)});
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();


        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + ITEMS_TABLE_NAME, null);

            if (cursor.moveToFirst()) {
                do {
                    Product product = new Product();

                    product.setProductId(cursor.getInt(0));
                    product.setProductName(cursor.getString(1));
                    product.setProductDescription(cursor.getString(2));
                    product.setProductCategory(cursor.getString(3));
//                    product.setItemImage(getImage(cursor.getBlob(5)));
                    product.setProductPrice(cursor.getInt(5));
                    product.setProductFavourite(cursor.getInt(6) == 1);
                    product.setProductQuantity(cursor.getInt(7));

                    products.add(product);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }


    //FAVOURITE

    public ArrayList<Product> getAllFavouriteProducts() {
        ArrayList<Product> products = new ArrayList<>();


        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + ITEMS_TABLE_NAME + " WHERE " + ITEMS_COLUMN_FAVOURITE + " = 1", null);

            if (cursor.moveToFirst()) {
                do {
                    Product product = new Product();

                    product.setProductId(cursor.getInt(0));
                    product.setProductName(cursor.getString(1));
                    product.setProductDescription(cursor.getString(2));
                    product.setProductCategory(cursor.getString(3));
                    product.setProductPrice(cursor.getInt(5));
                    product.setProductFavourite(true);
                    product.setProductQuantity(cursor.getInt(7));

                    products.add(product);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    public HashSet<Integer> getAllFavouriteProductIds() {
        HashSet<Integer> ids = new HashSet<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT " + ITEMS_COLUMN_ID +
                            " FROM " + ITEMS_TABLE_NAME +
                            " WHERE " + ITEMS_COLUMN_FAVOURITE + " = 1",
                    null);

            if (cursor.moveToFirst()) {
                do {
                    ids.add(cursor.getInt(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public boolean getFavourite(int id) {
        boolean fav = false;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT " + ITEMS_COLUMN_FAVOURITE +
                            " FROM " + ITEMS_TABLE_NAME +
                            " WHERE " + ITEMS_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)});

            if (cursor.moveToFirst()) {
                fav = cursor.getInt(0) != 0;
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fav;
    }

    public void setFavourite(int id, int fav) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("UPDATE " + ITEMS_TABLE_NAME +
                            " SET " + ITEMS_COLUMN_FAVOURITE + " = ?" +
                            " WHERE " + ITEMS_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(fav), String.valueOf(id)});
            db.close();
            Log.w(TAG, "Favourite Item set " + fav + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //CART

    public ArrayList<Product> getAllCartProducts() {

        ArrayList<Product> products = new ArrayList<>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + ITEMS_TABLE_NAME +
                            " WHERE " + ITEMS_COLUMN_QUANTITY + " != 0",
                    null);

            if (cursor.moveToFirst()) {
                do {

                    Product product = new Product();

                    product.setProductId(cursor.getInt(0));
                    product.setProductName(cursor.getString(1));
                    product.setProductDescription(cursor.getString(2));
                    product.setProductCategory(cursor.getString(3));
//                    product.setItemImage(getImage(cursor.getBlob(5)));
                    product.setProductPrice(cursor.getInt(5));
                    product.setProductFavourite(cursor.getInt(6) == 1);
                    product.setProductQuantity(cursor.getInt(7));
                    products.add(product);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    public int getQuantity(int id) {

        int quantity = 0;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT " + ITEMS_COLUMN_QUANTITY + " FROM " + ITEMS_TABLE_NAME + " WHERE " + ITEMS_COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

            if (cursor.moveToFirst()) {
                quantity = cursor.getInt(0);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return quantity;
    }

    public void setQuantity(int id, int quantity) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("UPDATE " + ITEMS_TABLE_NAME +
                            " SET " + ITEMS_COLUMN_QUANTITY + " = ?" +
                            " WHERE " + ITEMS_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(quantity), String.valueOf(id)});
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCartProductTotal() {
        int sum = 0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT SUM(" + ITEMS_COLUMN_PRICE + " * " + ITEMS_COLUMN_QUANTITY + ") AS GrandTotal FROM " + ITEMS_TABLE_NAME, null);

            if (cursor.moveToFirst()) {
                sum = cursor.getInt(0);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }
}

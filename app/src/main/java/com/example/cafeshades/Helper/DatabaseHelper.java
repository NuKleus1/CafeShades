package com.example.cafeshades.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.example.cafeshades.models.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "CafeShades.db";
    public static final String ITEMS_TABLE_NAME = "products";
    public static final String ITEMS_COLUMN_ID = "productId";
    public static final String ITEMS_COLUMN_NAME = "productName";
    public static final String ITEMS_COLUMN_CATEGORY = "categoryId";
    public static final String ITEMS_COLUMN_IMAGE = "productImage";
    public static final String ITEMS_COLUMN_PRICE = "productPrice";
    public static final String ITEMS_COLUMN_FAVOURITE = "productIsFavourite";
    public static final String CART_TABLE_NAME = "cart";
    public static final String CART_COLUMN_ID = "productId";
    public static final String CART_COLUMN_QUANTITY = "productQuantity";
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
        String queryItemTable = "CREATE TABLE IF NOT EXISTS " + ITEMS_TABLE_NAME + " (" +
                ITEMS_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                ITEMS_COLUMN_NAME + " TEXT, " +
                ITEMS_COLUMN_CATEGORY + " TEXT, " +
                ITEMS_COLUMN_IMAGE + " TEXT, " +
                ITEMS_COLUMN_PRICE + " INTEGER, " +
                ITEMS_COLUMN_FAVOURITE + " BOOLEAN)";


        // Create Cart Table
        String queryCartTable = "CREATE TABLE IF NOT EXISTS " + CART_TABLE_NAME + " (" +
                CART_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                CART_COLUMN_QUANTITY + " INTEGER)";

        sqLiteDatabase.execSQL(queryItemTable);
        sqLiteDatabase.execSQL(queryCartTable);
        Log.d(TAG, "DB Created");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Create Item Table
        String queryItemTable = "CREATE TABLE IF NOT EXISTS " + ITEMS_TABLE_NAME + " (" +
                ITEMS_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                ITEMS_COLUMN_NAME + " TEXT, " +
                ITEMS_COLUMN_CATEGORY + " TEXT, " +
                ITEMS_COLUMN_IMAGE + " TEXT, " +
                ITEMS_COLUMN_PRICE + " INTEGER, " +
                ITEMS_COLUMN_FAVOURITE + " BOOLEAN)";

        // Create Cart Table
        String queryCartTable = "CREATE TABLE IF NOT EXISTS " + CART_TABLE_NAME + " (" +
                CART_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                CART_COLUMN_QUANTITY + " INTEGER)";

        sqLiteDatabase.execSQL(queryItemTable);
        sqLiteDatabase.execSQL(queryCartTable);
    }

    //ITEMS
    public void insertProduct(Product product) {
        if (product.getProductId().isEmpty()){
            return ;
        }
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(ITEMS_COLUMN_ID, product.getProductId());
            cv.put(ITEMS_COLUMN_NAME, product.getProductName());
            cv.put(ITEMS_COLUMN_IMAGE, product.getProductImage());
            cv.put(ITEMS_COLUMN_CATEGORY, product.getProductCategory());
            cv.put(ITEMS_COLUMN_PRICE, product.getProductPrice());
            cv.put(ITEMS_COLUMN_FAVOURITE, product.isProductFavourite());

            db.insertWithOnConflict(ITEMS_TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
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
            Log.d(TAG, "deleteProducts");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
//            Cursor cursor = db.rawQuery("SELECT " + ITEMS_TABLE_NAME + ".*, " + CART_TABLE_NAME + "." + CART_COLUMN_QUANTITY +
//                            " FROM " + ITEMS_TABLE_NAME + ", " + CART_TABLE_NAME,
//                    null);

            Cursor cursor = db.rawQuery("SELECT products.*, cart.productQuantity FROM products left OUTER JOIN cart ON products.productId == cart.productid",
                    null);

//            Cursor cursor = db.rawQuery("SELECT * FROM products", null);

//            Log.d(TAG, cursor.getString());

//            Cursor cursor = db.rawQuery("SELECT * " +
//                            " FROM " + ITEMS_TABLE_NAME,
//                    null);

            if (cursor.moveToFirst()) {
                do {
                    Product product = new Product();

                    product.setProductId(cursor.getInt(0));
                    product.setProductName(cursor.getString(1));
                    product.setProductCategory(cursor.getString(2));
                    product.setProductImage(cursor.getString(3));
                    product.setProductPrice(cursor.getInt(4));
                    product.setProductFavourite(cursor.getInt(5) == 1);
                    product.setProductQuantity(cursor.getInt(6));

                    products.add(product);
                    Log.d(TAG, "getAllProducts/ " + product.getProductId());
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public void updateProductAndCartTable(List<String> productIDs) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
//            int delete = db.delete(ITEMS_TABLE_NAME, ITEMS_COLUMN_ID + " NOT IN ?", productIDs.toArray(new String[0]));
            db.rawQuery("DELETE FROM " + ITEMS_TABLE_NAME +
                    " WHERE " + ITEMS_COLUMN_ID + " NOT IN (" + TextUtils.join(", ", productIDs) + ")", null);

            Log.d(TAG, "Deleted Rows");
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL("DELETE FROM " + CART_TABLE_NAME +
                    " WHERE NOT EXISTS (SELECT " + CART_COLUMN_ID + " FROM " + ITEMS_TABLE_NAME +
                    " WHERE " + ITEMS_TABLE_NAME + "." + ITEMS_COLUMN_ID + " == " + CART_TABLE_NAME + "." + CART_COLUMN_ID
                    + ")");
            db.close();
            Log.d(TAG, "Deleted Exists");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //FAVOURITE

    public ArrayList<Product> getAllFavouriteProducts() {
        ArrayList<Product> products = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
//            Cursor cursor = db.rawQuery("SELECT " + ITEMS_TABLE_NAME + ".*, " + CART_TABLE_NAME + "." + CART_COLUMN_QUANTITY +
//                            " FROM " + ITEMS_TABLE_NAME + ", " + CART_TABLE_NAME +
//                            " WHERE " + ITEMS_COLUMN_FAVOURITE + " = 1",
//                    null);
            Cursor cursor = db.rawQuery("SELECT products.*, cart.productQuantity FROM products LEFT OUTER JOIN cart ON products.productId == cart.productId WHERE productIsFavourite = 1", null);

            if (cursor.moveToFirst()) {
                do {
                    Product product = new Product();

                    product.setProductId(cursor.getInt(0));
                    product.setProductName(cursor.getString(1));
                    product.setProductCategory(cursor.getString(2));
                    product.setProductImage(cursor.getString(3));
                    product.setProductPrice(cursor.getInt(4));
                    product.setProductFavourite(true);
                    product.setProductQuantity(cursor.getInt(6));

                    products.add(product);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            Log.d(TAG, "getAllFavouriteProducts");
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
            Log.d(TAG, "getAllFavouriteProductIds");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public boolean getFavourite(String id) {
        if (id.isEmpty()) {
            return false;
        }
        boolean fav = false;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT " + ITEMS_COLUMN_FAVOURITE +
                            " FROM " + ITEMS_TABLE_NAME +
                            " WHERE " + ITEMS_COLUMN_ID + " = ?",
                    new String[]{id});

            if (cursor.moveToFirst()) {
                fav = cursor.getInt(0) != 0;
            }
            cursor.close();
            db.close();
            Log.d(TAG, "getFavourite");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fav;
    }

    public void setFavourite(String id, int fav) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(ITEMS_COLUMN_FAVOURITE, fav == 1);
            int update = db.update(ITEMS_TABLE_NAME, cv,
                    ITEMS_COLUMN_ID + " = ?",
                    new String[]{id});
            db.close();
            Log.d(TAG, "setFavourite/ Updated Rows: " + update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //CART

    public ArrayList<Product> getAllCartProducts() {

        ArrayList<Product> products = new ArrayList<>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT " + ITEMS_TABLE_NAME + ".*, " + CART_COLUMN_QUANTITY +
                            " FROM " + ITEMS_TABLE_NAME + ", " + CART_TABLE_NAME +
                            " WHERE " + CART_TABLE_NAME + "." + CART_COLUMN_ID + " == " + ITEMS_TABLE_NAME + "." + ITEMS_COLUMN_ID,
                    null);

            if (cursor.moveToFirst()) {
                do {
                    Product product = new Product();

                    product.setProductId(cursor.getInt(0));
                    product.setProductName(cursor.getString(1));
                    product.setProductCategory(cursor.getString(2));
//                    product.setItemImage(getImage(cursor.getBlob(3)));
                    product.setProductPrice(cursor.getInt(4));
                    product.setProductFavourite(cursor.getInt(5) == 1);
                    product.setProductQuantity(cursor.getInt(6));
                    products.add(product);
                    Log.d(TAG, "getALLCartProduct: " + product.getProductId() + product.getProductQuantity());
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            Log.d(TAG, "getAllCartProducts");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public int getQuantity(String id) {

        if (id.isEmpty()) {
            return 0;
        }

        int quantity = 0;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT " + CART_COLUMN_QUANTITY +
                            " FROM " + CART_TABLE_NAME +
                            " WHERE " + CART_COLUMN_ID + " = ?",
                    new String[]{id});

            if (cursor.moveToFirst()) {
                quantity = cursor.getInt(0);
            }
            cursor.close();
            db.close();
            Log.d(TAG, "getQuantity");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return quantity;
    }

    public void setQuantity(int id, int quantity) {
        if (quantity == 0) {
            try {
                SQLiteDatabase db = this.getWritableDatabase();
                db.delete(CART_TABLE_NAME, CART_COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(CART_COLUMN_ID, id);
                cv.put(CART_COLUMN_QUANTITY, quantity);
                db.insertWithOnConflict(CART_TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getCartProductTotal() {
        int sum = 0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT SUM(" + ITEMS_TABLE_NAME + "." + ITEMS_COLUMN_PRICE + " * " + CART_TABLE_NAME + "." + CART_COLUMN_QUANTITY +
                            ") AS GrandTotal FROM " + ITEMS_TABLE_NAME + ", " + CART_TABLE_NAME +
                            " WHERE " + CART_TABLE_NAME + "." + CART_COLUMN_ID + " == " + ITEMS_TABLE_NAME + "." + ITEMS_COLUMN_ID,
                    null);

            if (cursor.moveToFirst()) {
                sum = cursor.getInt(0);
            }
            cursor.close();
            db.close();
            Log.d(TAG, "getCartProductTotal");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }
}

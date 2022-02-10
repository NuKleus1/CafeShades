package com.example.cafeshades.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.cafeshades.models.ItemModelClass;

import java.util.ArrayList;
import java.util.HashSet;

public final class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "CafeShades.db";
    public static final String ITEMS_TABLE_NAME = "items";
    public static final String ITEMS_COLUMN_ID = "itemId";
    public static final String ITEMS_COLUMN_NAME = "itemName";
    public static final String ITEMS_COLUMN_DESCRIPTION = "itemDescription";
    public static final String ITEMS_COLUMN_CATEGORY = "itemCategory";
    public static final String ITEMS_COLUMN_IMAGE = "itemImage";
    public static final String ITEMS_COLUMN_PRICE = "itemPrice";
    public static final String ITEMS_COLUMN_FAVOURITE = "itemIsFavourite";
    public static final String ITEMS_COLUMN_QUANTITY = "itemQuantity";
    private static final String TAG = "DatabaseHelper";

    private static DatabaseHelper instance = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
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
//        sqLiteDatabase.execSQL(queryCartTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }


    //ITEMS


    public void insertItems(ItemModelClass item) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(ITEMS_COLUMN_ID, item.getItemID());
            cv.put(ITEMS_COLUMN_NAME, item.getItemName());
            cv.put(ITEMS_COLUMN_DESCRIPTION, item.getItemDescription());
            cv.put(ITEMS_COLUMN_CATEGORY, item.getItemDescription());
//            cv.put(ITEMS_COLUMN_IMAGE, getBytes(item.getItemImage()));
            cv.put(ITEMS_COLUMN_PRICE, item.getItemPrice());
            cv.put(ITEMS_COLUMN_FAVOURITE, item.isItemFavourite());
            cv.put(ITEMS_COLUMN_QUANTITY, item.getItemQuantity());

            db.insert(ITEMS_TABLE_NAME, null, cv);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(int id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(ITEMS_TABLE_NAME, ITEMS_COLUMN_ID + " = ? ", new String[]{String.valueOf(id)});
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ItemModelClass> getAllItems() {
        ArrayList<ItemModelClass> items = new ArrayList<>();


        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + ITEMS_TABLE_NAME, null);

            if (cursor.moveToFirst()) {
                do {
                    ItemModelClass item = new ItemModelClass();

                    item.setItemID(cursor.getInt(0));
                    item.setItemName(cursor.getString(1));
                    item.setItemDescription(cursor.getString(2));
                    item.setItemCategory(cursor.getString(3));
//                    item.setItemImage(getImage(cursor.getBlob(5)));
                    item.setItemPrice(cursor.getInt(5));
                    item.setItemFavourite(cursor.getInt(6) == 1);
                    item.setItemQuantity(cursor.getInt(7));

                    items.add(item);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }


    //FAVOURITE

    public ArrayList<ItemModelClass> getAllFavouriteItems() {
        ArrayList<ItemModelClass> items = new ArrayList<>();


        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + ITEMS_TABLE_NAME + " WHERE " + ITEMS_COLUMN_FAVOURITE + " = 1", null);

            if (cursor.moveToFirst()) {
                do {
                    ItemModelClass item = new ItemModelClass();

                    item.setItemID(cursor.getInt(0));
                    item.setItemName(cursor.getString(1));
                    item.setItemDescription(cursor.getString(2));
                    item.setItemCategory(cursor.getString(3));
//                    item.setItemImage(getImage(cursor.getBlob(5)));
                    item.setItemPrice(cursor.getInt(5));
                    item.setItemFavourite(true);
                    item.setItemQuantity(cursor.getInt(7));

                    items.add(item);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    public HashSet<Integer> getAllFavouriteItemIDs() {
        HashSet<Integer> ids = new HashSet<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT " + ITEMS_COLUMN_ID + " FROM " + ITEMS_TABLE_NAME + " WHERE " + ITEMS_COLUMN_FAVOURITE + " = 1", null);

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
            Cursor cursor = db.rawQuery("SELECT " + ITEMS_COLUMN_FAVOURITE + " FROM " + ITEMS_TABLE_NAME + " WHERE " + ITEMS_COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

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


    // CART

//    public void insertItemsToCart(ItemModelClass item) {
//        try {
//            SQLiteDatabase db = this.getWritableDatabase();
//            ContentValues cv = new ContentValues();
//
//            cv.put(CART_COLUMN_ID, item.getItemID());
//            cv.put(CART_COLUMN_NAME, item.getItemName());
//            cv.put(CART_COLUMN_DESCRIPTION, item.getItemDescription());
//            cv.put(CART_COLUMN_CATEGORY, item.getItemDescription());
////            cv.put(CART_COLUMN_IMAGE, getBytes(item.getItemImage()));
//            cv.put(CART_COLUMN_PRICE, item.getItemPrice());
//            cv.put(CART_COLUMN_QUANTITY, item.getItemQuantity());
//
//            db.insert(CART_TABLE_NAME, null, cv);
//            db.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteItemFromCart(int id) {
//        try {
//            SQLiteDatabase db = this.getWritableDatabase();
//            db.delete(CART_TABLE_NAME, CART_COLUMN_ID + " = ? ", new String[]{String.valueOf(id)});
//            db.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public ArrayList<ItemModelClass> getAllCartItems() {
//        ArrayList<ItemModelClass> items = new ArrayList<>();
//
//
//        try {
//            SQLiteDatabase db = this.getReadableDatabase();
//            Cursor cursor = db.rawQuery("SELECT * FROM " + CART_TABLE_NAME, null);
//
//            if (cursor.moveToFirst()) {
//                do {
//                    ItemModelClass item = new ItemModelClass();
//
//                    item.setItemID(cursor.getInt(0));
//                    item.setItemName(cursor.getString(1));
//                    item.setItemDescription(cursor.getString(2));
//                    item.setItemCategory(cursor.getString(3));
////                    item.setItemImage(getImage(cursor.getBlob(4)));
//                    item.setItemPrice(cursor.getInt(5));
//                    item.setItemFavourite(true);
//                    item.setItemQuantity(cursor.getInt(6));
//
//                    items.add(item);
//                } while (cursor.moveToNext());
//            }
//
//            cursor.close();
//            db.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return items;
//    }
//
//    public HashSet<Integer> getAllCartItemIDs() {
//        HashSet<Integer> ids = new HashSet<>();
//        try {
//            SQLiteDatabase db = this.getReadableDatabase();
//            Cursor cursor = db.rawQuery("SELECT " + CART_COLUMN_ID + " FROM " + CART_TABLE_NAME, null);
//
//            if (cursor.moveToFirst()) {
//                do {
//                    ids.add(cursor.getInt(0));
//                } while (cursor.moveToNext());
//            }
//            cursor.close();
//            db.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ids;
//    }
//
//    public void updateQuantityToCart(int id, int quantity) {
//        try {
//            SQLiteDatabase db = this.getReadableDatabase();
//
//            ContentValues cv = new ContentValues();
//            cv.put(ITEMS_COLUMN_QUANTITY, String.valueOf(quantity));
//            db.update(ITEMS_TABLE_NAME, cv, ITEMS_COLUMN_ID + "= ?", new String[]{String.valueOf(id)});
//            db.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    //CART

    public ArrayList<ItemModelClass> getAllCartItems() {

        ArrayList<ItemModelClass> items = new ArrayList<>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + ITEMS_TABLE_NAME + " WHERE " + ITEMS_COLUMN_QUANTITY + " != 0", null);

            if (cursor.moveToFirst()) {
                do {

                    ItemModelClass item = new ItemModelClass();

                    item.setItemID(cursor.getInt(0));
                    item.setItemName(cursor.getString(1));
                    item.setItemDescription(cursor.getString(2));
                    item.setItemCategory(cursor.getString(3));
//                    item.setItemImage(getImage(cursor.getBlob(5)));
                    item.setItemPrice(cursor.getInt(5));
                    item.setItemFavourite(cursor.getInt(6) == 1);
                    item.setItemQuantity(cursor.getInt(7));
                    items.add(item);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
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
}

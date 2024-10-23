package com.DATA;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Data.Order;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "order_db";
    private static final int DATABASE_VERSION = 4; // Tăng version để cập nhật cấu trúc bảng

    // Tên bảng và cột cho lịch sử order
    private static final String TABLE_ORDER_HISTORY = "order_history";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_PHONE_NUMBER = "phone_number"; // Thêm số điện thoại
    private static final String COLUMN_TABLE_NUMBER = "table_number"; // Thêm số bàn
    private static final String COLUMN_FLOOR_NUMBER = "floor_number"; // Thêm số tầng
    private static final String COLUMN_SELECTED_ITEMS = "selected_items"; // Thêm danh sách món
    private static final String COLUMN_TIME = "time"; // Thêm thời gian chọn
    private static final String COLUMN_TOTAL_PRICE = "total_price";
    private static final String COLUMN_PAYMENT_STATUS = "payment_status";
    private static final String COLUMN_ORDER_TIME = "order_time"; // Thêm thời gian đặt hàng

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng order_history với các cột mới
        String createTable = "CREATE TABLE " + TABLE_ORDER_HISTORY + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID + " TEXT, " +
                COLUMN_USER_NAME + " TEXT, " +
                COLUMN_PHONE_NUMBER + " TEXT, " +
                COLUMN_TABLE_NUMBER + " TEXT, " +
                COLUMN_FLOOR_NUMBER + " TEXT, " +
                COLUMN_SELECTED_ITEMS + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_TOTAL_PRICE + " REAL, " +
                COLUMN_PAYMENT_STATUS + " TEXT DEFAULT 'Đã hoàn thành', " +
                COLUMN_ORDER_TIME + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 4) {
            // Xóa bảng cũ và tạo lại bảng mới với cấu trúc mới
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_HISTORY);
            onCreate(db);
        }
    }

    // Thêm đơn hàng vào cơ sở dữ liệu
    public void addOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Thêm các giá trị từ đối tượng Order vào ContentValues
        values.put(COLUMN_USER_ID, order.getUserId());
        values.put(COLUMN_USER_NAME, order.getUserName());
        values.put(COLUMN_PHONE_NUMBER, order.getPhoneNumber()); // Thêm số điện thoại
        values.put(COLUMN_TABLE_NUMBER, order.getTableNumber()); // Thêm số bàn
        values.put(COLUMN_FLOOR_NUMBER, order.getFloorNumber()); // Thêm số tầng
        values.put(COLUMN_SELECTED_ITEMS, String.join(",", order.getSelectedItems())); // Lưu danh sách món đã chọn
        values.put(COLUMN_TIME, order.getTime()); // Thêm thời gian chọn
        values.put(COLUMN_TOTAL_PRICE, order.getTotalPrice());
        values.put(COLUMN_PAYMENT_STATUS, order.getPaymentStatus());
        values.put(COLUMN_ORDER_TIME, order.getOrderTime()); // Thêm thời gian đặt hàng

        // Thực hiện insert dữ liệu vào bảng
        db.insert(TABLE_ORDER_HISTORY, null, values);
        db.close();
    }


    // Lấy danh sách tất cả đơn hàng
    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ORDER_HISTORY, null, null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID));
                    String userId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
                    String userName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME));
                    String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_NUMBER));
                    String tableNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TABLE_NUMBER));
                    String floorNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLOOR_NUMBER));
                    List<String> selectedItems = Arrays.asList(
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SELECTED_ITEMS)).split(","));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME));
                    double totalPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_PRICE));
                    String paymentStatus = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAYMENT_STATUS));
                    String orderTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_TIME));

                    Order order = new Order(id, userId, userName, phoneNumber, tableNumber, floorNumber,
                            selectedItems, time, totalPrice, paymentStatus, orderTime);
                    orderList.add(order);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        db.close();
        return orderList;
    }

    // Lấy danh sách đơn hàng theo userId
    public List<Order> getOrdersByUserId(String userId) {
        List<Order> orderList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ORDER_HISTORY,
                null,
                COLUMN_USER_ID + " = ?",
                new String[]{userId},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID));
                    String userName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME));
                    String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_NUMBER));
                    String tableNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TABLE_NUMBER));
                    String floorNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLOOR_NUMBER));
                    List<String> selectedItems = Arrays.asList(
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SELECTED_ITEMS)).split(","));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME));
                    double totalPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_PRICE));
                    String paymentStatus = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAYMENT_STATUS));
                    String orderTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_TIME));

                    Order order = new Order(id, userId, userName, phoneNumber, tableNumber, floorNumber,
                            selectedItems, time, totalPrice, paymentStatus, orderTime);
                    orderList.add(order);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        db.close();
        return orderList;
    }
}

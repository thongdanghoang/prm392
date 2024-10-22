package Data;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private String id;
    private String userId; // Thêm trường userId
    private String userName;
    private String phoneNumber; // Thêm số điện thoại
    private String tableNumber; // Thêm số bàn
    private String floorNumber; // Thêm số tầng
    private List<String> selectedItems; // Thêm danh sách món đã chọn
    private String time; // Thêm thời gian chọn
    private double totalPrice;
    private String paymentStatus;
    private String orderTime; // Thêm thời gian đặt hàng

    public Order(String id, String userId, String userName, String phoneNumber, String tableNumber,
                 String floorNumber, List<String> selectedItems, String time,
                 double totalPrice, String paymentStatus, String orderTime) {
        this.id = id;
        this.userId = userId; // Lưu userId
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.tableNumber = tableNumber;
        this.floorNumber = floorNumber;
        this.selectedItems = selectedItems;
        this.time = time;
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
        this.orderTime = orderTime;
    }

    public String getId() {
        return id;
    }

    public String getUserId() { // Thêm getter cho userId
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public List<String> getSelectedItems() {
        return selectedItems;
    }

    public String getTime() {
        return time;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getOrderTime() {
        return orderTime;
    }
}

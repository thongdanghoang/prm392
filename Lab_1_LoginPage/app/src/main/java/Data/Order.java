package Data;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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

    public Order(String id, double totalPrice, String paymentStatus) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
    }
}

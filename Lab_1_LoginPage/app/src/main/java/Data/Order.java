package Data;

public class Order {
    private String name;
    private double price;
    private String status;

    public Order(String name, double price, String status) {
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }
}

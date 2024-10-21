package Data;

import com.google.gson.annotations.SerializedName;

public class Table {

    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;

    @SerializedName("hours")
    private String hours;

    @SerializedName("imageResourceId")
    private int imageResourceId;

    @SerializedName("floorNumber")
    private int floorNumber;

    @SerializedName("seatNumber")
    private int seatNumber;

    // Constructor
    public Table(String name, String address, String hours, int imageResourceId, int floorNumber, int seatNumber) {
        this.name = name;
        this.address = address;
        this.hours = hours;
        this.imageResourceId = imageResourceId;
        this.floorNumber = floorNumber;
        this.seatNumber = seatNumber;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getHours() {
        return hours;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }
}

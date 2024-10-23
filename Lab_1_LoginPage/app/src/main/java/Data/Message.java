package Data;

public class Message {
    private String senderId; // ID của người gửi
    private String message;

    public Message() {
        // Constructor rỗng cần thiết cho Firebase
    }

    public Message(String senderId, String message) {
        this.senderId = senderId;
        this.message = message;
    }

    // Getters và setters
    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

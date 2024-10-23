package com.example.lab_1_loginpage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.API.PayPalAPI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Data.AccessTokenResponse;
import Data.PaymentResponse;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PayPalPaymentActivity extends AppCompatActivity {

    private double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

            // Kiểm tra URL được trả về

        // Nhận tổng giá từ CheckoutActivity
        totalPrice = getIntent().getDoubleExtra("TOTAL_PRICE", 0.0);

        // Tạo đối tượng PayPalClient
        PayPalClient payPalClient = new PayPalClient();
        Uri data = getIntent().getData();
        if (data != null) {
            String scheme = data.getScheme();
            String host = data.getHost();

            if ("yourapp".equals(scheme) && "success".equals(host)) {
                // Thanh toán thành công, chuyển sang SuccessActivity
                Intent intent = new Intent(PayPalPaymentActivity.this, SuccessActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Đóng PayPalPaymentActivity
            } else if ("yourapp".equals(scheme) && "paypal_cancel".equals(host)) {
                // Thanh toán bị hủy
                Toast.makeText(this, "Thanh toán bị hủy!", Toast.LENGTH_SHORT).show();
                finish(); // Đóng PayPalPaymentActivity
            }
        }

        // Gọi API để lấy access token
        payPalClient.getAccessToken(new Callback<AccessTokenResponse>() {
            @Override
            public void onResponse(Call<AccessTokenResponse> call, Response<AccessTokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String accessToken = response.body().getAccessToken();
                    Log.d("PayPalPaymentActivity", "Access Token: " + accessToken);
                    Toast.makeText(PayPalPaymentActivity.this, "Lấy Access Token thành công!", Toast.LENGTH_SHORT).show();

                    // Tạo thanh toán với access token và tổng giá
                    createPayment(accessToken, totalPrice);

                } else {
                    Toast.makeText(PayPalPaymentActivity.this, "Không lấy được Access Token!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccessTokenResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(PayPalPaymentActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createPayment(String accessToken, double amount) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.sandbox.paypal.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PayPalAPI payPalAPI = retrofit.create(PayPalAPI.class);

        // Tạo JSON cho yêu cầu thanh toán
        JSONObject paymentData = new JSONObject();
        try {
            paymentData.put("intent", "sale");

            // Payer
            JSONObject payer = new JSONObject();
            payer.put("payment_method", "paypal");
            paymentData.put("payer", payer);

            // Transaction
            JSONObject amountObject = new JSONObject();
            amountObject.put("total", String.format("%.2f", amount));
            amountObject.put("currency", "USD");

            JSONObject transaction = new JSONObject();
            transaction.put("amount", amountObject);
            transaction.put("description", "Thanh toán đơn hàng");

            JSONArray transactions = new JSONArray();
            transactions.put(transaction);
            paymentData.put("transactions", transactions);

            JSONObject redirectUrls = new JSONObject();
            redirectUrls.put("return_url", "yourapp://success");
            redirectUrls.put("cancel_url", "yourapp://paypal_cancel");

            paymentData.put("redirect_urls", redirectUrls);

        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        // Tạo RequestBody từ JSON
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), paymentData.toString());

        // Gọi API để tạo thanh toán
        Call<PaymentResponse> call = payPalAPI.createPayment("Bearer " + accessToken, requestBody);
        call.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Nhận liên kết để redirect người dùng đến PayPal để thanh toán
                    String approvalUrl = response.body().getApprovalLink();
                    Log.d("PayPalPaymentActivity", "Approval URL: " + approvalUrl);

                    // Mở URL trong trình duyệt
                    openApprovalUrl(approvalUrl);
                } else {
                    Toast.makeText(PayPalPaymentActivity.this, "Lỗi tạo thanh toán!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(PayPalPaymentActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Phương thức mở URL phê duyệt trong trình duyệt
    private void openApprovalUrl(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent); // Chỉ sử dụng startActivity để mở liên kết trong trình duyệt
    }



}


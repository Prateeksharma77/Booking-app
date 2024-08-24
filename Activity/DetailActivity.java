package com.example.booking_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.booking_app.Domain.ItemDomain;
import com.example.booking_app.R;
import com.example.booking_app.databinding.ActivityDetailBinding;

import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity implements PaymentResultListener {
    ActivityDetailBinding binding;
    private ItemDomain object;

    Button payBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Checkout.preload(getApplicationContext());


        payBtn = findViewById(R.id.payBtn);
        getIntentExtra();
        setVariable();

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentNow(String.valueOf(object.getPrice()),object.getTitle());
            }
        });
    }

    private void paymentNow(String amount,String name) {
        final Activity activity = this;
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_FlZdl2extpBUAN");

        checkout.setImage(R.drawable.ic_launcher_background);

        double finalAmount = Float.parseFloat(amount) * 100;

        try {
            JSONObject options = new JSONObject();

            options.put("name", name);
            options.put("description", "Reference No. #123456");
            options.put("image", "http://example.com/image/rzp.jpg");
            //options.put("order_id","o");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", finalAmount);//pass amount in currency subunits
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact", "9988776655");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }



    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(getApplicationContext(),"Payment Success", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int code, String response) {
        Log.e("Payment Error", "Code: " + code + ", Response: " + response);
        Toast.makeText(getApplicationContext(), "Payment Failed: " + response, Toast.LENGTH_SHORT).show();

    }

    private void setVariable() {
        binding.textView13.setText(object.getTitle());
        binding.textView19.setText("Rs"+object.getPrice());
        binding.imageView6.setOnClickListener(v->finish());
        binding.bedTxt.setText(""+object.getBed());
        binding.distanceTxt.setText(object.getDistance());
        binding.durationTxt.setText(object.getDuration());
        binding.textView14.setText(object.getAddress());
        binding.ratingBar.setRating((float) object.getScore());

        Glide.with(DetailActivity.this)
                .load(object.getPic())
                .into(binding.imageView2);
    }

    private void getIntentExtra() {
        object=(ItemDomain) getIntent().getSerializableExtra("object");
    }
}
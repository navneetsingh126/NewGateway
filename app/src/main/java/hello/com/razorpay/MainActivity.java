package hello.com.razorpay;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener{

    EditText editText;
    Button button;
    int amount;
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Checkout.preload(getApplicationContext());
        editText = (EditText)findViewById(R.id.amountid);
        button = (Button)findViewById(R.id.paybuttonid);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });

    }

    private void startPayment() {
        final Checkout checkout = new Checkout();
        amount = Integer.parseInt(editText.getText().toString());
        try{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","Loan Assistant");
        jsonObject.put("description","Loan Assistant");
        jsonObject.put("currency","INR");
        jsonObject.put("amount",amount*100);
        checkout.open(MainActivity.this,jsonObject);}
        catch (JSONException e){
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }



    @Override
    public void onPaymentError(int i, String s) {
        try {
            Toast.makeText(this, "Payment failed: " + i + " " + s, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }
}

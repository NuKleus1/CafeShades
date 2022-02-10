package com.example.cafeshades.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cafeshades.R;

public class MainActivity extends AppCompatActivity {

//    private Button btnNewUser, btnGetOtp, btnResendOtp;
//    private TextInputLayout txtIlPhoneNumber, txtIllOtp;
//    private TextInputEditText txtPhoneNumber, txtOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_main_activity);
//        init();
//        setListeners();
    }
//
//    private void init() {
//        btnNewUser = findViewById(R.id.btn_new_user);
//        btnGetOtp = findViewById(R.id.btn_get_otp);
//        btnResendOtp = findViewById(R.id.btn_resend_otp);
//        txtIlPhoneNumber = findViewById(R.id.txtIll_phone_number);
//        txtPhoneNumber = findViewById(R.id.txt_phone_number);
//        txtIllOtp = findViewById(R.id.txtIll_otp);
//        txtOtp = findViewById(R.id.txt_otp);
//    }
//
//    private void setListeners() {
//        btnNewUser.setOnClickListener(this);
//        btnGetOtp.setOnClickListener(this);
//
//        //Phone Number Text Change Listener
//        txtPhoneNumber.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.length() == 10) {
//                    btnGetOtp.setVisibility(View.VISIBLE);
//                    txtIlPhoneNumber.setErrorEnabled(false);
//                } else {
//                    btnGetOtp.setVisibility(View.INVISIBLE);
//                    txtIlPhoneNumber.setError("Enter Valid Number");
//                    txtIllOtp.setVisibility(View.VISIBLE);
//                    btnResendOtp.setVisibility(View.VISIBLE);
//                    btnGetOtp.setText(R.string.hint_get_otp);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
//
//        //Phone Number Focus Change Listener
//        txtPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (!hasFocus) {
//                    if (txtPhoneNumber.getText().toString().isEmpty()) {
//                        txtIlPhoneNumber.setError("Please Enter Phone Number");
//                    } else if (!isPhoneValid()) {
//                        txtIlPhoneNumber.setError("Enter Valid Phone Number");
//                    }
//                }
//            }
//        });
//
//        //OTP Text Listener
////        txtOtp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
////            @Override
////            public void onFocusChange(View view, boolean b) {
////                if (txtOtp.getText().toString().length() < 6) {
////                    txtIllOtp.setError("Enter your valid 6-digit OTP");
////                }
////            }
////        });
//
//        txtOtp.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.length() == 0){
//                    txtIllOtp.setErrorEnabled(false);
//                }else if (charSequence.length() < 6){
//                    txtIllOtp.setError("Enter your valid 6-digit OTP");
//                }
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.length() == 6){
//                    txtIllOtp.setErrorEnabled(false);
//                }else if (charSequence.length() < 6){
//                    txtIllOtp.setError("Enter your valid 6-digit OTP");
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//    }
//
//    private boolean isPhoneValid() {
//        return txtPhoneNumber.getText().length() >= 10;
//    }
//
//    @Override
//    public void onClick(View view) {
//        if (view.getId() == R.id.btn_new_user) {
//            startActivity(new Intent(MainActivity.this, SignUpFragment.class));
//        } else if (view.getId() == R.id.btn_get_otp) {
//            if (txtIllOtp.getVisibility() == View.GONE) {
//                txtIllOtp.setVisibility(View.VISIBLE);
//                btnResendOtp.setVisibility(View.VISIBLE);
//                btnGetOtp.setText(R.string.log_in);
//            } else if (txtIllOtp.getVisibility() == View.VISIBLE) {
//            }
//        }
//    }
}
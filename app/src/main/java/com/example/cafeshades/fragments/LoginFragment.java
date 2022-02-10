package com.example.cafeshades.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.cafeshades.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

public class LoginFragment extends Fragment implements View.OnClickListener {

    View v = null;
    private Button btnNewUser, btnGetOtp, btnResendOtp;
    private TextInputLayout txtIlPhoneNumber, txtIllOtp;
    private TextInputEditText txtPhoneNumber, txtOtp;


    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (v == null) {
            v = inflater.inflate(R.layout.fragment_login, container, false);
        }

        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setListeners();

    }

    private void init() {
        btnNewUser = v.findViewById(R.id.btn_new_user);
        btnGetOtp = v.findViewById(R.id.btn_get_otp);
        btnResendOtp = v.findViewById(R.id.btn_resend_otp);
        txtIlPhoneNumber = v.findViewById(R.id.txtIll_phone_number);
        txtPhoneNumber = v.findViewById(R.id.txt_phone_number);
        txtIllOtp = v.findViewById(R.id.txtIll_otp);
        txtOtp = v.findViewById(R.id.txt_otp);
    }

    private void setListeners() {
        btnNewUser.setOnClickListener(this);
        btnGetOtp.setOnClickListener(this);

        //Phone Number Text Change Listener
        txtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 10) {
                    txtIlPhoneNumber.setErrorEnabled(false);
                    btnGetOtp.setVisibility(View.VISIBLE);
                    btnGetOtp.setText(R.string.hint_get_otp);
                } else if (editable.length() < 10) {
                    txtIlPhoneNumber.setError("Enter Valid Phone Number");
                    txtIllOtp.setVisibility(View.GONE);
                    txtOtp.setText("");
                    btnResendOtp.setVisibility(View.GONE);
                    btnGetOtp.setVisibility(View.GONE);
                    btnGetOtp.setText(R.string.hint_get_otp);
                }
            }
        });

        txtOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0 || editable.length() == 6) {
                    txtIllOtp.setErrorEnabled(false);
                } else {
                    txtIllOtp.setError("Enter your valid 6-digit OTP");
                }
            }
        });
    }

    private boolean isPhoneValid() {
        return txtPhoneNumber.getText().length() == 10;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_new_user) {
//            startActivity(new Intent(MainActivity.this, SignUpFragment.class));
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment);
        } else if (view.getId() == R.id.btn_get_otp) {
            if (isPhoneValid()) {
                txtIllOtp.setVisibility(View.VISIBLE);
                btnResendOtp.setVisibility(View.VISIBLE);
                btnGetOtp.setText(R.string.log_in);
            }
        }
    }
}

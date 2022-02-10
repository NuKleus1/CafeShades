package com.example.cafeshades.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cafeshades.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

public class SignUpFragment extends Fragment {

    private View v;
    private TextInputLayout txtIllPhoneNumber, txtIllOtp, txtIllFirstName, txtIllLastName, txtIllEmail, txtIllFloorHouseNo, txtIllBuildingStreetName, txtIllLandmark;
    private TextInputEditText txtPhoneNumber, txtOtp, txtFirstName, txtLastName, txtEmail, txtFloorHouseNo, txtBuildingStreetName, txtLandmark;
    private MaterialButton btnLogin, btnResendOtp;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (v == null) {
            v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        }

        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setListeners();
    }

    private void setListeners() {

        //First Name focus Change listener
        txtFirstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (txtFirstName.getText().toString().isEmpty()) {
                        txtIllFirstName.setError("Enter your First Name");
                    }
                } else {
                    txtIllFirstName.setErrorEnabled(false);
                }
            }
        });

        //Last Name focus Change listener
        txtLastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (txtLastName.getText().toString().isEmpty()) {
                        txtIllLastName.setError("Enter Last Name");
                    }
                } else {
                    txtIllLastName.setErrorEnabled(false);
                }
            }
        });

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
                    txtIllPhoneNumber.setErrorEnabled(false);
                } else if (editable.length() < 10) {
                    txtIllPhoneNumber.setError("Enter Valid Phone Number");
                    txtIllOtp.setVisibility(View.GONE);
                    txtOtp.setText("");
                    btnResendOtp.setVisibility(View.GONE);
                    btnLogin.setText(R.string.hint_get_otp);
                }
            }
        });

        //Email focus change listener
        txtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (txtEmail.getText().toString().isEmpty()) {
                        txtIllEmail.setError("Please enter your email address");
                    } else if (!isEmailValid()) {
                        txtIllEmail.setError("Enter valid email address");
                    }
                } else {
                    txtIllEmail.setErrorEnabled(false);
                }
            }
        });

        //FloorHouseNo focus Change listener
        txtFloorHouseNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (txtFloorHouseNo.getText().toString().isEmpty()) {
                        txtIllFloorHouseNo.setError("Enter floor or house number");
                    }
                } else {
                    txtIllFloorHouseNo.setErrorEnabled(false);
                }
            }
        });

        //BuildingStreetName focus Change listener
        txtBuildingStreetName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (txtBuildingStreetName.getText().toString().isEmpty()) {
                        txtIllBuildingStreetName.setError("Enter building or street Name");
                    }
                } else {
                    txtIllBuildingStreetName.setErrorEnabled(false);
                }
            }
        });

        //Landmark focus Change listener
        txtLandmark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (txtLandmark.getText().toString().isEmpty()) {
                        txtIllLandmark.setError("Enter Landmark");
                    }
                } else {
                    txtIllLandmark.setErrorEnabled(false);
                }
            }
        });

        //OTP focus Change listener

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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validData()) {
                    txtIllOtp.setVisibility(View.VISIBLE);
                    btnResendOtp.setVisibility(View.VISIBLE);
                    btnLogin.setText(R.string.sign_up);
                }
            }
        });


    }

    private boolean validData() {
        boolean flag = true;
        if (txtFirstName.getText().toString().isEmpty()) {
            txtIllFirstName.setError("Enter First Name");
            flag = false;
        }
        if (txtLastName.getText().toString().isEmpty()) {
            txtIllLastName.setError("Enter Last Name");
            flag = false;
        }
        if (!isPhoneNumberValid()) {
            txtIllPhoneNumber.setError("Enter your phone number");
            flag = false;
        }
        if (!isEmailValid()) {
            txtIllEmail.setError("Enter your valid email address");
            flag = false;
        }
        if (txtFloorHouseNo.getText().toString().isEmpty()) {
            txtIllFloorHouseNo.setError("Enter floor or house number");
            flag = false;
        }
        if (txtBuildingStreetName.getText().toString().isEmpty()) {
            txtIllBuildingStreetName.setError("Enter building or house Name");
            flag = false;
        }
        if (txtLandmark.getText().toString().isEmpty()) {
            txtIllLandmark.setError("Enter Landmark");
            flag = false;
        }
        return flag;
    }

    private boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches();
    }

    private void init() {

        txtIllFirstName = v.findViewById(R.id.txtIll_first_name);
        txtFirstName = v.findViewById(R.id.txt_first_name);
        txtIllLastName = v.findViewById(R.id.txtIll_last_name);
        txtLastName = v.findViewById(R.id.txt_last_name);
        txtIllPhoneNumber = v.findViewById(R.id.txtIll_phone_number);
        txtPhoneNumber = v.findViewById(R.id.txt_phone_number);
        txtIllEmail = v.findViewById(R.id.txtIll_email);
        txtEmail = v.findViewById(R.id.txt_email);
        txtIllFloorHouseNo = v.findViewById(R.id.txtIll_floor_house_no);
        txtFloorHouseNo = v.findViewById(R.id.txt_floor_house_no);
        txtIllBuildingStreetName = v.findViewById(R.id.txtIll_building_street_name);
        txtBuildingStreetName = v.findViewById(R.id.txt_building_street_name);
        txtIllLandmark = v.findViewById(R.id.txtIll_landmark);
        txtLandmark = v.findViewById(R.id.txt_landmark);
        txtIllOtp = v.findViewById(R.id.txtIll_otp);
        txtOtp = v.findViewById(R.id.txt_otp);
        btnLogin = v.findViewById(R.id.btn_login);
        btnResendOtp = v.findViewById(R.id.btn_resend_otp);
    }

    public boolean isPhoneNumberValid() {
        return txtPhoneNumber.getText().length() == 10;
    }
}

package com.example.cafeshades.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.cafeshades.R;
import com.example.cafeshades.UserPreferences;
import com.example.cafeshades.models.UserData;
import com.example.cafeshades.utils.APIClient;
import com.example.cafeshades.utils.UserAPI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpProfileFragment extends Fragment {

    private final static String TAG = "SignUpProfileFragment";
    private static boolean flag;
    private View v;
    private TextInputLayout txtIllPhoneNumber, txtIllOtp, txtIllFirstName, txtIllLastName, txtIllEmail, txtIllOfficeNo, txtIllFloorNo, txtIllBuildingName, txtIllLandmark;
    private TextInputEditText txtPhoneNumber, txtOtp, txtFirstName, txtLastName, txtEmail, txtOfficeNo, txtFloorNo, txtBuildingName, txtLandmark;
    private LinearLayout llLogo;
    private MaterialButton btnOTP, btnSignUp, btnResendOtp, btnEdit;
    private ScrollView sv;
    private TextView tvSignUp;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private String verficationId;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (v == null) {
            v = inflater.inflate(R.layout.fragment_sign_up_profile, container, false);
        }

        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseGetToken();
        flag = UserPreferences.getPrefInstance().getLoggedInFlag();
        init();

        if (flag) {
            setUpProfile();
            setProfileListeners();
            setUserData();
        } else {
            setListeners();
            setUpSignUp();
        }
    }

    private void setUserData() {
        UserPreferences prefInstance = UserPreferences.getPrefInstance(getContext());
        txtFirstName.setText(prefInstance.getName().trim());
        txtLastName.setText(prefInstance.getName().trim());
        txtPhoneNumber.setText(prefInstance.getMobileNumber());
        txtEmail.setText(prefInstance.getEmailId());
        txtFloorNo.setText(prefInstance.getFloorNumber());
        txtOfficeNo.setText(prefInstance.getOfficeNumber());
        txtBuildingName.setText(prefInstance.getBuildingName());
        txtLandmark.setText(prefInstance.getLandmark());
    }

    private void setProfileListeners() {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btnText = String.valueOf(btnEdit.getText());

                if (btnText.equals("Edit")) {
                    txtFloorNo.setEnabled(true);
                    txtOfficeNo.setEnabled(true);
                    txtBuildingName.setEnabled(true);
                    txtLandmark.setEnabled(true);

                    btnEdit.setText(R.string.btn_save);
                } else {
                    txtFloorNo.setEnabled(false);
                    txtOfficeNo.setEnabled(false);
                    txtBuildingName.setEnabled(false);
                    txtLandmark.setEnabled(false);

                    btnEdit.setText(R.string.btn_edit);
                }
            }
        });
    }

    private void setUpProfile() {
        btnOTP.setVisibility(View.GONE);
        tvSignUp.setVisibility(View.GONE);
        llLogo.setVisibility(View.GONE);
        btnEdit.setVisibility(View.VISIBLE);

        txtFirstName.setEnabled(false);
        txtLastName.setEnabled(false);
        txtPhoneNumber.setEnabled(false);
        txtEmail.setEnabled(false);
        txtFloorNo.setEnabled(false);
        txtOfficeNo.setEnabled(false);
        txtLandmark.setEnabled(false);
        txtBuildingName.setEnabled(false);
    }

    private void setUpSignUp() {
        btnOTP.setVisibility(View.VISIBLE);
        tvSignUp.setVisibility(View.VISIBLE);
        llLogo.setVisibility(View.VISIBLE);
        btnEdit.setVisibility(View.GONE);

        txtFirstName.setEnabled(true);
        txtLastName.setEnabled(true);
        txtPhoneNumber.setEnabled(true);
        txtEmail.setEnabled(true);
        txtFloorNo.setEnabled(true);
        txtOfficeNo.setEnabled(true);
        txtLandmark.setEnabled(true);
        txtBuildingName.setEnabled(true);
    }

    private void setListeners() {

        //First Name focus Change listener
        txtFirstName.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                if (String.valueOf(txtFirstName.getText()).isEmpty()) {
                    txtIllFirstName.setError("Enter your First Name");
                }
            } else {
                txtIllFirstName.setErrorEnabled(false);
            }
        });

        //Last Name focus Change listener
        txtLastName.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                if (String.valueOf(txtLastName.getText()).isEmpty()) {
                    txtIllLastName.setError("Enter Last Name");
                }
            } else {
                txtIllLastName.setErrorEnabled(false);
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
                    btnOTP.setText(R.string.hint_get_otp);
                }
            }
        });

        //Email focus change listener
        txtEmail.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (String.valueOf(txtEmail.getText()).isEmpty()) {
                    txtIllEmail.setError("Please enter your email address");
                } else if (!isEmailValid()) {
                    txtIllEmail.setError("Enter valid email address");
                }
            } else {
                txtIllEmail.setErrorEnabled(false);
            }
        });


        //OfficeNo focus Change listener
        txtOfficeNo.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                if (String.valueOf(txtFloorNo.getText()).isEmpty()) {
                    txtIllFloorNo.setError("Enter office Number");
                }
            } else {
                txtIllFloorNo.setErrorEnabled(false);
            }
        });

        //FloorNo focus Change listener
        txtFloorNo.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                if (String.valueOf(txtFloorNo.getText()).isEmpty()) {
                    txtIllOfficeNo.setError("Enter floor number");
                }
            } else {
                txtIllOfficeNo.setErrorEnabled(false);
            }
        });

        //BuildingStreetName focus Change listener
        txtBuildingName.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                if (String.valueOf(txtBuildingName.getText()).isEmpty()) {
                    txtIllBuildingName.setError("Enter building or street Name");
                }
            } else {
                txtIllBuildingName.setErrorEnabled(false);
            }
        });

        //Landmark focus Change listener
        txtLandmark.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                if (String.valueOf(txtLandmark.getText()).isEmpty()) {
                    txtIllLandmark.setError("Enter Landmark");
                }
            } else {
                txtIllLandmark.setErrorEnabled(false);
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

        btnOTP.setOnClickListener(view -> {
            if (validData()) {
//                startProgressView();
                phoneNumberAuth();
            }
        });

        btnResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendOTP(String.valueOf(txtPhoneNumber.getText()));
            }
        });

        btnSignUp.setOnClickListener(view -> {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verficationId, String.valueOf(txtOtp.getText()));
            signInWithCredential(credential);
        });
    }

    private void firebaseGetToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            // Log and toast
                            Log.d(TAG, task.getResult());
                            Toast.makeText(getContext(), task.getResult(), Toast.LENGTH_SHORT).show();
                            UserPreferences.getPrefInstance().setToken(task.getResult());
                        } else {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        }
                    }
                });
    }

    private void phoneNumberAuth() {
        onVerificationStateChangedCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "phoneAuthentication : Verification Completed");
                signInWithCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.d(TAG, "phoneAuthentication : Verification Failed");
                e.printStackTrace();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.d(TAG, "phoneAuthentication : Code Sent");
                Toast.makeText(getContext(), "OTP sent successfully!", Toast.LENGTH_LONG).show();
                enableUserManuallyInputCode();
                resendToken = forceResendingToken;
                verficationId = s;
            }
        };

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder()
                .setPhoneNumber("+91" + txtPhoneNumber.getText())
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(onVerificationStateChangedCallbacks)
                .setActivity(getActivity())
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithCredential(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            callUserRegistrationAPI();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure ", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getContext(), "OTP entered is invalid!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void startProgressView() {

    }

    private void callUserRegistrationAPI() {
        UserAPI api = APIClient.getClient().create(UserAPI.class);

        Call<UserData> call = api.userRegistration(
                txtFirstName.getText() + " " + txtFirstName.getText(),
                String.valueOf(txtBuildingName.getText()),
                String.valueOf(txtFloorNo.getText()),
                String.valueOf(txtOfficeNo.getText()),
                String.valueOf(txtLandmark.getText()),
                String.valueOf(txtPhoneNumber.getText()),
                UserPreferences.getPrefInstance().getToken(),
                "Customer"
        );

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if (response.code() == 200) {
                    if (response.body().getResponseMessage().equals("true")) {
                        Log.d(TAG, "ResponseSuccess :" + response.body().getResponseMessage());
                        Toast.makeText(getContext(), response.body().getResponseMessage(), Toast.LENGTH_LONG).show();
//                        Toast.makeText(getContext(), "Sign Up successful!", Toast.LENGTH_LONG).show();
                        UserPreferences.getPrefInstance().setUserData(response.body().getUser());
                        UserPreferences.getPrefInstance().setLoggedInFlag(true);
                        Navigation.findNavController(v).navigate(R.id.action_signUpFragment_to_mainActivitySecond);
                        getActivity().finish();
                    } else {
                        Log.d(TAG, "ResponseSuccess :" + response.body().getResponseMessage());
                        Toast.makeText(getContext(), response.body().getResponseMessage(), Toast.LENGTH_LONG).show();
                        disableUserManuallyInputCode();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Log.d(TAG, "ResponseFailure");
                t.printStackTrace();
                Toast.makeText(getContext(), "Failed to connect to the Server", Toast.LENGTH_LONG).show();
                disableUserManuallyInputCode();
            }
        });
    }

    private void enableUserManuallyInputCode() {
        txtIllOtp.setVisibility(View.VISIBLE);
        btnResendOtp.setVisibility(View.VISIBLE);
        btnOTP.setVisibility(View.GONE);
        btnSignUp.setVisibility(View.VISIBLE);
    }

    private void disableUserManuallyInputCode() {
        txtIllOtp.setVisibility(View.GONE);
        btnResendOtp.setVisibility(View.GONE);
        btnOTP.setVisibility(View.VISIBLE);
        btnSignUp.setVisibility(View.GONE);
    }

    private void resendOTP(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder()
                .setPhoneNumber("+91"+phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(getActivity())
                .setCallbacks(onVerificationStateChangedCallbacks)
                .setForceResendingToken(resendToken)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private boolean validData() {
        boolean flag = true;
        if (String.valueOf(txtFirstName.getText()).isEmpty()) {
            txtIllFirstName.setError("Enter First Name");
            flag = false;
        }
        if (String.valueOf(txtLastName.getText()).isEmpty()) {
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
        if (String.valueOf(txtOfficeNo.getText()).isEmpty()) {
            txtIllOfficeNo.setError("Enter floor or house number");
            flag = false;
        }

        if (String.valueOf(txtFloorNo.getText()).isEmpty()) {
            txtIllFloorNo.setError("Enter floor or house number");
            flag = false;
        }
        if (String.valueOf(txtBuildingName.getText()).isEmpty()) {
            txtIllBuildingName.setError("Enter building or house Name");
            flag = false;
        }
        if (String.valueOf(txtLandmark.getText()).isEmpty()) {
            txtIllLandmark.setError("Enter Landmark");
            flag = false;
        }
        return flag;
    }

    private boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(String.valueOf(txtEmail.getText())).matches();
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
        txtIllFloorNo = v.findViewById(R.id.txtIll_floor_house_no);
        txtFloorNo = v.findViewById(R.id.txt_floor_house_no);
        txtIllOfficeNo = v.findViewById(R.id.txtIll_office_no);
        txtOfficeNo = v.findViewById(R.id.txt_office_no);
        txtIllBuildingName = v.findViewById(R.id.txtIll_building_street_name);
        txtBuildingName = v.findViewById(R.id.txt_building_street_name);
        txtIllLandmark = v.findViewById(R.id.txtIll_landmark);
        txtLandmark = v.findViewById(R.id.txt_landmark);
        txtIllOtp = v.findViewById(R.id.txtIll_otp);
        txtOtp = v.findViewById(R.id.txt_otp);
        btnOTP = v.findViewById(R.id.btn_otp);
        btnSignUp = v.findViewById(R.id.btn_sign_up);
        btnResendOtp = v.findViewById(R.id.btn_resend_otp);
        tvSignUp = v.findViewById(R.id.tv_sign_up);
        sv = v.findViewById(R.id.sv);
        btnEdit = v.findViewById(R.id.btn_edit);
        llLogo = v.findViewById(R.id.ll_logo);
    }

    public boolean isPhoneNumberValid() {
        return txtPhoneNumber.getText().length() == 10;
    }

}

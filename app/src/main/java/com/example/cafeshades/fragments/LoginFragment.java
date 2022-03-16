package com.example.cafeshades.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private final static String TAG = "LoginFragment";
    View v = null;
    boolean isExistingUser;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks;
    private Button btnNewUser, btnGetOtp, btnLogin, btnResendOtp;
    private TextInputLayout txtIlPhoneNumber, txtIllOtp;
    private TextInputEditText txtPhoneNumber, txtOtp;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private String verificationId;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
//        FirebaseAuth.getInstance().getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);
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
        btnLogin = v.findViewById(R.id.btn_login);
        btnResendOtp = v.findViewById(R.id.btn_resend_otp);
        txtIlPhoneNumber = v.findViewById(R.id.txtIll_phone_number);
        txtPhoneNumber = v.findViewById(R.id.txt_phone_number);
        txtIllOtp = v.findViewById(R.id.txtIll_otp);
        txtOtp = v.findViewById(R.id.txt_otp);
    }

    private void setListeners() {
        btnNewUser.setOnClickListener(this);
        btnGetOtp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnResendOtp.setOnClickListener(this);

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
                } else if (editable.length() < 10) {
                    txtIlPhoneNumber.setError("Enter Valid Phone Number");
                    btnGetOtp.setVisibility(View.GONE);
                }
            }
        });

//        txtPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (b){
//                    txtIllOtp.setEnabled(false);
//                }else {
//                    if (txtPhoneNumber.getText().length() < 10){
//                        txtIlPhoneNumber.setError("Enter Valid Phone Number");
//                        disableUserManuallyInputCode();
//                    } else {
//                        txtIlPhoneNumber.setErrorEnabled(false);
//                        btnGetOtp.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//        });

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
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment);
        } else if (view.getId() == R.id.btn_get_otp) {
            if (isPhoneValid()) {
                phoneAuthentication();
            }
        } else if (view.getId() == R.id.btn_login) {
            if (txtOtp.getText().length() == 6) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, String.valueOf(txtOtp.getText()));
                signInWithCredential(credential);
            }else {
                Toast.makeText(getContext(), "Enter valid Phone Number", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.btn_resend_otp) {
            resendOTP(String.valueOf(txtPhoneNumber.getText()));
        }
    }

    private void callUserAPI(String mobileNumber) {

        UserAPI api = APIClient.getClient().create(UserAPI.class);
        Call<UserData> call = api.getUser(mobileNumber, "Customer");

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if (response.code() == 200) {
                    if (response.body().getResponseStatus().equals("true")) {
                        Log.d(TAG, "/ResponseStatusTrue: " + response.body().getResponseMessage());
                        UserPreferences.getPrefInstance(getContext()).setUserData(response.body().getUser());
                        UserPreferences.getPrefInstance(getContext()).setLoggedInFlag(true);
                        Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_mainActivitySecond);
                        getActivity().finish();
                    } else {
                        Log.d(TAG, "/ResponseStatusFalse: " + response.body().getResponseMessage());
                        Toast.makeText(getContext(), response.body().getResponseMessage(), Toast.LENGTH_LONG).show();
                        disableUserManuallyInputCode();
                    }
                } else {
                    Log.d(TAG, "/ResponseCode: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Log.d(TAG, "/ResponseFailure");
                t.printStackTrace();
            }
        });
    }

    private void enableUserManuallyInputCode() {
        txtOtp.setText(R.string.empty_string);
        txtIllOtp.setVisibility(View.VISIBLE);
        btnResendOtp.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.VISIBLE);
        btnGetOtp.setVisibility(View.GONE);
    }

    private void disableUserManuallyInputCode() {
        txtOtp.setText(R.string.empty_string);
        txtIllOtp.setVisibility(View.GONE);
        btnResendOtp.setVisibility(View.GONE);
        btnLogin.setVisibility(View.GONE);
        btnGetOtp.setVisibility(View.VISIBLE);
    }

    private void signInWithCredential(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            callUserAPI(String.valueOf(txtPhoneNumber.getText()));
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getContext(), "OTP Entered is invalid", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void phoneAuthentication() {
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
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                ;
                disableUserManuallyInputCode();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.d(TAG, "phoneAuthentication : Code Sent");
                Toast.makeText(getContext(), "OTP sent successfully!", Toast.LENGTH_LONG).show();
                resendToken = forceResendingToken;
                verificationId = s;
                enableUserManuallyInputCode();
            }
        };

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder()
                .setPhoneNumber("+91 " + txtPhoneNumber.getText().toString())
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(getActivity())
                .setCallbacks(onVerificationStateChangedCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
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
}

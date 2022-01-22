package com.example.familymapclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.PrecomputedText;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

import javax.xml.transform.Result;

import RequestAndResponse.AllEventsResponse;
import RequestAndResponse.LoginRequest;
import RequestAndResponse.LoginResponse;
import RequestAndResponse.PersonResponse;
import RequestAndResponse.RegisterRequest;
import RequestAndResponse.RegisterResponse;

import static android.content.ContentValues.TAG;
import static com.example.familymapclient.R.string.login_failed;
import static com.example.familymapclient.R.string.register_failed;


public class LoginFragment extends Fragment {

    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton;
    private Button mSignInButton;
    private Button mRegisterButton;
    private EditText serverHost;
    private EditText serverPort;
    private EditText userName;
    private EditText password;
    private EditText email;
    private EditText firstName;
    private EditText lastName;
    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;
    private String gender;

    public LoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        mRadioButton = (RadioButton) v.findViewById(R.id.maleRadioButton);
        mSignInButton = (Button) v.findViewById(R.id.signInButton);
        mRegisterButton = (Button) v.findViewById(R.id.registerButton);

        mRadioGroup = (RadioGroup) v.findViewById((R.id.radioGroup));
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch(checkedId) {
                    case R.id.maleRadioButton:
                        mRadioButton = (RadioButton) v.findViewById(R.id.maleRadioButton);
                        break;
                    case R.id.femaleRadioButton:
                        mRadioButton = (RadioButton) v.findViewById(R.id.femaleRadioButton);
                        break;
                }
            }
        });

        serverHost = (EditText) v.findViewById(R.id.serverHostEditText);
        serverPort = (EditText) v.findViewById(R.id.portEditText);
        userName = (EditText) v.findViewById(R.id.usernameEditText);
        password = (EditText) v.findViewById(R.id.passwordEditText);
        firstName = (EditText) v.findViewById(R.id.firstNameEditText);
        lastName = (EditText) v.findViewById(R.id.lastNameEditText);
        email = (EditText) v.findViewById(R.id.emailEditText);

        serverHost.addTextChangedListener(loginTextWatcher);
        serverPort.addTextChangedListener(loginTextWatcher);
        userName.addTextChangedListener(loginTextWatcher);
        password.addTextChangedListener(loginTextWatcher);
        firstName.addTextChangedListener(loginTextWatcher);
        lastName.addTextChangedListener(loginTextWatcher);
        email.addTextChangedListener(loginTextWatcher);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gender = mRadioButton.getText().toString();

                registerRequest = createRegisterRequest();
                RegisterTask register = new RegisterTask();
                register.execute();
            }
        });

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gender = mRadioButton.getText().toString();

                loginRequest = createLoginRequest();
                SignInTask signIn = new SignInTask();
                signIn.execute();
            }
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String serverHostInput = serverHost.getText().toString().trim();
            String serverPortInput = serverPort.getText().toString().trim();
            String usernameInput = userName.getText().toString().trim();
            String passwordInput = password.getText().toString().trim();
            String firstNameInput = firstName.getText().toString().trim();
            String lastNameInput = lastName.getText().toString().trim();
            String emailInput = email.getText().toString().trim();

            mSignInButton.setEnabled(!serverHostInput.isEmpty() && !serverPortInput.isEmpty() &&
                                       !usernameInput.isEmpty() && !passwordInput.isEmpty());
            mRegisterButton.setEnabled(!serverHostInput.isEmpty() && !serverPortInput.isEmpty() &&
                                     !usernameInput.isEmpty() && !passwordInput.isEmpty() &&
                                     !emailInput.isEmpty() && !firstNameInput.isEmpty() &&
                                     !lastNameInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private class RegisterTask extends AsyncTask<RegisterRequest, Void, RegisterResponse> {
        ServerProxy server = new ServerProxy();
        DataCache cache = DataCache.getInstance();

        @Override
        protected RegisterResponse doInBackground(RegisterRequest... registerRequests) {
            cache.setServerHost(serverHost.getText().toString().trim());
            cache.setServerPort(serverPort.getText().toString().trim());


            RegisterResponse response = server.register(registerRequest);
            return response;
        }

        @Override
        protected void onPostExecute(RegisterResponse response) {
            if (!response.equals(null) && response.isSuccess()) {
                cache.setAuthToken(response.getAuthToken());
                cache.setCurrentPersonID(response.getPersonID());
                DataTask dataTask = new DataTask();
                dataTask.execute();


            }
            else {
                Toast.makeText(getContext(), register_failed, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private class SignInTask extends AsyncTask<LoginRequest, URL, LoginResponse> {

        ServerProxy server = new ServerProxy();
        DataCache cache = DataCache.getInstance();

        @Override
        protected LoginResponse doInBackground(LoginRequest... loginRequests) {
            cache.setServerHost(serverHost.getText().toString().trim());
            cache.setServerPort(serverPort.getText().toString().trim());

            LoginResponse response = server.login(loginRequest);
            return response;
        }

        @Override
        protected void onPostExecute(LoginResponse response) {
            if (!response.equals(null) && response.isSuccess()) {
                cache.setAuthToken(response.getAuthToken());
                cache.setCurrentPersonID(response.getPersonID());

                DataTask dataTask = new DataTask();
                dataTask.execute();
            }
            else {
                Toast.makeText(getContext(), login_failed, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class DataTask extends AsyncTask<Boolean, Void, Boolean> {

        ServerProxy server = new ServerProxy();
        DataCache cache = DataCache.getInstance();
        AllEventsResponse eventsResponse;
        PersonResponse personResponse;


        @Override
        protected Boolean doInBackground(Boolean... Booleans) {
            Boolean success;
            personResponse = server.getPeople(cache.getAuthToken());
            if (personResponse.isSuccess()) {
                cache.setAllPeople(personResponse.getPersons());
            }
            eventsResponse = server.getEvents(cache.getAuthToken());
            if (eventsResponse.isSuccess()) {
                cache.setAllEvents(eventsResponse.getEvents());
            }
            success = personResponse.isSuccess() && eventsResponse.isSuccess();
            return (success);
        }


        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                cache.setName();
                cache.partitionData();
                System.out.println("Data obtained");
                Toast.makeText(getContext(), cache.getDisplayName(), Toast.LENGTH_SHORT).show();
                openMapFragment();

            }
            else {
                System.out.println("Data not obtained");
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void openMapFragment() {
        DataCache cache = DataCache.getInstance();
        cache.setLoggedIn(true);
        MapFragment map = new MapFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, map);
        transaction.commit();
    }

    public LoginRequest createLoginRequest() {
        LoginRequest request = new LoginRequest();
        request.setUserName(userName.getText().toString().trim());
        request.setPassword(password.getText().toString().trim());
        return request;
    }


    public RegisterRequest createRegisterRequest() {
        RegisterRequest request = new RegisterRequest();
        request.setUserName(userName.getText().toString().trim());
        request.setPassword(password.getText().toString().trim());
        request.setEmail(email.getText().toString().trim());
        request.setFirstName(firstName.getText().toString().trim());
        request.setLastName(lastName.getText().toString().trim());
        request.setGender(gender);
        return request;
    }

}
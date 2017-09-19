package com.example.alaeddin.loginappapi;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class LoginActivity extends AppCompatActivity {

    Button btn_Login;
    EditText ET_Email,ET_Password;
    String EmailStr,PasswordStr,ReturnResult,doLoginResult;

    /** Web Service */
    public static String URL = "http://api.a-hamoud.com/account.asmx?WSDL";
    public static String NAMESPACE = "http://api.a-hamoud.com";
    /** Login */
    public static String SOAP_ACTION_Login = "http://api.a-hamoud.com/LoginAPI";
    public static String METHOD_NAME_Login = "LoginAPI";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ET_Email=(EditText)findViewById(R.id.ETEmail);
        ET_Password=(EditText)findViewById(R.id.ETPassword);

        btn_Login=(Button)findViewById(R.id.btnlogin);

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmailStr=ET_Email.getText().toString();
                PasswordStr=ET_Password.getText().toString();
                if(EmailStr.isEmpty()||PasswordStr.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Please, Enter Your Email and Password.", Toast.LENGTH_SHORT).show();
                }
                else{

                   new MyAsyncTask().execute(EmailStr,PasswordStr);
                }
            }
        });
    }

    private class MyAsyncTask extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... strings) {

            SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME_Login);

            PropertyInfo infoEmail=new PropertyInfo();
            infoEmail.setName("email");
            infoEmail.setType(String.class);
            infoEmail.setValue(strings[0].toString());
            request.addProperty(infoEmail);

            PropertyInfo infoPassword=new PropertyInfo();
            infoPassword.setName("password");
            infoPassword.setType(String.class);
            infoPassword.setValue(strings[1].toString());

            //Use this to add parameters
            request.addProperty(infoPassword);
            // request.addProperty("password", voids[1].toString());

            //Declare the version of the SOAP request
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            try {
                //Thread.sleep(5000);
                //  AndroidHttpTransport
                //this is the actual part that will call the webservice
                androidHttpTransport.call(SOAP_ACTION_Login, envelope);
                //  SoapPrimitive resultee=(SoapPrimitive)envelope.getResponse();
                // Get the SoapResult from the envelope body.
                SoapObject result = (SoapObject) envelope.bodyIn;

                if (result != null) {
                    //Get the first property and change the label text
                    ReturnResult = result.getProperty(0).toString();
//String msg=resultee.toString();
                    //    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }

            return ReturnResult;
        }


        protected void onPostExecute(String result) {
            // This method is executed in the UIThread
            // with access to the result of the long running task


            if(result.equals("true")){
                Intent MyHomeActivity=new Intent(LoginActivity.this,MainActivity.class);
                MyHomeActivity.putExtra("GetEmail",EmailStr);
                //No Full Name
                //MyHomeActivity.putExtra("GetDisplayName",user.getDisplayName());
                //  MyHomeActivity.putExtra("GetPhotoUrl",user.getPhotoUrl());
                startActivity(MyHomeActivity);
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(), "User Name or Password inccorret", Toast.LENGTH_LONG).show();
                // Hide the progress bar
                //  progressBar.setVisibility(View.GONE);
            }

        }

    }


}

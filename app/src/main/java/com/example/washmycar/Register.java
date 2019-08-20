package com.example.washmycar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity implements View.OnClickListener {

    ImageView profile;
    InputStream is;
    Button register;
    TextView filename;
    EditText name,email,password,contact,address;
    SharedPreferences prf;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        name = findViewById(R.id.editText1);
        email = findViewById(R.id.editText2);
        password = findViewById(R.id.editText3);
        contact = findViewById(R.id.editText4);
        address = findViewById(R.id.editText5);
        profile = findViewById(R.id.imageView1);
        register = findViewById(R.id.book1);
        filename = findViewById(R.id.textView4);
        register.setOnClickListener(this);
        profile.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

       if(resultCode==RESULT_OK)
       {
           String filename1 = data.getData().getPath();
           Uri uriImage = data.getData();
           profile.setImageURI(uriImage);
           filename.setText(filename1);
       }

//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        URL url = classLoader.getResource("path/to/folder");
//        File file = new File(url.toURI());

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id){
            case R.id.imageView1:

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 100);
                break;

            case R.id.book1:
                prf = getSharedPreferences("user_details",MODE_PRIVATE);

                String picture = filename.getText().toString();
                //Toast.makeText(getApplicationContext(), picture, Toast.LENGTH_SHORT).show();

                String r_name = name.getText().toString();
                String r_email = email.getText().toString();
                String r_password = password.getText().toString();
                String r_contact = contact.getText().toString();
                String r_address = address.getText().toString();

                List<NameValuePair> nameValuePairs = new ArrayList<>(1);
                nameValuePairs.add(new BasicNameValuePair("seeker_name", r_name));
                nameValuePairs.add(new BasicNameValuePair("seeker_email", r_email));
                nameValuePairs.add(new BasicNameValuePair("seeker_password", r_password));
                nameValuePairs.add(new BasicNameValuePair("seeker_telephone", r_contact));
                nameValuePairs.add(new BasicNameValuePair("seeker_address", r_address));
                nameValuePairs.add(new BasicNameValuePair("seeker_image", picture));

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://192.168.43.118/washmycar/index.php/androidcontroller/register");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is=entity.getContent();
                    Toast.makeText(getApplicationContext(), "Register", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(this, MainActivity.class);
                    startActivity(intent1);
                    //			txtname.setText("");
                    //			address.setText(caddress);
                    //			txtcontact.setText("");
                    //			txtusername.setText("");
                    //			txtpassword.setText("");


                }
                catch(ClientProtocolException e)
                {
                    Log.e("ClientProtocol","Log_tag");
                    e.printStackTrace();
                }
                catch(IOException e)
                {
                    Log.e("Log_tag", "IOException");
                    e.printStackTrace();
                }

                break;



        }


    }


}

package com.example.washmycar;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddVihicle extends AppCompatActivity implements View.OnClickListener {

    private MenuItem item;
    EditText plate_number,brand_name,model,color;
    ImageView img;
    Button btnAddVehicle;
    InputStream is;
    SharedPreferences prf;

    private static final int STORAGE_PERMISSION_CODE = 4655;
    private Uri filepath;
    private Bitmap bitmap;
    private static final int PICK_IMAGE_REQUEST = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final String UPLOAD_URL = "http://192.168.43.19/washmycar/index.php/androidcontroller/register";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_vihicle);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.sample);
        getSupportActionBar().setDisplayUseLogoEnabled(true);



        requestStoragePermission();

        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        plate_number = findViewById(R.id.editText1);
        brand_name = findViewById(R.id.editText2);
        model = findViewById(R.id.editText3);
        color = findViewById(R.id.editText4);
        img = findViewById(R.id.imageView14);
        btnAddVehicle = findViewById(R.id.book1);

        img.setOnClickListener(this);
        btnAddVehicle.setOnClickListener(this);

        String customer_id = prf.getString("seeker_id", "");



    }

    private void requestStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

//       if(resultCode==RESULT_OK)
//       {
//           String filename1 = data.getData().getPath();
//           Uri uriImage = data.getData();
//           profile.setImageURI(uriImage);
//           filename.setText(filename1);
//       }

        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {

            filepath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                img.setImageBitmap(bitmap);
            } catch (Exception ex) {

            }
        }

//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        URL url = classLoader.getResource("path/to/folder");
//        File file = new File(url.toURI());

    }

    private String getPath(Uri uri) {

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + "=?", new String[]{document_id}, null
        );
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }


    //menus
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.commonmenus,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.item = item;
        int id = item.getItemId();
        if (id==R.id.home){
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,DashBoard.class));
        }
        else
        if (id==R.id.settings){
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,Profile.class));
        }
        else
        if (id==R.id.logout){
            Toast.makeText(this, "Thank you for Using Washmycar", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    //    end of menu

    @Override
    public void onClick(View view) {


        int id = view.getId();

        switch (id) {
            case R.id.imageView14:

//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");
//                startActivityForResult(intent, 100);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                break;

            case R.id.book1:

                prf = getSharedPreferences("user_details",MODE_PRIVATE);


                String customer_id = prf.getString("seeker_id", "");

                String picture = getPath(filepath);

                String p_number = plate_number.getText().toString();
                String b_name = brand_name.getText().toString();
                String model_carwash = model.getText().toString();
                String p_color = color.getText().toString();


                List<NameValuePair> nameValuePairs = new ArrayList<>(1);
                nameValuePairs.add(new BasicNameValuePair("seeker_id", customer_id));
                nameValuePairs.add(new BasicNameValuePair("plate_number", p_number));
                nameValuePairs.add(new BasicNameValuePair("brand_name", b_name));
                nameValuePairs.add(new BasicNameValuePair("color", p_color));
                nameValuePairs.add(new BasicNameValuePair("model", model_carwash));
                nameValuePairs.add(new BasicNameValuePair("image_vehicle", picture));

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://192.168.43.19/washmycar/index.php/androidcontroller/add_vehicle");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is=entity.getContent();
                    Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(this, Profile.class);
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

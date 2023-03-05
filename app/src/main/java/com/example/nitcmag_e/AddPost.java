package com.example.nitcmag_e;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nitcmag_e.Login_and_signup.LoginPage;
import com.example.nitcmag_e.Login_and_signup.SignUpPage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

public class AddPost extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    TextView signout;
    Button articleImage, submit;
    EditText title,description;
    String item[] = {"Select Category","Home","Educational","Technical","Educational","Fest"};
    ArrayAdapter<String > arrayAdapter;
    Spinner autoCompleteTextView;

    boolean imgControl = false;
    Uri imageUri;
    String key;
    String imageSelectedName;

    String categorySelected = null;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();

    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        signout = findViewById(R.id.button2);
        autoCompleteTextView = findViewById(R.id.category);
        articleImage = findViewById(R.id.selectImg);
        title = findViewById(R.id.editTextTitle);
        description = findViewById(R.id.editTextTextMultiLineDescription);
        submit = findViewById(R.id.SubmitForReview);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();



        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(AddPost.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        arrayAdapter = new ArrayAdapter<String >(this,R.layout.category_drop_down_menu,item);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorySelected = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        articleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
                System.out.println(imgControl);
                if (imgControl) {
//                    Cursor cursor = getContentResolver().query(imageUri,null,null,null,null);
//                    int imageSelected = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
//                    imageSelectedName = cursor.getString(imageSelected);
//                    System.out.println(imageSelectedName);
                    articleImage.setText("imageSelectedName");
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticleDetails articleDetails = new ArticleDetails(title.getText().toString(),description.getText().toString(),categorySelected,auth.getCurrentUser().getUid());
                key = reference.child("Article").push().getKey();

                reference.child("Article").child(key).setValue(articleDetails);
                setArticleImage();
                Intent intent = new Intent(AddPost.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private void imageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imgControl = true;
        }
        else
        {
            imgControl = false;
        }
    }

    private void setArticleImage() {
        if(imgControl)
        {
            UUID randomId = UUID.randomUUID();
            String imgName = "images/" + randomId + ".jpg";
            System.out.println(imgName);

            storageReference.child(imgName).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    StorageReference myStorageRef = storage.getReference(imgName);
                    myStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String filePath = uri.toString();
                            reference.child("Article").child(key).child("Article Image").setValue(filePath).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(AddPost.this, "Success", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddPost.this, "Fail", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddPost.this, "this fail", Toast.LENGTH_SHORT).show();
                }
            });

        }

        else
        {
            reference.child("Article").child(key).child("Article Image").setValue("null");
        }
    }
}
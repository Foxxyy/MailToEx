package com.android.foxxyy.mailtoex;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    Bitmap bitmap;

    public void send(View view) {
        String text = getString(R.string.ok);
        EditText textOfMes = (EditText) findViewById(R.id.mesText);
        EditText emailOfMes = (EditText) findViewById(R.id.mesSubject);
        ImageView imV = (ImageView) findViewById(R.id.picture);
        TextView mes = (TextView) findViewById(R.id.userMessages);
        CheckBox cbg = (CheckBox) findViewById(R.id.cbg);
        CheckBox cbb = (CheckBox) findViewById(R.id.cbb);
        mes.setTextColor(this.getResources().getColor(R.color.colorPrimary));

        if (cbg.isChecked() && !cbb.isChecked()) {
            text = getString(R.string.girlEr);
            mes.setTextColor(Color.RED);
        }
        if (cbb.isChecked() && !cbg.isChecked()) {
            text = getString(R.string.barEr);
            mes.setTextColor(Color.RED);
        }
        if (cbb.isChecked() && cbg.isChecked()) {
            text = getString(R.string.bothEr);
            mes.setTextColor(Color.RED);
        }
        if (!cbb.isChecked() && !cbg.isChecked()) {

            final Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            try {
                intent.putExtra(Intent.EXTRA_TEXT, textOfMes.getText());
                if (flagPhoto) {
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null)));
                }
                } catch (Exception e) {

            }
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.Subject));
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailOfMes.getText().toString()});
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
        mes.setText(text);
    }

    Boolean flagPhoto = false;
    public void addPhoto(View view) {
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.photo);
        ViewGroup.LayoutParams params = rl.getLayoutParams();
        Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);

        Button add = (Button) findViewById(R.id.phBut);

        if(rl.getVisibility()==View.INVISIBLE){
            rl.startAnimation(slideDown);
            rl.setVisibility(View.VISIBLE);
            params.height = -2;
            params.width = -1;
            rl.setLayoutParams(params);
            add.setText(getString(R.string.delPic));
            RadioGroup rg = (RadioGroup) findViewById(R.id.radGr);
            rg.check(R.id.without);
        } else {
            rl.startAnimation(slideUp);
            rl.setVisibility(View.INVISIBLE);
            params.height = 0;
            rl.setLayoutParams(params);
            add.setText(getString(R.string.takePic));
            flagPhoto = false;
            ImageView iv = (ImageView) findViewById(R.id.picture);
            iv.setImageResource(R.drawable.camera);
        }
    }


    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView picture = (ImageView) findViewById(R.id.picture);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.photo);
        ViewGroup.LayoutParams params = rl.getLayoutParams();
        ViewGroup.LayoutParams params2 = picture.getLayoutParams();
        RadioButton car = (RadioButton) findViewById(R.id.car);
        RadioButton dubai = (RadioButton) findViewById(R.id.dubai);
        RadioButton girl = (RadioButton) findViewById(R.id.girl);
        RadioButton witout = (RadioButton) findViewById(R.id.without);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Get the dimensions of the View
            int targetW = picture.getWidth();
            int targetH = picture.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions).copy(Bitmap.Config.ARGB_8888, true);
            Bitmap filtr = BitmapFactory.decodeResource(getResources(), R.drawable.car).copy(Bitmap.Config.ARGB_8888, true);
            if (!witout.isChecked()) {
                if (car.isChecked()) {
                    filtr = BitmapFactory.decodeResource(getResources(), R.drawable.car, bmOptions).copy(Bitmap.Config.ARGB_8888, true);
                }
                if (girl.isChecked()) {
                    filtr = BitmapFactory.decodeResource(getResources(), R.drawable.girl, bmOptions).copy(Bitmap.Config.ARGB_8888, true);
                }
                if (dubai.isChecked()) {
                    filtr = BitmapFactory.decodeResource(getResources(), R.drawable.dubai, bmOptions).copy(Bitmap.Config.ARGB_8888, true);
                }
                Canvas comboImage = new Canvas(bitmap);
                comboImage.drawBitmap(filtr, 10, 10, null);
            }

            flagPhoto = true;
            picture.setImageBitmap(bitmap);
        }
    }



    String mCurrentPhotoPath;
    String newCurrentPhotoPath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;
    public void loadImage(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                //...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

}

package com.example.santiago.example;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.santiago.picture.PictureManager;
import com.example.santiago.picture.PictureUtils;

import java.io.IOException;

/**
 * Created by santiago on 11/04/16.
 */
public class ExampleActivity extends Activity {

    private static final int CODE_PICTURE = 200;

    private ImageView imageView;
    private TextView textView;

    private PictureManager pictureManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_example);

        textView = (TextView) findViewById(R.id.activity_example_text);
        imageView = (ImageView) findViewById(R.id.activity_example_image);

        pictureManager = new PictureManager(this);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });
    }

    private void pickImage() {
        pictureManager.startCamera(CODE_PICTURE, PictureManager.PICTURE_FROM_ANYWHERE, "Pick from where you want the picture");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            switch (requestCode) {
                case CODE_PICTURE:
                    switch (resultCode) {
                        case RESULT_OK:
                            Uri imageUri = pictureManager.parseIntent(data);
                            imageView.setImageBitmap(PictureUtils.getBitmapFromUri(ExampleActivity.this, imageUri));
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package com.example.santiago.picture;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;

public class PictureManager {

    public static final int PICTURE_FROM_CAMERA = 1;
    public static final int PICTURE_FROM_GALLERY = 2;
    public static final int PICTURE_FROM_ANYWHERE = 3;

    private Activity activity;

    private File callbackPicture;
    private Uri outputFileUri;

    public PictureManager(@NonNull Activity activity){
        this.activity = activity;
    }

    public void startCamera(int code, int flag, @Nullable String message) {
        deletePictureFile();

        callbackPicture = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), activity.getPackageName() + "_picture" + System.currentTimeMillis() + ".jpg");

        outputFileUri = Uri.fromFile(callbackPicture);

        Intent intent = null;

        switch(flag) {
            case PICTURE_FROM_GALLERY:
                intent = PictureUtils.createPickPictureIntent();
                break;

            case PICTURE_FROM_CAMERA:
                intent = PictureUtils.createTakePictureIntent(activity, outputFileUri);
                break;

            case PICTURE_FROM_ANYWHERE:
                intent = PictureUtils.createPickOrTakePictureIntent(activity, outputFileUri, message);
        }

        if(intent==null)
            return;

        activity.startActivityForResult(intent, code);
    }

    public void deletePictureFile() {
        if(callbackPicture!=null)
            callbackPicture.delete();

        callbackPicture = null;
        outputFileUri = null;
    }

    public Uri parseIntent(@NonNull Intent data) {
        Uri selectedImageUri;
        if ((data == null) || (data.getAction()!=null && data.getAction().equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)))
            selectedImageUri = outputFileUri;
        else  selectedImageUri = data.getData();

        if(selectedImageUri==null)
            return null;

        return outputFileUri = selectedImageUri;
    }

    public Uri getPictureUri() {
        return outputFileUri;
    }

    public File getPictureFile() {
        return callbackPicture;
    }

}

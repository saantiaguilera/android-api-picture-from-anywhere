package com.example.santiago.picture;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiago on 11/04/16.
 */
public class PictureUtils {

    public static Bitmap getBitmapFromUri(@NonNull Context context, @NonNull Uri uri) throws IOException {
        InputStream stream = context.getContentResolver().openInputStream( uri );
        Bitmap rawImage = BitmapFactory.decodeStream(stream);

        try {
            stream.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return rawImage;
    }

    public static Intent createPickOrTakePictureIntent(@NonNull Context context, @NonNull Uri outputFileUri, @Nullable String message){
        List<Intent> cameraIntents = createTakePictureIntentList(context, outputFileUri);

        Intent chooserIntent = Intent.createChooser(createPickPictureIntent(), message);

        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        return chooserIntent;
    }

    public static Intent createTakePictureIntent(@NonNull Context context, @NonNull Uri outputFileUri) {
        List<Intent> cameraIntents = createTakePictureIntentList(context, outputFileUri);

        if(cameraIntents.isEmpty())
            return null;

        Intent chooserIntent = new Intent(cameraIntents.get(0));

        cameraIntents.remove(0);

        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        return chooserIntent;
    }

    public static List<Intent> createTakePictureIntentList(@NonNull Context context, @NonNull Uri outputFileUri) {
        List<Intent> cameraIntents = new ArrayList<>();
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            String packageName = res.activityInfo.packageName;
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        return cameraIntents;
    }

    public static Intent createPickPictureIntent() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        return galleryIntent;
    }

}

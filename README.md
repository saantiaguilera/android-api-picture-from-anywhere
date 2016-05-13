# android-api-picture-from-anywhere
Pick a picture from gallery/camera/anywhere you want


Usage

```Java
private ImageView imageView;
private PictureManager pictureManager;
private static final int CODE_PICTURE = 1;

//...Stuff...

//Eg call this for start the pick image flow...
private void pickImage() {
    pictureManager.startCamera(CODE_PICTURE, PictureManager.PICTURE_FROM_ANYWHERE, "Pick from where you want the picture");
}

//And here you will be getting it
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    try {
        switch (requestCode) {
            case CODE_PICTURE:
                switch (resultCode) {
                    case RESULT_OK:
                        Uri imageUri = pictureManager.parseIntent(data);
                        imageView.setImageBitmap(PictureUtils.getBitmapFromUri(someContext, imageUri));
                }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

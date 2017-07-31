package com.yeeyuntech.framework.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.Window;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.app.Activity.RESULT_OK;

/**
 * Created by adu on 2017/5/20.
 * 图片选择器
 */

public class ImagePicker {

    private final String TAG = "ImagePicker";

    public static final int OPEN_CAMERA = 10001;
    public static final int OPEN_GALLERY = 10002;
    public static final int START_CROP = 10003;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 10004;
    public static final int MY_PERMISSIONS_REQUEST_STORAGE = 10005;


    private final String IMAGE_UNSPECIFIED = "image/*";

    private boolean useDefaultDialog = true;

    private String mDialogTitle = "更改头像";
    private String mOpenCameraItemText = "拍照";
    private String mOpenGalleryItemText = "从相册中选择照片";

    private Context context;
    private Activity activity;
    private File mImgFile;
    private String tempFileName = "image_picker_temp";
    private String cacheDir;

    private int mWidth = 300;
    private int mHeight = 300;
    private int aspectX = 1;
    private int aspectY = 1;

    //图片压缩率
    private int mQuality = 30;

    private PickActionCallback pickActionCallback;
    private PickResultCallback pickResultCallback;

    public interface PickActionCallback {
        void onPick();
    }

    public interface PickResultCallback {
        void onPickResult(Bitmap bitmap, byte[] bytes, File file);
    }

    public ImagePicker(Context context) {
        this.context = context;
        activity = (Activity) context;
    }

    public void setUseDefaultDialog(boolean enabled) {
        useDefaultDialog = enabled;
    }

    public void setImageFileCacheDir(String cacheDir) {
        this.cacheDir = cacheDir;
    }

    public void setPickResultCallback(PickResultCallback callback) {
        pickResultCallback = callback;
    }

    public void setPickActionCallback(PickActionCallback callback) {
        pickActionCallback = callback;
    }

    public void setDialogTitle(String dialogTitle) {
        mDialogTitle = dialogTitle;
    }

    public void setmOpenCameraItemText(String text) {
        mOpenCameraItemText = text;
    }

    public void setmOpenGalleryItemText(String text) {
        mOpenGalleryItemText = text;
    }

    public void setCropWidthAndHeight(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    public void setCropAspectXY(int aspectX, int aspectY) {
        this.aspectX = aspectX;
        this.aspectY = aspectY;
    }

    public void setQuality(int quality) {
        mQuality = quality;
    }


    public boolean checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE);
            return false;
        }
        return true;
    }


    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            return false;
        }
        return true;
    }


    private void showDefaultDialog() {
        if (!checkStoragePermission()) return;
        AlertDialog dialog = new AlertDialog.Builder(context).setTitle(mDialogTitle)
                .setItems(new String[]{mOpenCameraItemText, mOpenGalleryItemText}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            openCamera(getImageFile(true));
                        } else if (i == 1) {
                            openGallery();
                        }
                    }
                }).create();
        Window window = dialog.getWindow();
        if (window != null) window.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    public void show() {
        if (useDefaultDialog) {
            showDefaultDialog();
        } else {
            if (pickActionCallback != null) {
                pickActionCallback.onPick();
            }
        }
    }

    private File getImageFile() {
        return getImageFile(false);
    }

    public File getImageFile(boolean forceNew) {
        //初始化头像缓存文件
        if (mImgFile == null || forceNew) {
            File dir = new File(cacheDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            mImgFile = new File(cacheDir + "/" + tempFileName + ".png");
            if (!mImgFile.exists()) {
                try {
                    mImgFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return mImgFile;
    }

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
        activity.startActivityForResult(intent, OPEN_GALLERY);
    }

    public void openCamera(File file) {
        if (!checkCameraPermission()) return;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getImageContentUri(file));
        activity.startActivityForResult(intent, OPEN_CAMERA);
    }


    /**
     * call this method in activity's onRequestPermissionsResult()
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera(getImageFile(true));
            }
        } else if (requestCode == MY_PERMISSIONS_REQUEST_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (useDefaultDialog) {
                    showDefaultDialog();
                } else {
                    if (pickActionCallback != null) {
                        pickActionCallback.onPick();
                    }
                }
            }
        }
    }


    /**
     * call this method in activity's onActivityResult()
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case OPEN_CAMERA:
                    if (!startPhotoZoom(getImageContentUri(getImageFile()))) {
                        // 调用裁剪不成功,直接保存图片
                        saveBitmap(data);
                    }
                    break;
                case OPEN_GALLERY:
                    // 谷歌相册无法裁剪问题,将相册图片先进性复制
                    Uri uri;
                    if (copySelectPicture(data.getData())) {
                        uri = getImageContentUri(mImgFile);
                    } else {
                        uri = data.getData();
                    }
                    if (!startPhotoZoom(uri)) {
                        // 调用裁剪不成功,直接保存图片
                        saveBitmap(data);
                    }
                    break;
                case START_CROP:
                    saveBitmap(data);
                    break;
            }
        }
    }

    /**
     * 新增 不要裁剪的
     * <p>
     * call this method in activity's onActivityResult()
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResultNoCrop(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case OPEN_CAMERA:
                case OPEN_GALLERY:
                case START_CROP:
                    saveBitmapToFile(data);
                    break;
            }
        }
    }

    private boolean copySelectPicture(Uri uri) {
        boolean isCopy;
        try {
            InputStream ins = activity.getContentResolver().openInputStream(uri);
            if (ins == null) return false;
            OutputStream os = new FileOutputStream(getImageFile());
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
            isCopy = true;
        } catch (Exception e) {
            e.printStackTrace();
            isCopy = false;
        }
        return isCopy;
    }

    private Uri getImageContentUri(File imageFile) {
        Uri uri;
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = activity.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            uri = Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                uri = activity.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                uri = null;
            }
        }
        if (cursor != null)
            cursor.close();
        return uri;
    }


    private Bitmap getBitmap(Intent data) {
        Bitmap photo = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && data.getData() != null) {
                photo = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(data.getData()));
            } else {
                // in android version lower than M your method must work
                photo = data.getParcelableExtra("data");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return photo;
    }

    private void saveBitmap(Intent data) {
        Bitmap photo = getBitmap(data);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (photo != null) {
            photo.compress(Bitmap.CompressFormat.PNG, mQuality, stream);// (0 - 100)压缩文件
        }
        saveBitmapAsFile(photo, true);
    }

    //   ===============================================      这里是新增      =====================================


    public static Bitmap getPhoto(Activity ac, Uri uri) throws FileNotFoundException, IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return bitmap;//再进行质量压缩
    }


    /**
     * 新增
     *
     * @return 将bitmap 保存为 file
     */
    public void saveBitmapToFile(Intent data) {
        Bitmap photo = null;
        try {
            photo = getPhoto(activity, data.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (photo != null) {
            photo.compress(Bitmap.CompressFormat.PNG, mQuality, stream);// (0 - 100)压缩文件
        }
        saveBitmapToFile(photo, true);
    }

    /**
     * 新增
     *
     * @return 这个不是头像的 file  每一个文件都需要重新建一个 文件
     */
    public File getBitmapFile() {
        //初始化头像缓存文件
        File dir = new File(cacheDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File imgFile = new File(cacheDir + "/" + System.currentTimeMillis() + ".png");
        if (!imgFile.exists()) {
            try {
                imgFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imgFile;
    }

    /**
     * 新增
     *
     * @param bitmap
     * @param needCallback
     */
    private void saveBitmapToFile(Bitmap bitmap, boolean needCallback) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bitmapData = bos.toByteArray();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(getBitmapFile());
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (needCallback && pickResultCallback != null) {
            pickResultCallback.onPickResult(bitmap, bitmapData, getImageFile());
        }
    }

    //   ===============================================      这里是新增      =====================================

    private void saveBitmapAsFile(Bitmap bitmap, boolean needCallback) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bitmapData = bos.toByteArray();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(getImageFile());
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (needCallback && pickResultCallback != null) {
            pickResultCallback.onPickResult(bitmap, bitmapData, getImageFile());
        }
    }

    private boolean startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", mWidth);
        intent.putExtra("outputY", mHeight);
        intent.putExtra("return-data", true);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            //检测是否可以启动
            activity.startActivityForResult(intent, START_CROP);
            return true;
        } else {
            return false;
        }
    }
}

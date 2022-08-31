package com.catsruletheworld.cat;

import android.content.DialogInterface;
import android.content.Intent;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class NewPost extends AppCompatActivity implements View.OnClickListener{
    private static final int PICK_FROM_CAMERA = 0; // 촬영 -> 이미지 처리
    private static final int PICK_FROM_ALBUM = 1; // 앨범 -> 이미지 처리
    private static final int CROP_FROM_iMAGE = 2; // 이미지 크롭

    private Uri imageCaptureUri;
    private ImageView userImage;
    private String absolutePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_post);

        userImage = (ImageView) this.findViewById(R.id.new_image);
        Button uploadButton = (Button) this.findViewById(R.id.upload_button);

        uploadButton.setOnClickListener(this);
    }

    public void fromPhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시 파일 저장 경로
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        imageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    public void fromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK) return;

        switch(requestCode) {
            case PICK_FROM_ALBUM: {
                imageCaptureUri = data.getData();
                Log.d("CaT", imageCaptureUri.getPath().toString());
            }
            case PICK_FROM_CAMERA: {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(imageCaptureUri, "image/*");

                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 200);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);

                startActivityForResult(intent, CROP_FROM_iMAGE);
                break;
            }
            case CROP_FROM_iMAGE: {
                if(resultCode != RESULT_OK) return;

                final Bundle extras = data.getExtras();

                // 크롭 이미지 저장 경로
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/CaT/"+System.currentTimeMillis()+".jpg";

                if(extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    userImage.setImageBitmap(photo);

                    storedCropImage(photo, filePath);
                    absolutePath = filePath;
                    break;
                }

                // 임시 파일 삭제
                File temp_file = new File(imageCaptureUri.getPath());
                if(temp_file.exists())
                    temp_file.delete();
            }
        }
    }

    private void storedCropImage(Bitmap image, String filePath) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/CaT";
        File dirCaT = new File(dirPath);

        if(!dirCaT.exists())
            dirCaT.mkdir();

        File copiedFile = new File(filePath);
        BufferedOutputStream bufferedOutputStream = null;

        try {
            copiedFile.createNewFile();

            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(copiedFile));
            image.compress(Bitmap.CompressFormat.JPEG, 100, bufferedOutputStream);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copiedFile)));

            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.upload_button) {
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    fromPhoto();
                }
            };
            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    fromAlbum();
                }
            };
            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };

            new AlertDialog.Builder(this)
                    .setTitle("사진을 선택해주세요.")
                    .setPositiveButton("사진촬영", cameraListener)
                    .setNeutralButton("취소", cancelListener)
                    .setNegativeButton("앨범선택", albumListener)
                    .show();
        } else if(view.getId() == R.id.submit_button) {
            // db 저장

            // home 가기
            Intent backHome = new Intent(NewPost.this, Home.class);
            NewPost.this.startActivity(backHome);
            NewPost.this.finish();
            Toast.makeText(this, "게시물 작성 완료", Toast.LENGTH_SHORT).show();
        }
    }
}

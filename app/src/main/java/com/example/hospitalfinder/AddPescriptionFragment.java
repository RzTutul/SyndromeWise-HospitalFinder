package com.example.hospitalfinder;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hospitalfinder.pojos.PescriptionPojo;
import com.example.hospitalfinder.viewmodel.PescriptionViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddPescriptionFragment extends Fragment {

    private static final int REQUEST_STORAGE_CODE = 321;
    private static final int REQUEST_CAMERA_CODE = 123;
    private static final int GALLERY_REQUEST_CODE = 457;
    File file;
    TextInputEditText hostpitalNameET, doctorNameET, doctorPhoneET, apoinmentDateET;
    Button savePescriptionBtn;
    ImageView selectPescriptionImage;
    String currentPhotoPath;
    PescriptionPojo pescriptionPojo;

    PescriptionViewModel pescriptionViewModel;

    public AddPescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        pescriptionViewModel = ViewModelProviders.of(this).get(PescriptionViewModel.class);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_pescription, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hostpitalNameET = view.findViewById(R.id.hospitalNameET);
        doctorNameET = view.findViewById(R.id.doctorNameET);
        doctorPhoneET = view.findViewById(R.id.doctorPhoneET);
        apoinmentDateET = view.findViewById(R.id.selectApointmentDate);
        savePescriptionBtn = view.findViewById(R.id.savePescriptionBtn);
        selectPescriptionImage = view.findViewById(R.id.selectImage);

        savePescriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hospitalName = hostpitalNameET.getText().toString();
                String doctorName = doctorNameET.getText().toString();
                String doctorPhone = doctorPhoneET.getText().toString();
                String apoinmentDate = apoinmentDateET.getText().toString();

               pescriptionPojo = new PescriptionPojo(null,null, hospitalName, doctorName, doctorPhone, apoinmentDate, null);

               if (file!=null)
               {
                   pescriptionViewModel.uploadPescriptionToFirebaseStorage(getContext(),file, pescriptionPojo);

               }
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.pescriptionList);


            }
        });

        selectPescriptionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureSelected();
            }
        });


    }

    private void pictureSelected() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choice Option !");
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view1 = inflater.inflate(R.layout.pick_image_dilog, null);
        builder.setView(view1);


        CardView cam = view1.findViewById(R.id.cameraid);
        CardView gallery = view1.findViewById(R.id.galleryid);

        final AlertDialog dialog = builder.create();
        dialog.show();
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkStoragePermission()) {
                    dispatchCameraIntent();
                }
                dialog.dismiss();

            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkStoragePermission()) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
                }
                dialog.dismiss();

            }
        });


    }

    private boolean checkStoragePermission() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, REQUEST_STORAGE_CODE);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_CODE && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "Permission accepted", Toast.LENGTH_SHORT).show();
            dispatchCameraIntent();
        }
    }


    private void dispatchCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.hospitalfinder",
                        photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE);
            }
        }
    }

    private File createImageFile() throws IOException {
        /* Create an image file name */
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA_CODE &&
                resultCode == RESULT_OK){
            Log.e(TAG, "onActivityResult: "+currentPhotoPath);
             file = new File(currentPhotoPath);
            Uri fileUri = Uri.fromFile(file);
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),fileUri);
                selectPescriptionImage.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }

           // addPictureCard.setVisibility(View.GONE);
        }

        else if  (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(data.getData(), projection, null, null, null);
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(projection[0]);
            currentPhotoPath = cursor.getString(index);
            Log.e(TAG, "onActivityResultgalary: "+currentPhotoPath);
             file = new File(currentPhotoPath);
            Uri fileUri = Uri.fromFile(file);
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),fileUri);
                selectPescriptionImage.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //addPictureCard.setVisibility(View.GONE);

        }
    }
}

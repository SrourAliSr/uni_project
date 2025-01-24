package com.example.uniproject;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uniproject.databinding.ProfileFragmentBinding;

public class ProfilePage extends Fragment {

    private ProfileFragmentBinding binding;
    private Button saveProfile;

    private final ActivityResultLauncher<Intent> galleryResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        SharedClass.profileImageUri = selectedImageUri; // Save URI in SharedClass
                        binding.profileImageView.setImageURI(selectedImageUri); // Display the image
                    }
                }
            });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ProfileFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.editProfile.setOnClickListener(view1 -> openGallery());

        saveProfile = binding.saveProfile;
        saveProfile.setOnClickListener( view1 -> {
            SharedClass.username = binding.nameEditText.getText().toString();
            SharedClass.phoneNumber = binding.numberEditText.getText().toString();

        });
        if (SharedClass.profileImageUri != null) {
            try {
                binding.profileImageView.setImageURI(SharedClass.profileImageUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            binding.profileImageView.setImageDrawable(null);
        }

        if (SharedClass.username != null && !SharedClass.username.isEmpty()) {
            binding.nameEditText.setText(SharedClass.username);
        } else {
            binding.nameEditText.setHint("Enter your name"); // Default empty state
        }

        if (SharedClass.phoneNumber != null && !SharedClass.phoneNumber.isEmpty()) {
            binding.numberEditText.setText(SharedClass.phoneNumber);
        } else {
            binding.numberEditText.setHint("Enter your phone number"); // Default empty state
        }

      
    }

    @SuppressLint("IntentReset")
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        galleryResultLauncher.launch(intent); // Launch the gallery intent
    }
}

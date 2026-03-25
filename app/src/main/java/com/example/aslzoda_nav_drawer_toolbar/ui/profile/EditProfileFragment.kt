package com.example.aslzoda_nav_drawer_toolbar.ui.profile

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.aslzoda_nav_drawer_toolbar.R
import com.example.aslzoda_nav_drawer_toolbar.databinding.FragmentEditProfileBinding
import com.example.aslzoda_nav_drawer_toolbar.utils.ImageUtils
import com.example.aslzoda_nav_drawer_toolbar.utils.PrefManager
import java.io.File

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private var pickedImageUri: Uri? = null

    private val imagePicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                pickedImageUri = it
                binding.ivProfile.setImageURI(it)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEditProfileBinding.bind(view)

        binding.etName.setText(PrefManager.getName(requireContext()))
        binding.etEmail.setText(PrefManager.getEmail(requireContext()))

        PrefManager.getImage(requireContext())?.let { path ->
            val file = File(path)
            if (file.exists()) {
                binding.ivProfile.setImageURI(Uri.fromFile(file))
            }
        }

        binding.ivProfile.setOnClickListener {
            imagePicker.launch("image/*")
        }


        binding.btnSave.setOnClickListener {
            saveProfile()
        }
    }

    private fun saveProfile() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }


        PrefManager.saveName(requireContext(), name)
        PrefManager.saveEmail(requireContext(), email)


        pickedImageUri?.let { uri ->
            val path = ImageUtils.saveToInternal(requireContext(), uri)
            PrefManager.saveImage(requireContext(), path)
        }

        Toast.makeText(requireContext(), "Profile updated ✅", Toast.LENGTH_SHORT).show()
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

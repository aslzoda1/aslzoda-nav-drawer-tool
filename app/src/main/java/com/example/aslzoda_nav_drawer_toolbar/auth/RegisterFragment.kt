package com.example.aslzoda_nav_drawer_toolbar.auth

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.aslzoda_nav_drawer_toolbar.R
import com.example.aslzoda_nav_drawer_toolbar.databinding.FragmentRegisterBinding
import com.example.aslzoda_nav_drawer_toolbar.utils.ImageUtils
import com.example.aslzoda_nav_drawer_toolbar.utils.PrefManager

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private var pickedImageUri: Uri? = null

    private val imagePicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                pickedImageUri = it
                binding.ivProfile.setImageURI(it) // preview
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterBinding.bind(view)

        binding.ivProfile.setOnClickListener {
            imagePicker.launch("image/*")
        }

        binding.btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()

        if (name.isEmpty()) {
            binding.etName.error = "Name required"
            return
        }
        if (email.isEmpty()) {
            binding.etEmail.error = "Email required"
            return
        }


        PrefManager.saveName(requireContext(), name)
        PrefManager.saveEmail(requireContext(), email)

        pickedImageUri?.let { uri ->
            val path = ImageUtils.saveToInternal(requireContext(), uri)
            PrefManager.saveImage(requireContext(), path)
        }

        PrefManager.setRegistered(requireContext(), true)

        Toast.makeText(requireContext(), "Welcome, $name 👋", Toast.LENGTH_SHORT).show()

        // ===== NAVIGATE TO MAIN ACTIVITY =====
        (requireActivity() as AuthActivity).openMain()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

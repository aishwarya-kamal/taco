package com.candybytes.taco.ui.food

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.candybytes.taco.BuildConfig
import com.candybytes.taco.R
import com.candybytes.taco.databinding.FragmentFoodBinding
import com.candybytes.taco.ui.util.ImageUtils
import com.candybytes.taco.vo.Category
import com.candybytes.taco.vo.Nutrient
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FoodFragment : Fragment() {

    private lateinit var viewModel: FoodViewModel
    private lateinit var binding: FragmentFoodBinding
    private lateinit var category: Category
    private lateinit var adapter: NutrientAdapter
    private var nutrientsValueList = mutableListOf<Nutrient>()
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_food,
            container, false
        )

        viewModel = ViewModelProvider(this).get(FoodViewModel::class.java)

        val args = FoodFragmentArgs.fromBundle(requireArguments())

        binding.food = args.FoodDetails

        Timber.d("** Nutrient Keys ${args.FoodDetails.nutrients.keys}")

        // Initialize the adapter by passing in nutrient keys as list
        adapter = NutrientAdapter(args.FoodDetails.nutrients.keys.toList())

        binding.recyclerViewFragmentFood.adapter = adapter

        // Observe getCategory and set category name as button text
        viewModel.getCategory(args.FoodDetails.categoryId).observe(viewLifecycleOwner, {
            category = it
            Timber.d("** Category name on button- ${it.name}")
            binding.buttonCategoryFragmentFood.text = it.name
        })

        // Updates the food item to include imageUri
        viewModel.imageUri.observe(viewLifecycleOwner, {
            Timber.d("** Uri - ${it}")
            viewModel.update(it.toString(), args.FoodDetails.id)
        })

        // Navigate to category fragment upon button click
        binding.buttonCategoryFragmentFood.setOnClickListener {
            this.findNavController().navigate(
                FoodFragmentDirections.actionFoodFragmentToCategoryFragment(
                    category
                )
            )
        }

        // Display list of nutrients values
        viewModel.getFoodDetails(args.FoodDetails.id).observe(viewLifecycleOwner, {

            for (nutrient in args.FoodDetails.nutrients.keys) {
                nutrientsValueList.add(
                    Nutrient(
                        args.FoodDetails.nutrients[nutrient]?.unit.toString(),
                        args.FoodDetails.nutrients[nutrient]?.qty.toString()
                    )
                )
            }
            adapter.submitList(nutrientsValueList)
        })

        // Request camera on fab click
        binding.fabAddPictureItemFoodDetail.setOnClickListener {
            requestCamera(callback = captureFoodImage)
        }

        return binding.root
    }


    // Capture and set the imageUri
    private val captureFoodImage: Runnable = Runnable {

        ImageUtils.createFoodImageFile(requireContext())?.also {
            imageUri = FileProvider.getUriForFile(
                requireContext(),
                BuildConfig.APPLICATION_ID + ".fileprovider",
                it
            )
            captureFoodImageRegistration.launch(imageUri)
            imageUri.let { imageUri ->
                if (imageUri != null) {
                    viewModel.setImageUri(imageUri)
                }
            }
        }
    }

    // Take a picture and when it's successful set it on image view using Glide
    private val captureFoodImageRegistration =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->

            if (isSuccess) {
                Timber.d("** imageuri $imageUri")
                Glide.with(requireView().context)
                    .load(imageUri)
                    .placeholder(R.drawable.animation_loading)
                    .fitCenter()
                    .error(R.drawable.ic_broken_image)
                    .into(binding.imageViewFragmentFood)
            }
        }

    // Launch camera if permission granted
    private fun requestCamera(callback: Runnable? = null) {
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { permissionGiven: Boolean ->

            val message = if (permissionGiven) {
                "Permission to use Camera granted!"
            } else {
                "No permission to use Camera!"
            }

            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

            if (permissionGiven) {
                callback?.run()
            }
        }.launch(Manifest.permission.CAMERA)
    }

}
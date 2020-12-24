package com.candybytes.taco.ui.food

import android.Manifest
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.candybytes.taco.BuildConfig
import com.candybytes.taco.R
import com.candybytes.taco.databinding.FragmentFoodBinding
import com.candybytes.taco.ui.util.ImageUtils
import com.candybytes.taco.ui.util.NUTRIENTS_LIST
import com.candybytes.taco.vo.Category
import com.candybytes.taco.vo.Nutrient
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FoodFragment : Fragment() {

    private var nutrientsValue = mutableListOf<String>()
    private var nutrientsValueList = mutableListOf<Nutrient>()

    private var imageUri: Uri? = null

    private val takePicture: Runnable = Runnable {
        ImageUtils.createImageFile(requireContext())?.also {
            imageUri = FileProvider.getUriForFile(
                requireContext(),
                BuildConfig.APPLICATION_ID + ".fileprovider",
                it
            )
            takePictureRegistration.launch(imageUri)
        }
    }

    private val takePictureRegistration =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                Glide.with(requireView().context)
                    .load(imageUri)
                    .placeholder(R.drawable.animation_loading)
                    .fitCenter()
                    .error(R.drawable.ic_broken_image)
                    .into(binding.imageViewFragmentFood)
            }
        }

    fun onRequestCameraClick(callback: Runnable? = null) {
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->

            val message = if (isGranted) {
                "Camera permission has been granted!"
            } else {
                "Camera permission denied! :("
            }

            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

            if (isGranted) {
                callback?.run()
            }
        }.launch(Manifest.permission.CAMERA)
    }

    private lateinit var viewModel: FoodViewModel
    private lateinit var binding: FragmentFoodBinding
    private lateinit var category: Category
    private lateinit var adapter: NutrientAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_food,
            container, false
        )

        viewModel = ViewModelProvider(this).get(FoodViewModel::class.java)

        val args = FoodFragmentArgs.fromBundle(requireArguments())

        binding.food = args.FoodDetails

        Timber.d("** yo ${args.FoodDetails.nutrients.keys}")

        adapter = NutrientAdapter(args.FoodDetails.nutrients.keys.toList())

        binding.recyclerViewFragmentFood.adapter = adapter

        viewModel.getCategory(args.FoodDetails.categoryId).observe(viewLifecycleOwner, {
            category = it
            Timber.d("********** Category name on button- ${it.name}")
            binding.buttonCategoryFragmentFood.text = it.name
        })

        binding.buttonCategoryFragmentFood.setOnClickListener {
            this.findNavController().navigate(
                FoodFragmentDirections.actionFoodFragmentToCategoryFragment(
                    category
                )
            )
        }

        viewModel.getFoodDetails(args.FoodDetails.id).observe(viewLifecycleOwner, {

            for (nutrient in NUTRIENTS_LIST){

                nutrientsValueList.add(
                    Nutrient(
                        args.FoodDetails.nutrients[nutrient]?.unit.toString(),
                        args.FoodDetails.nutrients[nutrient]?.qty.toString()
                    )
                )

            }

            adapter.submitList(nutrientsValueList)
        })

        if (ImageUtils.createImageFile(requireContext()) != null){

            Glide.with(requireContext())
                .load(ImageUtils.createImageFile(requireContext()))
                .placeholder(R.drawable.animation_loading)
                .fitCenter()
                .error(R.drawable.ic_broken_image)
                .into(binding.imageViewFragmentFood)
        }

        binding.fabAddPictureItemFoodDetail.setOnClickListener {
            onRequestCameraClick(callback = takePicture)
        }

        return binding.root
    }
}
package com.example.finalproject.presentation.screen.wallpaper_details

import android.os.Parcel
import android.os.Parcelable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.finalproject.databinding.FragmentWallpaperDetailsBinding
import com.example.finalproject.databinding.WallpaperSettingDialogBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.event.WallpaperDetailsEvent
import com.example.finalproject.presentation.extension.loadImage
import com.example.finalproject.presentation.extension.showSnackBar
import com.example.finalproject.presentation.state.wallpaper_details.WallpaperDetailsState
import com.example.finalproject.presentation.util.WallpaperUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WallpaperDetailsFragment() :
    BaseFragment<FragmentWallpaperDetailsBinding>(FragmentWallpaperDetailsBinding::inflate),
    Parcelable {

    private val wallpaperDetailsViewModel: WallpaperDetailsViewModel by viewModels()
    private val args: WallpaperDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var wallpaperUtil: WallpaperUtil

    constructor(parcel: Parcel) : this() {}

    override fun setUp() {
        wallpaperDetailsViewModel.onEvent(
            WallpaperDetailsEvent.GetData(
                args.imageId
            )
        )
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                wallpaperDetailsViewModel.imageDetailsStateFlow.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun showWallpaperSettingDialog(imgUrl: String) {
        val dialogBinding = WallpaperSettingDialogBinding.inflate(LayoutInflater.from(context))
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(dialogBinding.root)

        val window = dialog.window
        window?.setGravity(Gravity.BOTTOM)

        val layoutParams = window?.attributes
        layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
        window?.attributes = layoutParams

        dialogBinding.btnSetLockScreen.setOnClickListener {
            wallpaperUtil.downloadAndSetWallpaper(imageUrl = imgUrl, setHomeScreen = false, setLockScreen = true)
            dialog.dismiss()
        }

        dialogBinding.btnSetHomeScreen.setOnClickListener {
            wallpaperUtil.downloadAndSetWallpaper(imageUrl = imgUrl, setHomeScreen = true, setLockScreen = false)
            dialog.dismiss()
        }

        dialogBinding.btnSetBoth.setOnClickListener {
            wallpaperUtil.downloadAndSetWallpaper(imageUrl = imgUrl, setHomeScreen = true, setLockScreen = true)
            dialog.dismiss()
        }

        dialogBinding.btnSetAsProfileImage.setOnClickListener {
            wallpaperDetailsViewModel.onEvent(WallpaperDetailsEvent.SetUserImageEvent(imgUrl))
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun handleState(state: WallpaperDetailsState) {
        binding.progressBarDetails.isVisible = state.isLoading

        state.data?.let {
            with(binding) {
                imageViewDetailsImage.loadImage(it.webformatURL)
                tvViewsNumber.text = it.views.toString()
                tvDownloadsNumber.text = it.downloads.toString()
                tvLikesNumber.text = it.likes.toString()
                tvCommentsNumber.text = it.comments.toString()
                container.visibility = View.VISIBLE
            }
        }
        if (state.errorMessage.isNotEmpty())
            binding.root.showSnackBar(state.errorMessage)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {}

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WallpaperDetailsFragment> {
        override fun createFromParcel(parcel: Parcel): WallpaperDetailsFragment {
            return WallpaperDetailsFragment(parcel)
        }

        override fun newArray(size: Int): Array<WallpaperDetailsFragment?> {
            return arrayOfNulls(size)
        }
    }
}
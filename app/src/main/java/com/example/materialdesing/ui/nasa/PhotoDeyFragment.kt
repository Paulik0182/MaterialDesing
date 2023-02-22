package com.example.materialdesing.ui.nasa

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.text.*
import android.text.style.BulletSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.TypefaceSpan
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.provider.FontRequest
import androidx.core.provider.FontsContractCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.transition.*
import com.example.materialdesing.App
import com.example.materialdesing.R
import com.example.materialdesing.databinding.FragmentPhotoDescriptionCoordinatorBinding
import com.example.materialdesing.domain.entity.PhotoDto
import com.example.materialdesing.domain.repo.PhotoRepo
import com.example.materialdesing.utils.indexesOf
import com.example.materialdesing.utils.toastMake
import com.squareup.picasso.Picasso

class PhotoDeyFragment : Fragment(R.layout.fragment_photo_description_coordinator) {

    private var _binding: FragmentPhotoDescriptionCoordinatorBinding? = null
    private val binding get() = _binding!!

    lateinit var spannableRainbowDate: SpannableString//объеденяет все
    lateinit var spannableRainbowTitle: SpannableString//объеденяет все

    private var flagVisible = true
    private var flagApproximationImage = true
    private var positionButtonFab = true

    private val app: App get() = requireActivity().application as App

    private val photoRepo: PhotoRepo by lazy {
        app.photoRepo
    }

    private val viewModel: PhotoDeyViewModel by viewModels {
        PhotoDeyViewModel.Factory(
            photoRepo
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPhotoDescriptionCoordinatorBinding.bind(view)

        onClickIcon()

        viewModel.inProgressLiveData.observe(viewLifecycleOwner) { inProgress ->
            binding.photoDeyImageView.isVisible = !inProgress
            binding.progressTaskBar.isVisible = inProgress
        }

        binding.todayChip.performClick()
        setPhotoDey()

        binding.todayChip.setOnClickListener {
            setPhotoDey()
            context?.toastMake("Сегодня")
        }

        binding.yesterdayChip.setOnClickListener {
            viewModel.yesterdayLiveData.observe(viewLifecycleOwner) {
                setPhotoDto(it)
            }
            context?.toastMake("Вчера")
        }

        binding.twoDaysAgoChip.setOnClickListener {
            viewModel.twoDaysAgoLiveData.observe(viewLifecycleOwner) {
                setPhotoDto(it)
            }
            context?.toastMake("Позавчера")
        }

        binding.inputLayoutChipGroup.visibility = View.GONE

        setApproximationImage()

        actionСlickingOnFab()
    }

    private fun actionСlickingOnFab() {
        binding.fab.setOnClickListener {
            // setPhotoDey() // возврат на сегодняшний день
            flagVisible = !flagVisible

            // Все должно быть из androidX. Проверять если будет ругатся
            val myAutoTransition =
                TransitionSet()// состоит из нескольких параметров, поэтому TransitionSet
//            myAutoTransition.ordering = TransitionSet.ORDERING_SEQUENTIAL
            myAutoTransition.ordering = TransitionSet.ORDERING_TOGETHER
//            val fade = Hold()
            val fade = Slide(Gravity.BOTTOM)
            fade.duration = 1_000L
            val changeBounds = ChangeBounds()
            changeBounds.duration = 1_000L
            myAutoTransition.addTransition(changeBounds)
            myAutoTransition.addTransition(fade)
            TransitionManager.beginDelayedTransition(binding.transitionsContainer, myAutoTransition)

            binding.inputLayoutChipGroup.visibility = if (flagVisible) View.GONE else View.VISIBLE
//            binding.todayChip.performClick() // устанавливаем отметку нажатия

            Handler(Looper.getMainLooper()).postDelayed({
                // Движение Fab
                positionButtonFab = !positionButtonFab
                val params = it.layoutParams as FrameLayout.LayoutParams
                val changeBoundsFab = ChangeBounds()
                changeBoundsFab.duration = 2000L
                changeBoundsFab.setPathMotion(ArcMotion())
                TransitionManager.beginDelayedTransition(binding.root, changeBoundsFab)
                if (positionButtonFab) {
                    params.gravity = Gravity.END or Gravity.BOTTOM
                } else {
                    params.gravity = Gravity.START or Gravity.BOTTOM
                }
                binding.fab.layoutParams = params
            }, 1_002)
        }
    }

    // Приближение (увеличение) картинки по нажатию на нее
    private fun setApproximationImage() {
        binding.photoDeyImageView.setOnClickListener {
            flagApproximationImage = !flagApproximationImage

            // плавное сокрытие элементов
            val myAutoTransition =
                TransitionSet()// состоит из нескольких параметров, поэтому TransitionSet
//            myAutoTransition.ordering = TransitionSet.ORDERING_SEQUENTIAL
            myAutoTransition.ordering = TransitionSet.ORDERING_TOGETHER
//            val fade = Hold()
            val fade = Slide(Gravity.BOTTOM)
            fade.duration = 2_000L
            val changeBounds1 = ChangeBounds()
            changeBounds1.duration = 2_000L
            myAutoTransition.addTransition(changeBounds1)
            myAutoTransition.addTransition(fade)
            TransitionManager.beginDelayedTransition(binding.transitionsContainer, myAutoTransition)
            binding.coordinatorLayout.visibility =
                if (flagApproximationImage) View.GONE else View.VISIBLE

            // Приближение (увеличение) картинки
            val params = it.layoutParams as LinearLayout.LayoutParams
            val transitionSet = TransitionSet()
            val changeImageTransform = ChangeImageTransform()
            val changeBounds = ChangeBounds()
            changeBounds.duration = 2000L
            changeImageTransform.duration = 2000L

            transitionSet.ordering = TransitionSet.ORDERING_TOGETHER
            transitionSet.addTransition(changeBounds)// важен порядок, обязетельно changeImageTransform после changeBounds
            transitionSet.addTransition(changeImageTransform)

            TransitionManager.beginDelayedTransition(binding.root, transitionSet)
            if (flagApproximationImage) {
                params.height = LinearLayout.LayoutParams.MATCH_PARENT
                binding.photoDeyImageView.scaleType = ImageView.ScaleType.CENTER_CROP
            } else {
                params.height = LinearLayout.LayoutParams.WRAP_CONTENT
                binding.photoDeyImageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            }
            binding.photoDeyImageView.layoutParams = params
        }
    }


    private fun onClickIcon() {
        binding.inputLayout.setEndIconOnClickListener {
            Toast.makeText(requireContext(), "Wiki", Toast.LENGTH_SHORT).show()
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse(
                        "https://en.wikipedia.org/wiki/" +
                                binding.inputEditText.text.toString()
                    )
            })
        }
    }

    private fun setPhotoDey() {
        viewModel.photoDeyLiveData.observe(viewLifecycleOwner) {
            setPhotoDto(it)
        }
    }

    @SuppressLint("NewApi") // не потерять пользователей с 24 по 27 sdk
    private fun setPhotoDto(photoDto: PhotoDto) {
//        binding.dateTextView.text = photoDto.date

//        binding.titleTextView.text = photoDto.title
//        binding.explanationTextView.text = photoDto.explanation

        if (photoDto.url.isNotBlank()) {
            Picasso.get()
                .load(photoDto.url)
                .placeholder(R.drawable.uploading_images)
                .into(binding.photoDeyImageView)
        }

        val spanned: Spanned
        val spannableString: SpannableString
        val spannableStringTitle: SpannableString
        var spannableStringBuilder: SpannableStringBuilder
        var spannableStringBuilderDate: SpannableStringBuilder
        var spannableStringBuilderTitle: SpannableStringBuilder


        val text = "My text \nbullet one \nbullet two"

        // применение spannableStringBuilder без пересоздания и использования жизненых циклов (работаем с текстом на лету)
        spannableStringBuilder = SpannableStringBuilder(photoDto.explanation)
        binding.explanationTextView.setText(spannableStringBuilder, TextView.BufferType.EDITABLE)
        spannableStringBuilder = binding.explanationTextView.text as SpannableStringBuilder

        for (i in photoDto.explanation.indices) {
            if (photoDto.explanation[i] == '.') {
//                spannableStringBuilder.insert(i,".\n")
                spannableStringBuilder.replace(i, i, ".\n")
            }
        }

        val result =
            photoDto.explanation.indexesOf("  ") // поиск соответствующего символа (два пробела)
        Log.d("@@@", result.toString())

        var current =
            result.lastIndexOf(0) //.first() // заполнили текущий элемент (пропускаем нулевую позицию) можно установить 0 и тогда первый также будет отмечен)
        result.forEach {
            if (current != it) {
                spannableStringBuilder.setSpan(
                    BulletSpan(20, ContextCompat.getColor(requireContext(), R.color.red), 20),
                    current + 1, it, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            current = it
        }
        spannableStringBuilder.setSpan(
            BulletSpan(20, ContextCompat.getColor(requireContext(), R.color.red), 20),
            current + 1, photoDto.explanation.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // отметили все буквы "t" красным цветом (прошли по массиву, выбрали индекс и перекрасили букву)
        for (i in photoDto.explanation.indices) {
            if (photoDto.explanation[i] == 't') {
                spannableStringBuilder.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.red)),
                    i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        for (i in photoDto.explanation.indices) {
            if (photoDto.explanation[i] == 'u') {
                spannableStringBuilder.setSpan(
                    ForegroundColorSpan(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.bnv_default
                        )
                    ),
                    i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        spannableStringBuilderTitle = SpannableStringBuilder(photoDto.title)
        // заменили букву на картинку в тексте
        val bitmap = ContextCompat.getDrawable(requireContext(), R.drawable.earth)!!.toBitmap()
        for (i in photoDto.title.indices) {
            if (photoDto.title[i] == 'o') {
                spannableStringBuilderTitle.setSpan(
                    ImageSpan(requireContext(), bitmap),
                    i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

//        binding.explanationTextView.text = spannableStringBuilder

        binding.titleTextView.text = spannableStringBuilderTitle

//        binding.dateTextView.typeface =
//            Typeface.createFromAsset(requireActivity().assets, "folder1/folder3/az_eret.ttf")


        spannableStringBuilderDate = SpannableStringBuilder(photoDto.date)
        // подгружаем шрифты
        val request = FontRequest(
            "com.google.android.gms.fonts", "com.google.android.gms", "Aladin",
            R.array.com_google_android_gms_fonts_certs
        )

        val callback = object : FontsContractCompat.FontRequestCallback() {
            override fun onTypefaceRetrieved(typeface: Typeface?) {
                typeface?.let {
                    spannableStringBuilderDate.setSpan(
                        TypefaceSpan(it),
                        // применение шрифта на всю строку
                        0, spannableStringBuilderDate.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }

                super.onTypefaceRetrieved(typeface)
            }
        }

        FontsContractCompat.requestFont(
            requireContext(),
            request,
            callback,
            Handler(Looper.getMainLooper())
        )

        binding.dateTextView.text = spannableStringBuilderDate

        spannableRainbowDate = SpannableString(photoDto.date)
        rainbow(1, spannableRainbowDate, binding.dateTextView)
        spannableRainbowTitle = SpannableString(photoDto.title)
        rainbow(1, spannableRainbowTitle, binding.titleTextView)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PhotoDeyFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun rainbow(i: Int = 1, span: SpannableString, container: TextView) {
        var currentCount = i
        val x = object : CountDownTimer(20000, 200) {
            override fun onTick(millisUntilFinished: Long) {
                colorText(currentCount, span, container)
                currentCount = if (++currentCount > 5) 1 else currentCount
            }

            override fun onFinish() {
                rainbow(currentCount, span, container)
            }
        }
        x.start()
    }

    private fun colorText(
        colorFirstNumber: Int,
        spannableString: SpannableString,
        container: TextView
    ) {
        container.setText(spannableString, TextView.BufferType.SPANNABLE)
        spannableRainbowTitle = container.text as SpannableString
        spannableRainbowDate = container.text as SpannableString
        val map = mapOf(
            0 to ContextCompat.getColor(requireContext(), R.color.red),
            1 to ContextCompat.getColor(requireContext(), R.color.orange),
            2 to ContextCompat.getColor(requireContext(), R.color.yellow),
            3 to ContextCompat.getColor(requireContext(), R.color.green_white),
            4 to ContextCompat.getColor(requireContext(), R.color.blue),
            5 to ContextCompat.getColor(requireContext(), R.color.purple_700),
            6 to ContextCompat.getColor(requireContext(), R.color.purple_500)
        )
        val spans = spannableString.getSpans(
            0, spannableString.length,
            ForegroundColorSpan::class.java
        )
        for (span in spans) {
            spannableString.removeSpan(span)
        }

        var colorNumber = colorFirstNumber
        for (i in 0 until container.text.length) {
            if (colorNumber == 5) colorNumber = 0 else colorNumber += 1
            spannableString.setSpan(
                ForegroundColorSpan(map.getValue(colorNumber)),
                i, i + 1,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
    }
}
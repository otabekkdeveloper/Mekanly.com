package com.mekanly.presentation.ui.fragments.singleHouse

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.mekanly.R
import com.mekanly.data.models.Banner
import com.mekanly.data.models.HouseDetails
import com.mekanly.data.models.Option
import com.mekanly.data.models.Report
import com.mekanly.databinding.FragmentSingleHouseBinding
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.presentation.ui.adapters.AdapterInformationInSingleHouse
import com.mekanly.presentation.ui.adapters.HouseItem
import com.mekanly.presentation.ui.enums.BannerType
import com.mekanly.presentation.ui.fragments.home.AdapterBigBanners
import com.mekanly.presentation.ui.fragments.singleHouse.adapter.AdapterPossibilities
import com.mekanly.ui.fragments.favorite.FavoritesViewModel
import com.mekanly.ui.fragments.home.FragmentHomeState
import com.mekanly.ui.fragments.home.VMHome
import com.mekanly.ui.fragments.search.viewModel.VMSearch
import com.mekanly.ui.fragments.singleHouse.report.ReportBottomSheetFragment
import com.mekanly.ui.fragments.singleHouse.report.VMReport
import com.mekanly.utils.extensions.showErrorSnackBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class FragmentSingleHouse : Fragment() {

    private lateinit var binding: FragmentSingleHouseBinding
    private val args by navArgs<FragmentSingleHouseArgs>()
    private lateinit var houseAdapter: AdapterInformationInSingleHouse
    private lateinit var opportunityAdapter: AdapterPossibilities
    private lateinit var adapterBigBanners: AdapterBigBanners
    private val viewModelHouse: VMHome by viewModels()
    private val viewModel: VMSingleHouse by viewModels()
    private val viewModelSearch: VMSearch by activityViewModels()
    private val viewModelReport: VMReport by viewModels()
    private val viewModelFavorite: FavoritesViewModel by viewModels()
    private lateinit var currentHouse: HouseDetails
    private var isFavoriteChanged = false

    private var currentBigBannerPosition = 0
    private val bigBannerScrollHandler = Handler(Looper.getMainLooper())
    private val bigBannerScrollRunnable = object : Runnable {
        override fun run() {
            val itemCount = adapterBigBanners.itemCount
            if (itemCount == 0) return

            currentBigBannerPosition = (currentBigBannerPosition + 1) % itemCount
            binding.rvBigBanners.smoothScrollToPosition(currentBigBannerPosition)

            bigBannerScrollHandler.postDelayed(this, 5000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingleHouseBinding.inflate(inflater, container, false)
        getHouseInfo()
        initListeners()
        observeViewModel()
        viewModelReport.getReports()
        observeReportViewModel()







        binding.collapsingToolbar.title = "Gyssagly satlyk jay"
        binding.collapsingToolbar.title = binding.tvAddress.text // Это будет "Aşgabat/mir1"

        binding.appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val totalScrollRange = appBarLayout.totalScrollRange
            val percentage = Math.abs(verticalOffset) / totalScrollRange.toFloat()

            binding.apply {
                // Плавный переход для expanded элементов (исчезают с 0 до 0.5)
                val expandedAlpha = when {
                    percentage <= 0.3f -> 1f
                    percentage >= 0.7f -> 0f
                    else -> 1f - ((percentage - 0.3f) / 0.4f)
                }

                // Плавный переход для collapsed элементов (появляются с 0.5 до 1)
                val collapsedAlpha = when {
                    percentage <= 0.3f -> 0f
                    percentage >= 0.7f -> 1f
                    else -> (percentage - 0.3f) / 0.4f
                }

                // Управление expanded элементами
                buttonsExpanded.alpha = expandedAlpha
                if (expandedAlpha > 0f && buttonsExpanded.visibility != View.VISIBLE) {
                    buttonsExpanded.visibility = View.VISIBLE
                } else if (expandedAlpha == 0f && buttonsExpanded.visibility != View.GONE) {
                    buttonsExpanded.visibility = View.GONE
                }

                // Управление collapsed элементами
                val collapsedViews = listOf(toolbar, btnBackCollapsed, btnShareCollapsed, btnLikeCollapsed, locationLinear)

                collapsedViews.forEach { view ->
                    view.alpha = collapsedAlpha
                    if (collapsedAlpha > 0f && view.visibility != View.VISIBLE) {
                        view.visibility = View.VISIBLE
                    } else if (collapsedAlpha == 0f && view.visibility != View.GONE) {
                        view.visibility = View.GONE
                    }
                }
            }
        }






        return binding.root
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.singleHouseState.collectLatest {
                when (it) {
                    is ResponseBodyState.Error -> {
                        requireContext().showErrorSnackBar(binding.root, it.error.toString())
                        binding.progressBar.visibility = View.GONE
                    }

                    ResponseBodyState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is ResponseBodyState.Success -> {
                        delay(200)
                        binding.progressBar.visibility = View.GONE
                        binding.countImages.visibility = View.VISIBLE
                        val houseDetails = it.dataResponse as HouseDetails
                        currentHouse = houseDetails
                        setViewPager(houseDetails)
                        setHouseDetails(houseDetails)
                        setPossibilityAdapter(houseDetails.possibilities)
                        setHomeDetails(houseDetails)
                        decorationVipAndLuxHouses(houseDetails)
                        updateLikeButton(houseDetails.favorite)
                        observeBannersViewModel()
                        viewModelHouse.getBanners()
                    }

                    else -> {}
                }
            }
        }
    }

    private fun observeBannersViewModel() {
        lifecycleScope.launch {
            viewModelHouse.homeState.collectLatest { state ->
                when (state) {
                    is FragmentHomeState.Error -> {
                        // Обработка ошибки загрузки баннеров
                        Log.e("BannerError", "Ошибка загрузки баннеров: ${state.error}")
                    }

                    FragmentHomeState.Loading -> {
                        // Можно показать индикатор загрузки для баннеров если нужно
                    }

                    is FragmentHomeState.SuccessBanners -> {
                        val bigBanners = state.dataResponse.filter {
                            it.type == BannerType.BIG_BANNER.value
                        }
                        if (bigBanners.isNotEmpty()) {
                            setUpBigBanners(bigBanners)
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun observeReportViewModel() {
        lifecycleScope.launch {
            viewModelReport.reportState.collectLatest { state ->
                when (state) {
                    is ResponseBodyState.Error -> {
                        // Показываем ошибку загрузки отчетов
                        Toast.makeText(
                            requireContext(),
                            "Ошибка загрузки причин жалоб: ${state.error}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    ResponseBodyState.Loading -> {
                        // Можно показать индикатор загрузки для отчетов если нужно
                    }

                    is ResponseBodyState.SuccessList -> {
                        // Отчеты успешно загружены, теперь кнопка жалобы может работать корректно
                        Log.d(
                            "ReportList",
                            "Загружено ${(state.dataResponse as List<*>).size} причин жалоб"
                        )
                    }

                    else -> {}
                }
            }
        }

        // Наблюдаем за выбранным отчетом
        lifecycleScope.launch {
            viewModelReport.selectedReport.collectLatest { report ->
                if (report != null) {
                    Log.d("SelectedReport", "Выбрана причина: ${report.description}")
                }
            }
        }
    }

    private fun setPossibilityAdapter(possibilities: List<Option>) {
        opportunityAdapter = AdapterPossibilities(possibilities)
        binding.rvOpportunity.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvOpportunity.adapter = opportunityAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun setHouseDetails(house: HouseDetails) {
        binding.apply {
            tvTitle.text = house.name
            textDescription.text = house.name
            textLocation.text = house.location.parentName
            tvDate.text = formatDate(house.createdAt)
            tvDetails.text = house.description
            tvHouseType.text = house.categoryName
            tvViewCount.text = house.viewed.toString()
            tvAddress.text = "${house.location.parentName}/${house.location.name}"
            btnComments.text = if (house.commentCount > 0) {
                getString(R.string.comments_with_count, house.comment)
            } else {
                getString(R.string.comments) }

            val phoneNumber = house.bronNumber.orEmpty()

            btnSms.setOnClickListener {
                val smsIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("sms:$phoneNumber")
                    putExtra("sms_body", "")
                }
                startActivity(smsIntent)
            }

            btnCall.setOnClickListener {
                val callIntent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$phoneNumber")
                }
                startActivity(callIntent)
            }
        }
    }

    private fun setViewPager(house: HouseDetails) {
        val imageSliderAdapter = HouseImagesAdapter(house.images)
        binding.viewPager.adapter = imageSliderAdapter

        val totalCount = house.images.size
        // Установить начальный текст
        binding.countImages.text = "1/$totalCount"

        // Обновление текста при смене изображения
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.countImages.text = "${position + 1}/$totalCount"
            }
        })
    }

    private fun setHomeDetails(dataResponse: HouseDetails) {
        val houseList = listOf(
            HouseItem(R.drawable.ic_houses_for_sale, "Bölümi", dataResponse.categoryName),
            HouseItem(
                R.drawable.location_icon, "Ýerleşýän ýeri", dataResponse.location?.name ?: ""
            ),
            HouseItem(R.drawable.ic_calendar, "Goýlan senesi", formatDate(dataResponse.createdAt)),
            HouseItem(R.drawable.ic_phone, "Telefon nomeri", dataResponse.bronNumber.toString()),
            HouseItem(R.drawable.ic_elite, "Emläk görnüşi", dataResponse.categoryName),
            HouseItem(R.drawable.ic_count_room, "Otag sany", dataResponse.roomNumber.toString()),
            HouseItem(
                R.drawable.ic_number_of_floors, "Gat sany", dataResponse.floorNumber.toString()
            ),
            // HouseItem(R.drawable.elitka, "Remont görnüşi", dataResponse.luxe.toString()),
            // HouseItem(R.drawable.ic_total_area, "Umumy meýdany", "Not available"),
            HouseItem(R.drawable.ic_price, "Bahasy", "${dataResponse.price} TMT")
        )

        houseAdapter = AdapterInformationInSingleHouse(houseList)
        binding.rvSingleHouse.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvSingleHouse.adapter = houseAdapter
    }

    private fun getHouseInfo() {
        if (args.houseId == -1L) {
            return
        } else {
            viewModel.getHouseDetails(args.houseId)
        }
    }






    // В FragmentSingleHouse добавляем только логику share без дополнительных API запросов

    private fun initListeners() {
        binding.apply{
            btnBack.setOnClickListener {
                findNavController().popBackStack() }
            btnBackCollapsed.setOnClickListener{
                findNavController().popBackStack()
            }

        }

        binding.btnComments.setOnClickListener {
            showCommentsBottomSheet()
        }

        binding.btnReport.apply {
            paintFlags = binding.btnReport.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            setOnClickListener {
                val reports = viewModelReport.reportList.value
                if (reports.isNotEmpty()) {
                    openReportSelector(reports)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Идет загрузка причин жалоб, повторите через несколько секунд",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModelReport.getReports()
                }
            }
        }

        binding.apply {
            btnShare.setOnClickListener {
                shareHouse()
            }
            btnShareCollapsed.setOnClickListener{
                shareHouse()
            }
        }

        binding.btnLike.setOnClickListener {
            currentHouse.let { house ->
                val currentLikeStatus = house.favorite

                // ✅ Обновляем локально
                house.favorite = !currentLikeStatus
                updateLikeButton(house.favorite)

                // ✅ Обновляем в FavoritesViewModel
                viewModelFavorite.toggleLike(house.id, currentLikeStatus, "House")

                // ✅ ДОБАВЛЕНО: Также обновляем в VMSearch (если доступен)
                // Если у вас есть доступ к VMSearch в этом фрагменте:
                viewModelSearch?.updateHouseLikeStatus(house.id, !currentLikeStatus)

                // ✅ АЛЬТЕРНАТИВНО: Используйте SharedViewModel или Event Bus
                // для передачи изменений между фрагментами

                val message = if (currentLikeStatus) {
                    "Удалено из избранного"
                } else {
                    "Добавлено в избранное"
                }
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun shareHouse() {
        if (!::currentHouse.isInitialized) {
            Toast.makeText(
                requireContext(),
                "Данные дома еще загружаются",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Создаем ссылку на дом используя его ID
        val shareUrl = "https://mekanly.com.tm/house/${args.houseId}"

        // Формируем красивый текст для share
        val shareText = buildString {
            append("${shareUrl}")

        }

        // Создаем Intent для share
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
            putExtra(Intent.EXTRA_SUBJECT, "🏠 ${currentHouse.name} - Mekanly")
        }

        try {
            startActivity(Intent.createChooser(shareIntent, "Поделиться объявлением"))
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Не удалось открыть меню шаринга",
                Toast.LENGTH_SHORT
            ).show()
            Log.e("ShareError", "Ошибка при открытии share menu", e)
        }
    }

    // ✅ ДОБАВЛЕНО: Функция для обновления кнопки лайка
    private fun updateLikeButton(isLiked: Boolean) {
        binding.btnLike.setIconResource(
            if (isLiked) R.drawable.heart_bold else R.drawable.favourite_three
        )
    }


    private fun showCommentsBottomSheet() {
        val action = FragmentSingleHouseDirections.actionFragmentSingleHouseToBottomSheetComments(args.houseId)
        findNavController().navigate(action)
    }

    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault()
        ) // формат входной строки
        val outputFormat =
            SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()) // нужный формат: день месяц год

        val date = inputFormat.parse(dateString) // парсим строку в объект Date
        return outputFormat.format(date)
    }

    private fun openReportSelector(reports: List<Report>) {
        if (reports.isNotEmpty()) {
            val bottomSheet = ReportBottomSheetFragment(reports) { selectedReport ->
                // Сохраняем выбранный отчет в ViewModel
                viewModelReport.selectReport(selectedReport)

                // Отправляем жалобу на сервер с помощью ViewModel
                sendReportToServer(selectedReport)

                // Показываем уведомление об успешной отправке
                Toast.makeText(
                    requireContext(),
                    "Жалоба успешно отправлена: ${selectedReport.description}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            bottomSheet.show(childFragmentManager, "ReportBottomSheet")
        } else {
            Toast.makeText(
                requireContext(), "Нет доступных причин для жалобы", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun sendReportToServer(report: Report) {
        val houseId = args.houseId.toInt() // или toLong().toInt() если нужно
        val abuseListId = report.id
        val message = report.description
        val type = report.type.ifBlank { "house" } // тип жалобы, например "house"

        viewModelReport.sendReport(
            abuseListId = abuseListId, itemId = houseId, message = message, type = type
        )
    }

    private fun setUpBigBanners(dataBanners: List<Banner>) {
        if (dataBanners.isEmpty()) return

        adapterBigBanners = AdapterBigBanners(dataBanners)
        binding.rvBigBanners.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvBigBanners.adapter = adapterBigBanners
        binding.rvBigBanners.isNestedScrollingEnabled = true

        // Запускаем автопрокрутку только если есть больше одного баннера
        if (dataBanners.size > 1) {
            bigBannerScrollHandler.postDelayed(bigBannerScrollRunnable, 5000)
        }
    }

    private fun decorationVipAndLuxHouses(house: HouseDetails) {
        if (house.luxeStatus) {
            binding.decorationLinear.setBackgroundResource(R.drawable.vertical_lux_gradient)
            binding.luxLogo.visibility = View.VISIBLE
        }

        if (house.vipStatus) {
            binding.decorationLinear.setBackgroundResource(R.drawable.vertical_vip_gradient)
            binding.vipLogo.visibility = View.VISIBLE
        }
    }

    override fun onPause() {
        super.onPause()
        // Останавливаем автопрокрутку при паузе фрагмента
        bigBannerScrollHandler.removeCallbacks(bigBannerScrollRunnable)
    }

    override fun onResume() {
        super.onResume()
        // Возобновляем автопрокрутку при возврате фрагмента
        if (::adapterBigBanners.isInitialized && adapterBigBanners.itemCount > 1) {
            bigBannerScrollHandler.postDelayed(bigBannerScrollRunnable, 5000)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Обязательно очищаем handlers при уничтожении view
        bigBannerScrollHandler.removeCallbacks(bigBannerScrollRunnable)
    }
}
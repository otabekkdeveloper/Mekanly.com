package com.mekanly.ui.bottomSheet.bottomSheetComments

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mekanly.R
import com.mekanly.data.models.Comment
import com.mekanly.data.local.preferences.AppPreferences
import com.mekanly.data.request.AddCommentBody
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.databinding.BottomSheetCommentsBinding
import com.mekanly.presentation.ui.adapters.CommentsAdapter
import com.mekanly.utils.Constants.Companion.COMMENT_TYPE_HOUSE
import com.mekanly.utils.extensions.showErrorSnackBar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BottomSheetComments : BottomSheetDialogFragment() {

    private var _binding: BottomSheetCommentsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VMComments by viewModels()
    private val args: BottomSheetCommentsArgs by navArgs()
    private lateinit var commentAdapter: CommentsAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        // Устанавливаем поведение при открытии клавиатуры
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let { sheet ->
                val behavior = BottomSheetBehavior.from(sheet)

                // Устанавливаем состояние как раскрытое
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.skipCollapsed = true

                // Высота = 75% от высоты экрана
                val displayMetrics = Resources.getSystem().displayMetrics
                val screenHeight = displayMetrics.heightPixels
                val desiredHeight = (screenHeight * 0.65).toInt()

                val layoutParams = sheet.layoutParams
                layoutParams.height = desiredHeight
                sheet.layoutParams = layoutParams
            }
        }


        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetCommentsBinding.inflate(inflater, container, false)
        initViews()
        observeViewModel()
        initListeners()
        setupKeyboardHandling()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getHouseComments()
    }

    private fun setupKeyboardHandling() {
        // Обработка появления клавиатуры с помощью WindowInsets
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Применяем отступы снизу когда появляется клавиатура
            view.setPadding(
                view.paddingLeft,
                view.paddingTop,
                view.paddingRight,
                imeInsets.bottom
            )

            // Скроллим к полю ввода когда появляется клавиатура
            if (imeInsets.bottom > 0) {
                binding.root.post {
                    binding.linearLayout5.requestFocus()
                    // Прокручиваем RecyclerView в самый низ
                    if (::commentAdapter.isInitialized && commentAdapter.itemCount > 0) {
                        binding.commentsRecyclerView.smoothScrollToPosition(commentAdapter.itemCount - 1)
                    }
                }
            }

            insets
        }
    }

    private fun initViews() {
        // Инициализируем начальное состояние UI
        binding.progressBar.visibility = View.GONE
        binding.commentsRecyclerView.visibility = View.GONE
        binding.noComment.visibility = View.VISIBLE
        binding.sendButton.isEnabled = false
        binding.sendButton.alpha = 0.5f
    }

    private fun getHouseComments() {
        viewModel.getComments(args.houseId, COMMENT_TYPE_HOUSE)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.commentsState.collectLatest {
                when (it) {
                    is ResponseBodyState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        showEmptyState()
                    }

                    ResponseBodyState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.noComment.visibility = View.GONE
                        binding.commentsRecyclerView.visibility = View.GONE
                    }

                    is ResponseBodyState.SuccessList -> {
                        binding.progressBar.visibility = View.GONE
                        val comments = it.dataResponse as List<Comment>

                        if (comments.isEmpty()) {
                            showEmptyState()
                        } else {
                            showComments(comments)
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun showEmptyState() {
        binding.noComment.visibility = View.VISIBLE
        binding.commentsRecyclerView.visibility = View.GONE
    }

    private fun showComments(comments: List<Comment>) {
        binding.noComment.visibility = View.GONE
        binding.commentsRecyclerView.visibility = View.VISIBLE
        setCommentsAdapter(comments)
    }

    private fun initListeners() {
        binding.sendButton.setOnClickListener {
            val newCommentText = binding.commentEditText.text.toString().trim()
            if (newCommentText.isNotEmpty()) {
                handleCommentAction(newCommentText)
            }
        }

        binding.icClose.setOnClickListener {
            dismiss()
        }

        // TextWatcher для управления состоянием кнопки
        binding.commentEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val hasText = !s.isNullOrEmpty()
                binding.sendButton.isEnabled = hasText
                binding.sendButton.alpha = if (hasText) 1.0f else 0.5f

                if (hasText) {
                    binding.sendButton.setImageResource(R.drawable.ic_arrow_to_top) // активная иконка
                } else {
                    binding.sendButton.setImageResource(R.drawable.ic_unselected_arrow_to_top) // та же иконка, но неактивная
                }


            }


            override fun afterTextChanged(s: Editable?) {}
        })

        // Обработка фокуса на EditText
        binding.commentEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Прокручиваем к последнему комментарию когда фокусируемся на поле ввода
                binding.root.post {
                    if (::commentAdapter.isInitialized && commentAdapter.itemCount > 0) {
                        binding.commentsRecyclerView.smoothScrollToPosition(commentAdapter.itemCount - 1)
                    }
                }
            }
        }
    }

    private fun handleCommentAction(newCommentText: String) {
        if (AppPreferences.getToken()?.isEmpty() == true) {
            requireContext().showErrorSnackBar(binding.root, getString(R.string.log_in_to_write_comment))
            return
        }

        val newComment = Comment(
            userName = AppPreferences.getUsername() ?: "Unknown User",
            date = getCurrentTime(),
            text = newCommentText,
            likeCount = 0,
            dislikeCount = 0
        )

        if (binding.noComment.visibility == View.VISIBLE) {
            binding.noComment.visibility = View.GONE
            binding.commentsRecyclerView.visibility = View.VISIBLE
        }

        if (::commentAdapter.isInitialized) {
            commentAdapter.addItem(newComment)
            // Прокручиваем к новому комментарию
            binding.commentsRecyclerView.smoothScrollToPosition(commentAdapter.itemCount - 1)
        } else {
            commentAdapter = CommentsAdapter(mutableListOf(newComment))
            binding.commentsRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = commentAdapter
            }
        }

        binding.commentEditText.text?.clear()

        viewModel.addComment(
            addCommentBody = AddCommentBody(
                comment = newCommentText,
                postId = args.houseId,
                type = COMMENT_TYPE_HOUSE,
                parentId = null
            )
        ) {}
    }

    private fun setCommentsAdapter(list: List<Comment>) {
        if (::commentAdapter.isInitialized) {
            commentAdapter.apply {
                comments.clear()
                comments.addAll(list)
                notifyDataSetChanged()
            }
        } else {
            commentAdapter = CommentsAdapter(list.toMutableList())
            binding.commentsRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = commentAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }
}
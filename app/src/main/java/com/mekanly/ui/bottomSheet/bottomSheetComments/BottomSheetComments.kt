package com.mekanly.ui.bottomSheet.bottomSheetComments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetCommentsBinding.inflate(inflater, container, false)
        observeViewModel()
        initListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getHouseComments()
    }

    private fun getHouseComments() {
        viewModel.getComments(args.houseId, COMMENT_TYPE_HOUSE)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.commentsState.collectLatest {
                when (it) {
                    is ResponseBodyState.Error -> {
                        requireContext().showErrorSnackBar(binding.root, it.error.toString())
                        binding.progressBar.visibility = View.GONE
                    }

                    ResponseBodyState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is ResponseBodyState.SuccessList -> {
                        binding.progressBar.visibility = View.GONE
                        setCommentsAdapter(it.dataResponse as List<Comment>)
                        if (it.dataResponse.isEmpty()) {
                            requireContext().showErrorSnackBar(binding.root, "Empty Data")
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun initListeners() {
        binding.sendButton.setOnClickListener {
            val newCommentText = binding.commentEditText.text.toString().trim()
            if (newCommentText.isNotEmpty()) {
                handleCommentAction(newCommentText)
            }
        }
    }

    private fun handleCommentAction(newCommentText: String) {
        if (AppPreferences.getToken()?.isEmpty() == true) {
            requireContext().showErrorSnackBar(binding.root, getString(R.string.log_in_to_write_comment))
        } else {
            // Создаем новый комментарий для немедленного отображения
            val newComment = Comment(
                userName = AppPreferences.getUsername() ?: "Unknown User",
                date = getCurrentTime(),
                text = newCommentText,
                likeCount = 0,
                dislikeCount = 0
            )

            // Добавляем комментарий в UI сразу же
            if (::commentAdapter.isInitialized) {
                commentAdapter.addItem(newComment)
            }

            // Очищаем поле ввода
            binding.commentEditText.text?.clear()

            // Отправляем запрос на сервер
            viewModel.addComment(
                addCommentBody = AddCommentBody(
                    comment = newCommentText,
                    postId = args.houseId,
                    type = COMMENT_TYPE_HOUSE,
                    parentId = null
                )
            ) {
                // Callback выполнится после успешной отправки на сервер
                // Комментарий уже добавлен в UI, поэтому здесь ничего дополнительного не делаем
            }
        }
    }

    private fun setCommentsAdapter(list: List<Comment>) {
        if (::commentAdapter.isInitialized) {
            // Обновляем весь список — очистим и добавим заново
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


        binding.commentEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    binding.sendButton.setImageResource(R.drawable.ic_arrow_to_top) // täze ikonka
                } else {
                    binding.sendButton.setImageResource(R.drawable.ic_unselected_arrow_to_top) // öňki ikonka
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })




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
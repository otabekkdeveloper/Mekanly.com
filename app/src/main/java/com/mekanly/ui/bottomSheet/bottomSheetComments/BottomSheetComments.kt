package com.mekanly.presentation.ui.bottomSheet.bottomSheetComments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mekanly.data.models.Comment
import com.mekanly.data.local.preferences.AppPreferences
import com.mekanly.domain.model.ResponseBodyState
import com.mekanly.databinding.BottomSheetCommentsBinding
import com.mekanly.presentation.ui.adapters.CommentsAdapter
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

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.commentsState.collectLatest {
                when(it){
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
                        if (it.dataResponse.isEmpty()){
                            requireContext().showErrorSnackBar( binding.root,"Empty Data")
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getHouseComments()
    }

    private fun getHouseComments() {
        viewModel.getHouseComments(args.houseId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun initListeners(){
        binding.sendButton.setOnClickListener {
            val newCommentText = binding.commentEditText.text.toString().trim()
            if (newCommentText.isNotEmpty()) {
                binding.commentsRecyclerView.scrollToPosition(0)
                binding.commentEditText.text.clear()
                handleCommentAction(newCommentText)

            }
        }
    }

    private fun handleCommentAction(newCommentText: String) {
        if (AppPreferences(requireContext()).token==""){
            requireContext().showErrorSnackBar(binding.root, "Now on development")
        }else{
            viewModel.addComment(newCommentText)
        }
    }

    private fun setCommentsAdapter(list:List<Comment>){
        binding.commentsRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
        commentAdapter = CommentsAdapter(list)
        binding.commentsRecyclerView.adapter = commentAdapter
    }
}
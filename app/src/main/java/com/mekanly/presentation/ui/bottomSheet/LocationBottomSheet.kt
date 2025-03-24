import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mekanly.databinding.BottomSheetLocationBinding
import com.mekanly.presentation.ui.adapters.AdapterLocations

class LocationBottomSheet(
    private val cities: List<String>,
    private val onCitySelected: (String) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetLocationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomSheetLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnClose.setOnClickListener{
            dismiss()
        }

        val adapter = AdapterLocations(cities) { selectedCity ->
            onCitySelected(selectedCity)
            dismiss() // Закрываем BottomSheet
        }

        binding.rvLocation.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLocation.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mekanly.data.models.Location
import com.mekanly.databinding.BottomSheetLocationBinding
import com.mekanly.presentation.ui.adapters.AdapterLocations

class LocationBottomSheet(
    private val cities: List<Location>,
    private val onDelete: () -> Unit, // Новый параметр для удаления
    private val onCitySelected: (Location) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetLocationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomSheetLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        val adapter = AdapterLocations(cities) { selectedCity ->
            onCitySelected(selectedCity)
            dismiss()
        }

        binding.rvLocation.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLocation.adapter = adapter

        binding.deleteText.setOnClickListener {
            onDelete()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

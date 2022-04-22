package com.nikolas.navcomponent

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.nikolas.navcomponent.databinding.FragmentRootBinding

class RootFragment : Fragment(R.layout.fragment_root) {

    private lateinit var binding: FragmentRootBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRootBinding.bind(view)
        binding.openYellowBoxButton.setOnClickListener {
            openBox(Color.rgb(255, 255,200), getString(R.string.color_name_yellow))
        }
        binding.openGreenBoxButton.setOnClickListener {
            openBox(Color.rgb(200,255,200), getString(R.string.color_name_green))
        }

        val livedata = findNavController().currentBackStackEntry
            ?.savedStateHandle?.getLiveData<Int>(BoxFragment.EXTRA_RANDOM_NUMBER)
        livedata?.observe(viewLifecycleOwner) { randomNumber ->
            if(randomNumber != null) {
                Toast.makeText(requireContext(), "Generate number : $randomNumber", Toast.LENGTH_SHORT).show()
            }
            livedata.value = null
        }
//        parentFragmentManager.setFragmentResultListener(BoxFragment.REQUEST_CODE, viewLifecycleOwner) { requestCode, data ->
//            val number = data.getInt(BoxFragment.EXTRA_RANDOM_NUMBER)
//            Toast.makeText(requireContext(), "Generate number : $number", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun openBox(color: Int, colorName: String) {

        val direction = RootFragmentDirections.actionRootFragmentToBoxFragment(color, colorName)
        findNavController().navigate(
            direction,
//            R.id.action_rootFragment_to_boxFragment,
//            bundleOf(BoxFragment.ARG_COLOR to color, BoxFragment.ARG_COLOR_NAME to colorName),
            navOptions {
                anim {
                    enter = R.anim.enter
                    exit = R.anim.exit
                    popEnter = R.anim.pop_enter
                    popExit = R.anim.pop_exit
                }
            }
        )
    }

}
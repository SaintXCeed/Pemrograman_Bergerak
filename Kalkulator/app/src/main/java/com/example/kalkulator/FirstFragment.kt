package com.example.kalkulator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kalkulator.databinding.FragmentFirstBinding
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etAngka1 = view.findViewById<EditText>(R.id.etAngka1)
        val etAngka2 = view.findViewById<EditText>(R.id.etAngka2)
        val tvHasil = view.findViewById<TextView>(R.id.tvHasil)

        val btnTambah = view.findViewById<Button>(R.id.btnTambah)
        val btnKurang = view.findViewById<Button>(R.id.btnKurang)
        val btnKali = view.findViewById<Button>(R.id.btnKali)
        val btnBagi = view.findViewById<Button>(R.id.btnBagi)

        fun getAngka(editText: EditText): Double {
            val text = editText.text.toString()
            return if (text.isEmpty()) 0.0 else text.toDouble()
        }

        btnTambah.setOnClickListener {
            val hasil = getAngka(etAngka1) + getAngka(etAngka2)
            tvHasil.text = "Hasil: $hasil"
        }

        btnKurang.setOnClickListener {
            val hasil = getAngka(etAngka1) - getAngka(etAngka2)
            tvHasil.text = "Hasil: $hasil"
        }

        btnKali.setOnClickListener {
            val hasil = getAngka(etAngka1) * getAngka(etAngka2)
            tvHasil.text = "Hasil: $hasil"
        }

        btnBagi.setOnClickListener {
            val a = getAngka(etAngka1)
            val b = getAngka(etAngka2)

            if (b == 0.0) {
                tvHasil.text = "Hasil: Tidak bisa dibagi 0"
            } else {
                val hasil = a / b
                tvHasil.text = "Hasil: $hasil"
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
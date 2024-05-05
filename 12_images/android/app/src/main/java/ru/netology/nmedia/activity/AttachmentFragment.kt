package ru.netology.nmedia.activity

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.databinding.FragmentAttachmentBinding
import ru.netology.nmedia.view.load

class AttachmentFragment : Fragment() {
    private val args: AttachmentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAttachmentBinding.inflate(inflater, container, false)
        binding.image.setBackgroundColor(Color.BLACK)
        binding.image.load(args.url)
        return binding.root
    }
}

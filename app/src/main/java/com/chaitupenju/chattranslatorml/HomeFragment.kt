package com.chaitupenju.chattranslatorml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chaitupenju.chattranslatorml.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeBinding.btnEnterChat.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_chatFragment, bundleOf(
                Constants.CHAT_NAME_KEY to homeBinding.etName.text.toString(),
                Constants.CHAT_ROOM_KEY to homeBinding.etRoom.text.toString()
            ))
        }
    }
}
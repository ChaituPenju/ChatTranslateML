package com.chaitupenju.chattranslatorml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chaitupenju.chattranslatorml.databinding.FragmentChatBinding


class ChatFragment : Fragment() {

    private lateinit var chatBinding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        chatBinding = FragmentChatBinding.inflate(inflater, container, false)
        return chatBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
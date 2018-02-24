package cl.mascayanogerardo.snapchatclone.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.mascayanogerardo.snapchatclone.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {


    public static ChatFragment newInstance() {
        ChatFragment chatFragment = new ChatFragment();
        return chatFragment;
    }

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

}

package edu.cs4735.program2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class pic extends Fragment {

    private OnPicFragmentInteractionListener mListener;

    ImageView iv;
    TextView tv;
    Button butt;

    Bitmap bm;
    final int size = 990;
    Canvas mCanvas;

    String tvText;


    public pic() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public pic(String text) {
        tvText = text;
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_pic, container, false);

        iv = myView.findViewById(R.id.imageView);
        String t = "Location_41.3283938,_-105.5865193";
        iv.setImageResource(R.drawable.loc);


        tv = myView.findViewById(R.id.locTV);
        if(tv != null)
            tv.setText(tvText);
        butt = myView.findViewById(R.id.backButton);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onPicFragmentInteraction();
                }
                //getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return myView;
    }

    public void setTV(String text){
        tv.setText(text);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPicFragmentInteractionListener) {
            mListener = (OnPicFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPicFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPicFragmentInteraction();
    }
}

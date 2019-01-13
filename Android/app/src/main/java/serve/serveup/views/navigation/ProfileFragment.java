package serve.serveup.views.navigation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import serve.serveup.R;
import serve.serveup.dataholder.ProfileOption;
import serve.serveup.dataholder.UserInfo;
import serve.serveup.utils.CircleTransformation;
import serve.serveup.utils.adapters.UserRecyclerAdapter;

public class ProfileFragment extends Fragment {
    /*
    *  TODO  design userOption cards and its click events for further functionality
    * */
    private OnFragmentInteractionListener mListener;
    private RecyclerView userOptionRecyclerView;
    private UserRecyclerAdapter userOptionRecyclerAdapter;
    private LinearLayoutManager linearLayoutManager;

    private String userEmail;

    // Contains data of every user option
    ArrayList<ProfileOption> userOptionsData;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myContainer =  inflater.inflate(R.layout.fragment_profile, container, false);

        userOptionsData = new ArrayList<>();

        Bundle myBundle = getActivity().getIntent().getExtras();
        if(myBundle != null) {
            UserInfo userInfo = (UserInfo) myBundle.getSerializable("userInfo");

            Uri photoUrl = Uri.parse(userInfo.getPhotoUrl());
            ImageView image = myContainer.findViewById(R.id.userPhoto);
            TextView displayName = myContainer.findViewById(R.id.displayName);

            userEmail = userInfo.getEmail();
            displayName.setText(userInfo.getDisplayName());
            Picasso.get().load(photoUrl).transform(new CircleTransformation()).into(image);
        }
        fillUpUserOptions();

        userOptionRecyclerView = myContainer.findViewById(R.id.userOptions);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        userOptionRecyclerAdapter = new UserRecyclerAdapter(getActivity(), userOptionsData);

        // Set the layout manager and the adapter of the Recycler View
        userOptionRecyclerView.setLayoutManager(linearLayoutManager);
        userOptionRecyclerView.setAdapter(userOptionRecyclerAdapter);
        return myContainer;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void fillUpUserOptions() {
        userOptionsData.add(new ProfileOption(retDrawable(R.drawable.ic_baseline_email_24px), userEmail));
        userOptionsData.add(new ProfileOption(retDrawable(R.drawable.ic_baseline_account_set_up), getString(R.string.account_info)));
        userOptionsData.add(new ProfileOption(retDrawable(R.drawable.ic_baseline_settings_20px), getString(R.string.settings)));
        userOptionsData.add(new ProfileOption(retDrawable(R.drawable.ic_sign_out_icon), getString(R.string.log_out)));
        userOptionsData.add(new ProfileOption(retDrawable(R.drawable.ic_baseline_info_24px), getString(R.string.about_app)));
    }

    private Drawable retDrawable(int resourceID) {
        return getResources().getDrawable(resourceID);
    }

}

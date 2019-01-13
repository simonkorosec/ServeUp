package serve.serveup.utils.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import serve.serveup.R;
import serve.serveup.dataholder.ProfileOption;
import serve.serveup.utils.ContentStore;
import serve.serveup.utils.DialogPassableMethod;
import serve.serveup.utils.Utils;
import serve.serveup.views.AboutActivity;

public class UserRecyclerAdapter
        extends RecyclerView.Adapter<UserRecyclerAdapter.UserRecyclerHolder> {

    private List<ProfileOption> userOptions;
    private Activity myActivity;

    static class UserRecyclerHolder extends RecyclerView.ViewHolder {
        CardView cardUserOptionView;
        ImageView cardDiscoveryImage;
        TextView cardDiscoveryTitle;

        UserRecyclerHolder(final View itemView) {
            super(itemView);
            cardUserOptionView = itemView.findViewById(R.id.cardUserOptionView);
            cardDiscoveryImage = itemView.findViewById(R.id.userOptionIcon);
            cardDiscoveryTitle = itemView.findViewById(R.id.userOptionText);
        }
    }

    public UserRecyclerAdapter(Activity myActivity, List<ProfileOption> userOptions) {
        this.userOptions = userOptions;
        this.myActivity = myActivity;
    }

    @NonNull
    @Override
    public UserRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardUserOptionView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_user_option, parent, false);
        return new UserRecyclerHolder(cardUserOptionView);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserRecyclerHolder holder, int position) {
        ProfileOption profileOption = this.userOptions.get(position);
        holder.cardDiscoveryTitle.setText(profileOption.getOptionTitle());
        Drawable myIcon = profileOption.getIcon();

        DrawableCompat.setTint(myIcon, myActivity.getResources().getColor(R.color.colorAccent));
        holder.cardDiscoveryImage.setImageDrawable(profileOption.getIcon());

        holder.cardUserOptionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (holder.getAdapterPosition()) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        logOut();
                        break;
                    case 4:
                        openAboutApp();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userOptions.size();
    }


    private void logOut() {
        
        Utils.createDialog(myActivity, "Log out",
                "Se želite izpisati iz aplikacije",
                "OK", "Cancel",
                new DialogPassableMethod() {
                    @Override
                    public void executeMethod() {
                        // erase current session and sign out of the application
                        Utils.logInfo("Erase current session.");
                        ContentStore cntStore = new ContentStore(myActivity);
                        cntStore.eraseSession();

                        Utils.logInfo("current user: " + FirebaseAuth.getInstance().getCurrentUser());
                        FirebaseAuth.getInstance().signOut();
                        myActivity.finish();

                    }
                }
        );
    }

    private void openAboutApp() {
        Intent myIntent = new Intent(myActivity, AboutActivity.class);
        myActivity.startActivity(myIntent);
    }
}

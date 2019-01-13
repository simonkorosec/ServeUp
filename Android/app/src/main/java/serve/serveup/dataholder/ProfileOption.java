package serve.serveup.dataholder;

import android.graphics.drawable.Drawable;

public class ProfileOption {

    private Drawable icon;
    private String optionTitle;

    public ProfileOption(Drawable icon, String optionTitle) {
        this.icon = icon;
        this.optionTitle = optionTitle;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
    public void setOptionTitle(String optionTitle) {
        this.optionTitle = optionTitle;
    }

    public Drawable getIcon() {
        return icon;
    }
    public String getOptionTitle() {
        return optionTitle;
    }
}

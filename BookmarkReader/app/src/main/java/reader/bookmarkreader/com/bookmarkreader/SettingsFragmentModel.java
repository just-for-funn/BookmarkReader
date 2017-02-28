package reader.bookmarkreader.com.bookmarkreader;

import android.databinding.ObservableField;

/**
 * Created by davut on 28.02.2017.
 */

public class SettingsFragmentModel {

    public void setName(ObservableField<String> name) {
        this.name = name;
    }

    public ObservableField<String> getName() {
        return name;
    }

    ObservableField<String> name = new ObservableField<String>("initial");
}

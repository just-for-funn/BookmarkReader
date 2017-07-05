package reader.bookmarkreader.com.bookmarkreader.service;



import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.codezilla.bookmarkreader.service.UserService;
import com.codezilla.bookmarkreader.settings.Settings;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Created by davut on 20.02.2017.
 */
@RunWith(AndroidJUnit4.class)
public class UserServiceTest
{
    UserService service;
    @Before
    public void before()
    {
        service = new UserService();
    }

    @Test
    public void shouldSaveWorkModeSettings()
    {
        Settings settings = new Settings();
        settings.workMode(Settings.WorkMode.ONLY_WIFI);
        service.save(settings);
        Settings recovered = service.settings();
        assertThat(recovered.workMode() , equalTo(Settings.WorkMode.ONLY_WIFI));
    }
}
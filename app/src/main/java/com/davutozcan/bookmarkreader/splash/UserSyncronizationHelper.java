package com.davutozcan.bookmarkreader.splash;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.davutozcan.bookmarkreader.backend.User;
import com.davutozcan.bookmarkreader.domainmodel.WebUnit;
import com.davutozcan.bookmarkreader.weblist.WebUnitService;

import java.util.List;

public class UserSyncronizationHelper {
    WebUnitService webUnitRepository;

    public UserSyncronizationHelper(WebUnitService webUnitRepository) {
        this.webUnitRepository = webUnitRepository;
    }

    public void syncronize(User user){
        List<String> existings = Stream.of(webUnitRepository.getWebSitesInfos()).map(WebUnit::getUrl).collect(Collectors.toList());
        List<String> fromBackend = user.getBookmarks();
        Stream.of(fromBackend)
                .filter(url->!existings.contains(url))
                .forEach(webUnitRepository::add);

        Stream.of(existings)
                .filter(url -> !fromBackend.contains(url))
                .forEach(webUnitRepository::remove);
    }

}

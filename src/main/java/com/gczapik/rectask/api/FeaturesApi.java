package com.gczapik.rectask.api;

import com.gczapik.rectask.control.StorageService;
import com.gczapik.rectask.model.FeatureDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gczapik.rectask.api.FeaturesApi.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
public class FeaturesApi {

    public static final String BASE_PATH = "/features";
    public static final String FEATURE_INFO = "feature";
    public static final String USER_FEATURES = "user-features";
    public static final String ENABLE = "enable";
    public static final String DISABLE = "disable";

    @Autowired
    private StorageService storageService;

    @GetMapping
    public List<String> findAllFeatures() {
        return storageService.getAllFeatures();
    }

    @GetMapping(value = "/" + FEATURE_INFO + "/{feature}")
    public FeatureDTO fetchFeature(@PathVariable("feature") String featureName) {
        return storageService.getFeature(featureName);
    }

    @GetMapping(value = "/" + USER_FEATURES + "/{user}")
    public List<String> fetchUserFeatures(@PathVariable("user") String username) {
        return storageService.getAllEnabledFeatures(username);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody String featureName) {
        storageService.create(featureName);
    }

    @PutMapping(value = "/" + ENABLE + "/{username}/{feature}")
    @ResponseStatus(HttpStatus.OK)
    public void enable(@PathVariable("username") String username, @PathVariable("feature") String featureName) {
        storageService.enable(username, featureName);
    }

    @PutMapping(value = "/"+DISABLE+"/{username}/{feature}")
    @ResponseStatus(HttpStatus.OK)
    public void disable(@PathVariable("username") String username, @PathVariable("feature") String featureName) {
        storageService.disable(username, featureName);
    }
}

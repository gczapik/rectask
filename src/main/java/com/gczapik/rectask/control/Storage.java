package com.gczapik.rectask.control;

import com.gczapik.rectask.model.FeatureDTO;

import java.util.List;

public interface Storage {
    void save(FeatureDTO feature);
    FeatureDTO get(String featureName);
    void enable(String username, String featureName);
    void disable(String username, String featureName);
    List<String> getAllFeaturesForUser(String username);
    List<String> getAllFeatures();
}

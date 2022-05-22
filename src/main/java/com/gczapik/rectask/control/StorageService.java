package com.gczapik.rectask.control;

import com.gczapik.rectask.model.FeatureDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Component
public class StorageService {

    @Autowired
    private Storage storage;

    public void create(String featureName) {
        validateIfNew(featureName);
        FeatureDTO feature = FeatureDTO.builder()
                .name(featureName)
                .globallyEnabled(false)
                .build();
        storage.save(feature);
    }

    public void update(FeatureDTO feature) {
        validateIfExists(feature.getName());
        storage.save(feature);
    }

    public List<String> getAllEnabledFeatures(String username) {
        return storage.getAllFeaturesForUser(username);
    }

    public List<String> getAllFeatures() {
        return storage.getAllFeatures();
    }

    public FeatureDTO getFeature(String featureName) {
        return storage.get(featureName);
    }

    public void enable(String username, String featureName) {
        storage.enable(username, featureName);
    }

    public void disable(String username, String featureName) {
        storage.disable(username, featureName);
    }

    private void validateIfNew(String featureName) {
        Optional<FeatureDTO> featureOptional = ofNullable(storage.get(featureName));
        if (featureOptional.isPresent()) {
            throw new IllegalStateException("FeatureDTO with this name is already defined! Use PUT method to update it or pick a different name to create new one.");
        }
    }

    private void validateIfExists(String featureName) {
        Optional<FeatureDTO> featureOptional = ofNullable(storage.get(featureName));
        if (!featureOptional.isPresent()) {
            throw new IllegalStateException("FeatureDTO with this name does not exist! Use POST method to create it first.");
        }
    }
}

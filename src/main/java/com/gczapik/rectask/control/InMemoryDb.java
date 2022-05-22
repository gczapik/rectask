package com.gczapik.rectask.control;

import com.gczapik.rectask.model.FeatureDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;
import java.util.*;

import static java.util.Collections.sort;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.BooleanUtils.isTrue;

@Repository
public class InMemoryDb implements Storage {

    private Map<String, Boolean> features;
    private MultiValueMap<String, String> usersToFeatures;

    @PostConstruct
    public void init() {
        features = new HashMap<>();
        usersToFeatures = new LinkedMultiValueMap();
    }

    @Override
    public synchronized void save(FeatureDTO feature) {
        features.put(feature.getName(), false);
    }

    @Override
    public synchronized FeatureDTO get(String featureName) {
        Boolean enabled = features.get(featureName);
        if(enabled != null) {
            return FeatureDTO.builder()
                    .name(featureName)
                    .globallyEnabled(enabled)
                    .build();
        }
        return null;
    }

    @Override
    public synchronized void enable(String username, String featureName) {
        usersToFeatures.addIfAbsent(username, featureName);
    }

    @Override
    public synchronized void disable(String username, String featureName) {
        usersToFeatures.remove(username, featureName);
    }

    @Override
    public synchronized List<String> getAllFeaturesForUser(String username) {
        List<String> userFeatures = usersToFeatures.getOrDefault(username, new ArrayList<>());
        userFeatures.addAll(extractGloballyEnabled(features));
        ArrayList<String> uniqueFeatureEntries = new ArrayList<>(new HashSet<>(userFeatures));
        sort(uniqueFeatureEntries);
        return uniqueFeatureEntries;
    }

    @Override
    public synchronized List<String> getAllFeatures() {
        List<String> allFeatures = new ArrayList<>(features.keySet());
        sort(allFeatures);
        return allFeatures;
    }

    private Set<String> extractGloballyEnabled(Map<String, Boolean> features) {
        return features.entrySet()
                .stream()
                .filter(entry -> isTrue(features.get(entry.getKey())))
                .map(entry -> entry.getKey())
                .collect(toSet());
    }

}

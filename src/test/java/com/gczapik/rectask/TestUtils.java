package com.gczapik.rectask;

import com.gczapik.rectask.model.FeatureDTO;

public class TestUtils {

    public static final String SAMPLE_FEATURE = "Sample feature";
    public static final String SAMPLE_USER = "SampleUser";

    public static FeatureDTO sampleFeature() {
        return FeatureDTO.builder()
                .name(SAMPLE_FEATURE)
                .globallyEnabled(false)
                .build();
    }
}

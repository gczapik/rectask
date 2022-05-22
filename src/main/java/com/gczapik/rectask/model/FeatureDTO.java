package com.gczapik.rectask.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeatureDTO {
    private String name;
    private boolean globallyEnabled;
}

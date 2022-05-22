package com.gczapik.rectask.control;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gczapik.rectask.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag("UnitTest")
class StorageServiceTest {

    private static final int ZERO_TIMES = 0;

    @Mock
    private Storage storage;

    @InjectMocks
    private StorageService tested;

    @Test
    void shouldCreate() {
        //given
        given(storage.get(SAMPLE_FEATURE)).willReturn(null);

        //when
        tested.create(SAMPLE_FEATURE);

        //then
        verify(storage).save(sampleFeature());
    }

    @Test
    void shouldNotCreateIfRecordExists() {
        //given
        given(storage.get(SAMPLE_FEATURE)).willReturn(sampleFeature());

        //when & then
        assertThrows(IllegalStateException.class, () -> tested.create(SAMPLE_FEATURE));
        verify(storage, times(ZERO_TIMES)).save(any());
    }

    @Test
    void shouldUpdate() {
        //given
        given(storage.get(SAMPLE_FEATURE)).willReturn(sampleFeature());

        //when
        tested.update(sampleFeature());

        //then
        verify(storage).save(sampleFeature());
    }

    @Test
    void shouldNotUpdateIfNoRecord() {
        //given
        given(storage.get(SAMPLE_FEATURE)).willReturn(null);

        //when & then
        assertThrows(IllegalStateException.class, () -> tested.update(sampleFeature()));
        verify(storage, times(ZERO_TIMES)).save(any());
    }

    @Test
    void shouldGetAllEnabledFeatures() {
        //given

        //when
        tested.getAllEnabledFeatures(SAMPLE_USER);

        //then
        verify(storage).getAllFeaturesForUser(SAMPLE_USER);
    }

    @Test
    void shouldGetAllFeatures() {
        //given

        //when
        tested.getAllFeatures();

        //then
        verify(storage).getAllFeatures();
    }

    @Test
    void shouldGetFeature() {
        //given

        //when
        tested.getFeature(SAMPLE_FEATURE);

        //then
        verify(storage).get(SAMPLE_FEATURE);
    }

    @Test
    void shouldEnable() {
        //given

        //when
        tested.enable(SAMPLE_USER, SAMPLE_FEATURE);

        //then
        verify(storage).enable(SAMPLE_USER, SAMPLE_FEATURE);
    }

    @Test
    void shouldDisable() {
        //given

        //when
        tested.disable(SAMPLE_USER, SAMPLE_FEATURE);

        //then
        verify(storage).disable(SAMPLE_USER, SAMPLE_FEATURE);
    }
}
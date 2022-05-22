package com.gczapik.rectask.api;

import com.gczapik.rectask.control.Storage;
import com.gczapik.rectask.model.FeatureDTO;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.gczapik.rectask.TestUtils.*;
import static com.gczapik.rectask.api.FeaturesApi.*;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("IntegrationTest")
class FeaturesApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Storage storage;

    @Test
    void findAllFeatures_shouldReturnHttpOk() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get(BASE_PATH));

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void findAllFeatures_shouldReturnStoredData() throws Exception {
        //given
        given(storage.getAllFeatures()).willReturn(singletonList(SAMPLE_FEATURE));

        //when
        ResultActions resultActions = mockMvc.perform(get(BASE_PATH));

        //then
        resultActions.andExpect(content().string(containsString(SAMPLE_FEATURE)));
    }

    @Test
    void fetchFeature_shouldReturnHttpOk() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get(BASE_PATH + "/" + FEATURE_INFO + "/" + SAMPLE_FEATURE));

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void fetchFeature_shouldReturnStoredData() throws Exception {
        //given
        given(storage.get(any())).willReturn(sampleFeature());

        //when
        ResultActions resultActions = mockMvc.perform(get(BASE_PATH + "/" + FEATURE_INFO + "/" + SAMPLE_FEATURE));

        //then
        resultActions.andExpect(content().string(containsString(SAMPLE_FEATURE)));
    }

    @Test
    void fetchUserFeatures_shouldReturnHttpOk() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get(BASE_PATH + "/" + USER_FEATURES + "/" + SAMPLE_USER));

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void fetchUserFeatures_shouldReturnStoredData() throws Exception {
        //given
        given(storage.getAllFeaturesForUser(any())).willReturn(singletonList(SAMPLE_FEATURE));

        //when
        ResultActions resultActions = mockMvc.perform(get(BASE_PATH + "/" + USER_FEATURES + "/" + SAMPLE_FEATURE));

        //then
        resultActions.andExpect(content().string(containsString(SAMPLE_FEATURE)));
    }

    @Test
    void create_shouldReturnHttpOk() throws Exception {
        //given
        given(storage.get(any())).willReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(post(BASE_PATH).content(SAMPLE_FEATURE));

        //then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    void create_shouldProccess() throws Exception {
        //given
        given(storage.get(any())).willReturn(null);

        //when
        mockMvc.perform(post(BASE_PATH).content(SAMPLE_FEATURE));

        //then
        verify(storage).save(sampleFeature());
    }

    @Test
    void enable_shouldReturnHttpOk() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(put(BASE_PATH + "/" + ENABLE + "/" + SAMPLE_USER + "/" + SAMPLE_FEATURE));

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void enable_shouldProccess() throws Exception {
        //given

        //when
        mockMvc.perform(put(BASE_PATH + "/" + ENABLE + "/" + SAMPLE_USER + "/" + SAMPLE_FEATURE));

        //then
        verify(storage).enable(SAMPLE_USER, SAMPLE_FEATURE);
    }

    @Test
    void disable_shouldReturnHttpOk() throws Exception {
        //given

        //when
        ResultActions resultActions = mockMvc.perform(put(BASE_PATH + "/" + DISABLE + "/" + SAMPLE_USER + "/" + SAMPLE_FEATURE));

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void disable_shouldProccess() throws Exception {
        //given

        //when
        mockMvc.perform(put(BASE_PATH + "/" + DISABLE + "/" + SAMPLE_USER + "/" + SAMPLE_FEATURE));

        //then
        verify(storage).disable(SAMPLE_USER, SAMPLE_FEATURE);
    }
}
package pl.parser.nbp.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyRatesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnRatesStatistics() throws Exception {
        //given
        String urlTemplate = "/api/rate-statistics?currencyCode=EUR&startDateString=2015-03-17&endDateString=2015-03-17";
        String responseBody = "{\"avgBuyingRate\":\"4,0887\",\"stdDevSellingRate\":\"0,0000\"}";

        //then
        mockMvc.perform(get(urlTemplate)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test
    public void shouldReturnBadRequestWhenMissingParam() throws Exception {
        //given
        String urlTemplate = "/api/rate-statistics?currencyCode=EUR&startDateString=2015-03-17";

        //then
        mockMvc.perform(get(urlTemplate)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnUnregisteredDateMessage() throws Exception {
        //given
        String urlTemplate = "/api/rate-statistics?currencyCode=EUR&startDateString=2015-03-17&endDateString=2015-03-21";
        String responseBody = "Given date: 2015-03-21 is not recorded in currency data";

        //then
        mockMvc.perform(get(urlTemplate)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(responseBody)))
                .andExpect(status().isOk());
    }
}
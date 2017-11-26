package pl.artcoder.playground.videostore.rent.infrastructure.boundary.rest

import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.ResultActions
import pl.artcoder.playground.videostore.base.IntegrationSpec
import pl.artcoder.playground.videostore.film.commons.SampleFilms
import pl.artcoder.playground.videostore.film.domain.Title
import pl.artcoder.playground.videostore.rent.infrastructure.boundary.rest.requestmodel.CalculateRentCostRequestJson

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class CalculateRentCostControllerIntegrationSpec extends IntegrationSpec implements SampleFilms {

    @WithMockUser
    def "should skip result for non-existing films"() {
        given: 'Non existing film title'
        Title nonExistingTitle = Title.from("Spiderman")

        when: 'I post to /calculate with non-existing film title for 3 days'
        def calcRequest = aCalculateRentCostRequest([nonExistingTitle], 3)
        ResultActions postCalculate = mockMvc.perform(
                post("/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(calcRequest))
        )
        then: 'I see empty response'
        postCalculate.andExpect(status().isOk())
                .andExpect(content().json("[]"))
    }

    private def aCalculateRentCostRequest(List<Title> titles, int rentalDays) {
        CalculateRentCostRequestJson.of(
                titles.collect { it.value() },
                rentalDays
        )
    }

}

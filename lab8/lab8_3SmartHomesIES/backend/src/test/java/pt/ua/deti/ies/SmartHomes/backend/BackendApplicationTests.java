package pt.ua.deti.ies.SmartHomes.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
		"spring.datasource.url=jdbc:h2:mem:test",
		"spring.datasource.driverClassName=org.h2.Driver",
		"spring.h2.console.enabled=true",
		"spring.jpa.hibernate.ddl-auto=create-drop",
		"spring.jpa.show-sql=true"
})
class BackendApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(mockMvc);
	}

	@Test
	void healthCheckEndpointShouldBeAccessible() {
		try {
			MvcResult result = mockMvc.perform(get("/actuator/health")).andReturn();
			Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		} catch (Exception e) {
			Assertions.fail("Health check request failed: " + e.getMessage());
		}
	}

	@Test
	@WithMockUser(username = "testuser", roles = "USER")
	void authenticatedUserCanAccessSecuredEndpoint() {
		try {
			MvcResult result = mockMvc.perform(get("/api/v1/users/me")).andReturn();
			Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		} catch (Exception e) {
			Assertions.fail("Authenticated request failed: " + e.getMessage());
		}
	}

	@Test
	void unauthenticatedUserCannotAccessSecuredEndpoint() {
		try {
			MvcResult result = mockMvc.perform(get("/api/v1/users/me")).andReturn();
			Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getResponse().getStatus());
		} catch (Exception e) {
			Assertions.fail("Unauthenticated request failed: " + e.getMessage());
		}
	}

}

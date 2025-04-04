package ua.tqs.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import ua.tqs.service.APIService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class APIServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private APIService apiService;

    @BeforeEach
    public void setUp() {
    }
}

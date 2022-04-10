package mj.song.microservices.core.management;

import mj.song.microservices.core.management.domain.ManagementInfo;
import mj.song.microservices.core.management.domain.Post;
import mj.song.microservices.core.management.domain.User;
import mj.song.microservices.core.management.service.ManagementService;
import mj.song.util.exceptions.InvalidInputException;
import mj.song.util.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@AutoConfigureWebTestClient
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ManagementServiceApplicationTests {

	private static final long USER_ID_OK = 1;
	private static final long USER_ID_NOT_FOUND = 404;
	private static final long USER_ID_INVALID = 0;

	@Autowired
	private WebTestClient client;

	@MockBean
	ManagementService managementService;

	@BeforeEach
	private void setUp(){
		when(managementService.getManagementInfo(USER_ID_OK))
				.thenReturn(
					new ManagementInfo(
						"title",
						new User(USER_ID_OK, "name of user", "0100000000" + USER_ID_OK),
						singletonList(new Post(1, USER_ID_OK, "content of post"))
					)
				);

		when(managementService.getManagementInfo(USER_ID_NOT_FOUND))
			.thenThrow(new NotFoundException("NOT FOUND:" + USER_ID_NOT_FOUND));

		when(managementService.getManagementInfo(USER_ID_INVALID))
			.thenThrow(new InvalidInputException("INVALID INPUT:" + USER_ID_INVALID));
	}

	@Test
	void contextLoads() {
	}

	@Test
	void getManagementInfoOK(){
		client.get()
			.uri("/management/" + USER_ID_OK)
			.accept(APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody()
			.jsonPath("$.user.id").isEqualTo(USER_ID_OK)
			.jsonPath("$.posts.length()").isEqualTo(1);
	}

	@Test
	void getManagementInfoNotFound(){
		client.get()
				.uri("/management/" + USER_ID_NOT_FOUND)
				.accept(APPLICATION_JSON)
				.exchange()
				.expectStatus().isNotFound()
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/management/" + USER_ID_NOT_FOUND)
				.jsonPath("$.message").isEqualTo("NOT FOUND:" + USER_ID_NOT_FOUND);
	}

	@Test
	void getManagementInfoInvalid(){
		client.get()
				.uri("/management/" + USER_ID_INVALID)
				.accept(APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/management/" + USER_ID_INVALID)
				.jsonPath("$.message").isEqualTo("INVALID INPUT:" + USER_ID_INVALID);
	}

}

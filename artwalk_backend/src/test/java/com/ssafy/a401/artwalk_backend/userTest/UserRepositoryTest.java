package com.ssafy.a401.artwalk_backend.domain.user;

import java.util.List;

import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void findAll() {
		List<User> users = userRepository.findAll();
		System.out.println(users.size());
		// assertThat(users.size(), );
	}
}
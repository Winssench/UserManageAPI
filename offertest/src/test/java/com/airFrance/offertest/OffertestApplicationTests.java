package com.airFrance.offertest;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SpringBootTest
class OffertestApplicationTests {

	@Test
	void contextLoads() {
		String path = "src/test/resources";

		File file = new File(path);
		String absolutePath = file.getAbsolutePath();

		System.out.println(absolutePath);

		assertThat(absolutePath.endsWith("src/test/resources"));
	}

}

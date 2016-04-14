package net.vg4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import net.vg4.valcana.ValcanApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ValcanApplication.class)
@WebAppConfiguration
public class ValcanApplicationTests {

	@Test
	public void contextLoads() {
	}

}

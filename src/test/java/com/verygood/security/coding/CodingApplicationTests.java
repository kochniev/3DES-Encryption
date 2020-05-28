package com.verygood.security.coding;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.verygood.security.coding.api.exception.WrongInputArgumentsException;
import org.junit.jupiter.api.Test;

class CodingApplicationTests {

	@Test
	void applicationStartsWithoutErrors() {
		String[] args = new String[]{"encryptionKey", "textToEncrypt"};
		CodingApplication.main(args);
	}

	@Test
	void applicationEncryptsDecryptsRussian() {
		String[] args = new String[]{"encryptionKey", "русский"};
		CodingApplication.main(args);
	}

	@Test
	void applicationEncryptsDecryptsRussian_whenKeyInRussian() {
		String[] args = new String[]{"русскийКлюч", "русский"};
		CodingApplication.main(args);
	}

	@Test
	void applicationStartsWithoutErrors_ifKeyAndTextAreEmpty() {
		String[] args = new String[]{"", ""};
		CodingApplication.main(args);
	}

	@Test
	void applicationStartsWithoutErrors_ifKeyAndTextAreBlank() {
		String[] args = new String[]{" ", " "};
		CodingApplication.main(args);
	}

	@Test
	void applicationStartsWithoutErrors_ifKeyIsBlank_whileTextHasSomeValue() {
		String[] args = new String[]{" ", "test"};
		CodingApplication.main(args);
	}

	@Test
	void applicationStartsWithoutErrors_ifKeyNotBlank_whileTextIsBlank() {
		String[] args = new String[]{"test", " "};
		CodingApplication.main(args);
	}


	@Test
	void applicationThrowsWrongInputArgumentsException_ifOnlyOneArgPassed() {
		assertThrows(WrongInputArgumentsException.class,
				()->{
					String[] args = new String[]{"encryptionKey"};
					CodingApplication.main(args);
				});
	}

	@Test
	void applicationThrowsWrongInputArgumentsException_ifNoArgPassed() {
		assertThrows(WrongInputArgumentsException.class,
				()->{
					String[] args = new String[]{};
					CodingApplication.main(args);
				});
	}
}

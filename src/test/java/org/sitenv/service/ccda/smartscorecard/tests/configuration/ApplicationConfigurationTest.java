package org.sitenv.service.ccda.smartscorecard.tests.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.sitenv.service.ccda.smartscorecard.configuration.ApplicationConfiguration.CCDA_DEV_SERVER_URL;
import static org.sitenv.service.ccda.smartscorecard.configuration.ApplicationConfiguration.CCDA_TEST_SERVER_URL;
import static org.sitenv.service.ccda.smartscorecard.configuration.ApplicationConfiguration.CCDA_GOV_PROD_SERVER_URL;
import static org.sitenv.service.ccda.smartscorecard.configuration.ApplicationConfiguration.CCDA_PROD_SERVER_URL;
import static org.sitenv.service.ccda.smartscorecard.configuration.ApplicationConfiguration.CCDA_SCORECARD_SERVICE;
import static org.sitenv.service.ccda.smartscorecard.configuration.ApplicationConfiguration.CODE_AND_DISPLAYNAME_IN_CODESYSTEM_SERVICE;
import static org.sitenv.service.ccda.smartscorecard.configuration.ApplicationConfiguration.CODE_IN_CODESYSTEM_SERVICE;
import static org.sitenv.service.ccda.smartscorecard.configuration.ApplicationConfiguration.CODE_IN_VALUESET_SERVICE;
import static org.sitenv.service.ccda.smartscorecard.configuration.ApplicationConfiguration.DEFAULT_LOCAL_REF_VAL_SERVER_URL;
import static org.sitenv.service.ccda.smartscorecard.configuration.ApplicationConfiguration.DEFAULT_LOCAL_SCORECARD_SERVER_URL;
import static org.sitenv.service.ccda.smartscorecard.configuration.ApplicationConfiguration.REFERENCE_CCDA_SERVICE;
import static org.sitenv.service.ccda.smartscorecard.configuration.ApplicationConfiguration.SAVE_SCORECARD_SERVICE_BACKEND;
import static org.sitenv.service.ccda.smartscorecard.configuration.ApplicationConfiguration.TTP_DEV_SERVER_URL;
import static org.sitenv.service.ccda.smartscorecard.configuration.ApplicationConfiguration.TTP_TEST_SERVER_URL;
import static org.sitenv.service.ccda.smartscorecard.configuration.ApplicationConfiguration.TTP_GOV_PROD_SERVER_URL;
import static org.sitenv.service.ccda.smartscorecard.configuration.ApplicationConfiguration.TTP_PROD_SERVER_URL;

import org.junit.Ignore;
import org.junit.Test;
import org.sitenv.service.ccda.smartscorecard.configuration.ApplicationConfiguration;
import org.sitenv.service.ccda.smartscorecard.configuration.ApplicationConfiguration.EndpointType;
import org.sitenv.service.ccda.smartscorecard.configuration.ApplicationConfiguration.Environment;

public class ApplicationConfigurationTest {

	private static void checkEnvironment(Environment envToTest, String expectedRefValServerUrl,
			String expectedScorecardServerUrl) {
		String server = envToTest.server(EndpointType.RefVal);
		assertEquals(server + CODE_AND_DISPLAYNAME_IN_CODESYSTEM_SERVICE,
				expectedRefValServerUrl + CODE_AND_DISPLAYNAME_IN_CODESYSTEM_SERVICE);
		assertEquals(server + CODE_IN_VALUESET_SERVICE, expectedRefValServerUrl + CODE_IN_VALUESET_SERVICE);
		assertEquals(server + CODE_IN_CODESYSTEM_SERVICE, expectedRefValServerUrl + CODE_IN_CODESYSTEM_SERVICE);
		assertEquals(server + REFERENCE_CCDA_SERVICE, expectedRefValServerUrl + REFERENCE_CCDA_SERVICE);

		server = envToTest.server(EndpointType.Scorecard);
		assertEquals(server + SAVE_SCORECARD_SERVICE_BACKEND,
				expectedScorecardServerUrl + SAVE_SCORECARD_SERVICE_BACKEND);
		assertEquals(server + SAVE_SCORECARD_SERVICE_BACKEND,
				expectedScorecardServerUrl + SAVE_SCORECARD_SERVICE_BACKEND);
		assertEquals(server + CCDA_SCORECARD_SERVICE, expectedScorecardServerUrl + CCDA_SCORECARD_SERVICE);
	}

	@Test
	public void testLocalOrCustomEnvironment() {
		final Environment env = Environment.LOCAL_OR_CUSTOM;

		assertTrue(env.isDevLocalOrCustom());
		assertFalse(env.isProduction());

		checkEnvironment(env, DEFAULT_LOCAL_REF_VAL_SERVER_URL, DEFAULT_LOCAL_SCORECARD_SERVER_URL);
	}

	@Test
	public void testDevEnvironment() {
		final Environment env = Environment.DEV;

		assertTrue(env.isDevLocalOrCustom());
		assertFalse(env.isProduction());

		checkEnvironment(env, TTP_DEV_SERVER_URL, CCDA_DEV_SERVER_URL);
	}
	
	@Test
	public void testTestEnvironment() {
		final Environment env = Environment.TEST;

		assertTrue(env.isDevLocalOrCustom());
		assertFalse(env.isProduction());

		checkEnvironment(env, TTP_TEST_SERVER_URL, CCDA_TEST_SERVER_URL);
	}	

	@Test
	public void testProdEnvironment() {
		final Environment env = Environment.PROD;

		assertTrue(env.isProduction());
		assertFalse(env.isDevLocalOrCustom());

		checkEnvironment(env, TTP_PROD_SERVER_URL, CCDA_PROD_SERVER_URL);
	}

	@Test
	public void testDotGovProdEnvironment() {
		final Environment env = Environment.DOT_GOV_PROD;

		assertTrue(env.isProduction());
		assertFalse(env.isDevLocalOrCustom());

		checkEnvironment(env, TTP_GOV_PROD_SERVER_URL, CCDA_GOV_PROD_SERVER_URL);
	}
	
	@Test
	public void testProdRefValLocalOrCustomScorecardEnvironment() {
		final Environment env = Environment.PROD_REF_VAL_WITH_LOCAL_OR_CUSTOM_SCORECARD;

		assertTrue(env.isDevLocalOrCustom());
		assertFalse(env.isProduction());

		checkEnvironment(env, TTP_PROD_SERVER_URL, DEFAULT_LOCAL_SCORECARD_SERVER_URL);
	}

	private static void checkEnvironmentIfSetInConfig(Environment envToTest, String expectedRefValServerUrl,
			String expectedScorecardServerUrl) {
		if (ApplicationConfiguration.ENV == envToTest) {
			assertEquals(ApplicationConfiguration.CODE_DISPLAYNAME_VALIDATION_URL,
					expectedRefValServerUrl + CODE_AND_DISPLAYNAME_IN_CODESYSTEM_SERVICE);
			assertEquals(ApplicationConfiguration.CODE_VALUSET_VALIDATION_URL,
					expectedRefValServerUrl + CODE_IN_VALUESET_SERVICE);
			assertEquals(ApplicationConfiguration.CODE_CODESYSTEM_VALIDATION_URL,
					expectedRefValServerUrl + CODE_IN_CODESYSTEM_SERVICE);
			assertEquals(ApplicationConfiguration.REFERENCE_VALIDATOR_URL,
					expectedRefValServerUrl + REFERENCE_CCDA_SERVICE);

			assertEquals(ApplicationConfiguration.SAVESCORECARDSERVICEBACKEND_URL,
					expectedScorecardServerUrl + SAVE_SCORECARD_SERVICE_BACKEND);
			assertEquals(ApplicationConfiguration.SAVESCORECARDSERVICEBACKENDSUMMARY_URL,
					expectedScorecardServerUrl + SAVE_SCORECARD_SERVICE_BACKEND);
			assertEquals(ApplicationConfiguration.CCDASCORECARDSERVICE_URL,
					expectedScorecardServerUrl + CCDA_SCORECARD_SERVICE);
		}
	}

	@Test
	public void testLocalOrCustomEnvironmentIfSetInConfig() {
		if (ApplicationConfiguration.ENV == Environment.LOCAL_OR_CUSTOM) {
			assertTrue(ApplicationConfiguration.ENV.isDevLocalOrCustom());
			assertFalse(ApplicationConfiguration.ENV.isProduction());
			checkEnvironmentIfSetInConfig(ApplicationConfiguration.ENV, DEFAULT_LOCAL_REF_VAL_SERVER_URL,
					DEFAULT_LOCAL_SCORECARD_SERVER_URL);
		}
	}

	@Test
	public void testDevEnvironmentIfSetInConfig() {
		if (ApplicationConfiguration.ENV == Environment.DEV) {
			assertTrue(ApplicationConfiguration.ENV.isDevLocalOrCustom());
			assertFalse(ApplicationConfiguration.ENV.isProduction());
			checkEnvironmentIfSetInConfig(ApplicationConfiguration.ENV, TTP_DEV_SERVER_URL, CCDA_DEV_SERVER_URL);
		}
	}

	@Test
	public void testProdEnvironmentIfSetInConfig() {
		if (ApplicationConfiguration.ENV == Environment.PROD) {
			assertTrue(ApplicationConfiguration.ENV.isProduction());
			assertFalse(ApplicationConfiguration.ENV.isDevLocalOrCustom());
			checkEnvironmentIfSetInConfig(ApplicationConfiguration.ENV, TTP_PROD_SERVER_URL, CCDA_PROD_SERVER_URL);
		}
	}

	@Ignore
	@Test
	public void testDotGovDevEnvironmentIfSetInConfig() {
		// Nothing to test yet in this manner since not implemented/no constant exists
		// yet to compare with in ApplicationConfiguration
	}

	@Test
	public void testDotGovProdEnvironmentIfSetInConfig() {
		if (ApplicationConfiguration.ENV == Environment.DOT_GOV_PROD) {
			assertTrue(ApplicationConfiguration.ENV.isProduction());
			assertFalse(ApplicationConfiguration.ENV.isDevLocalOrCustom());
			checkEnvironmentIfSetInConfig(ApplicationConfiguration.ENV, TTP_GOV_PROD_SERVER_URL,
					CCDA_GOV_PROD_SERVER_URL);
		}
	}
	
	@Test
	public void testProdRefValLocalOrCustomScorecardEnvironmentIfSetInConfig() {
		if (ApplicationConfiguration.ENV == Environment.PROD_REF_VAL_WITH_LOCAL_OR_CUSTOM_SCORECARD) {
			assertTrue(ApplicationConfiguration.ENV.isDevLocalOrCustom());
			assertFalse(ApplicationConfiguration.ENV.isProduction());
			checkEnvironmentIfSetInConfig(ApplicationConfiguration.ENV, TTP_PROD_SERVER_URL,
					DEFAULT_LOCAL_SCORECARD_SERVER_URL);
		}
	}
	
	/*
	@Test
	public void testOverrideConfigFilePathToScorecardConfigXMLRulesPath() {
//		System.out.println(LOCAL_CONFIG_FILE_PATH);
//		System.out.println(DEPLOYED_CONFIG_FILE_PATH);
	}
	*/

}

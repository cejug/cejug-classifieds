package net.java.dev.cejug.classifieds.test.integration;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness;
import net.java.dev.cejug_classifieds.business.CejugClassifiedsServiceBusiness;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.business.Locale;
import net.java.dev.cejug_classifieds.metadata.business.Period;
import net.java.dev.cejug_classifieds.metadata.business.PublishingHeader;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementType;
import net.java.dev.cejug_classifieds.metadata.common.Customer;
import net.java.dev.cejug_classifieds.metadata.common.Domain;

import org.junit.Assert;

import pl.kernelpanic.dbmonster.DBMonster;
import pl.kernelpanic.dbmonster.DBMonsterContext;
import pl.kernelpanic.dbmonster.DictionaryManager;
import pl.kernelpanic.dbmonster.generator.StringGenerator;

public class BusinessClientMock {
	private transient CejugClassifiedsBusiness service = new CejugClassifiedsServiceBusiness()
			.getCejugClassifiedsBusiness();
	private transient final StringGenerator strGen = new StringGenerator();

	public BusinessClientMock() {
		try {
			DBMonsterContext ctx = new DBMonsterContext();
			DictionaryManager dictmanager = new DictionaryManager();
			Random random = new java.util.Random();
			dictmanager.setRandom(random);
			ctx.setProperty(DBMonster.DICTIONARY_MANAGER_KEY, dictmanager);
			ctx.setProperty(DBMonster.RANDOM_KEY, random);
			strGen.initialize(ctx);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public Advertisement createAdvertisement(Domain domain,
			AdvertisementType advType, AdvertisementCategory category) {
		Advertisement advertisement = new Advertisement();
		Customer customer = new Customer();
		customer.setLogin("fgaucho");
		customer.setDomainId(domain.getEntityId());
		advertisement.setCustomer(customer);
		advertisement.setTypeId(advType.getEntityId());

		// Publishing period
		Calendar today = GregorianCalendar.getInstance();
		Period period = new Period();
		period.setStart(today);

		Calendar fiveDaysLater = GregorianCalendar.getInstance();
		fiveDaysLater.roll(Calendar.DAY_OF_YEAR, 5);
		period.setFinish(fiveDaysLater);
		// Advertisement contents
		advertisement.setPublishingPeriod(period);
		strGen.setAllowSpaces(true);
		strGen.setMinLength(1);
		strGen.setMaxLength(15);
		advertisement.setHeadline(strGen.generate().toString());
		strGen.setMaxLength(40);
		advertisement.setSummary(strGen.generate().toString());
		strGen.setMaxLength(250);
		advertisement.setText(strGen.generate().toString());
		advertisement.setCategoryId(category.getEntityId());
		Locale locale = new Locale();
		locale.setLanguage("pt");
		locale.setCountry("BR");
		advertisement.setLocale(locale);

		strGen.setAllowSpaces(false);
		strGen.setMaxLength(20);
		advertisement.setKeywords(strGen.generate().toString() + ", "
				+ strGen.generate().toString());
		advertisement.setStatus(1);

		PublishingHeader header = new PublishingHeader();
		header.setCustomerDomainId(domain.getEntityId());
		header.setCustomerLogin("fgaucho");

		return service.publishOperation(advertisement, header);
	}
}

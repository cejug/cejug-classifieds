package net.java.dev.cejug.classifieds.test.integration;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.imageio.ImageIO;

import net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness;
import net.java.dev.cejug_classifieds.business.CejugClassifiedsServiceBusiness;
import net.java.dev.cejug_classifieds.metadata.attachments.AtavarImage;
import net.java.dev.cejug_classifieds.metadata.attachments.AvatarImageOrUrl;
import net.java.dev.cejug_classifieds.metadata.attachments.ObjectFactory;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
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

		try {
			AvatarImageOrUrl avatar = new AvatarImageOrUrl();
			URL imgUrl = getClass().getClassLoader().getResource("img/car.png");
			Image image = ImageIO.read(imgUrl);
			ObjectFactory attachmentFactory = new ObjectFactory();
			AtavarImage avimage = attachmentFactory.createAtavarImage();
			avimage.setValue(imageToByteArray(image));
			avimage.setContentType("image/png");
			avatar.setImage(avimage);
			avatar.setName("car.png");
			avatar.setDescription("test of avatar...");
			avatar.setUrl(null);
			advertisement.setAvatarImageOrUrl(avatar);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		advertisement.setLocale("pt_BR");

		strGen.setAllowSpaces(false);
		strGen.setMaxLength(20);
		advertisement.setKeywords(strGen.generate().toString() + ", "
				+ strGen.generate().toString());
		advertisement.setStatus(1);

		PublishingHeader header = new PublishingHeader();
		header.setCustomerDomainId(domain.getEntityId());
		header.setCustomerLogin("fgaucho");

		Advertisement adv = service.publishOperation(advertisement, header);
		return adv;
	}

	/**
	 * @see http://forums.sun.com/thread.jspa?messageID=9470374
	 * @param image
	 * @return
	 * @throws IOException
	 */
	public byte[] imageToByteArray(Image image) {
		MediaTracker tracker = new MediaTracker(new Container());
		tracker.addImage(image, 0);
		try {
			tracker.waitForAll();
		} catch (InterruptedException e) {
		}
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), 1);
		Graphics gc = bufferedImage.createGraphics();
		gc.drawImage(image, 0, 0, null);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "jpeg", bos);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		return bos.toByteArray();
	}
}

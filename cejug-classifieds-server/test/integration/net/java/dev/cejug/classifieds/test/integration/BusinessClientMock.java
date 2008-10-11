package net.java.dev.cejug.classifieds.test.integration;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

		AvatarImageOrUrl avatar = new AvatarImageOrUrl();
		// URL imgUrl = getClass().getClassLoader().getResource("img/car.png");
		// Image image = ImageIO.read(imgUrl);
		ObjectFactory attachmentFactory = new ObjectFactory();
		AtavarImage avimage = attachmentFactory.createAtavarImage();
		avimage.setValue(getCarPngImage());
		avimage.setContentType("image/png");
		avatar.setImage(avimage);
		avatar.setName("car.png");
		avatar.setDescription("test of avatar...");
		avatar.setUrl(null);
		advertisement.setAvatarImageOrUrl(avatar);

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

	private byte[] getCarPngImage() {
		return new byte[] { -1, -40, -1, -32, 0, 16, 74, 70, 73, 70, 0, 1, 2,
				0, 0, 1, 0, 1, 0, 0, -1, -37, 0, 67, 0, 8, 6, 6, 7, 6, 5, 8, 7,
				7, 7, 9, 9, 8, 10, 12, 20, 13, 12, 11, 11, 12, 25, 18, 19, 15,
				20, 29, 26, 31, 30, 29, 26, 28, 28, 32, 36, 46, 39, 32, 34, 44,
				35, 28, 28, 40, 55, 41, 44, 48, 49, 52, 52, 52, 31, 39, 57, 61,
				56, 50, 60, 46, 51, 52, 50, -1, -37, 0, 67, 1, 9, 9, 9, 12, 11,
				12, 24, 13, 13, 24, 50, 33, 28, 33, 50, 50, 50, 50, 50, 50, 50,
				50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50,
				50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50,
				50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, -1, -64, 0, 17, 8,
				0, 89, 0, 120, 3, 1, 34, 0, 2, 17, 1, 3, 17, 1, -1, -60, 0, 31,
				0, 0, 1, 5, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3,
				4, 5, 6, 7, 8, 9, 10, 11, -1, -60, 0, -75, 16, 0, 2, 1, 3, 3,
				2, 4, 3, 5, 5, 4, 4, 0, 0, 1, 125, 1, 2, 3, 0, 4, 17, 5, 18,
				33, 49, 65, 6, 19, 81, 97, 7, 34, 113, 20, 50, -127, -111, -95,
				8, 35, 66, -79, -63, 21, 82, -47, -16, 36, 51, 98, 114, -126,
				9, 10, 22, 23, 24, 25, 26, 37, 38, 39, 40, 41, 42, 52, 53, 54,
				55, 56, 57, 58, 67, 68, 69, 70, 71, 72, 73, 74, 83, 84, 85, 86,
				87, 88, 89, 90, 99, 100, 101, 102, 103, 104, 105, 106, 115,
				116, 117, 118, 119, 120, 121, 122, -125, -124, -123, -122,
				-121, -120, -119, -118, -110, -109, -108, -107, -106, -105,
				-104, -103, -102, -94, -93, -92, -91, -90, -89, -88, -87, -86,
				-78, -77, -76, -75, -74, -73, -72, -71, -70, -62, -61, -60,
				-59, -58, -57, -56, -55, -54, -46, -45, -44, -43, -42, -41,
				-40, -39, -38, -31, -30, -29, -28, -27, -26, -25, -24, -23,
				-22, -15, -14, -13, -12, -11, -10, -9, -8, -7, -6, -1, -60, 0,
				31, 1, 0, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 2,
				3, 4, 5, 6, 7, 8, 9, 10, 11, -1, -60, 0, -75, 17, 0, 2, 1, 2,
				4, 4, 3, 4, 7, 5, 4, 4, 0, 1, 2, 119, 0, 1, 2, 3, 17, 4, 5, 33,
				49, 6, 18, 65, 81, 7, 97, 113, 19, 34, 50, -127, 8, 20, 66,
				-111, -95, -79, -63, 9, 35, 51, 82, -16, 21, 98, 114, -47, 10,
				22, 36, 52, -31, 37, -15, 23, 24, 25, 26, 38, 39, 40, 41, 42,
				53, 54, 55, 56, 57, 58, 67, 68, 69, 70, 71, 72, 73, 74, 83, 84,
				85, 86, 87, 88, 89, 90, 99, 100, 101, 102, 103, 104, 105, 106,
				115, 116, 117, 118, 119, 120, 121, 122, -126, -125, -124, -123,
				-122, -121, -120, -119, -118, -110, -109, -108, -107, -106,
				-105, -104, -103, -102, -94, -93, -92, -91, -90, -89, -88, -87,
				-86, -78, -77, -76, -75, -74, -73, -72, -71, -70, -62, -61,
				-60, -59, -58, -57, -56, -55, -54, -46, -45, -44, -43, -42,
				-41, -40, -39, -38, -30, -29, -28, -27, -26, -25, -24, -23,
				-22, -14, -13, -12, -11, -10, -9, -8, -7, -6, -1, -38, 0, 12,
				3, 1, 0, 2, 17, 3, 17, 0, 63, 0, -34, 127, 8, 89, 36, 108, -21,
				115, 48, 32, 103, -26, 10, 127, -91, 97, -101, 38, 70, 32, 73,
				25, -63, -64, -55, -59, 116, 105, -81, -40, 93, -38, -68, -106,
				-41, -111, -56, -96, 17, -72, 28, 12, -2, 61, 107, 6, 27, -53,
				-69, -101, -91, 71, -71, -52, 101, -70, 110, -19, 92, -107,
				-31, 24, -39, 71, 67, 74, 111, 123, -105, -20, -76, 104, 36,
				-39, -10, -71, -27, 86, 110, -117, 22, -33, -26, 65, -83, 97,
				-31, -19, 41, 10, -126, -109, -79, 35, -85, 77, -113, -28, 41,
				-126, -26, 21, -102, 40, -68, -52, 49, 28, 99, -4, 104, -44,
				124, 67, -89, -40, 79, 20, 51, 92, 50, -56, -35, 2, 33, 108,
				15, -10, -120, -23, 93, 10, -108, 34, -75, 68, 54, -39, -113,
				-84, -40, 71, 103, 50, -117, 84, 42, -92, 115, -71, -77, 89,
				89, -105, 60, -94, -25, -36, -118, -42, -43, -17, -95, -72,
				-103, 26, -42, 102, -108, 99, -8, 73, -1, 0, 10, -92, -109, 72,
				-53, -52, 76, 79, -48, 26, -32, -84, -46, -101, 73, 27, 69, 43,
				106, 85, 50, 72, -68, 24, -64, 52, -46, -19, -97, -71, -109,
				-19, 86, 23, 81, -124, -71, -113, 99, -85, -114, -69, -93, 32,
				126, 100, 98, -91, 23, 18, 28, 5, -124, -112, 122, 16, 43, 55,
				39, -43, 14, -56, -94, 38, 108, -14, -124, 31, 111, -2, -75,
				47, -102, -36, -113, 45, -113, -32, 107, 64, 79, 112, 62, 80,
				-124, 123, -80, 52, -90, -30, -31, 7, 68, 30, -89, -102, -101,
				-7, 5, -111, -102, 30, 82, 120, -128, -1, 0, -33, 36, -45, -42,
				57, -40, 110, 16, 17, -18, 84, -43, -31, 117, 33, -53, 101, 8,
				-90, 25, -50, 114, 71, -32, 41, -13, 1, 84, -63, 114, -40, -60,
				88, 30, -69, 113, 82, -101, 89, 66, -116, 79, 22, 125, 54, -79,
				-2, -104, -89, 27, -111, -126, 12, 106, 42, 54, -107, 72, -30,
				35, -98, -4, -102, 124, -64, 39, -110, -32, 18, -50, -121, -2,
				0, 7, -13, 52, 84, -122, -31, 85, 64, 49, 40, -4, 77, 20, -82,
				5, 20, -14, 67, 117, 92, -5, 3, 86, 0, 70, -58, 16, 48, -86, 1,
				-112, -114, 127, -15, -45, 74, 25, 50, 2, -122, 31, -16, 44,
				81, 113, -87, 23, 76, 9, -44, -62, -96, -10, 61, -23, 68, 72,
				-71, -1, 0, 86, -65, 82, 42, -90, -28, -56, 47, -68, -29, -66,
				115, 74, 100, 92, 12, 3, -125, -33, -67, 59, -95, 115, 34, -39,
				68, -63, -53, -93, 103, -36, 82, -86, 109, 0, -85, 34, -3, 56,
				21, 72, -122, 39, 114, -12, -9, 56, -2, -75, -73, -95, -8, 115,
				80, -41, -73, -68, 45, 4, 22, -54, 118, -103, -91, 63, 41, 111,
				-18, -128, 57, 39, -41, -46, -100, 99, -52, -20, -125, -102,
				43, 114, -111, 103, 60, 23, 83, -12, -25, -6, -48, 30, 108, 13,
				-114, -33, -128, -59, 116, -53, -32, 59, 86, -73, 49, -113, 20,
				-39, 37, -21, 70, 25, 81, 21, 10, -28, -12, 56, 45, -99, -92,
				-15, -46, -107, -68, 33, -90, -38, 90, 34, -34, -8, -94, 19,
				121, -71, 67, -92, 40, 15, -52, -57, 56, 11, -100, -29, 29, 51,
				90, -3, 90, 118, -67, -123, -19, 34, 115, 62, 100, -61, -85,
				-73, -31, 74, 46, 37, -57, 51, 55, -30, 107, -95, -1, 0, -124,
				75, 73, -105, 113, -125, -59, -42, -52, 85, -118, -78, -78, 47,
				4, 117, 7, 13, -44, 85, 9, 60, 37, -85, -59, 120, 30, -43, -84,
				117, 107, 53, 0, -104, -19, 46, -124, 83, 49, -17, -9, -2, 80,
				58, 115, -109, 88, 69, 38, -20, -102, -5, -41, -7, -108, -45,
				-20, -52, -58, -110, 98, 120, -108, -25, -23, -1, 0, -42, -90,
				-7, -77, 3, -13, 78, 127, -17, -102, -102, -20, -23, -42, -73,
				50, 67, 115, -92, -21, -111, 76, -89, 6, 54, -69, -120, 17, -7,
				-57, -45, -34, -88, 38, -91, -93, -51, 123, 117, 106, -106, 26,
				-62, -75, -80, 86, 124, -49, 11, 100, 48, -56, -63, -29, 61,
				41, -5, 55, -35, 17, 114, 103, -103, -64, -26, 66, 115, -19,
				76, 19, -72, 24, -35, -70, -105, 78, -68, -47, 53, 91, 24, -18,
				-93, 77, 94, 24, -97, 59, 76, -119, 11, 30, 9, 29, 55, 15, 74,
				-125, 87, -69, -47, -76, -83, 54, 91, -47, 46, -85, 56, 66,
				-96, 33, -76, -115, 70, 73, 3, -106, -13, 14, 7, 62, -108, -3,
				-116, -81, 107, -85, -6, -120, -104, -54, -57, -116, -102, 41,
				-122, 34, 8, 25, 70, 59, 67, 124, -116, -84, -93, 32, 28, 100,
				117, -57, 74, 43, 55, 25, 39, 102, 9, 92, -86, -92, 55, -16,
				-126, 125, -86, 117, 65, -113, -71, -6, -44, -56, -118, 121,
				41, -97, -61, -4, 42, 119, -98, 13, 43, 77, -105, 83, -72,
				-116, 18, -82, -79, -64, -84, 50, -84, -35, 73, 32, -16, 64,
				24, -29, -44, -118, 112, -90, -25, 37, 20, 39, -95, -99, 114,
				-9, 86, -65, 99, 75, 125, 29, -82, -34, -6, 97, 109, 19, 59,
				-56, -86, -84, -64, -112, 70, -47, -13, 28, 41, -29, 61, -70,
				26, -42, 26, 14, -94, 56, -2, -52, -44, 9, -11, -5, 59, 127,
				-123, 114, 26, -41, -60, 77, 107, 88, -72, -127, 30, -24, -97,
				38, 81, 36, 1, 0, 12, -113, -47, 74, -111, -47, -80, 72, -56,
				-25, -109, 94, -103, -16, -1, 0, -61, -73, -105, -74, 48, -21,
				26, -27, -42, -89, -10, -113, 52, -68, 118, -17, 114, -34, 91,
				109, -56, 12, -21, -4, 93, 72, 25, 56, -17, -34, -69, -98, 18,
				60, -74, 108, 92, -38, -24, 83, -48, -4, 33, 119, -86, 106, 34,
				25, -83, -89, -74, -73, 95, -102, 105, 101, -116, -82, 7, -94,
				-25, -85, 31, -2, -67, 119, 26, -75, -27, -123, -106, -106,
				-102, 125, -108, 98, 20, -124, 40, -115, 118, 28, 12, 30, -93,
				-7, -109, -42, -90, -42, -19, 110, 46, -25, -75, 118, -65, -65,
				-114, -38, 41, 50, -74, -74, -110, -120, -106, 83, -114, -110,
				48, 27, -118, -116, 30, 1, 81, -49, 39, -127, 84, -11, 77, 70,
				-38, -59, -42, 91, -121, -114, 50, -40, 11, 107, 109, 18, -122,
				99, -37, 115, -97, -101, -2, -7, -57, -42, -86, 42, 24, 117,
				-26, -54, -116, 37, 81, -98, 117, -87, 120, -102, 72, -75, -73,
				-45, -83, -83, -18, -91, -103, -112, 74, -14, -27, 35, 65, 22,
				-19, -95, -127, 36, -77, 0, 73, 27, 112, 8, -89, -63, 21, -70,
				-51, 54, -77, 119, 109, 11, 77, 23, -17, 82, 119, 92, 59, 62,
				54, 0, 125, -69, -29, 31, -61, -111, 91, 26, -25, -120, 98,
				-45, -81, -38, -6, -58, -50, -43, 117, 38, -73, 49, -119, 37,
				80, 124, -72, 65, -55, -36, 79, 36, 22, 97, -22, 88, -112, 5,
				114, 90, 71, -114, -11, -69, -81, 16, -55, 99, -84, -49, 17,
				-33, -105, -124, -84, 64, 46, -31, -4, 37, 78, 71, 67, -57,
				127, -57, -90, 85, 113, 78, -83, 9, 74, 16, 110, 63, -102, -21,
				101, -65, 116, 116, -46, -62, -14, -41, 80, -108, -110, -105,
				-97, -31, 114, -124, -84, -113, 115, -110, -52, -54, -49, -13,
				63, 70, -63, 60, -97, -83, 110, -64, -106, -38, 124, -120, -16,
				94, 74, 37, 63, 41, 71, 101, 35, 25, -36, 57, 83, -58, 118,
				-29, -89, 122, 47, -11, -89, 121, 46, 46, -28, 107, 69, -127,
				87, 106, -57, 13, -68, 114, 52, -82, 25, 71, -54, -67, -37, 39,
				107, 47, 67, -111, -100, 17, -102, -44, -73, -15, 105, -75,
				-47, -123, -11, -84, 118, -10, -42, 105, 7, -101, -5, -72, 21,
				72, 92, 103, -97, -10, -69, 117, 60, -26, -70, -22, -44, 94,
				-53, 85, -65, 67, -98, -115, 55, -19, 116, 107, 79, -45, 123,
				127, 91, 27, -46, -37, -59, -84, -40, -76, 119, 55, 105, -108,
				92, 91, -77, 16, -91, 27, -66, 88, -10, -20, 71, 78, 43, -49,
				-59, -123, -82, -111, 54, -77, 62, -85, -81, 104, -10, -78,
				-36, -55, -104, -118, -52, -45, -112, -86, -69, 87, 34, 48,
				113, -21, -116, -41, -100, 95, 120, -114, -5, -60, 90, -83,
				-59, -34, -91, -86, -55, 2, 92, 76, 93, -62, -126, 66, -125,
				-45, 0, 118, -19, -59, 73, 101, -93, 89, 93, 95, 110, -118,
				-18, 75, -24, 98, 93, -45, 6, -123, -73, 115, -100, 99, 105,
				97, -113, -87, 21, -57, 79, 15, 74, -118, -27, -101, 108, -24,
				-105, -76, -60, 62, 104, 69, 125, -22, -25, 91, 97, -81, -8,
				127, 78, -45, 45, -84, -29, -66, -106, 97, 110, -126, 54, -98,
				59, 54, -14, -53, 122, -25, 57, -25, -81, 76, -5, 86, -51, -99,
				-54, 106, 74, -49, -89, -122, -44, 35, 24, 4, -37, 39, -103,
				-116, -13, -54, -29, 112, -23, -36, 118, -82, 6, 40, -98, 77,
				34, -42, 75, 57, 29, 85, 67, 59, 66, -117, -43, 55, 16, 73, 56,
				-22, 74, -109, -113, -24, 5, 105, 120, 127, 75, -97, 78, -15,
				-86, 41, 73, 32, -69, 24, 49, 4, -56, 125, -31, -105, 112, 95,
				124, 28, 96, 115, -125, 90, -43, -61, -45, 73, -53, 83, -102,
				-105, 52, -27, -53, -3, 104, 118, 48, 56, -107, 25, -112, 74,
				54, -79, 86, 12, -91, 88, 48, -22, 8, 35, -118, 43, 85, 116,
				-69, -44, 80, -111, -40, 60, 67, -80, 114, -79, -1, 0, -24, 88,
				-94, -72, -99, 57, 116, -117, 31, 49, -106, 28, 116, 14, -125,
				-40, 117, -87, 60, 71, -85, -24, 48, -23, 122, 22, -115, -87,
				-38, -36, 92, -57, -74, 91, -85, -125, 111, 47, -105, -28, -77,
				28, 43, 55, 76, -128, 7, 60, -113, -57, -91, 116, -62, 107,
				-112, -91, 82, -22, -27, 120, -32, -85, -111, -113, -89, 34,
				-71, 77, 83, -62, 55, 90, -107, -60, -78, -55, -84, -56, 90,
				95, -11, -126, 107, 117, -105, 127, -90, -30, -60, -109, -7,
				-41, 117, 26, 10, -100, -71, -82, 68, -91, 116, 26, 5, -49,
				-61, 125, 6, -31, 46, -124, -22, -73, 82, 47, -103, 27, -36,
				-93, -56, 85, 79, 77, -92, -126, 0, -9, 53, -37, 15, 28, -8,
				74, -6, 33, -115, 106, -37, 13, -122, -39, -25, -108, 101, -29,
				60, -100, -116, 126, 125, 107, -50, -29, -16, 53, -22, 77, 36,
				-115, 121, -90, -35, 52, -125, 4, -35, -23, -54, 79, -31, -75,
				-123, 78, 124, 35, 116, 121, -2, -53, -16, -76, -104, 32, -99,
				-42, -109, 46, 113, -2, -20, -104, -83, 93, -29, -84, 117, 31,
				55, 55, -59, -94, 61, 26, 61, 70, 100, 97, 42, -19, -5, 49,
				-113, -51, 49, 125, -87, 36, 102, -115, -79, -13, 12, -4, -40,
				56, 24, -57, -31, -42, -78, 117, -97, 46, -42, 70, 23, -10,
				-30, 91, -42, 62, 124, 83, -61, 46, -27, -119, 7, 27, 72, 31,
				-60, 122, -9, -19, -23, 88, -78, -51, -30, -90, -13, -117, -23,
				-34, 28, -111, -91, 1, 89, -125, 76, -92, -88, -24, 57, -49, 3,
				-45, -91, 82, -99, 60, 85, 36, 102, 51, 101, -95, -60, -89,
				-78, -55, 49, 3, -14, -59, 121, -11, -95, -119, -86, -99, -32,
				-81, 107, 94, -3, 15, 67, 13, 60, 53, 57, 38, -28, -19, 116,
				-19, 110, -85, -12, 49, 60, 65, 118, -33, -16, -110, -108, 118,
				42, 26, -38, 39, 27, -121, 42, -86, 100, 25, -57, 124, 51, 2,
				71, -5, 53, 70, -6, -54, 57, 45, -76, -69, -71, -116, -105, 50,
				-36, -68, -94, 61, -17, -28, -103, -29, -114, 32, 25, -13, -44,
				43, 49, -64, 62, -118, 107, 67, 80, -16, -26, -71, -88, -49,
				103, 51, -35, -39, 90, 79, 104, -52, -47, -53, 107, 28, -101,
				-7, -58, 65, 44, -57, 35, -113, -25, -21, 73, 63, -123, -11,
				91, -19, 67, -19, -41, 122, -19, -52, -105, 91, 60, -96, -31,
				6, 85, 125, 23, 57, -64, -6, 87, 110, 22, -101, -89, 66, 52,
				-27, -48, -27, -59, 85, -116, -15, 18, -87, 13, -103, 75, 82,
				-77, 104, 109, -20, 22, 51, 105, 3, 77, 110, 108, -62, 67, 14,
				-28, 12, 92, 21, 10, -52, 55, 46, 68, -84, -39, 28, -109, 24,
				-51, 79, -81, 88, -22, 114, 120, 22, -18, 51, 16, -126, 65, 59,
				-36, 61, -70, 48, 112, -79, 23, 44, 80, 48, -29, -122, 98, -33,
				64, 7, 90, -41, -1, 0, -124, 127, 82, -104, 42, -55, -83, 106,
				-123, 85, 66, -127, 25, 88, -122, 63, -32, 42, 63, 62, -76,
				-89, -63, -55, 44, 101, 39, -102, -2, 101, 61, 68, -73, 114,
				16, 126, -93, 118, 43, 121, 107, -79, -51, 6, -107, -18, 121,
				55, -10, -19, -56, -47, 14, -107, -28, 90, -120, 120, 38, 95,
				36, 121, -68, 29, -61, -26, -21, 87, -76, 104, -30, -76, -98,
				-28, 79, 115, 105, -119, 33, 92, 55, -104, -93, 4, -13, -58,
				65, -63, 29, -8, 53, -23, 113, 120, 15, 73, -113, 0, 105, -10,
				-1, 0, -16, 63, -101, -7, -26, -76, 32, -16, -82, -99, 0, 27,
				109, -83, -109, 31, -35, 64, 127, -91, 68, -29, -50, -83, 115,
				74, 21, 85, 41, 115, -38, -20, -13, 127, 8, -8, -110, -61, 78,
				-115, -20, 53, 75, 123, -37, -85, 116, 118, 104, 35, -74, 42,
				67, 49, -32, -85, 110, -57, 4, 100, 110, 28, -128, -51, -57,
				66, 59, 125, 55, 75, -114, -9, -60, -119, -30, 61, 71, 84, 73,
				110, 24, -76, -94, -55, 23, 11, 19, -73, 109, -57, -106, -57,
				25, -64, 25, 35, -16, -83, -60, -47, -20, -29, -24, 20, 127,
				-70, -128, 85, -124, -79, -73, 92, 97, 88, -2, 85, 77, -103,
				117, -71, -96, -109, -58, -61, 42, 19, 20, 85, 116, -119, 23,
				-94, 55, -30, -44, 82, 1, -63, -57, 101, 81, -11, 36, -45,
				-124, -117, -3, -12, 21, 93, 23, -47, 84, 125, 23, 53, 40, 87,
				-57, 127, -56, 80, 50, 77, -22, 127, -116, -109, -20, -76,
				-103, 7, -77, -97, -58, -128, -83, -114, 91, -11, -91, -38, 59,
				-80, 63, 83, -102, 0, 67, -113, -18, 31, -59, -87, 55, 1, -4,
				10, 62, -90, -105, 106, 14, -92, 126, 20, -121, 103, -87, -4,
				40, 1, 55, 30, -37, 7, -31, -102, 66, -17, -3, -4, 125, 22,
				-108, -70, 14, -68, -3, 77, 32, 112, -57, -27, 77, -33, 76,
				-102, 0, 76, -69, 117, 45, -6, 10, 54, -71, 29, -1, 0, -17,
				-84, -44, -23, 111, 119, 41, -60, 118, -110, -73, -47, 9, -2,
				-108, -81, 105, 120, -115, -119, 81, 97, 63, -12, -39, -42, 63,
				-3, 8, -118, 0, -82, 35, 99, -48, 47, -28, 105, -62, 39, -11,
				81, -1, 0, 1, 20, -30, -86, 62, -2, -95, 102, 63, -36, -105,
				-51, -57, -3, -5, 13, 81, -106, -75, 3, 38, -7, -104, 122, -59,
				110, -61, -1, 0, 67, -37, 82, -25, 21, -69, 65, 102, 63, -53,
				35, -85, -2, 71, 20, -69, 16, 117, 111, -42, -93, 19, -40, 118,
				-110, -18, 83, -1, 0, 1, -113, 63, -103, 106, -123, -81, -83,
				-109, 33, 109, 11, 30, -62, 91, -109, -97, -4, 117, 106, 61,
				-75, 63, -26, 26, -117, 101, -100, 68, 7, 56, -94, -86, 13, 89,
				16, 100, 89, 90, 41, -17, -72, 51, 1, -8, -106, 90, 42, 126,
				-79, 75, -72, -3, -100, -119, -54, 20, -1, 0, 91, 113, 4, 88,
				-2, -12, -21, -3, 9, -90, 53, -59, -70, -32, 27, -108, 111, -9,
				73, 97, -4, -85, -102, -75, -1, 0, 88, -75, 114, 110, -94, -71,
				-98, 46, 111, 100, -117, -115, 52, -51, -77, 117, -89, 32, -26,
				-26, 87, 63, -35, -114, 6, 63, -52, -118, 71, -44, 52, -44,
				-23, 29, -21, -1, 0, -68, -117, 24, -4, -14, -43, -119, 7, -34,
				-4, -22, 123, -97, -7, 103, -12, 52, -3, -67, 70, -81, 114,
				-43, 40, -102, 7, 85, -74, 4, 121, 118, 76, -2, -46, 76, 127,
				-10, 80, -76, 29, 105, -44, -31, 52, -69, 85, -9, 98, -51, -1,
				0, -95, 49, 21, -105, 15, -6, -31, -12, -85, 99, -67, 103, -19,
				-86, 63, -76, 87, -77, -113, 98, -49, -10, -42, -94, 79, -18,
				-46, -46, 33, -40, 69, 18, -87, -2, 85, 12, -70, -74, -86, -31,
				-125, -35, -36, 47, -5, -78, 5, 63, -90, 42, -68, 95, -21,
				-113, -46, -95, -97, -18, 31, -83, 102, -22, 77, -18, -39, 14,
				41, 116, 21, -26, -67, -99, 72, -110, 105, -27, 94, -62, 75,
				-122, 111, -26, 106, 4, 87, -115, 48, -120, 51, -3, -43, 93,
				-65, -54, -99, 23, -6, -70, 120, -5, -1, 0, -123, 102, -107,
				-55, -37, 98, 35, 51, -128, 4, -128, -81, -72, -55, 52, -28,
				-98, 44, -19, -36, 1, 29, -103, 77, 54, 111, -11, -47, -3, 69,
				50, 111, -11, -76, 33, -35, -106, 30, -30, 50, -72, 12, -86,
				-34, -71, -30, -93, 50, 68, 84, -18, -104, 17, -35, -108, 19,
				85, 37, -5, -33, -123, 32, -1, 0, 80, -33, -17, 83, 123, -117,
				-103, -105, 67, 32, 80, 2, -93, 39, -87, 95, -23, 69, 49, 126,
				-28, 127, -17, 10, 42, -71, 80, -37, 104, -1, -39 };
	}
}

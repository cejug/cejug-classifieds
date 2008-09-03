package net.java.dev.cejug.classifieds.test.unit.utils;

import net.java.dev.cejug.classifieds.server.ejb3.entity.DomainEntity;
import net.java.dev.cejug.utils.AttributesCopier;
import net.java.dev.cejug_classifieds.metadata.common.Domain;

import org.junit.Assert;
import org.junit.Test;

public class AttributesCopierTest {
	@Test
	public void testShallow() {
		try {
			AttributesCopier<A, B> copier = new AttributesCopier<A, B>();
			A a = new A(1, "value");
			B b = new B();
			copier.copyValuesByAttributeNames(a, b);
			Assert.assertEquals(a.getX(), b.getX());
			Assert.assertEquals(a.getY(), b.getY());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testComplex() {
		Domain d = new Domain();
		d.setBrand("lll");
		d.setEntityId(1);
		d.setSharedQuota(true);
		d.setTimezone("lllllllll");
		d.setUri("h");

		DomainEntity entity = new DomainEntity();
		try {
			AttributesCopier<Domain, DomainEntity> copier = new AttributesCopier<Domain, DomainEntity>();
			copier.copyValuesByAttributeNames(d, entity);
			Assert.assertEquals(d.getBrand(), entity.getBrand());
			Assert.assertTrue(d.getEntityId() == entity.getId());
			Assert.assertEquals(d.isSharedQuota(), entity.getSharedQuota());
			Assert.assertEquals(d.getUri(), entity.getDomainName());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	public class A {
		int x;
		String y;

		public String getY() {
			return y;
		}

		public void setY(String y) {
			this.y = y;
		}

		public A(int x, String y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}
	}

	public class B {
		int x;
		String y;

		public String getY() {
			return y;
		}

		public void setY(String y) {
			this.y = y;
		}

		String differentName;

		public String getDifferentName() {
			return differentName;
		}

		public void setDifferentName(String differentName) {
			this.differentName = differentName;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}
	}
}
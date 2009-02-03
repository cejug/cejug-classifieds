package net.java.dev.cejug.classifieds.login.jms;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for registration.constants.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="registration.constants">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="name"/>
 *     &lt;enumeration value="email"/>
 *     &lt;enumeration value="login"/>
 *     &lt;enumeration value="password"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "registration.constants")
@XmlEnum
public enum RegistrationConstants {
    @XmlEnumValue("name")
    NAME("name"),
    @XmlEnumValue("email")
    EMAIL("email"),
    @XmlEnumValue("login")
    LOGIN("login"),
    @XmlEnumValue("password")
    PASSWORD("password");
    private final String value;

    RegistrationConstants(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RegistrationConstants fromValue(String v) {
        for (RegistrationConstants c: RegistrationConstants.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}

/***
 * @pName mi-ocr-web-common-utility
 * @name Address
 * @user HongWei
 * @date 2018/7/30
 * @desc
 */
package org.millions.idea.ocr.web.common.utility.json;

public class Address {
   private String city;
   private String province;

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", province='" + province + '\'' +
                '}';
    }

    public Address() {
    }

    public Address(String city, String province) {

        this.city = city;
        this.province = province;
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}

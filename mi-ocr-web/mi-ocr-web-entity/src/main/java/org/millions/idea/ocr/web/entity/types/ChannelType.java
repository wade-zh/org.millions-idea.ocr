/***
 * @pName org.millions-idea.ocr
 * @name ChannelType
 * @user HongWei
 * @date 2018/6/8
 * @desc
 */
package org.millions.idea.ocr.web.entity.types;

public enum ChannelType {
    /**
     * Normal Captcha
     * strategy: 0-9, A-Z
     * len: 4
     */
    T0003604("T0003604");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    ChannelType(String value) {

        this.value = value;
    }
}

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
     */
    NC36("A36");

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

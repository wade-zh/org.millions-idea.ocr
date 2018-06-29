/***
 * @pName mi-ocr-web-order
 * @name Operations
 * @user HongWei
 * @date 2018/6/29
 * @desc
 */
package org.millions.idea.ocr.web.order.entity.enums.transfer;

public enum Operations {
    Add(1),
    Reduce(2);

    private Integer type;

    Operations(Integer type) {
        this.type = type;
    }

    public Integer getType() {

        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

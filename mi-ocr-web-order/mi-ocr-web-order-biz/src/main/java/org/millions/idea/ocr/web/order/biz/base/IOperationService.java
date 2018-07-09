/***
 * @pName mi-ocr-web-finance
 * @name IOperationService
 * @user HongWei
 * @date 2018/6/30
 * @desc
 */
package org.millions.idea.ocr.web.order.biz.base;

public interface IOperationService<T,R> {
    R add(T model);
    boolean delete(T model);
    boolean update(T model);
}

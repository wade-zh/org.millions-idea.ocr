/***
 * @pName mi-ocr-web-finance
 * @name FinanceException
 * @user HongWei
 * @date 2018/6/29
 * @desc
 */
package org.millions.idea.ocr.web.order.entity.exception;


import org.millions.idea.ocr.web.order.entity.enums.transfer.Exceptions;

public class FinanceException extends RuntimeException {
    private Exceptions error;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public FinanceException(String message, Exceptions error) {
        super(message);
        this.error = error;
    }

    public Exceptions getError() {

        return error;
    }

    public void setError(Exceptions error) {
        this.error = error;
    }
}

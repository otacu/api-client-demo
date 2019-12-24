package com.sdt.api.client.demo.exception;

/**
 *  自定义异常
 */
public class SdtException extends Exception {

    /**
     * 异常状态码
     */
    private int exceptionStatus = 0;

    /**
     * @param message            错误信息
     * @param cause              异常
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SdtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param message
     * @param cause
     */
    public SdtException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public SdtException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public SdtException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message            错误信息
     * @param cause              异常
     * @param enableSuppression
     * @param writableStackTrace
     * @param exceptionStatus
     */
    public SdtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int exceptionStatus) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.exceptionStatus = exceptionStatus;
    }

    /**
     * @param message
     * @param cause
     * @param exceptionStatus
     */
    public SdtException(String message, Throwable cause, int exceptionStatus) {
        super(message, cause);
        this.exceptionStatus = exceptionStatus;
    }

    /**
     * @param message
     * @param exceptionStatus
     */
    public SdtException(String message, int exceptionStatus) {
        super(message);
        this.exceptionStatus = exceptionStatus;
    }

    /**
     * @param cause
     * @param exceptionStatus
     */
    public SdtException(Throwable cause, int exceptionStatus) {
        super(cause);
        this.exceptionStatus = exceptionStatus;
    }

    /**
     * @param exceptionStatus
     */
    public SdtException(int exceptionStatus) {
        super("");
        this.exceptionStatus = exceptionStatus;
    }

    /**
     * @return the exceptionStatus
     */
    public int getExceptionStatus() {
        return exceptionStatus;
    }

    /**
     * @param exceptionStatus the exceptionStatus to set
     */
    public void setExceptionStatus(int exceptionStatus) {
        this.exceptionStatus = exceptionStatus;
    }
}

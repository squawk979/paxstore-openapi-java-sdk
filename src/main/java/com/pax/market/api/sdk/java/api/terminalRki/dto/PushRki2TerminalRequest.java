package com.pax.market.api.sdk.java.api.terminalRki.dto;

import java.util.Date;

/**
 * The type Push rki 2 terminal request.
 *
 * @author liukai
 * {@code @date} 2020 /2/7
 */
public class PushRki2TerminalRequest extends PushRkiBasicRequest {

    private static final long serialVersionUID = 1L;

    private Date effectiveTime;
    private Date expiredTime;

    /**
     * Gets effective time.
     *
     * @return the effective time
     */
    public Date getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * Sets effective time.
     *
     * @param effectiveTime the effective time
     */
    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    /**
     * Gets expired time.
     *
     * @return the expired time
     */
    public Date getExpiredTime() {
        return expiredTime;
    }

    /**
     * Sets expired time.
     *
     * @param expiredTime the expired time
     */
    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }
}

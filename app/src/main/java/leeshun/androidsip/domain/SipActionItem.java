package leeshun.androidsip.domain;

import android.javax.sip.ClientTransaction;

/**
 * Created by leeshun on 2017/7/12.
 */

public class SipActionItem {
    private ClientTransaction transaction;
    private String method;
    private String content;
    private boolean result;

    public SipActionItem(ClientTransaction transaction, String method, String content) {
        this.transaction = transaction;
        this.method = method;
        this.content = content;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public ClientTransaction getTransaction() {
        return transaction;
    }

    public String getMethod() {
        return method;
    }

    public String getContent() {
        return content;
    }
}

package org.HelloPlayer;

public class JavaCeoTools {
    private boolean enabled = false;
    public boolean CeoTools = false;
    private boolean ErrorTools = false;
    private boolean ErrorCode;
    private boolean Code;

    public void Java() {
        CeoTools = true;
        enabled = true;
        System.out.println("Java Ceo Tools >> ON");
        if(enabled) {
            System.out.println("Java Ceo Tools >> Skip Error...");
        }
    }

    public void Error() {
        ErrorTools = true;
        ErrorCode = true;
        System.out.println("Java Ceo Tools >> Error Java Ceo Tools. Error Code: 0x012A2");
        return;
    }

    public boolean isEnabled() {
        return enabled;
    }
}

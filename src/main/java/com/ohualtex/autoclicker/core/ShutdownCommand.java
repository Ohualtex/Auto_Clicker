package com.ohualtex.autoclicker.core;

/**
 * Isletim sistemine gore guvenli kapatma komutunu secer. Saf ve test edilebilir;
 * gercek exec/UI ile karismaz (boylece test gercekten PC kapatmadan tum dallari dogrular).
 */
public final class ShutdownCommand {

    private ShutdownCommand() {}

    /** Secilen komut ve kullaniciya gosterilecek gecikme etiketi. command null ise desteklenmiyor. */
    public static final class Result {
        public final String[] command;
        public final String delayLabel;
        public Result(String[] command, String delayLabel) {
            this.command = command;
            this.delayLabel = delayLabel;
        }
        public boolean isSupported() { return command != null; }
    }

    public static Result forOs(String osName) {
        String os = (osName == null) ? "" : osName.toLowerCase();
        if (os.contains("win")) {
            return new Result(new String[]{"shutdown", "-s", "-t", "15"}, "15 sn");
        }
        if (os.contains("mac")) {
            return new Result(new String[]{"shutdown", "-h", "+1"}, "1 dk");
        }
        if (os.contains("nux") || os.contains("nix") || os.contains("aix")) {
            return new Result(new String[]{"shutdown", "-h", "+1"}, "1 dk");
        }
        return new Result(null, null);
    }
}

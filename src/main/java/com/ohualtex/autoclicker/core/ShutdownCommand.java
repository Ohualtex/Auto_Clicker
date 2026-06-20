package com.ohualtex.autoclicker.core;

/**
 * Isletim sistemine gore guvenli kapatma komutunu secer. Saf ve test edilebilir;
 * gercek exec/UI ile karismaz (boylece test gercekten PC kapatmadan tum dallari dogrular).
 */
public final class ShutdownCommand {

    private ShutdownCommand() {}

    /**
     * Secilen kapatma komutu, iptal komutu ve gecikme etiketi.
     * command null ise OS desteklenmiyor demektir.
     */
    public static final class Result {
        public final String[] command;
        public final String[] cancelCommand;
        public final String delayLabel;
        public Result(String[] command, String[] cancelCommand, String delayLabel) {
            this.command = command;
            this.cancelCommand = cancelCommand;
            this.delayLabel = delayLabel;
        }
        public boolean isSupported() { return command != null; }
    }

    public static Result forOs(String osName) {
        String os = (osName == null) ? "" : osName.toLowerCase();
        if (os.contains("win")) {
            return new Result(new String[]{"shutdown", "-s", "-t", "15"}, new String[]{"shutdown", "-a"}, "15 sn");
        }
        if (os.contains("mac")) {
            return new Result(new String[]{"shutdown", "-h", "+1"}, new String[]{"killall", "shutdown"}, "1 dk");
        }
        if (os.contains("nux") || os.contains("nix") || os.contains("aix")) {
            return new Result(new String[]{"shutdown", "-h", "+1"}, new String[]{"shutdown", "-c"}, "1 dk");
        }
        return new Result(null, null, null);
    }
}

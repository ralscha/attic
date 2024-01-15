package swift;

public final class SWIFT_MT798 extends SWIFT_MT7 {

    public SWIFT_MT798() {
        super();
        tags.put("20", "Transaction Reference Number");
        tags.put("12", "SubMessage Type");
        tags.put("77E", "Proprietary Message");
    }

    public String getMTName() {
        return("Proprietary Message");
    }

}

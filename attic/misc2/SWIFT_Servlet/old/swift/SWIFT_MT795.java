package swift;

public final class SWIFT_MT795 extends SWIFT_MT7 {

    public SWIFT_MT795() {
        super();
        tags.put("20", "Transaction Reference Number");
        tags.put("21", "Related Reference");
        tags.put("75", "Queries");
        tags.put("77A", "Narrative");
        tags.put("11R", "MT and Date of Original Message");
        tags.put("11S", "MT and Date of Original Message");
        tags.put("79", "Narrative description of the message to which the query relates");
    }

    public String getMTName() {
        return("Queries");
    }

}

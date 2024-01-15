package swift;

public final class SWIFT_MT796 extends SWIFT_MT7 {

    public SWIFT_MT796() {
        super();
        tags.put("20", "Transaction Reference Number");
        tags.put("21", "Related Reference");
        tags.put("76", "Answers");
        tags.put("77A", "Narrative");
        tags.put("11R", "MT and Date of Original Message");
        tags.put("11S", "MT and Date of Original Message");
        tags.put("79", "Narrative description of the message to which the answer relates");
    }

    public String getMTName() {
        return("Answers");
    }

}

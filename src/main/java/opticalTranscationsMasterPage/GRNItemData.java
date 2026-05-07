package opticalTranscationsMasterPage;

public class GRNItemData {

    private final String freeQty;
    private final String recQty;

    public GRNItemData(String freeQty, String recQty) {
        this.freeQty = freeQty;
        this.recQty = recQty;
    }

    public String getFreeQty() {
        return freeQty;
    }

    public String getRecQty() {
        return recQty;
    }
}
package serve.serveup.dataholder.order;

public enum OrderStatusType {


    // new order status
    NOVO(0),
    // ready order status
    PRIPRAVLJENO(1),
    // finished order status
    KONCANO(2);

    private int status;

    OrderStatusType(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

    public static String getName(int index) {
        for (OrderStatusType status : OrderStatusType.values()) {
            if (status.getStatus() == index)
                return status.toString();
        }
        return "DEFAULT";
    }


}

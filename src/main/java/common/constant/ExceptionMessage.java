package common.constant;

public final class ExceptionMessage {
    private ExceptionMessage() {}

    // Campaigns
    public static final String ACTIVE_CAMPAIGN_PRESENT_MESSAGE = "Active campaign present. New campaign cannot be created until the active one is stopped";
    public static final String NO_ACTIVE_CAMPAIGN_PRESENT_MESSAGE = "No active campaign present to be stopped";

    // Orders
    public static final String NO_SUCH_ORDER_FOUND_MESSAGE = "No such order found";

    // Users
    public static final String NO_SUCH_USER_FOUND = "No such user found";

    // Products
    public static final String NO_SUCH_PRODUCT_FOUND_MESSAGE = "No such product found";
    public static final String INVALID_MINIMUM_PRICE = "Minimum price of product must be greater than 0";
    public static final String INVALID_CURRENT_PRICE_MESSAGE = "Current price must be greater than or equal to minimum price";
    public static final String INSUFFICIENT_PRODUCT_QUANTITY_MESSAGE = "Insufficient product quantity";
}
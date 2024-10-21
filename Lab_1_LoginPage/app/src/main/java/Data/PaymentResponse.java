package Data;

import com.google.gson.annotations.SerializedName;

public class PaymentResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("links")
    private Link[] links;

    public String getApprovalLink() {
        for (Link link : links) {
            if ("approval_url".equals(link.getRel())) {
                return link.getHref();
            }
        }
        return null;
    }

    public static class Link {
        @SerializedName("href")
        private String href;

        @SerializedName("rel")
        private String rel;

        public String getHref() {
            return href;
        }

        public String getRel() {
            return rel;
        }
    }
}

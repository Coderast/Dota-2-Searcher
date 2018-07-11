package ua.fromrandomcountry.dota2searcher.api;

public class DotaRequestBuilder {
    private static final String BASE_URL = "https://api.opendota.com/api/";
    private static boolean useApiKey = false;
    private static String apiKey;
    //
    public static void setApiKey(String apiKey) {
        DotaRequestBuilder.useApiKey = true;
        DotaRequestBuilder.apiKey = apiKey;
    }
    public static void disableApi() {
        DotaRequestBuilder.useApiKey = false;
    }
    public static boolean enableApi() {
        if (DotaRequestBuilder.apiKey != null) {
            DotaRequestBuilder.useApiKey = true;
            return true;
        }
        return false;
    }

    public static String buildSearchByName(String personname) {
        StringBuilder res = new StringBuilder();
        res.append(BASE_URL);
        res.append("search?q=");
        res.append(personname);
        return res.toString();
    }
    public static String buildPlayerInfoById(long id) {
        StringBuilder res = new StringBuilder();
        res.append(BASE_URL);
        res.append("players/");
        res.append(id);
        return res.toString();
    }

}

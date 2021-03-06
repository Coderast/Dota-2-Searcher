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
        String resString = res.toString();
        resString = resString.replaceAll(" " ,"%20");
        return resString;
    }
    public static String buildPlayerInfoById(long id) {
        StringBuilder res = new StringBuilder();
        res.append(BASE_URL);
        res.append("players/");
        res.append(id);
        return res.toString();
    }
    public static String buildPlayerRecentMatches(long id) {
        StringBuilder res = new StringBuilder();
        res.append(BASE_URL);
        res.append("players/");
        res.append(id);
        res.append("/recentMatches");
        return res.toString();
    }
    public static String getHeroesList() {
        return "https://api.opendota.com/api/heroes";
    }
    public static String buildWinLosesById(long id) {
        StringBuilder res = new StringBuilder();
        res.append(BASE_URL);
        res.append("players/");
        res.append(id);
        res.append("/wl/");
        return res.toString();
    }

}

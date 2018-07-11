package ua.fromrandomcountry.dota2searcher.dota;

import org.json.JSONObject;

public class Player {
    private long accountId;
    private String soloRank;
    private String groupRank;
    private String profileUrl;
    private String username;
    private String localCountryCode;
    private String avatarUrl;

    public Player(JSONObject player) {
        this.set(player);
    }

    public void set(JSONObject player) {
        try {
            System.out.println(player.toString(1));
            soloRank = player.getString("solo_competitive_rank");
            if (soloRank.equals("null")) {
                soloRank = "Unknown";
            }
            groupRank = player.getString("competitive_rank");
            if (groupRank.equals("null")) {
                groupRank = "Unknown";
            }
            JSONObject profile = player.getJSONObject("profile");
            accountId = profile.getLong("account_id");
            username = profile.getString("personaname");
            profileUrl = profile.getString("profileurl");
            localCountryCode = profile.getString("loccountrycode");
            avatarUrl = profile.getString("avatarmedium");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getAccountId() {
        return accountId;
    }
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getSoloRank() {
        return soloRank;
    }
    public void setSoloRank(String soloRank) {
        this.soloRank = soloRank;
    }

    public String getGroupRank() {
        return groupRank;
    }
    public void setGroupRank(String groupRank) {
        this.groupRank = groupRank;
    }

    public String getProfileUrl() {
        return profileUrl;
    }
    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocalCountryCode() {
        return localCountryCode;
    }
    public void setLocalCountryCode(String localCountryCode) {
        this.localCountryCode = localCountryCode;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}

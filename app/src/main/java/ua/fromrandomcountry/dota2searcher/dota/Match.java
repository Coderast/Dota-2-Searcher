package ua.fromrandomcountry.dota2searcher.dota;

import org.json.JSONArray;
import org.json.JSONObject;

import ua.fromrandomcountry.dota2searcher.api.DotaRequestBuilder;
import ua.fromrandomcountry.dota2searcher.api.JsonParser;

public class Match {
    private long matchId;
    private boolean radiantWin;
    private String heroName;
    private short kills;
    private short deaths;
    private short assists;
    private short xpPerMin;
    private short goldPerMin;
    private int heroDamage;
    private int towerDamage;
    private int heroHealing;
    private int lastHits;

    public Match(long accountId) {
        this.setLastMatch(accountId);
    }

    public void setLastMatch(long accountId) {
        try {
            String url = DotaRequestBuilder.buildPlayerRecentMatches(accountId);
            System.out.println("!!!??!!!!!!!!!!!!!!!");
            System.out.println(url);
            System.out.println("!!!!!!!!!!!!!!!??!!!");
            JSONObject lastMatch = JsonParser.parseJSONArrayByURL(url).getJSONObject(0);

            matchId = lastMatch.getLong("match_id");
            radiantWin = lastMatch.getBoolean("radiant_win");

            short heroId = (short) lastMatch.getInt("hero_id");
            heroName = JsonParser.parseJSONArrayByURL(
                    DotaRequestBuilder.getHeroesList()
            ).getJSONObject(heroId).getString("localized_name");

            kills = (short) lastMatch.getInt("kills");
            deaths = (short) lastMatch.getInt("deaths");
            assists = (short) lastMatch.getInt("assists");

            xpPerMin = (short) lastMatch.getInt("xp_per_min");
            goldPerMin = (short) lastMatch.getInt("gold_per_min");

            heroDamage = lastMatch.getInt("hero_damage");
            towerDamage = lastMatch.getInt("tower_damage");
            heroHealing = lastMatch.getInt("hero_healing");
            lastHits = lastMatch.getInt("last_hits");

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getMatchId() {
        return matchId;
    }

    public boolean isRadiantWin() {
        return radiantWin;
    }

    public String getHeroName() {
        return heroName;
    }

    public int getLastHits() {
        return lastHits;
    }

    public int getHeroHealing() {
        return heroHealing;
    }

    public short getKills() {
        return kills;
    }

    public short getDeaths() {
        return deaths;
    }

    public short getAssists() {
        return assists;
    }

    public short getXpPerMin() {
        return xpPerMin;
    }

    public short getGoldPerMin() {
        return goldPerMin;
    }

    public int getHeroDamage() {
        return heroDamage;
    }

    public int getTowerDamage() {
        return towerDamage;
    }
}

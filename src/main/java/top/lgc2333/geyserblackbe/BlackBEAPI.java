package top.lgc2333.geyserblackbe;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public class BlackBEAPI {
    private final String baseUrl = "https://api.blackbe.xyz/openapi/v3/";

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    static public String getLevelDesc(short level) {
        return switch (level) {
            case 1 -> "有作弊行为，但未对其他玩家造成实质上损害";
            case 2 -> "有作弊行为，且对玩家造成一定的损害";
            case 3 -> "严重破坏服务器，对玩家和服务器造成较大的损害";
            default -> "未知";
        };
    }

    public API getAPI() {
        return retrofit.create(API.class);
    }

    interface API {
        @GET("check")
        Call<Response> check(@Query("name") String name, @Query("xuid") String xuid);
    }

    record Response(boolean success, short status, String message, BlackBEAPI.Response.Data data) {
        record Info(short level, String info, long qq) {
        }

        record Data(boolean exist, List<Info> info) {
        }
    }
}

package top.lgc2333.geyserblackbe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import javax.annotation.processing.Generated;
import java.util.List;

public class BlackBEAPI {
    private final String baseUrl = "https://api.blackbe.xyz/openapi/v3/";

    private final Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();

    static public String getLevelDesc(int level) {
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

    /**
     * https://www.jsonschema2pojo.org/ 自动生成
     */
    @Generated("jsonschema2pojo")
    public class Response {
        @SerializedName("success")
        @Expose
        private Boolean success;

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("version")
        @Expose
        private String version;
        @SerializedName("codename")
        @Expose
        private String codename;
        @SerializedName("time")
        @Expose
        private Integer time;
        @SerializedName("data")
        @Expose
        private Data data;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getCodename() {
            return codename;
        }

        public void setCodename(String codename) {
            this.codename = codename;
        }

        public Integer getTime() {
            return time;
        }

        public void setTime(Integer time) {
            this.time = time;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        @Generated("jsonschema2pojo")
        public class Info {

            @SerializedName("uuid")
            @Expose
            private String uuid;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("black_id")
            @Expose
            private String blackId;
            @SerializedName("xuid")
            @Expose
            private String xuid;
            @SerializedName("info")
            @Expose
            private String info;
            @SerializedName("level")
            @Expose
            private Integer level;
            @SerializedName("qq")
            @Expose
            private Integer qq;
            @SerializedName("photos")
            @Expose
            private List<String> photos = null;

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getBlackId() {
                return blackId;
            }

            public void setBlackId(String blackId) {
                this.blackId = blackId;
            }

            public String getXuid() {
                return xuid;
            }

            public void setXuid(String xuid) {
                this.xuid = xuid;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public Integer getLevel() {
                return level;
            }

            public void setLevel(Integer level) {
                this.level = level;
            }

            public Integer getQq() {
                return qq;
            }

            public void setQq(Integer qq) {
                this.qq = qq;
            }

            public List<String> getPhotos() {
                return photos;
            }

            public void setPhotos(List<String> photos) {
                this.photos = photos;
            }

        }

        @Generated("jsonschema2pojo")
        public class Data {

            @SerializedName("exist")
            @Expose
            private Boolean exist;
            @SerializedName("info")
            @Expose
            private List<Info> info = null;

            public Boolean getExist() {
                return exist;
            }

            public void setExist(Boolean exist) {
                this.exist = exist;
            }

            public List<Info> getInfo() {
                return info;
            }

            public void setInfo(List<Info> info) {
                this.info = info;
            }
        }
    }
}

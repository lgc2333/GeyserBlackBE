package top.lgc2333.geyserblackbe;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.floodgate.api.FloodgateApi;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class GeyserBlackBE extends JavaPlugin {
    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerJoin(), this);

        getLogger().info("PaperBlackBE Loaded.");
    }

    public class PlayerJoin implements Listener {
        private String componentToStr(Component c) {
            List<String> tmpLi = new ArrayList<>();
            for (Component i : c.children()) {
                tmpLi.add(((TextComponent) i).content());
            }
            return String.join("", tmpLi);
        }

        @EventHandler
        public void onPlayerJoin(@NotNull PlayerJoinEvent ev) {
            Logger logger = GeyserBlackBE.this.getLogger();
            FloodgateApi floodgate = FloodgateApi.getInstance();
            Player pl = ev.getPlayer();
            String plName = pl.getName();

            if (!floodgate.isFloodgatePlayer(pl.getUniqueId())) {
                logger.info("玩家 " + plName + " 并不是从Geyser加入服务器的基岩版玩家，跳过检测BlackBE违规记录");
                return;
            }

            String xuid = floodgate.getXuidFor(plName).toString();
            BlackBEAPI.API api = new BlackBEAPI().getAPI();

            logger.info("开始检测玩家 " + plName + "（XUID：" + xuid + "） 的BlackBE违规记录");
            api.check(plName, xuid).enqueue(new Callback<>() {
                @Override
                public void onResponse(
                        @NotNull Call<BlackBEAPI.Response> call,
                        @NotNull Response<BlackBEAPI.Response> response
                ) {
                    BlackBEAPI.Response ret = response.body();
                    if (ret == null) {
                        logger.warning("检测玩家 " + plName + " 的BlackBE违规记录失败：返回值为空");
                        return;
                    }

                    if (ret.success()) {
                        BlackBEAPI.Response.Data data = ret.data();
                        if (data.exist()) {
                            BlackBEAPI.Response.Info info = data.info().get(0);
                            pl.kick(
                                    Component.text(
                                            "检测到您在 BlackBE 存在违规记录，已将您断开连接！",
                                            TextColor.color(255, 85, 85)
                                    )
                            );

                            short lvl = info.level();
                            String lvlDesc = BlackBEAPI.getLevelDesc(lvl);
                            Component comp = Component.empty();
                            comp = comp.append(Component.text(
                                    "检测到玩家 " + plName + " 在BlackBE存在违规记录！\n",
                                    TextColor.color(255, 85, 85)
                            ));
                            comp = comp.append(Component.text(
                                    "违规等级：" + lvl + "（" + lvlDesc + "）\n" +
                                            "违规原因：" + info.info() + "\n" +
                                            "玩家QQ：" + info.qq(),
                                    TextColor.color(0, 170, 170)
                            ));
                            logger.warning(componentToStr(comp));
                            GeyserBlackBE.this.getServer().broadcast(comp);
                        } else {
                            logger.info("玩家 " + plName + " 没有BlackBE违规记录");
                        }
                    } else {
                        logger.warning("检测玩家 " + plName + " 的BlackBE违规记录失败：" +
                                "[" + ret.status() + "] " + ret.message());
                    }
                }

                @Override
                public void onFailure(
                        @NotNull Call<BlackBEAPI.Response> call,
                        @NotNull Throwable throwable
                ) {
                    logger.log(
                            Level.WARNING,
                            "检测玩家 " + plName + " 的BlackBE违规记录失败！",
                            throwable
                    );
                }
            });
        }
    }
}

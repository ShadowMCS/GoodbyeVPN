package red.zach.goodbyevpn.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import red.zach.goodbyevpn.Main;
import red.zach.goodbyevpn.utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

/**
 * Created by 18zzwerling on 1/30/17.
 */
public class JoinListener implements Listener {

    StringUtils su = new StringUtils();

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        Player p = e.getPlayer();

        String ip = p.getAddress().getAddress().getHostName();
        Logger logger = Main.getInstance().getServer().getLogger();
        logger.info(ip);

        String email = Main.getInstance().getConfig().getString("contact");

        if (isUsingVPN(ip, email)) {

            if (!p.isOp()) {
                p.kickPlayer(su.convertString(Main.getInstance().getConfig().getString("kick-message")));
            }
        }
    }

    public boolean isUsingVPN(String ip, String email){
        try {
            URL url = new URL("http://check.getipintel.net/check.php?ip=" + ip + "&contact=" + email);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String strTemp = "";
            while (null != (strTemp = br.readLine())) {
                if (strTemp.contains("0")){
                    return false;
                }else{
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }


}

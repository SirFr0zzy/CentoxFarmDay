package de.centox.farmday.utils;

import de.centox.farmday.FarmDay;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class ScoreboardManager {

    public static HashMap<String, Scoreboard> boards = new HashMap<>();

    public static String getPrefix(Player player) {
        User user = FarmDay.getLuckPerms().getUserManager().getUser(player.getUniqueId());
        String groupName = user.getPrimaryGroup();
        Group  LPgroupName = FarmDay.getLuckPerms().getGroupManager().getGroup(groupName);
        String prefix = LPgroupName.getCachedData().getMetaData().getPrefix();
        return prefix;
    }


    public static void setScoreboard(Player player) {


        if(!boards.containsKey(player.getName())) {
            Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective obj = board.registerNewObjective("score", "board");

            obj.setDisplayName("§c§lFarmDay");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);

            registerNewTeam(board, "§0", "§0", "§0", "§0");
            obj.getScore("§0").setScore(11);

            registerNewTeam(board, "§1", "§7► ","§b§lRang", "§1");
            obj.getScore("§1").setScore(10);

            registerNewTeam(board, "§2", "  ","Rang", "§2");
            obj.getScore("§2").setScore(9);

            registerNewTeam(board, "§3", "§3 ","§3", "§3");
            obj.getScore("§3").setScore(8);

            registerNewTeam(board, "§4", "§7► ", "§b§lMünzen", "§4");
            obj.getScore("§4").setScore(7);

            registerNewTeam(board, "§5", "  ", "§f0", "§5");
            obj.getScore("§5").setScore(6);

            registerNewTeam(board, "§6", "§6", "§6", "§6");
            obj.getScore("§6").setScore(5);

            registerNewTeam(board, "§7", "§7► ", "§b§lGebiet", "§7");
            obj.getScore("§7").setScore(4);

            registerNewTeam(board, "§8", " §7 ", "§fTestwelt", "§8");
            obj.getScore("§8").setScore(3);

            registerNewTeam(board, "§9", "§9", "§9", "§9");
            obj.getScore("§9").setScore(2);

            registerNewTeam(board, "§a", "§7► ", "§b§lOnline", "§a");
            obj.getScore("§a").setScore(1);

            registerNewTeam(board, "§b", "  §f" + Bukkit.getOnlinePlayers().size(), "§f/" + Bukkit.getMaxPlayers(), "§b");
            obj.getScore("§b").setScore(0);

            boards.put(player.getName(), board);
            player.setScoreboard(board);
        } else {
            Scoreboard board = boards.get(player.getName());
            board.getTeam("§b").setPrefix("  §f" + Bukkit.getOnlinePlayers().size());
            board.getTeam("§2").setSuffix(getPrefix(player));
//            if(CoinsAPI.getCoins(player.getUniqueId().toString()) >= Long.parseLong("1000000000000")) {
//                board.getTeam("§5").setSuffix("§f∞");
//            } else {
//                board.getTeam("§5").setSuffix("§f" + CoinsAPI.getCoins(player.getUniqueId().toString()).toString());
//            }

        }

    }

    public static void registerNewTeam(Scoreboard board, String name, String prefix, String suffix, String token) {
        Team team = board.registerNewTeam(name);
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        team.addEntry(token);
    }


}
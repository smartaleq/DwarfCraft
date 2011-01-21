package com.smartaleq.bukkit.dwarfcraft;

import java.io.*;
import java.util.*;
import org.bukkit.entity.Player;
import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

public class DwarfCraft extends JavaPlugin {

/**
 * A Block listener.
 */
private final DwarfCraftBlockListener blockListener = new DwarfCraftBlockListener(this);
private final DwarfCraftPlayerListener	playerListener	= new DwarfCraftPlayerListener(this);

/**
 * Something related to debugging
 */
private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();

/**
 * Constructor; not all that much should be done here
 */
public DwarfCraft(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
    super(pluginLoader, instance, desc, folder, plugin, cLoader);
}


/**
 * Called upon enabling the plugin
 */
public void onEnable() {
    // TODO: Place any custom enable code here including the registration of any events

    // Register our events
    PluginManager pm = getServer().getPluginManager();
	pm.registerEvent(Event.Type.BLOCK_DAMAGED, blockListener, Priority.High, this);
	pm.registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Priority.Normal, this);
	pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);

	
	DwarfCraftSkillEffects.readEffects();
	DwarfCraftSkills.readSkills();
	DwarfCraftPlayerSkills.readPlayers();
	
	
    PluginDescriptionFile pdfFile = this.getDescription();
    System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
}

/*
 * Called upon disabling the plugin.
 */
public void onDisable() {
	DwarfCraftPlayerSkills.saveSkills();

}
public boolean isDebugging(final Player player) {
    if (debugees.containsKey(player)) {
        return debugees.get(player);
    } else {
        return false;
    }
}

public void setDebugging(final Player player, final boolean value) {
    debugees.put(player, value);
}
}

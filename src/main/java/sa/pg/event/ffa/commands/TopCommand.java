package sa.pg.event.ffa.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import sa.pg.event.ffa.impl.plugin.ICommand;

public class TopCommand extends ICommand {
    @Override
    public String getName() {
        return "top";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }
}

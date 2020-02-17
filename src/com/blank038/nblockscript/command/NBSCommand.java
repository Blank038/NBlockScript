package com.blank038.nblockscript.command;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Location;
import cn.nukkit.utils.TextFormat;
import com.blank038.nblockscript.NBlockScript;
import com.blank038.nblockscript.enums.EnumType;
import com.blank038.nblockscript.util.MessageUtil;

import java.util.List;

/**
 * @author Blank038
 */
public class NBSCommand extends Command {
    private NBlockScript main;

    public NBSCommand(NBlockScript script) {
        super("nbs", "获取 NBlockScript 命令帮助");
        this.main = script;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender.hasPermission("nblockscript.admin")) {
            if (strings.length == 0) {
                MessageUtil.sendMessage(commandSender, MessageUtil.formatMessage(false, "&bNBlockScript &f命令帮助:",
                        "&c /nbs create <脚本名> &7> 创建一个脚本", "&c /nbs delete <脚本名> &7> 删除对应脚本", "&c /nbs add <脚本> <内容> &7> 对脚本新增脚本内容",
                        "&c /nbs remove <脚本名> <引索> &7> 删除对应行数的脚本", "&c /nbs bind <脚本> <类型> &7> 给准心对准的方块增加脚本",
                        "&c /nbs info <脚本> &7> 查看脚本信息", "&c /nbs run <脚本> [玩家名]&7 > 使玩家执行对应脚本",
                        "&c /nbs condition add <脚本名> <条件> &7> 给脚本新增条件", "&c /nbs condition remove <脚本名> <引索> &7> 删除对应脚本引索条件"));
            } else {
                switch (strings[0].toLowerCase()) {
                    case "create":
                        create(commandSender, strings);
                        break;
                    case "bind":
                        bind(commandSender, strings);
                        break;
                    case "delete":
                        delete(commandSender, strings);
                        break;
                    case "add":
                        add(commandSender, strings);
                        break;
                    case "remove":
                        remove(commandSender, strings);
                        break;
                    case "run":
                        run(commandSender, strings);
                        break;
                    case "info":
                        info(commandSender, strings);
                        break;
                    case "condition":
                        condition(commandSender, strings);
                        break;
                    case "reload":
                        main.loadConfig();
                        commandSender.sendMessage(MessageUtil.getConfigString("message.reload", true));
                        break;
                    default:
                        break;
                }
            }
        }
        return true;
    }

    public void create(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage(MessageUtil.getConfigString("message.need-script", true));
            return;
        }
        String scriptName = args[1];
        if (main.getScriptManager().addScript(scriptName)) {
            sender.sendMessage(MessageUtil.getConfigString("message.create", true).replace("%script%", scriptName));
        } else {
            sender.sendMessage(MessageUtil.getConfigString("message.script-exist", true));
        }
    }

    public void delete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage(MessageUtil.getConfigString("message.need-script", true));
            return;
        }
        String scriptName = args[1];
        if (NBlockScript.getScriptManager().hasScript(scriptName)) {
            NBlockScript.getScriptManager().removeScript(scriptName);
            sender.sendMessage(MessageUtil.getConfigString("message.delete", true));
        } else {
            sender.sendMessage(MessageUtil.getConfigString("message.wrong-script", true));
        }
    }


    public void add(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage(MessageUtil.getConfigString("message.need-script", true));
            return;
        }
        if (args.length == 2) {
            sender.sendMessage(MessageUtil.getConfigString("message.need-model", true));
            return;
        }
        String scriptName = args[1];
        String model = "";
        for (int i = 2; i < args.length; i++) {
            model += args[i] + ((i + 1) == args.length ? "" : " ");
        }
        if (NBlockScript.getScriptManager().hasScript(scriptName)) {
            NBlockScript.getScriptManager().addModel(scriptName, model);
            sender.sendMessage(MessageUtil.getConfigString("message.add", true).replace("%model%", model));
        } else {
            sender.sendMessage(MessageUtil.getConfigString("message.wrong-script", true));
        }
    }

    public void remove(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage(MessageUtil.getConfigString("message.need-script", true));
            return;
        }
        if (args.length == 2) {
            MessageUtil.sendMessage(sender, "&c请输入脚本行引索!", true);
            return;
        }
        String scriptName = args[1];
        int index;
        try {
            index = Integer.parseInt(args[2]);
        } catch (Exception e) {
            MessageUtil.sendMessage(sender, "&c请输入一个正确的数字!", true);
            return;
        }
        if (NBlockScript.getScriptManager().hasScript(scriptName)) {
            NBlockScript.getScriptManager().removeModel(scriptName, index);
            sender.sendMessage(MessageUtil.getConfigString("message.remove", true));
        } else {
            sender.sendMessage(MessageUtil.getConfigString("message.wrong-script", true));
        }
    }

    public void bind(CommandSender sender, String[] args) {
        if (isPlayer(sender)) {
            if (args.length == 1) {
                sender.sendMessage(MessageUtil.getConfigString("message.need-script", true));
                return;
            }
            if (args.length == 2) {
                sender.sendMessage(MessageUtil.getConfigString("message.need-type", true));
                return;
            }
            EnumType enumType;
            try {
                enumType = EnumType.valueOf(args[2]);
            } catch (Exception e) {
                sender.sendMessage(MessageUtil.getConfigString("message.wrong-type", true));
                return;
            }
            String scriptName = args[1];
            if (main.getScriptManager().hasScript(scriptName)) {
                Player player = (Player) sender;
                Block block = player.getTargetBlock(5, new Integer[]{0});
                Location location = block.getLocation();
                NBlockScript.getScriptManager().addBind(scriptName, location, enumType);
                sender.sendMessage(MessageUtil.getConfigString("message.bind", true));
            } else {
                sender.sendMessage(MessageUtil.getConfigString("message.wrong-script", true));
            }
        }
    }

    public void condition(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return;
        }
        if (args.length == 2) {
            MessageUtil.sendMessage(sender, "&c请输入 &fadd &c或 &fremove &c来操作", true);
            return;
        }
        if (args.length == 3) {
            MessageUtil.sendMessage(sender, "&c请输入条件或条件引索!", true);
            return;
        }
        String scriptName = args[2];
        if (main.getScriptManager().hasScript(scriptName)) {
            if (args[1].equalsIgnoreCase("add")) {
                addCondition(sender, scriptName, args[3]);
            } else if (args[1].equalsIgnoreCase("remove")) {
                removeCondition(sender, scriptName, args[3]);
            }
        } else {
            sender.sendMessage(MessageUtil.getConfigString("message.wrong-script", true));
        }
    }

    public void run(CommandSender sender, String[] args) {
        if (isPlayer(sender)) {
            if (args.length == 1) {
                sender.sendMessage(MessageUtil.getConfigString("message.need-script", true));
                return;
            }
            String scriptName = args[1];
            Player player = args.length > 2 ? main.getServer().getPlayer(args[2]) : (Player) sender;
            if (player == null || !player.isOnline()) {
                MessageUtil.sendMessage(sender, "&c目标玩家不在线!", true);
                return;
            }
            if (NBlockScript.getScriptManager().hasScript(scriptName)) {
                NBlockScript.getScriptManager().ignoreTrigger(player, scriptName);
            } else {
                sender.sendMessage(MessageUtil.getConfigString("message.wrong-script", true));
            }
        }
    }

    public void info(CommandSender sender, String[] args) {
        if (isPlayer(sender)) {
            if (args.length == 1) {
                sender.sendMessage(MessageUtil.getConfigString("message.need-script", true));
                return;
            }
            String scriptName = args[1];
            if (NBlockScript.getScriptManager().hasScript(scriptName)) {
                List<String> scripts = NBlockScript.getScriptManager().getScriptInfo(scriptName);
                List<String> conditions = NBlockScript.getScriptManager().getScriptCondition(scriptName);
                sender.sendMessage(TextFormat.colorize('&', "&c脚本 &f" + scriptName + " &c信息列表:"));
                sender.sendMessage(TextFormat.colorize('&', "&e 脚本行:"));
                if (scripts.size() == 0) {
                    MessageUtil.sendMessage(sender, "&f - 无", false);
                } else {
                    for (int i = 0; i < scripts.size(); i++) {
                        sender.sendMessage(TextFormat.colorize('&', "&f - " + i + ".&7" + scripts.get(i)));
                    }
                }
                sender.sendMessage(TextFormat.colorize('&', "&e 条件列表:"));
                if (conditions.size() == 0) {
                    MessageUtil.sendMessage(sender, "&f - 无", false);
                } else {
                    for (int i = 0; i < conditions.size(); i++) {
                        sender.sendMessage(TextFormat.colorize('&', "&f - " + i + ".&7" + conditions.get(i)));
                    }
                }
            } else {
                sender.sendMessage(MessageUtil.getConfigString("message.wrong-script", true));
            }
        }
    }

    public boolean isPlayer(CommandSender sender) {
        if (sender instanceof Player) {
            return true;
        }
        MessageUtil.sendMessage(sender, "&c后台无法执行该命令!", true);
        return false;
    }

    private void addCondition(CommandSender sender, String script, String condition) {
        if (NBlockScript.getScriptManager().addCondition(script, condition)) {
            sender.sendMessage(MessageUtil.getConfigString("message.condition.add-success", true));
        } else {
            sender.sendMessage(MessageUtil.getConfigString("message.condition.add-error", true));
        }
    }

    private void removeCondition(CommandSender sender, String script, String args2) {
        int index;
        try {
            index = Integer.parseInt(args2);
        } catch (Exception e) {
            MessageUtil.sendMessage(sender, "&c请输入一个正确的数字!", true);
            return;
        }
        NBlockScript.getScriptManager().removeCondition(script, index);
        sender.sendMessage(MessageUtil.getConfigString("message.condition.remove-success", true));
    }
}
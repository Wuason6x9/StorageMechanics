package wuason.storagemechanics.Commands;

import wuason.storagemechanics.Storage;

public class CommandManager {

    private Storage core;

    public CommandManager(Storage plugin){
        this.core = plugin;
        register();

    }
    public void register(){
        core.getCommand("storagemechanics").setTabCompleter(new Tab(core));
        core.getCommand("storagemechanics").setExecutor(new StorageCommand(core));
        core.getCommand("test").setExecutor(new testCommand(core));
    }
}

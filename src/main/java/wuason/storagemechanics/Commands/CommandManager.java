package wuason.storagemechanics.Commands;

import wuason.storagemechanics.Storage;

public class CommandManager {

    private Storage core;

    public CommandManager(Storage plugin){
        this.core = plugin;
        register();

    }
    public void register(){
        core.getCommand("editor").setExecutor(new EditorCommand(core));
        core.getCommand("editor").setTabCompleter(new Tab(core));
    }
}

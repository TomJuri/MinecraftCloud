package de.tomjuri.hycloud.controller.command.system;

public class CloudCommandManager extends CommandManager<CommandSender> {

        public CloudCommandManager() {
            super(CommandExecutionCoordinator.simpleCoordinator(), new CloudCommandRegistrationHandler());
        }

        @Override
        public boolean hasPermission(@NonNull CommandSender sender, @NonNull String permission) {
            return true;
        }

        @Override
        public @NonNull CommandMeta createDefaultCommandMeta() {
            return SimpleCommandMeta.empty();
        }
    }
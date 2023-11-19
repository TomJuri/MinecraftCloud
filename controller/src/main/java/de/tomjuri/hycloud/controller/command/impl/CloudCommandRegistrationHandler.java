package de.tomjuri.hycloud.controller.command.system;

private static class CloudCommandRegistrationHandler implements CommandRegistrationHandler {

        private final Map<CommandArgument<?, ?>, Command<CommandSender>> commands = new HashMap<>();

        @Override
        @SuppressWarnings("unchecked")
        public boolean registerCommand(@NonNull Command<?> command) {
            CommandArgument<?, ?> commandArgument = command.getArguments().get(0);
            if (this.commands.containsKey(commandArgument)) return false;
            this.commands.put(commandArgument, (Command<CommandSender>) command);
            return true;
        }
    }
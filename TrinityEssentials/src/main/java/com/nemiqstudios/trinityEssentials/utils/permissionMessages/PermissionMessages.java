package com.nemiqstudios.trinityEssentials.utils.permissionMessages;

public enum PermissionMessages {
    NO_PERMISSION_DEFAULT("§cVocê não tem permissão para executar este comando."),
    NO_PERMISSION_FOR_MEMBER("§cEste comando é §eexclusivo §cpara jogadores §eVIPs.");

    private final String text;

    PermissionMessages(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}

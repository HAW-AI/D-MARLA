package NetworkAdapter.Messages;

import EnvironmentPluginAPI.CustomNetworkMessages.NetworkMessage;

/**
 * This message is sent by the server to inform clients about the game start.
 */
public class CycleStartsMessage extends NetworkMessage {

    private Object environmentInitInfo;

    public CycleStartsMessage(int clientId, Object environmentInitInfo) {
        super(clientId);
        this.environmentInitInfo = environmentInitInfo;
    }

    /**
     * @return The color, that the player was assigned to.
     */
    public Object getEnvironmentInitInfo() {
        return environmentInitInfo;
    }
}
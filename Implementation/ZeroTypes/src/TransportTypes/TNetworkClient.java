package TransportTypes;

import org.joda.time.DateTime;

import java.net.InetAddress;

/**
 * Transport type for the information about a network client.
 */
public class TNetworkClient implements java.io.Serializable {
    private int id;
    private String name;
    private InetAddress address;
    private DateTime connectedSince;

    /**
     * @param id             the client's id
     * @param name           the provided name
     * @param address        the client's internet address
     * @param connectedSince the point in time when the connection was accepted
     */
    public TNetworkClient(int id, String name, InetAddress address, DateTime connectedSince) {
        this.id = id;
        if (name != null) {
            this.name = name;
        } else {
            this.name = "anonymous";
        }
        this.address = address;
        this.connectedSince = connectedSince;
    }

    /**
     * a unique id representing the
     *
     * @return > 0
     */
    public int getId() {
        return id;
    }

    /**
     * the name the connecting client has provided
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * the client's internet address.
     *
     * @return
     */
    public InetAddress getAddress() {
        return address;
    }

    /**
     * Start of the connection.
     *
     * @return != null
     */
    public DateTime getConnectedSince() {
        return connectedSince;
    }
}

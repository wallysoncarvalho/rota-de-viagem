package info.wallyson.core.ports;

import info.wallyson.core.domain.Connection;

public interface GraphConnection {
    boolean add(String graphIdentifier, Connection connection);
}

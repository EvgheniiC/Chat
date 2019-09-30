package com.network;

public interface TCPConnectionLister {
    void  onConnectionsReade(TCPConnection tcpConnection);
    void  onReciveString(TCPConnection tcpConnection,String value);
    void  onDissconnect(TCPConnection tcpConnection);
    void  onException(TCPConnection tcpConnection,Exception e);
}

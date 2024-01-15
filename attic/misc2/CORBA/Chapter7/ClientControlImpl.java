
class ClientControlImpl extends Client._ClientControlImplBase {
    private CountMultiClient thisClient;    
    
    ClientControlImpl(Object client) {
        super();
        thisClient = (CountMultiClient)client);
        System.out.println("Client Control Object Created as client " + thisClient.clientNumber);
    }
    
    public boolean start() {
        return thisClient.startCounting();
    }
    
    public String stop() {
        return thisClient.stopCounting();
    }
    
}
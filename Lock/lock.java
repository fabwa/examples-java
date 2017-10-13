import org.neo.smartcontract.framework.SmartContract;
import org.neo.smartcontract.framework.services.neo.Blockchain;
import org.neo.smartcontract.framework.services.neo.Header;


public class lock extends SmartContract{
	
    public static Boolean Main(int timestamp, byte[] pubkey, byte[] signature)
    {
        Header header = Blockchain.getHeader(Blockchain.height());
        if (timestamp > header.timestamp()) return false;
        return verifySignature(pubkey, signature);
    }
}

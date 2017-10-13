import org.neo.smartcontract.framework.SmartContract;
import org.neo.smartcontract.framework.services.neo.Storage;

public class Domain extends SmartContract {

    public static Object Main(String operation, Object... args){

        if (operation == "query") return Query((String) args[0]);
        if (operation == "register") return Register((String)args[0], (byte[])args[1]);
        if (operation == "transfer") return Transfer((String) args[0], (byte[])args[1]);
        if (operation == "delete") return Delete((String) args[0]);
        return false;

    }

    private static byte[] Query(String domain){
        return Storage.get(Storage.currentContext(),domain);
    }

    private static boolean Register(String domain, byte[] owner) {
        byte[] value = Storage.get(Storage.currentContext(), domain);
        if (value!= null) return false;
        Storage.put(Storage.currentContext(),domain,owner);
        return true;
    }

    private static boolean Transfer(String domain, byte[] to)
    {
        byte[] from = Storage.get(Storage.currentContext(), domain);
        if (from == null) return false;
        Storage.put(Storage.currentContext(), domain, to);
        return true;
    }

    private static boolean Delete(String domain)
    {
        byte[] owner = Storage.get(Storage.currentContext(), domain);
        if (owner == null) return false;
        Storage.delete(Storage.currentContext(), domain);
        return true;
    }
}

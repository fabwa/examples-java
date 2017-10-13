import org.neo.smartcontract.framework.SmartContract;
import org.neo.smartcontract.framework.services.neo.Storage;

public class HelloWorld extends SmartContract{

  public static void Main(String[] args){
    Storage.put(Storage.currentContext(), "Wake up ...!", "NEO!");
  }

}

package examples_neo;

import org.neo.smartcontract.framework.SmartContract;
import org.neo.smartcontract.framework.services.neo.Storage;

public class HelloWorld extends SmartContract{

  public static void main(String[] args){
    Storage.put(Storage.currentContext(), "Greeting to the World", "Hello World!");
  }

}
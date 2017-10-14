import java.math.BigInteger;

import org.neo.smartcontract.framework.Helper;
import org.neo.smartcontract.framework.SmartContract;
import org.neo.smartcontract.framework.services.neo.Runtime;
import org.neo.smartcontract.framework.services.neo.Storage;

public class ERC20 extends SmartContract {

	public static Object Main(byte[] originator, String Event, byte[] args0, byte[] args1, byte[] args2)
    {
		BigInteger supply = BigInteger.valueOf(100000);
        
        if (!Runtime.checkWitness(originator)) return false;

        if (Event == "deploy") return Deploy(originator, supply);
        if (Event == "totalSupply") return supply;
        if (Event == "balanceOf") return Storage.get(Storage.currentContext(), args0);
        if (Event == "transfer") return Transfer(originator, args0, args1);
        if (Event == "transferFrom") return TransferFrom(originator, args0, args1, args2);
        if (Event == "approve") return Approve(originator, args0, args1);
        if (Event == "allowance") return Allowance(args0, args1);
        return false;
    }

     private static Boolean Deploy(byte[] originator, BigInteger supply)
     {
         byte[] adminKey = new byte[] {};
         if (originator != adminKey) return false;
         Storage.put(Storage.currentContext(), originator, supply.toByteArray());
         return true;
     }

     private static Boolean Transfer(byte[] originator, byte[] to, byte[] amount)
     {           
    	 	 BigInteger originatorValue = new BigInteger(Storage.get(Storage.currentContext(), originator));
    	 	 BigInteger targetValue = new BigInteger(Storage.get(Storage.currentContext(), to));
         
         BigInteger nOriginatorValue = originatorValue.subtract(new BigInteger(amount)) ;
         BigInteger nTargetValue = targetValue.add(new BigInteger(amount));
         
         if (originatorValue.compareTo(BigInteger.ZERO) != -1 && new BigInteger(amount).compareTo(BigInteger.ZERO) != -1)
         {
             Storage.put(Storage.currentContext(), originator, nOriginatorValue.toByteArray());
             Storage.put(Storage.currentContext(), to, nTargetValue.toByteArray());
             return true;
         }
         return false;
     }


     private static Boolean TransferFrom(byte[] originator, byte[] from, byte[] to, byte[] amount)
     {
	    	 BigInteger allVallong 	= 	new BigInteger(Storage.get(Storage.currentContext(), Helper.concat(from,to)));
	    	 BigInteger fromVallong 	= 	new BigInteger(Storage.get(Storage.currentContext(), from));
	    	 BigInteger toVallong 	= 	new BigInteger(Storage.get(Storage.currentContext(), to));

	    	 if (fromVallong.compareTo(BigInteger.ZERO) != -1 && new BigInteger(amount).compareTo(BigInteger.ZERO) != -1
	    			 && allVallong.compareTo(BigInteger.ZERO) != -1)
         {
             Storage.put(Storage.currentContext(), Helper.concat(from,originator), (allVallong.subtract(new BigInteger(amount))).toByteArray());
             Storage.put(Storage.currentContext(), to, (toVallong.add(new BigInteger(amount))).toByteArray());
             Storage.put(Storage.currentContext(), from, (fromVallong.subtract(new BigInteger(amount))).toByteArray());
             return true;
         }
         return false;
     }

     private static Boolean Approve(byte[] originator, byte[] to, byte[] amount)
     {
         Storage.put(Storage.currentContext(), Helper.concat(originator,to), amount);
         return true;
     }
     
     private static BigInteger Allowance(byte[] from, byte[] to)
     {
         return new BigInteger(Storage.get(Storage.currentContext(), Helper.concat(from,to)));
     }

     
}

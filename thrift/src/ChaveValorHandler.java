import org.apache.thrift.TException;
import java.util.HashMap;

public class ChaveValorHandler implements chavevalor.ChaveValor.Iface {
    private HashMap<Integer,String> kv = new HashMap<>();
    @Override
    public String getKV(int key) throws TException {
        if(kv.containsKey(key))
            return kv.get(key);
        else
            throw new chavevalor.KeyNotFound();
    }
    @Override
    public String setKV(int key, String valor) throws TException {
        String lastValue = kv.get(key);
        kv.put(key,valor);
        return lastValue;
    }
    @Override
    public void delKV(int key) throws TException {
        kv.remove(key);
    }
}
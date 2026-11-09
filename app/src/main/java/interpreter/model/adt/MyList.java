package interpreter.model.adt;
import interpreter.exceptions.ADTException;
import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T> {
    private final List<T> list=new ArrayList<>();
    @Override
    public void add(T v){
        list.add(v);
    }
    @Override
    public T get(int index) throws ADTException{
        if (index<0 || index >=size()) throw new ADTException("index is out of range");
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public List<T> toList(){
        return new ArrayList<>(list);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}

package base.Utilities;

public class Pair<T, U> {
    public final T first;
    public final U second;

    public Pair(T t, U u) {
        first = t;
        second = u;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

    public String toString() {
        return new String("Pair(" + first + ", " + second + ")");
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Pair))
            return false;
        return this.first.equals(((Pair<?, ?>) o).first)
                && this.second.equals(((Pair<?, ?>) o).second);
    }
}

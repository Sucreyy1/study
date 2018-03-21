package delayOrder;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class BaseDelayed<T> implements Delayed {

    private int id;

    private long startTime;

    private T value;

    public BaseDelayed() {
    }


    public BaseDelayed(int id,int timeout, T value) {
        this.id=id;
        this.startTime = System.currentTimeMillis() + timeout * 1000L;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    //当缓存时间小于等于0的时候从延迟队列里取出来。
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.getStartTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (o == this)
            return 0;
        if (o instanceof BaseDelayed) {
            BaseDelayed o1 = (BaseDelayed) o;
            return (int) (this.getStartTime() - o1.getStartTime());
        }
        return -1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaseDelayed obj1 = (BaseDelayed) obj;
        if (this.startTime != obj1.getStartTime())
            return false;
        if (value == null) {
            return obj1.getValue() == null;
        } else if (!value.equals(obj1.getValue()))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = result * prime + (int) (startTime ^ startTime >>> 32);
        result = result * prime + (value == null ? 0 : value.hashCode());
        return result;
    }
}

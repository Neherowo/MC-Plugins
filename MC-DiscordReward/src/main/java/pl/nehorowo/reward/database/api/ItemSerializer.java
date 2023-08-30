package pl.nehorowo.reward.database.api;

public interface ItemSerializer<T> {

    Class<T> supportedClass();

    T serialize(String s);

    String deserialize(T t);

}

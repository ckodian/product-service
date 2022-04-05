package au.twc.core.product.populators;

public interface Populator<T, S> {

    T populate(T dto, S model);

    S reversePopulate(S model, T dto);
}

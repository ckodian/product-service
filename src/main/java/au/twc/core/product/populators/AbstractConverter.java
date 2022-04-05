package au.twc.core.product.populators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public abstract class AbstractConverter<T, S> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    public abstract T getDtoItem(S dto);

    public abstract S getModelItem(T model);

    protected Set<Populator<T, S>> populators;

    protected Set<Populator<T, S>> reversePopulator;

    public void addPopulator(Populator<T, S> populator) {
        if (CollectionUtils.isEmpty(this.populators)) {
            this.populators = new HashSet<>();
        }
        this.populators.add(populator);
    }

    public void addReversePopulator(Populator<T, S> populator) {
        if (CollectionUtils.isEmpty(this.reversePopulator)) {
            this.reversePopulator = new HashSet<>();
        }
        this.reversePopulator.add(populator);
    }

    public T convert(S model) {
        T target = getDtoItem(model);
        populators.stream().forEach(each -> each.populate(target, model));
        return target;
    }

    public S reverseConvert(T dto, S model) {
        final AtomicReference<S> modRef = new AtomicReference();
        if (model == null) {
            model = getModelItem(dto);
        }
        modRef.set(model);
        log.debug(model.toString());
        reversePopulator.stream().forEach(each -> each.reversePopulate(modRef.get(), dto));
        return model;
    }

}

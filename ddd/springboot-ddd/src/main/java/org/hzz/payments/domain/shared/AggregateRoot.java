package org.hzz.payments.domain.shared;

import org.springframework.context.ApplicationContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 聚合根
 */
public abstract class AggregateRoot<E,ID> implements Entity<E,ID>{
    public final ID entityId;
    public final ApplicationContext applicationContext;
    public final AggregateRootBehavior behavior;

    protected AggregateRoot(ID id,ApplicationContext applicationContext){
        this.entityId = id;
        this.applicationContext = applicationContext;
        this.behavior = initialBehavior();
    }
    public class AggregateRootBehavior{
        protected final Map<Class<? extends Command>,CommandHandler<? extends Command,? extends Event,ID>> handlers;
        public AggregateRootBehavior(Map<Class<? extends Command>, CommandHandler<? extends Command, ? extends Event, ID>> handlers) {
            this.handlers = Collections.unmodifiableMap(handlers);
        }
    }

    protected abstract AggregateRootBehavior initialBehavior();

    public class AggregateRootBehaviorBuilder{
        private final Map<Class<? extends Command>, CommandHandler<? extends Command, ? extends Event, ID>> handlers = new HashMap<>();

        public <A extends Command,B extends Event> AggregateRootBehaviorBuilder setCommandHandler(
                Class<A> commandType, CommandHandler<A,B,ID> handler){
            handlers.put(commandType,handler);
            return this;
        }

        public AggregateRootBehavior build() {
            return new AggregateRootBehavior(handlers);
        }
    }
}

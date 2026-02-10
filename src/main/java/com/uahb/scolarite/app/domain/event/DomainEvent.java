package com.uahb.scolarite.app.domain.event;
import java.time.LocalDateTime;

public interface DomainEvent {



        String aggregateId();

        String aggregateType();

        LocalDateTime occurredAt();

}

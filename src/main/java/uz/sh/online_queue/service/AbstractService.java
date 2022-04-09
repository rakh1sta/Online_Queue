package uz.sh.online_queue.service;

import uz.sh.online_queue.mapper.Mapper;
import uz.sh.online_queue.repository.AbstractRepository;
import uz.sh.online_queue.utils.validator.Validator;

public abstract class AbstractService <R extends AbstractRepository, M extends Mapper,V extends Validator>{
    protected final R repository;
    protected final M mapper;
    protected final V validator;

    public AbstractService(R repository, M mapper, V validator) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
    }
}

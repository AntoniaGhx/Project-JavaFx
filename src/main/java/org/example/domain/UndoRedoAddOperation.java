package org.example.domain;
import org.example.repository.IRepository;

public class UndoRedoAddOperation<T extends Entity> implements IUndoRedoOperation {
    private IRepository<T> repository;
    private T entity;

    public UndoRedoAddOperation(IRepository<T> repository, T entity) {
        this.repository = repository;
        this.entity = entity;
    }

    @Override
    public void doUndo() {
        this.repository.delete(entity.getIdEntity());
    }

    @Override
    public void doRedo() {
        this.repository.create(entity);
    }
}

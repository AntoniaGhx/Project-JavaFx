package org.example.domain;
import org.example.repository.IRepository;

public class UndoRedoDeleteOperation<T extends Entity> implements IUndoRedoOperation {
    private IRepository<T> repository;
    private T entity;

    public UndoRedoDeleteOperation(IRepository<T> repository, T entity) {
        this.repository = repository;
        this.entity = entity;
    }

    @Override
    public void doUndo() {
        this.repository.create(entity);
    }

    @Override
    public void doRedo() {
        this.repository.delete(entity.getIdEntity());
    }
}

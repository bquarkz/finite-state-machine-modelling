package com.bquarkz.hellskitchen.fsm;

import java.io.Serializable;
import java.util.Set;

public interface IState< ID extends Serializable, T >
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static fields
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    static < ID extends Serializable, T > IState< ID, T > wrap(
            final FiniteStateMachine< ID, T > machine,
            final ID id,
            final IBeanStateFactory< ID, T > factory,
            final Set< ID > possibleNext )
    {
        return new IState< ID, T >()
        {
            private T content;

            {
                this.content = factory.createEmpty( id );
            }

            @Override
            public ID getId()
            {
                return id;
            }

            @Override
            public T getContent()
            {
                return content;
            }

            @Override
            public IFSMNavigation< ID, T > getMachine()
            {
                return machine;
            }

            @Override
            public void loadContent( T content )
            {
                this.content = content;
            }

            @Override
            public Set< ID > getPossibleNext()
            {
                return possibleNext;
            }
        };
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Methods
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Default Methods
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Contracts
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ID getId();
    T getContent();
    IFSMNavigation< ID, T > getMachine();
    void loadContent( T content );
    Set< ID > getPossibleNext();
}

package com.bquarkz.hellskitchen.fsm;

import java.io.Serializable;
import java.util.*;

public class FiniteStateMachine< ID extends Serializable, T > implements IFSMNavigation< ID, T >
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constants
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Special Fields And Injections
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Fields
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private final Map< ID, StateMapping< ID > > stateMappings;
    private final IBeanStateFactory< ID, T > factory;
    private final FSMProperties properties;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public FiniteStateMachine( IBeanStateFactory< ID, T > factory )
    {
        this.factory = factory;
        this.stateMappings = new HashMap<>();
        this.properties = new FSMProperties();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Factories
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters And Setters
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Methods
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void load( StateMapping< ID > stateMapping )
    {
        stateMappings.put( stateMapping.getId(), stateMapping );
    }

    protected Set< ID > get( ID id )
    {
        return Optional
                .ofNullable( stateMappings.get( id ) )
                .map( StateMapping::getPossibleNext )
                .orElse( new HashSet<>() );
    }

    protected IState< ID, T > newState( ID id )
    {
        final Set< ID > possibleNext = get( id );
        return IState.wrap( this, id, factory, possibleNext );
    }

    public IFSMNavigation< ID, T > start( ID id ) throws FSMStartException
    {
        if( !stateMappings.containsKey( id ) || properties.current != null )
        {
            throw new FSMStartException();
        }
        final IState< ID, T > currentState = newState( id );
        properties.current = currentState;
        return this;
    }

    @Override
    public IAction< ID, T > goTo( ID id, IFSMTransition< T > transition ) throws FSMTransitionException, FSMMachineNotRunningException
    {
        final IState< ID, T > currentState = getCurrentState();
        if( currentState == null )
        {
            throw new FSMMachineNotRunningException();
        }

        final Set< ID > possibleNext = currentState.getPossibleNext();
        if( possibleNext.contains( id ) )
        {
            final IState< ID, T > nextState = newState( id );
            return IAction.wrap( currentState, nextState, transition, () -> {
                final T currentContent = currentState.getContent();
                final T nextContent = nextState.getContent();
                nextState.loadContent( transition.runTransition( currentContent, nextContent ) );
                properties.current = nextState;
            } );
        }
        else
        {
            throw new FSMTransitionException( currentState.getId(), id );
        }
    }

    @Override
    public IState< ID, T > getCurrentState()
    {
        return properties.current;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Inner Classes And Patterns
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class FSMProperties
    {
        private IState< ID , T > current;
    }
}

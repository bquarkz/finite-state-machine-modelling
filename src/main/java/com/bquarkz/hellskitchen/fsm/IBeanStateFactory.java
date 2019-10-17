package com.bquarkz.hellskitchen.fsm;

import java.io.Serializable;

@FunctionalInterface
public interface IBeanStateFactory< ID extends Serializable, T >
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static fields
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Methods
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Default Methods
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Contracts
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    T createEmpty( ID id );
}

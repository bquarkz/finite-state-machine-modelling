package com.bquarkz.hellskitchen.fsm;

import org.junit.Assert;
import org.junit.Test;

public class FiniteStateMachineTest
{
    @Test
    public void test()
    {
        final FiniteStateMachine< String, Bean > fsm = new FiniteStateMachine<>( Bean::new );
        fsm.load( new StateMapping<>( "01", "02" ) );
        fsm.load( new StateMapping<>( "02", "03" ) );
        fsm.load( new StateMapping<>( "03", "01" ) );

        final IFSMNavigation< String, Bean > navi = fsm.start( "01" );

        navi.goTo( "02" ).dispatch();
        Assert.assertNotNull( navi.getCurrentState() );
        Assert.assertEquals( "02", navi.getCurrentState().getId() );

        navi.goTo( "03" ).dispatch();
        Assert.assertNotNull( navi.getCurrentState() );
        Assert.assertEquals( "03", navi.getCurrentState().getId() );

        navi.goTo( "01" ).dispatch();
        Assert.assertNotNull( navi.getCurrentState() );
        Assert.assertEquals( "01", navi.getCurrentState().getId() );
    }
}
